package wolf3d.components.behaviours;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wolf3d.components.Inventory;
import wolf3d.components.Weight;
import wolf3d.components.sensors.ProximitySensor;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Component;
import engine.core.Entity;
import engine.core.World;
import engine.input.Keyboard;

/**
 * This Class is a Component to allow players to pickup and drop items in the
 * world
 * 
 * @author Sameer Magan
 *
 */
public class PickUp extends Behaviour {
	World world;

	public PickUp(World world) {
		this.world = world;
	}

	/**
	 * Picks up the entity attached to this component and adds it to the
	 * targeted players Inventory
	 * 
	 * @return true if entity is picked up false if not
	 */
	public boolean pickUpItem() {
		Entity item = getOwner();
		Entity player = item.getComponent(ProximitySensor.class).getTarget();
		Inventory inventory = player.getComponent(Inventory.class);
		if(item.getComponent(Weight.class) == null){
			System.out.println("Item " + item.getName() +" needs a Weight component to be picked up!");
			return false;
		}
		//gets weight and checks if weight does not exceed the players carry amt.
		int itemWeight = item.getComponent(Weight.class).getWeight();
		if (inventory.reduceStrength(itemWeight)) {
			inventory.addItem(item);
			setChanged();
			return world.destroyEntity(item.getID());
		}
		System.out.printf("\nNot stong enough to pick up %s item, try dropping an item first", item.getName());
		return false;
	}

	@Override
	public void update(float delta) {
		requires(ProximitySensor.class);
		requires(Weight.class);
		// Checks if the proximity sensor attached to the owner of this
		// component
		// is triggered, ie. if the player is close enough to the item to be
		// picked up
		if (getOwner().getComponent(ProximitySensor.class).isTriggered()) {
			if (Keyboard.isKeyDownOnce(KeyEvent.VK_E)) {
				pickUpItem();
			}
		}

	}

}
