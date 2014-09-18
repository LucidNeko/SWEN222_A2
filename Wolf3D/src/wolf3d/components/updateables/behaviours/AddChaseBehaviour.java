package wolf3d.components.updateables.behaviours;

import wolf3d.components.Transform;
import wolf3d.components.sensors.ProximitySensor;
import wolf3d.components.updateables.Updateable;

public class AddChaseBehaviour extends Updateable {

	private float speed = 5;
	
	@Override
	public void update(float delta) {
		requires(ProximitySensor.class);
		
		if(getOwner().getComponent(ProximitySensor.class).isTriggered()) {
			getOwner().attachComponent(new Updateable() {
				public void update(float delta) {
					requires(Transform.class);
					getOwner().getTransform().walk(speed*delta);
				}
			});
			getOwner().detachComponent(this);
		}
	}

}
