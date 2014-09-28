package wolf3d.components.behaviours;

import wolf3d.components.sensors.ProximitySensor;
import engine.components.Behaviour;
import engine.components.Transform;

public class AddChaseBehaviour extends Behaviour {

	private float speed = 1;
	
	@Override
	public void update(float delta) {
		requires(ProximitySensor.class);
		
		if(getOwner().getComponent(ProximitySensor.class).isTriggered()) {
			getOwner().attachComponent(new Behaviour() {
				public void update(float delta) {
					requires(Transform.class);
					getOwner().getTransform().walk(speed*delta);
				}
			});
			getOwner().detachComponent(this);
		}
	}

}
