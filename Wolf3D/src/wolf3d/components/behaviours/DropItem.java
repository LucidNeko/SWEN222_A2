package wolf3d.components.behaviours;

import wolf3d.components.Inventory;
import wolf3d.components.Strength;
import wolf3d.components.Weight;
import wolf3d.components.behaviours.animations.Rotate;

import com.jogamp.newt.event.KeyEvent;

import engine.common.Vec3;
import engine.components.Behaviour;
import engine.core.Entity;
import engine.core.TempEntityDef;
import engine.core.World;
import engine.input.Keyboard;
import engine.util.ServiceLocator;

/**
 * This class is responsible for dropping items in the attached players
 * Inventory into the world and removing that item from the inventory
 *
 * @author Sameer Magan 300223776
 *
 */
public class DropItem extends Behaviour {
	World world = ServiceLocator.getService(World.class);

	/**
	 * Drops the given entity in the world on the player current position and
	 * removes the given from the attached players inventory
	 *
	 * @param item The Entity to be dropped
	 * @return true if the player is dropped into the world false if not
	 */
	public boolean drop(int itemId) {
		Entity player = getOwner();
		Inventory inventory = player.getComponent(Inventory.class);
		if (inventory.contains(itemId)) {
			Entity item = world.getEntity(itemId);
			Vec3 pos = player.getTransform().getPosition();
			// setting item position to the players current position and look vector
			item.getTransform().setPosition(pos.getX(), pos.getY(), pos.getZ());
			item.getTransform().lookInDirection(player.getTransform().getLook());
			// gets weight and adds it back to the players carryWeight
			int itemWeight = item.getComponent(Weight.class).getWeight();
			Strength strength = player.getComponent(Strength.class);
			strength.releaseWeight(itemWeight);
			// creates an EntityDef to use to add back to the world
//			TempEntityDef entDef = new TempEntityDef(item);
			return inventory.removeEntity(item.getID());

//			return world.addEntityDef(entDef);

		}
		return false;
	}

	@Override
	public void update(float delta) {
		if (Keyboard.isKeyDownOnce(KeyEvent.VK_1)) {
			itemToDrop(0);
		} else if (Keyboard.isKeyDownOnce(KeyEvent.VK_2)) {
			itemToDrop(1);
		} else if (Keyboard.isKeyDownOnce(KeyEvent.VK_3)) {
			itemToDrop(2);
		} else if (Keyboard.isKeyDownOnce(KeyEvent.VK_4)) {
			itemToDrop(3);
		} else if (Keyboard.isKeyDownOnce(KeyEvent.VK_5)) {
			itemToDrop(4);
		} else if (Keyboard.isKeyDownOnce(KeyEvent.VK_6)) {
			itemToDrop(5);
		} else if (Keyboard.isKeyDownOnce(KeyEvent.VK_7)) {
			itemToDrop(6);
		} else if (Keyboard.isKeyDownOnce(KeyEvent.VK_8)) {
			itemToDrop(7);
		} else if (Keyboard.isKeyDownOnce(KeyEvent.VK_9)) {
			itemToDrop(8);
		} else if (Keyboard.isKeyDownOnce(KeyEvent.VK_0)) {
			itemToDrop(9);
		}

	}

	/**
	 * Gives the drop method the corresponding item at the given index
	 * @param index the index of the item that you want to drop
	 */
	private void itemToDrop(int index) {
		Entity player = getOwner();
		Inventory inventory = player.getComponent(Inventory.class);
		if (inventory.get(index) != null) {
			drop(inventory.get(index));
		}
	}

}
