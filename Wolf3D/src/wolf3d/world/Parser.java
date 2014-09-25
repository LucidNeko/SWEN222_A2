package wolf3d.world;

import java.io.File;
import java.io.IOException;
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

	private String filePath;

	private Cell[][] walls;

	private int width, height;

	private int leftX = -1;
	private int rightX = 1;
	private int bottomY = -1;
	private int topY = 1;
	private int tileX = 1;
	private int tileY = 1;
	
	//These need to be changed to 4, 5 if running on MAC OS
	//Otherwise should be 2, 3
	//^v^v^v^v^lol^v^v^v^v^
	private int wallTexture = 4;
	private int floorTexture = 5;

	public Parser(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * passes file into 2d array of ints
	 */
	public void passFileToArray() {
		File file = new File(filePath);

		try {
			Scanner sc = new Scanner(file);
			int total;

			width = sc.nextInt();
			height = sc.nextInt();
			int cur=0;
			walls = new Cell[height][width];
			int col = 0;
			while (sc.hasNext()) {
				if(2*height==col*2){
					total=sc.nextInt();
					break;
				}
				String line = sc.next();
				char[] row = line.toCharArray();
				int rowCheck = sc.nextInt();
				for (int i = 0; i < row.length; i++) {
					int temp = Integer.decode("0x" + row[i]);
					walls[col][i] = new Cell(temp);
				}
				col++;
			}
			sc.close();
		} catch (IOException e) {
			System.out.println("Something went wrong with the scanner!!!");
		}
	}

	/**
	 * Creates the floor for the given world
	 * @param world the world that the floor will be added to
	 */
	public void createFloor(World world){
		Texture floorTex = Resources.getTexture("debug_floor.png", true);
		Mesh mesh = Resources.getMesh("wall.obj");
		Entity floor = world.createEntity("floor");
		floor.attachComponent(MeshFilter.class).setMesh(mesh);
		floor.attachComponent(MeshRenderer.class).setMaterial(new Material(floorTex)); 
		//TODO: Need to make a Mesh creator that can make a basic Mesh from (width, Height) 
//		floor.attachComponent(new TextureRenderer(floorTexture, -width, -height, width, height, width, height));
		floor.getTransform().translate(width, bottomY, height);
		floor.getTransform().pitch(Mathf.degToRad(90));
	}

	/**
	 * Creates all the walls in the given world
	 * @param world the world that the walls will be added to
	 */
	public void createWalls(World world) {
		//+x-->
		//z
		//|
		//V
		
		Texture wallTex = Resources.getTexture("debug_wall.png", true);
		Mesh mesh = Resources.getMesh("wall.obj");
		Material material = new Material(wallTex, Color.WHITE);
		
		float width = 2;
		float height = 2;
		for(int row=0; row<walls.length; row++){
			for(int col=0; col<walls[row].length; col++){
				float x = col*width;
				float z = row*height;
				float halfWidth = width/2;
				float halfHeight = height/2;
				x += width/2;
				z += height/2;
				if(walls[row][col].hasNorth()){
					z -= halfHeight;
					Entity wall = world.createEntity("wall");
					wall.attachComponent(MeshFilter.class).setMesh(mesh);
					wall.attachComponent(MeshRenderer.class).setMaterial(material);
//					wall.attachComponent(new TextureRenderer(wallTexture, leftX, bottomY, rightX, topY, tileX, tileY));
					wall.getTransform().translate(x, 0, z);
					z += halfHeight;
				}
				if(walls[row][col].hasEast()) {
					x += halfWidth;
					Entity wall = world.createEntity("wall");
					wall.attachComponent(MeshFilter.class).setMesh(mesh);
					wall.attachComponent(MeshRenderer.class).setMaterial(material);
//					wall.attachComponent(new TextureRenderer(wallTexture, leftX, bottomY, rightX, topY, tileX, tileY));
					wall.getTransform().translate(x, 0, z);
					wall.getTransform().rotateY(Mathf.degToRad(90));
					x -= halfWidth;
				}
				if(walls[row][col].hasSouth()){
					z += halfHeight;
					Entity wall = world.createEntity("wall");
					wall.attachComponent(MeshFilter.class).setMesh(mesh);
					wall.attachComponent(MeshRenderer.class).setMaterial(material);
//					wall.attachComponent(new TextureRenderer(wallTexture, leftX, bottomY, rightX, topY, tileX, tileY));
					wall.getTransform().translate(x, 0, z);
					wall.getTransform().rotateY(Mathf.degToRad(180));
					z-= halfHeight;
				}
				if(walls[row][col].hasWest()){
					x -= halfWidth;
					Entity wall = world.createEntity("wall");
					wall.attachComponent(MeshFilter.class).setMesh(mesh);
					wall.attachComponent(MeshRenderer.class).setMaterial(material);
//					wall.attachComponent(new TextureRenderer(wallTexture, leftX, bottomY, rightX, topY, tileX, tileY));
					wall.getTransform().translate(x, 0, z);
					wall.getTransform().rotateY(Mathf.degToRad(-90));
					x += halfWidth;
				}
			}
		}
	}

	/**
	 * Returns a WASDCollion object with the walls array initialised
	 * @return Returns a WASDCollion object with the walls array initialised
	 */
	public WASDCollisions getWallCollisionComponent(){
		return new WASDCollisions(walls);
	}
}
