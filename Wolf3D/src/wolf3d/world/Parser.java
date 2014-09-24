package wolf3d.world;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.media.opengl.GL2;

import wolf3d.common.Mathf;
import wolf3d.components.renderers.Renderer;
import wolf3d.components.renderers.TextureRenderer;
import wolf3d.core.Entity;
import wolf3d.core.World;

/**
 * This is responsible for turning the text file containing all the walls for
 * the world into objects and creating a 2D array which can be used for
 * collision detection
 *
 * @author magansame
 *
 */
public class Parser {

	private String filePath;

	private int[][] walls;
	private short northMask = 8;
	private short eastMask = 4;
	private short southMask = 2;
	private short westMask = 1;
	
	private int width, height;

	private int leftX = -1;
	private int rightX = 1;
	private int bottomY = -1;
	private int topY = 1;
	private int tileX = 1;
	private int tileY = 1;

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
			walls = new int[height][width];
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
					walls[col][i] = Integer.decode("0x" + row[i]);
				}
				col++;
				sc.close();
			}

		} catch (IOException e) {
			System.out.println("Something went wrong with the scanner!!!");
		}
	}

	/**
	 * Creates the floor for the given world
	 * @param world the world that the floor will be added to
	 */
	public void createFloor(World world){
		Entity floor = world.createEntity("floor");

		//TODO need to fix this
		floor.attachComponent(new TextureRenderer(3, width, height, width, height, tileX, tileY));
		floor.getTransform().translate(0, bottomY, 0);
		floor.getTransform().pitch(Mathf.degToRad(90));
	}

	/**
	 * Creates all the walls in the given world
	 * @param world the world that the walls will be added to
	 */
	public void createWalls(World world){
		for(int i=0; i<walls.length; i++){
			for(int j=0; j<walls[i].length; j++){
				if(hasNorth(walls[i][j])){
					Entity wall = world.createEntity("wall");
					//TODO fix the magic 2 number to match texture
					wall.attachComponent(new TextureRenderer(2, leftX, bottomY, rightX, topY, tileX, tileY));
					wall.getTransform().strafe(j+rightX*(j+1));
					wall.getTransform().walk(i+rightX*(i));
				}
				if(hasEast(walls[i][j])){
					Entity wall = world.createEntity("wall");
					//TODO fix the magic 2 number to match texture
					wall.attachComponent(new TextureRenderer(2, leftX, bottomY, rightX, topY, tileX, tileY));
					wall.getTransform().yaw(Mathf.degToRad(90));

					wall.getTransform().strafe(i+rightX*(i+1));
					wall.getTransform().walk(-(j+rightX*(j+2)));

				}
				if(hasWest(walls[i][j])){
					Entity wall = world.createEntity("wall");
					//TODO fix the magic 2 number to match texture
					wall.attachComponent(new TextureRenderer(2, leftX, bottomY, rightX, topY, tileX, tileY));
					wall.getTransform().yaw(Mathf.degToRad(-90));

					wall.getTransform().strafe(-(i+rightX*(i+1)));
					wall.getTransform().walk(j+rightX*(j*0.5f));

				}
				if(hasSouth(walls[i][j])){
					Entity wall = world.createEntity("wall");
					//TODO fix the magic 2 number to match texture
					wall.attachComponent(new TextureRenderer(2, leftX, bottomY, rightX, topY, tileX, tileY));
					wall.getTransform().yaw(Mathf.degToRad(180));

					wall.getTransform().strafe(-(j+rightX*(j+1)));
					wall.getTransform().walk(-(i+rightX*(i+2)));
				}
			}
		}
	}

	public boolean hasNorth(int x){
		if((x&northMask)==northMask)return true;
		return false;
	}

	public boolean hasEast(int x){
		if((x&eastMask)==eastMask)return true;
		return false;
	}

	public boolean hasSouth(int x){
		if((x&southMask)==southMask)return true;
		return false;
	}

	public boolean hasWest(int x){
		if((x&westMask)==westMask)return true;
		return false;
	}

	public static void main(String[] args) {
		Parser p = new Parser("Map.txt");
		p.passFileToArray();
	}
}
