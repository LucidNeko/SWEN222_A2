package wolf3d.world;

import java.io.InputStream;
import java.util.Scanner;

import wolf3d.components.behaviours.WASDCollisions;
import engine.common.Color;
import engine.common.Mathf;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.core.Entity;
import engine.core.World;
import engine.texturing.Material;
import engine.texturing.Mesh;
import engine.texturing.Texture;
import engine.util.Resources;

/**
 * This is responsible for turning the text file containing all the walls for
 * the world into objects and creating a 2D array which can be used for
 * collision detection
 *
 * @author Sameer Magan
 *
 */
public class Parser {

	private String wallFilePath, doorFilePath;

	private Cell[][] walls;
	private Cell[][] doors;

	private int width, height;

	private int leftX = -1;
	private int rightX = 1;
	private int bottomY = -1;
	private int topY = 1;
	private int tileX = 1;
	private int tileY = 1;

	public Parser(String wallFilePath, String doorFilePath) {
		this.wallFilePath = wallFilePath;
		this.doorFilePath = doorFilePath;
	}

	/**
	 * Parses walls file into a 2d array of Cells
	 */
	public void passWallFileToArray() {
		walls = passFileToArray(wallFilePath);
	}
	
	/**
	 * Parses doors file into a 2d array of Cells
	 */
	public void passDoorFileToArray() {
		doors = passFileToArray(doorFilePath);
	}

	/**
	 * creates all walls in the world
	 * @param world the world in which the walls are added to
	 */
	public void createWalls(World world) {
		create3DObjects(world, walls, "Walls");
	}

	/**
	 * Creates all doors in the world
	 * @param world the world in which the doors are added to
	 */
	public void createDoors(World world) {
		create3DObjects(world, doors, "Doors");
	}

	/**
	 * passes file into 2d array of Cells
	 */
	private Cell[][] passFileToArray(String filePath) {
		InputStream in = Resources.getInputStream(filePath);

		Scanner sc = new Scanner(in);
		int total;

		width = sc.nextInt();
		height = sc.nextInt();
		// int cur = 0;
		Cell[][] processArray = new Cell[height][width];
		int col = 0;
		while (sc.hasNext()) {
			if (2 * height == col * 2) {
				total = sc.nextInt();
				break;
			}
			String line = sc.next();
			char[] row = line.toCharArray();
			int rowCheck = sc.nextInt();
			for (int i = 0; i < row.length; i++) {
				int temp = Integer.decode("0x" + row[i]);
				processArray[col][i] = new Cell(temp);
			}
			col++;
		}
		sc.close();
		return processArray;
	}

	/**
	 * Creates the floor for the given world
	 *
	 * @param world
	 *            the world that the floor will be added to
	 */
	public void createFloor(World world) {
		Texture floorTex = Resources.getTexture("debug_floor.png", true);
		Mesh mesh = Resources.getMesh("wall.obj");
		Entity floor = world.createEntity("floor");
		floor.attachComponent(MeshFilter.class).setMesh(mesh);
		floor.attachComponent(MeshRenderer.class).setMaterial(
				new Material(floorTex));
		// TODO: Need to make a Mesh creator that can make a basic Mesh from
		// (width, Height)
		// floor.attachComponent(new TextureRenderer(floorTexture, -width,
		// -height, width, height, width, height));
		floor.getTransform().translate(width, bottomY, height);
		floor.getTransform().pitch(Mathf.degToRad(90));
	}

	/**
	 * Creates all the walls in the given world
	 *
	 * @param world
	 *            the world that the walls will be added to
	 */
	private void create3DObjects(World world, Cell[][] processArray, String type) {
		// +x-->
		// z
		// |
		// V
		float width = 2;
		float height = 2;
		for (int row = 0; row < processArray.length; row++) {
			for (int col = 0; col < processArray[row].length; col++) {
				float x = col * width;
				float z = row * height;
				float halfWidth = width / 2;
				float halfHeight = height / 2;
				x += width / 2;
				z += height / 2;
				if (processArray[row][col].hasNorth()) {
					z -= halfHeight;
					Entity object = addObject(world, type);
					object.getTransform().translate(x, 0, z);
					z += halfHeight;
				}
				if (processArray[row][col].hasEast()) {
					x += halfWidth;
					Entity object = addObject(world, type);
					object.getTransform().translate(x, 0, z);
					object.getTransform().rotateY(Mathf.degToRad(90));
					x -= halfWidth;
				}
				if (processArray[row][col].hasSouth()) {
					z += halfHeight;
					Entity object = addObject(world, type);
					object.getTransform().translate(x, 0, z);
					object.getTransform().rotateY(Mathf.degToRad(180));
					z -= halfHeight;
				}
				if (processArray[row][col].hasWest()) {
					x -= halfWidth;
					Entity object = addObject(world, type);
					object.getTransform().translate(x, 0, z);
					object.getTransform().rotateY(Mathf.degToRad(-90));
					x += halfWidth;
				}
			}
		}
	}
	
	/**
	 * Adds an entity of the given type to the world
	 * @param world the world for the entity to be added to
	 * @param type the type of entity that what gets created
	 * @return the newly created entity or null if the type does not match a valid input
	 */
	private Entity addObject(World world, String type){
		switch(type){
		case "Walls":
			return addWall(world);
		case "Doors":
			return addDoor(world);
		}
		return null;
	}

	/**
	 * Adds a Wall to the given world with a Texture, Mesh, and Material
	 * 
	 * @param world
	 *            the world for the wall to be added to
	 * @return the newly created wall
	 */
	private Entity addWall(World world) {
		// the texture for the wall
		Texture wallTex = Resources.getTexture("debug_wall.png", true);
		Mesh mesh = Resources.getMesh("wall.obj");
		Material material = new Material(wallTex, Color.WHITE);

		Entity wall = world.createEntity("wall");
		wall.attachComponent(MeshFilter.class).setMesh(mesh);
		wall.attachComponent(MeshRenderer.class).setMaterial(material);
		return wall;
	}

	/**
	 * Adds a Door to the given world with a Texture, Mesh, and Material
	 * 
	 * @param world
	 *            the world for the door to be added to
	 * @return the newly created door
	 */
	private Entity addDoor(World world) {
		// the texture for the wall
		Texture wallTex = Resources.getTexture("1.png", true);
		Mesh mesh = Resources.getMesh("wall.obj");
		Material material = new Material(wallTex, Color.WHITE);

		Entity door = world.createEntity("door");
		door.attachComponent(MeshFilter.class).setMesh(mesh);
		door.attachComponent(MeshRenderer.class).setMaterial(material);
		return door;
	}

	/**
	 * Returns a WASDCollion object with the walls array initialised
	 *
	 * @return Returns a WASDCollion object with the walls array initialised
	 */
	public WASDCollisions getWallCollisionComponent() {
		return new WASDCollisions(walls);
	}
}
