package wolf3d.components.behaviours;

import wolf3d.components.Inventory;
import wolf3d.components.sensors.ProximitySensor;

import com.jogamp.newt.event.KeyEvent;

import engine.common.Vec3;
import engine.components.Behaviour;
import engine.core.Entity;
import engine.core.EntityDef;
import engine.core.World;
import engine.input.Keyboard;

public class DropItem extends Behaviour {
	World world;
	
	public DropItem(World world) {
		this.world = world;
	}
	
	public boolean drop(Entity item){
		Entity player = item.getComponent(ProximitySensor.class).getTarget();
		Inventory inventory = player.getComponent(Inventory.class);
		if(inventory.contains(item)){
			Vec3 pos = player.getTransform().getPosition();
			//setting item position to the players current position
			item.getTransform().setPosition(pos.getX(), pos.getY(), pos.getZ());
			//creates an EntityDef to use to add back to the world
			EntityDef entDef = new EntityDef();
			entDef.setName(item.getName());
			entDef.addComponents(item.getAllComponents());
			inventory.removeEntity(item);
			return world.addEntityDef(entDef);
		}
		return false;
	}
	
	@Override
	public void update(float delta) {
		if(Keyboard.isKeyDownOnce(KeyEvent.VK_R)){
			Entity item = getOwner();
			Entity player = item.getComponent(ProximitySensor.class).getTarget();
			Inventory inventory = player.getComponent(Inventory.class);
			drop(inventory.getItems().get(0));
		}

	}

}
