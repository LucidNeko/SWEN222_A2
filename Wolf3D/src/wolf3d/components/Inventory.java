package wolf3d.components;

import java.util.ArrayList;
import java.util.List;

import engine.components.Component;
import engine.core.Entity;

/**
 * This class is responsible for holding all of the items that a player has
 * picked up
 * 
 * @author Sameer Magan 300223776
 *
 */
public class Inventory extends Component {
	private List<Integer> items = new ArrayList<Integer>();

	/**
	 * @return the items
	 */
	public List<Integer> getItems() {
		return items;
	}

	/**
	 * Appends the specified element to the end of this list (optional operation).
	 * @param item element to be appended to this list
	 * @return true (as specified by Collection.add)
	 */
	public boolean addItem(int item) {
		return items.add(item);
	}

	/**
	 * Returns the element at the specified position in this list.
	 * @param index the index of the element to be returned
	 * @return null if index is out of bounds, the Integer associated with the given index
	 */
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
