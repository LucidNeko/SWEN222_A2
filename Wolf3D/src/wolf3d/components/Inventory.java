package wolf3d.components;

import java.util.ArrayList;
import java.util.List;

import engine.components.Component;
import engine.core.Entity;

/**
 * This class is responsible for holding all of the items that a player has
 * picked up
 * 
 * @author Sameer Magan
 *
 */
public class Inventory extends Component {
	private List<Entity> items = new ArrayList<Entity>();

	private int carryWeight = 100;

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

	public int getCarryWeight() {
		return carryWeight;
	}

	public boolean reduceCarryWeight(int weight) {
		if ((carryWeight - weight) >= 0) {
			carryWeight -= weight;
			return true;
		}
		return false;
	}

	public void releaseWeight(int weight) {
		carryWeight += weight;
	}

}
