package wolf3d.world;

/**
 * This class holds an int value between 0 and 15 and contains masks and
 * methods for decoding the int so that we know what walls the int
 * represents
 * @author Sameer Magan
 *
 */
public class Cell {
	//masks for each directional wall
	private short northMask = 8;
	private short eastMask = 4;
	private short southMask = 2;
	private short westMask = 1;

	private int walls;

	public Cell(int walls){
		this.walls = walls;
	}

	/**
	 * checks if given x contains a north wall
	 * @param walls the integer that you want to check
	 * @return true if it does contain north wall else false
	 */
	public boolean hasNorth(){
		if((walls&northMask)==northMask)return true;
		return false;
	}

	/**
	 * checks if given x contains a East wall
	 * @param walls the integer that you want to check
	 * @return true if it does contain East wall else false
	 */
	public boolean hasEast(){
		if((walls&eastMask)==eastMask)return true;
		return false;
	}

	/**
	 * checks if given x contains a South wall
	 * @param walls the integer that you want to check
	 * @return true if it does contain South wall else false
	 */
	public boolean hasSouth(){
		if((walls&southMask)==southMask)return true;
		return false;
	}

	/**
	 * checks if given x contains a South wall
	 * @param walls the integer that you want to check
	 * @return true if it does contain South wall else false
	 */
	public boolean hasWest(){
		if((walls&westMask)==westMask)return true;
		return false;
	}

}
