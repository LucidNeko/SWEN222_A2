package wolf3d.components.behaviours;

import wolf3d.components.Health;
import wolf3d.components.sensors.ProximitySensor;

import com.jogamp.newt.event.KeyEvent;

import engine.components.Behaviour;
import engine.core.Entity;
import engine.core.World;
import engine.input.Keyboard;

public class Attackable extends Behaviour{
	private World world;
	
	public Attackable(World world){
		this.world = world;
	}
	
	private void attack(){
		Entity player = getOwner().getComponent(ProximitySensor.class).getTarget();
		Health playerHealth = player.getComponent(Health.class);
		Health enemyHealth = getOwner().getComponent(Health.class);
		
		if(!enemyHealth.decreaseHealth(playerHealth.getDamageAmt())){
			world.destroyEntity(getOwner().getID());
		}
	}

	@Override
	public void update(float delta) {
		requires(Health.class);
		requires(ProximitySensor.class);
		if(getOwner().getComponent(ProximitySensor.class).isTriggered()){
			if(Keyboard.isKeyDownOnce(KeyEvent.VK_X)){
				attack();
				System.out.println("C has been pressed!");
			}
		}
		
	}

}
