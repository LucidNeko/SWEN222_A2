package wolf3d.components.behaviours;

import java.awt.event.KeyEvent;

import wolf3d.components.Inventory;
import wolf3d.components.Strength;
import wolf3d.components.Weight;
import wolf3d.components.behaviours.animations.Rotate;
import wolf3d.components.sensors.ProximitySensor;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.core.Entity;
import engine.core.World;
import engine.input.Keyboard;
import engine.util.Messenger;
import engine.util.ServiceLocator;

/**
 * This Class is a Component to allow players to pickup and drop items in the
 * world
 *
 * @author Sameer Magan 300223776
 *
 */
public class PickUp extends Behaviour {
	World world = ServiceLocator.getService(World.class);
	Vec3 outBounds = new Vec3(-100, 0, -100);

	/**
	 * Picks up the entity attached to this component and adds it to the
	 * targeted players Inventory
	 *
	 * @return true if entity is picked up false if not
	 */
	public boolean pickUpItem() {
		Messenger messenger = ServiceLocator.getService(Messenger.class);
		Entity item = getOwner();
		Entity player = item.getComponent(ProximitySensor.class).getTarget();
		Inventory inventory = player.getComponent(Inventory.class);
		if(item.getComponent(Weight.class) == null){
			log.error("Item {} needs a Weight component to be picked up!", item.getName());
			return false;
		}
		//gets weight and checks if weight does not exceed the players carry amt.
		int itemWeight = item.getComponent(Weight.class).getWeight();
		Strength strength = player.getComponent(Strength.class);
		if (strength.reduceStrength(itemWeight)) {
			inventory.addItem(item.getID());
			getOwner().detachComponent(getOwner().getComponent(Rotate.class));
			setChanged();
			//move item to a position that cannot be seen so network and database people are happy
			item.getTransform().setPosition(outBounds);
			return true;
//			return world.destroyEntity(item.getID());
		}

		messenger.sendMessage("Not stong enough to pick up {} item, try dropping an item first\n", item.getName());
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
			ServiceLocator.getService(Messenger.class).sendMessage("Press E to pick up item");
			if (Keyboard.isKeyDownOnce(KeyEvent.VK_E)) {
				pickUpItem();
			}
		}

	}

}
