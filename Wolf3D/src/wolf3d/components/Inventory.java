package wolf3d.components;

import java.util.ArrayList;
import java.util.List;

import engine.components.Component;
import engine.core.Entity;

/**
 * This class is responsible for holding all of the items that a player has
 * picked up and maintaining a strength so that a player is only strong enough
 * to hold a certain amount of weight
 * 
 * @author Sameer Magan
 *
 */
public class Inventory extends Component {
	private List<Entity> items = new ArrayList<Entity>();

	private int strength = 100;

	public List<Entity> getItems() {
		return items;
	}

	public boolean addItem(Entity item) {
		return items.add(item);
	}

	public Entity get(int index) {
		if (index >= items.size()) {
			return null;
		}
		return items.get(index);
	}

	public boolean removeEntity(Entity item) {
		return items.remove(item);
	}

	public boolean contains(Entity item) {
		return items.contains(item);
	}

	public int getStrength() {
		return strength;
	}

	/**
	 * Reduces the carryWeight by the given amount
	 * 
	 * @param weight the amount to reduce by
	 * @return true if it can be reduced, false if not
	 */
	public boolean reduceStrength(int weight) {
		if ((strength - weight) >= 0) {
			strength -= weight;
			return true;
		}
		return false;
	}

	/**
	 * Releases the given amount of weight to Strength
	 * @param weight the amount to be released
	 */
	public void releaseWeight(int weight) {
		strength += weight;
	}

}
