package wolf3d.world;

import engine.util.Service;

public class MapData implements Service {

	private Cell[][] walls;
	private Cell[][] doors;

	public MapData(Cell[][] walls, Cell[][] doors){
		this.walls = walls;
		this.doors = doors;
	}

	/**
	 * Gets the wall for the given row and col
	 * @param row the row to be referenced
	 * @param col the col to be referenced
	 * @return the int representing wall at the given position
	 */
	public Cell getWall(int row, int col){
		return walls[row][col];
	}

	/**
	 * @return the width of the walls array
	 */
	public int getWallsWidth(){
		return walls[0].length;
	}

	/**
	 *
	 * @return the height of the walls array
	 */
	public int getWallsHeight(){
		return walls.length;
	}

	/**
	 * Gets the door for the given row and col
	 * @param row the row to be referenced
	 * @param col the col to be referenced
	 * @return the int representing door at the given position
	 */
	public Cell getDoors(int row, int col){
		return doors[row][col];
	}
}
