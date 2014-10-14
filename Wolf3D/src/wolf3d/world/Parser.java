package wolf3d.world;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import wolf3d.components.behaviours.DoorBehaviour;
import wolf3d.components.behaviours.SpecialDoorBehaviour;
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
import engine.util.ServiceLocator;

/**
 * This class takes in the root directory for all files to be created
 * in the world, this follows a strict format to be run correctly
 *
 * The format is :
 * root>
 * 		ceilingTextures>
 * 		floorTexture>
 * 		textureFiles>
 * 		wallTextures>
 * 		ceilings.txt
 * 		doors.txt
 * 		floors.txt
 * 		specialDoors.txt
 * 		walls.txt
 * @author Sameer Magan
 *
 */
public class Parser {

	private String wallFilePath, doorFilePath, wallTexturePath, floorFilePath,
			floorTexturePath, ceilingTexturePath, ceilingFilePath, mapFilePath,
			specialDoorFilePath, textureFilesPath;

	private Cell[][] walls, doors, floor, ceiling, specialDoors;

	private Map<Integer, Cell[][]> textures = new HashMap<Integer, Cell[][]>();

	private Entity player;
	private World world;

	private int width, height;

	private int noTextures = 3;
	private int row, col;
	private int bottomY = -1;
	private int topY = 1;

	public Parser(String map) {
		this.mapFilePath = map;
		if (!map.substring(map.length() - 1).equals("/")) {
			map += "/";
		}
		this.wallFilePath = map + "walls.txt";
		this.doorFilePath = map + "doors.txt";
		this.ceilingFilePath = map + "ceilings.txt";
		this.floorFilePath = map + "floors.txt";
		this.specialDoorFilePath = map + "specialDoors.txt";
		this.wallTexturePath = map + "wallTextures/";
		this.floorTexturePath = map + "floorTextures/";
		this.ceilingTexturePath = map + "ceilingTextures/";
		this.textureFilesPath = map+ "textureFiles/";
		this.world = ServiceLocator.getService(World.class);
	}

	/**
	 * Parses walls file into a 2d array of Cells
	 */
	public void parseWallFileToArray() {
		walls = parseFileToArray(wallFilePath);
	}

	/**
	 * Parses doors file into a 2d array of Cells
	 */
	public void parseDoorFileToArray() {
		doors = parseFileToArray(doorFilePath);
	}

	/**
	 * Parses floors file into a 2d array of Cells
	 */
	public void parsefloorFileToArray() {
		floor = parseFileToArray(floorFilePath);
	}

	/**
	 * Parses ceilings file into a 2d array of Cells
	 */
	public void parseCeilingFileToArray() {
		ceiling = parseFileToArray(ceilingFilePath);
	}

	/**
	 * Parses SpecialDoors file into a 2d array of Cells
	 */
	public void parseSpecialDoorsFileToArray() {
		specialDoors = parseFileToArray(specialDoorFilePath);
	}

	public void createEntities(Entity player){
		parseWallFileToArray();
		parseDoorFileToArray();
		parsefloorFileToArray();
		parseCeilingFileToArray();
		parseSpecialDoorsFileToArray();
		parseTextures();
		createWalls();
		createFloor();
		createCeiling();
		createDoors(player);
		createSpecialDoors(player);
	}

	/**
	 * creates all walls in the world
	 */
	public void createCeiling() {
		String type = "Ceilings";
		float width = 2;
		float height = 2;
		float halfWidth = width / 2;
		float halfHeight = width / 2;
		for (row = 0; row < floor.length; row++) {
			for (col = 0; col < floor[row].length; col++) {
				float x = col * width + halfWidth;
				float z = row * height + halfHeight;
				Entity ceiling = addObject(type, "");
				if (ceiling != null) {
					ceiling.getTransform().translate(x, topY, z);
					ceiling.getTransform().pitch(Mathf.degToRad(-90));
				}

			}
		}
	}

	/**
	 * creates all walls in the world
	 */
	public void createWalls() {
		create3DObjects(walls, "Walls");
	}

	/**
	 * Creates all normal doors in the world
	 */
	public void createDoors(Entity player) {
		this.player = player;
		create3DObjects(doors, "Doors");
	}

	/**
	 * Creates all normal doors in the world
	 */
	public void createSpecialDoors(Entity player) {
		this.player = player;
		create3DObjects(specialDoors, "SpecialDoors");
	}

	/**
	 * Passes the textures into the map of textures
	 */
	public void parseTextures() {
		for (int i = 0; i <= 3; i++) {
			String filepath = textureFilesPath + Integer.toString(i) + ".txt";
			textures.put(i, parseFileToArray(filepath));
		}
	}

