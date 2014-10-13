package wolf3d.components.behaviours;

import wolf3d.components.sensors.ProximitySensor;
import engine.components.Behaviour;
import engine.components.Transform;

public class AddChaseBehaviour extends Behaviour {

	@Override
	public void update(float delta) {
		requires(ProximitySensor.class);

		if(getOwner().getComponent(ProximitySensor.class).isTriggered()) {
			getOwner().attachComponent(WalkForward.class);
			getOwner().detachComponent(this);
		}
	}

}
