package wolf3d.components.behaviours;

import wolf3d.components.sensors.ProximitySensor;
import engine.components.Behaviour;

public class AttackPlayer extends Behaviour{

	private float time;
	@Override
	public void update(float delta) {
		if (getOwner().getComponent(ProximitySensor.class).isTriggered()){
			time += delta;
			if(time > 2){

			}
		}
		else{
			time = 0;
		}

	}


}