	/**
	 * passes file into 2d array of Cells
	 */
	private Cell[][] parseFileToArray(String filePath) {
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
	 */
	public void createFloor() {
		String type = "Floor";
		float width = 2;
		float height = 2;
		float halfWidth = width / 2;
		float halfHeight = width / 2;
		for (row = 0; row < floor.length; row++) {
			for (col = 0; col < floor[row].length; col++) {
				float x = col * width + halfWidth;
				float z = row * height + halfHeight;
				Entity floor = addObject(type, "");
				floor.getTransform().translate(x, bottomY, z);
				floor.getTransform().pitch(Mathf.degToRad(90));
			}
		}
	}

	/**
	 * Creates all the walls in the given world
	 */
	private void create3DObjects(Cell[][] processArray, String type) {
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
					Entity object = addObject(type, "North");
					object.getTransform().translate(x, 0, z);
					z += halfHeight;
				}
				if (processArray[row][col].hasEast()) {
					x += halfWidth;
					Entity object = addObject(type, "East");
					object.getTransform().translate(x, 0, z);
					object.getTransform().rotateY(Mathf.degToRad(90));
					x -= halfWidth;
				}
				if (processArray[row][col].hasSouth()) {
					z += halfHeight;
					Entity object = addObject(type, "South");
					object.getTransform().translate(x, 0, z);
					object.getTransform().rotateY(Mathf.degToRad(180));
					z -= halfHeight;
				}
				if (processArray[row][col].hasWest()) {
					x -= halfWidth;
					Entity object = addObject(type, "West");
					object.getTransform().translate(x, 0, z);
					object.getTransform().rotateY(Mathf.degToRad(-90));
					x += halfWidth;
				}
			}
		}
	}

	/**
	 * Adds an entity of the given type to the world
	 * @param type
	 *            the type of entity that what gets created
	 *
	 * @return the newly created entity or null if the type does not match a
	 *         valid input
	 */
	private Entity addObject(String type, String dir) {
		switch (type) {
		case "Walls":
			return addWall(dir);
		case "Doors":
			return addDoor(dir);
		case "SpecialDoors":
			return addSpecialDoor(dir);
		case "Floor":
			return addFloor();
		case "Ceilings":
			return addCeiling();
		}
		return null;
	}

	/**
	 * Adds a floor panel to the given world with a Texture, Mesh, and Material
	 *
	 * @return the newly created floor panel
	 */
	private Entity addCeiling() {
		Texture floorTex;
		floorTex = getCeilingTexture();
		if (floorTex == null) {
			return null;
		}
		Mesh mesh = Resources.getMesh("wall.obj");
		Entity floor = world.createEntity("floor");

		Material material = new Material(floorTex, Color.WHITE);
		floor.attachComponent(MeshFilter.class).setMesh(mesh);
		floor.attachComponent(MeshRenderer.class).setMaterial(material);
		return floor;
	}

	/**
	 * Adds a floor panel to the given world with a Texture, Mesh, and Material
	 *
	 * @return the newly created floor panel
	 */
	private Entity addFloor() {
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
	 * @return the newly created wall
	 */
	private Entity addWall(String dir) {
		// the texture for the wall
		Texture wallTex = getTexture(dir);
		// setting default if there is no texture specified in map
		if (wallTex == null) {
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
	 * @return the newly created door
	 */
	private Entity addSpecialDoor(String dir) {
		// the texture for the Door
		Texture doorTex = getTexture(dir);
		Mesh mesh = Resources.getMesh("wall.obj");
		Material material = new Material(doorTex, Color.WHITE);

		Entity door = world.createEntity("door");
		door.attachComponent(MeshFilter.class).setMesh(mesh);
		door.attachComponent(MeshRenderer.class).setMaterial(material);
		door.attachComponent(ProximitySensor.class).setTarget(player);
		door.attachComponent(SpecialDoorBehaviour.class);
		return door;
	}

	/**
	 * Adds a Door to the given world with a Texture, Mesh, and Material
	 *
	 * @return the newly created door
	 */
	private Entity addDoor(String dir) {
		// the texture for the Wall
		Texture doorTex = getTexture(dir);
		Mesh mesh = Resources.getMesh("wall.obj");
		Material material = new Material(doorTex, Color.WHITE);

		Entity door = world.createEntity("door");
		door.attachComponent(MeshFilter.class).setMesh(mesh);
		door.attachComponent(MeshRenderer.class).setMaterial(material);
		door.attachComponent(ProximitySensor.class).setTarget(player);
		door.attachComponent(DoorBehaviour.class);
		return door;
	}

	/**
	 * Gets the corresponding ceiling texture for the current position
	 *
	 * @return the corresponding ceiling texture or null if 0
	 */
	private Texture getCeilingTexture() {
		if (ceiling[row][col].getWalls() == 0) {
			return null;
		}
		String fname = ceilingTexturePath
				+ Integer.toString(ceiling[row][col].getWalls()) + ".png";
		return Resources.getTexture(fname, true);
	}

	/**
	 * Gets the corresponding floor texture for the current position
	 *
	 * @return the corresponding floor texture
	 */
	private Texture getFloorTexture() {
		String fname = floorTexturePath
				+ Integer.toString(floor[row][col].getWalls()) + ".png";
		return Resources.getTexture(fname, true);
	}

	/**
	 * Gets the texture of the current row and col from the textures map
	 *
	 * @param dir
	 *            the direction in which wall/door we are looking for
	 * @return the texture associated with the wall/door or null if it can not
	 *         find it
	 */
	private Texture getTexture(String dir) {
		for (Integer i : textures.keySet()) {
			if (dir.equals("North")) {
				if (textures.get(i)[row][col].hasNorth()) {
					String fname = wallTexturePath + Integer.toString(i)
							+ ".png";
					return Resources.getTexture(fname, true);
				}
			}
			if (dir.equals("East")) {
				if (textures.get(i)[row][col].hasEast()) {
					String fname = wallTexturePath + Integer.toString(i)
							+ ".png";
					return Resources.getTexture(fname, true);
				}
			}
			if (dir.equals("South")) {
				if (textures.get(i)[row][col].hasSouth()) {
					String fname = wallTexturePath + Integer.toString(i)
							+ ".png";
					return Resources.getTexture(fname, true);
				}
			}
			if (dir.equals("West")) {
				if (textures.get(i)[row][col].hasWest()) {
					String fname = wallTexturePath + Integer.toString(i)
							+ ".png";
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
		//puts all of specialDoors doors into doors array
		for(int row=0; row < doors.length; row++){
			for(int col=0; col < doors[row].length; col++){
				if(specialDoors[row][col].getWalls() != 0){
					doors[row][col].setWalls(specialDoors[row][col].getWalls());
				}

			}
		}
		return new WASDCollisions(walls, doors);
	}
}
