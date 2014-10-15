package wolf3d.components.behaviours;

import wolf3d.components.Health;
import wolf3d.components.sensors.ProximitySensor;
import engine.components.Behaviour;
import engine.core.Entity;

/**
 * This allows the enemy to attack players
 * @author Sameer Magan 300223776
 *
 */
public class AttackPlayer extends Behaviour{

	private float time;
	@Override
	public void update(float delta) {
		if (getOwner().getComponent(ProximitySensor.class).isTriggered()){
			time += delta;
			if(time > 2){
				Entity player = getOwner().getComponent(ProximitySensor.class).getTarget();
				int damageAmt = getOwner().getComponent(Health.class).getDamageAmt();
				Health health = player.getComponent(Health.class);
				if(!health.decreaseHealth(delta*damageAmt)){
					health.setHealth(100);
					player.getTransform().setPosition(1, 0, 1);
				}
				setChanged();
				notifyObservers();
			}
		}
		else{
			time = 0;
		}

	}


}
