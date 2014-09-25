package wolf3d.components.behaviours;

import engine.components.Behaviour;
import wolf3d.components.behaviours.animations.MoveUpAnimation;
import wolf3d.components.sensors.ProximitySensor;

public class AddAnimation extends Behaviour {

	@Override
	public void update(float delta) {
		requires(ProximitySensor.class);
		
		if(getOwner().getComponent(ProximitySensor.class).isTriggered()) {
			getOwner().attachComponent(MoveUpAnimation.class);
			getOwner().detachComponent(this);
		}
	}

}
