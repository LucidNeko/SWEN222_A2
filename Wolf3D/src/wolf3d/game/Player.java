package wolf3d.game;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a player in the game world
 * @author Sameer Magan
 *
 */
public class Player {
	private Vector pos;
	private List<Item> items = new ArrayList<Item>();
	
	public Player(int x, int y, int z, float rotation){
		pos = new Vector(x, y, z, rotation);
	}
	
	/**
	 * Adds an item to the items list
	 * @param item to be added
	 * @return true if successful false if not
	 */
	public boolean addItem(Item item){
		return items.add(item);
	}
}
