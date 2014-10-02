package wolf3d.components.behaviours;

import wolf3d.components.Inventory;
import wolf3d.components.Weight;

import com.jogamp.newt.event.KeyEvent;

import engine.common.Vec3;
import engine.components.Behaviour;
import engine.core.Entity;
import engine.core.TempEntityDef;
import engine.core.World;
import engine.input.Keyboard;

/**
 * This class is responsible for dropping items in the attached players
 * Inventory into the world and removing that item from the inventory
 * 
 * @author Sameer Magan
 *
 */
public class DropItem extends Behaviour {
	World world;

	public DropItem(World world) {
		this.world = world;
	}

	/**
	 * Drops the given entity in the world on the player current position and
	 * removes the given from the attached players inventory
	 * 
	 * @param item
	 *            The Entity to be dropped
	 * @return true if the player is dropped into the world false if not
	 */
	public boolean drop(Entity item) {
		Entity player = getOwner();
		Inventory inventory = player.getComponent(Inventory.class);
		if (inventory.contains(item)) {
			Vec3 pos = player.getTransform().getPosition();
			// setting item position to the players current position
			item.getTransform().setPosition(pos.getX(), pos.getY(), pos.getZ());
			// gets weight and adds it back to the players carryWeight
			int itemWeight = item.getComponent(Weight.class).getWeight();
			inventory.releaseWeight(itemWeight);
			// creates an EntityDef to use to add back to the world
			TempEntityDef entDef = new TempEntityDef(item);

			inventory.removeEntity(item);
			setChanged();
			return world.addEntityDef(entDef);

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

	private void itemToDrop(int index) {
		Entity player = getOwner();
		Inventory inventory = player.getComponent(Inventory.class);
		if (inventory.get(index) != null) {
			drop(inventory.get(index));
		}
	}

}
