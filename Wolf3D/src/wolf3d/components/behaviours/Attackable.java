package wolf3d.components.behaviours;

import wolf3d.components.Health;
import wolf3d.components.sensors.ProximitySensor;

import com.jogamp.newt.event.KeyEvent;

import engine.components.Behaviour;
import engine.core.Entity;
import engine.core.World;
import engine.input.Keyboard;

/**
 * This class is responsible for allowing the owner of this Component to be 
 * attacked when they are in a certain proximity of a target
 * @author Sameer Magan
 *
 */
public class Attackable extends Behaviour{
	private World world;
	
	public Attackable(World world){
		this.world = world;
	}
	
	/**
	 * Attacks the Owner of this component by the damage amount of the player
	 * attacking
	 */
	private void attack(){
		Entity player = getOwner().getComponent(ProximitySensor.class).getTarget();
		Health playerHealth = player.getComponent(Health.class);
		Health enemyHealth = getOwner().getComponent(Health.class);
		
		//checks if player is dead or alive
		if(!enemyHealth.decreaseHealth(playerHealth.getDamageAmt())){
			world.destroyEntity(getOwner().getID());
		}
	}

	@Override
	public void update(float delta) {
		requires(Health.class);
		requires(ProximitySensor.class);
		if(getOwner().getComponent(ProximitySensor.class).isTriggered()){
			if(Keyboard.isKeyDownOnce(KeyEvent.VK_C)){
				attack();
				System.out.println("C has been pressed!");
			}
		}
		
	}

}
