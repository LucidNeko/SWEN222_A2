package wolf3d.components;

import java.util.ArrayList;
import java.util.List;

import engine.components.Component;
import engine.core.Entity;
/**
 * This class is responsible for holding all of the items that a player
 * has picked up
 * @author Sameer Magan
 *
 */
public class Inventory extends Component {
	List<Entity> items = new ArrayList<Entity>();

	public List<Entity> getItems() {
		return items;
	}

	public boolean addItem(Entity item){
		return items.add(item);
	}

	public boolean removeEntity(Entity item){
		return items.remove(item);
	}
	
	public boolean contains(Entity item){
		return items.contains(item);
	}


}
