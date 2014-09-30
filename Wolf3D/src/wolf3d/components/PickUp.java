package wolf3d.components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import engine.common.Vec3;
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
public class PickUp extends Component implements KeyListener {
	Map<Integer, Entity> items = new HashMap<Integer, Entity>();
	World world;

	public PickUp(World world) {
		this.world = world;
	}

	public boolean pickUpItem(int id) {
		items.put(id, world.getEntity(id));
		return world.destroyEntity(id);
	}

	public boolean dropItem(int id, Entity item) {
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("key Pressed");
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_F) {
			Set<Integer> keys = items.keySet();
			for (int id : keys) {
				dropItem(id, items.get(id));
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
