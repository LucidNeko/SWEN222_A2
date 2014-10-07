package wolf3d.world;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import wolf3d.components.behaviours.AddAnimation;
import wolf3d.components.behaviours.WASDCollisions;
import wolf3d.components.sensors.ProximitySensor;
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

	private String wallFilePath, doorFilePath, textureFilePath, floorFilePath, floorTexturePath;

	private Cell[][] walls, doors, floor;

	private Map<Integer, Cell[][]> textures = new HashMap<Integer, Cell[][]>();

	private Entity player;

	private int width, height;

	private int row, col;
	private int bottomY = -1;

	public Parser(String wallFilePath, String doorFilePath) {
		this.wallFilePath = wallFilePath;
		this.doorFilePath = doorFilePath;
		this.textureFilePath = "wallTextures/";
		this.floorFilePath = "floors.txt";
		this.floorTexturePath = "floorTextures/";
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
	 * Parses floors file into a 2d array of Cells
	 */
	public void passfloorFileToArray() {
		floor = passFileToArray(floorFilePath);
	}

	/**
	 * creates all walls in the world
	 *
	 * @param world
	 *            the world in which the walls are added to
	 */
	public void createWalls(World world) {
		create3DObjects(world, walls, "Walls");
	}

	/**
	 * Creates all doors in the world
	 *
	 * @param world
	 *            the world in which the doors are added to
	 */
	public void createDoors(World world, Entity player) {
		this.player = player;
		create3DObjects(world, doors, "Doors");
	}

	/**
	 * Passes the textures into the map of textures
	 */
	public void passTextures() {
		for (int i = 0; i < 3; i++) {
			String filepath = "textureFiles/" + Integer.toString(i) + ".txt";
			textures.put(i, passFileToArray(filepath));
		}
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
	public void createFloor(World world){
		String type = "Floor";
		float width = 2;
		float height = 2;
		float halfWidth = width/2;
		float halfHeight = width/2;
		for(row = 0; row < floor.length; row++){
			for(col = 0; col < floor[row].length; col++){
				float x = col * width + halfWidth;
				float z = row * height + halfHeight;
				Entity floor = addObject(world, type, "");
				floor.getTransform().translate(x, bottomY, z);
				floor.getTransform().pitch(Mathf.degToRad(90));
			}
		}
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
		for (row = 0; row < processArray.length; row++) {
			for (col = 0; col < processArray[row].length; col++) {
				float x = col * width;
				float z = row * height;
				float halfWidth = width / 2;
				float halfHeight = height / 2;
				x += width / 2;
				z += height / 2;
				if (processArray[row][col].hasNorth()) {
					z -= halfHeight;
					Entity object = addObject(world, type, "North");
					object.getTransform().translate(x, 0, z);
					z += halfHeight;
				}
				if (processArray[row][col].hasEast()) {
					x += halfWidth;
					Entity object = addObject(world, type, "East");
					object.getTransform().translate(x, 0, z);
					object.getTransform().rotateY(Mathf.degToRad(90));
					x -= halfWidth;
				}
				if (processArray[row][col].hasSouth()) {
					z += halfHeight;
					Entity object = addObject(world, type, "South");
					object.getTransform().translate(x, 0, z);
					object.getTransform().rotateY(Mathf.degToRad(180));
					z -= halfHeight;
				}
				if (processArray[row][col].hasWest()) {
					x -= halfWidth;
					Entity object = addObject(world, type, "West");
					object.getTransform().translate(x, 0, z);
					object.getTransform().rotateY(Mathf.degToRad(-90));
					x += halfWidth;
				}
			}
		}
	}

	/**
	 * Adds an entity of the given type to the world
	 *
	 * @param world
	 *            the world for the entity to be added to
	 * @param type
	 *            the type of entity that what gets created
	 * @return the newly created entity or null if the type does not match a
	 *         valid input
	 */
	private Entity addObject(World world, String type, String dir) {
		switch (type) {
		case "Walls":
			return addWall(world, dir);
		case "Doors":
			return addDoor(world, dir);
		case "Floor":
			return addFloor(world);
		}
		return null;
	}

	/**
	 * Adds a floor panel to the given world with a Texture, Mesh, and Material
	 *
	 * @param world
	 *            the world for the floor to be added to
	 * @return the newly created floor panel
	 */
	private Entity addFloor(World world){
		Texture floorTex = getFloorTexture();
		Mesh mesh = Resources.getMesh("wall.obj");
		Entity floor = world.createEntity("floor");

		Material material = new Material(floorTex, Color.WHITE);
		floor.attachComponent(MeshFilter.class).setMesh(mesh);
		floor.attachComponent(MeshRenderer.class).setMaterial(material);
		return floor;
	}

	/**
	 * Adds a Wall to the given world with a Texture, Mesh, and Material
	 *
	 * @param world
	 *            the world for the wall to be added to
	 * @return the newly created wall
	 */
	private Entity addWall(World world, String dir) {
		// the texture for the wall
		Texture wallTex = getTexture(dir);
		//setting default if there is no texture specified in map
		if(wallTex == null){
			wallTex = Resources.getTexture("debug_wall.png", true);
		}

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
	private Entity addDoor(World world, String dir) {
		// the texture for the wall
//		Texture doorTex = Resources.getTexture("1.png", true);
		Texture doorTex = getTexture(dir);
		Mesh mesh = Resources.getMesh("wall.obj");
		Material material = new Material(doorTex, Color.WHITE);

		Entity door = world.createEntity("door");
		door.attachComponent(MeshFilter.class).setMesh(mesh);
		door.attachComponent(MeshRenderer.class).setMaterial(material);
		door.attachComponent(ProximitySensor.class).setTarget(player);
		door.attachComponent(AddAnimation.class);
		return door;
	}

	/**
	 * Gets the corresponding floor texture for the current position
	 * @return the corresponding floor texture
	 */
	private Texture getFloorTexture(){
		String fname = floorTexturePath +Integer.toString(floor[row][col].getWalls()) + ".png";
		return Resources.getTexture(fname, true);
	}

	/**
	 * Gets the texture of the current row and col from the textures map
	 * @param dir the direction in which wall/door we are looking for
	 * @return the texture associated with the wall/door or null if it can not find it
	 */
	private Texture getTexture(String dir) {
		for (Integer i : textures.keySet()) {
			if (dir.equals("North")) {
				if (textures.get(i)[row][col].hasNorth()) {
					String fname = textureFilePath +Integer.toString(i) + ".png";
					return Resources.getTexture(fname, true);
				}
			}
			if (dir.equals("East")) {
				if (textures.get(i)[row][col].hasEast()) {
					String fname = textureFilePath+Integer.toString(i) + ".png";
					return Resources.getTexture(fname, true);
				}
			}
			if (dir.equals("South")) {
				if (textures.get(i)[row][col].hasSouth()) {
					String fname = textureFilePath+Integer.toString(i) + ".png";
					return Resources.getTexture(fname, true);
				}
			}
			if (dir.equals("West")) {
				if (textures.get(i)[row][col].hasWest()) {
					String fname = textureFilePath+Integer.toString(i) + ".png";
					return Resources.getTexture(fname, true);
				}
			}
		}
		return null;
	}

	/**
	 * Returns a WASDCollion object with the walls array initialised
	 *
	 * @return Returns a WASDCollion object with the walls array initialised
	 */
	public WASDCollisions getWallCollisionComponent() {
		return new WASDCollisions(walls, doors);
	}
}
