package wolf3d.components.updateables.behaviours;

import wolf3d.components.sensors.ProximitySensor;
import wolf3d.components.updateables.Updateable;
import wolf3d.components.updateables.animations.MoveUpAnimation;

public class AddAnimation extends Updateable {

	@Override
	public void update(float delta) {
		requires(ProximitySensor.class);
		
		if(getOwner().getComponent(ProximitySensor.class).isTriggered()) {
			getOwner().attachComponent(MoveUpAnimation.class);
			getOwner().detachComponent(this);
		}
	}

}
