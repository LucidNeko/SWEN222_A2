package wolf3d.components;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.common.Vec3;
import engine.components.Component;
import engine.components.Sensor;
import engine.components.Transform;
import engine.core.Entity;
import engine.core.World;
import engine.input.Keyboard;

/**
 * This Class is a Component to allow players to pickup and
 * drop items in the world
 * @author Sameer Magan
 *
 */
public class PickUp extends Sensor{
	Map<Integer, Entity> items = new HashMap<Integer, Entity>();
	World world;

	public PickUp(World world) {
		this.world = world;
	}

	/**
	 * picks up the item of the given id
	 * @param id the id of the entity to be picked up
	 * @return true if entity exists in world false if not
	 */
	public boolean pickUpItem(int id) {
		items.put(id, world.getEntity(id));
		return world.destroyEntity(id);
	}

	/**
	 * Drops the item of the given id at current position
	 * @param id the id of the entity to be dropped
	 * @return true if entity exist in items false if not
	 */
	public boolean dropItem(int id) {
		if(items.get(id) == null){
			return false;
		}
		Entity item = items.get(id);
		Entity entity = world.createEntity(id, item.getName());
		List<Component> itemComponents = item.getComponents(Component.class);
		// attaching components to new entity
		for (Component c : itemComponents) {
			entity.attachComponent(c);
		}
		Vec3 pos = this.getOwner().getTransform().getPosition();
		entity.getTransform().setPosition(pos.getX(), pos.getY(), pos.getZ());
		return items.remove(id) != null;
	}

	/**
	 * @return the items
	 */
	public Map<Integer, Entity> getItems() {
		return items;
	}

	@Override
	public boolean isTriggered() {
		if(world == null){
			return false;
		}
		if(Keyboard.isKeyDown(KeyEvent.VK_P) || Keyboard.isKeyDown(KeyEvent.VK_F)){
			return true;
		}
		return false;
	}

}
