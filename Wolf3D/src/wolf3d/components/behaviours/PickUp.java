package wolf3d.components.behaviours;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wolf3d.components.Inventory;
import wolf3d.components.sensors.ProximitySensor;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Component;
import engine.core.Entity;
import engine.core.World;
import engine.input.Keyboard;

/**
 * This Class is a Component to allow players to pickup and
 * drop items in the world
 * @author Sameer Magan
 *
 */
public class PickUp extends Behaviour{
	World world;

	public PickUp(World world) {
		this.world = world;
	}

	/**
	 * Picks up the entity attached to this component and
	 * adds it to the targeted players Inventory
	 * @return true if entity exists in world false if not
	 */
	public boolean pickUpItem() {
		Entity item = getOwner();
		Entity player = item.getComponent(ProximitySensor.class).getTarget();
		Inventory inventory = player.getComponent(Inventory.class);
		inventory.addItem(item);
		return world.destroyEntity(item.getID());
	}

	@Override
	public void update(float delta) {
		requires(ProximitySensor.class);
		//Checks if the proximity sensor attached to the owner of this component
		//is triggered, ie. if the player is close enough to the item to be picked up
		if(getOwner().getComponent(ProximitySensor.class).isTriggered()){
			if(Keyboard.isKeyDownOnce(KeyEvent.VK_E)){
				pickUpItem();
			}
		}

	}

}
