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
	private List<Integer> items = new ArrayList<Integer>();

	public List<Integer> getItems() {
		return items;
	}

	public boolean addItem(int item) {
		return items.add(item);
	}

	public Integer get(int index) {
		if (index >= items.size()) {
			return null;
		}
		return items.get(index);
	}

	public boolean removeEntity(Integer item) {
		return items.remove(item);
	}

	public boolean contains(Integer item) {
		return items.contains(item);
	}


}
