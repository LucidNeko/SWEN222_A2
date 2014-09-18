package wolf3d.components.sensors;

import wolf3d.components.Transform;
import wolf3d.core.Entity;

public class ProximitySensor extends Sensor {

	private Entity target;

	public void setTarget(Entity target) {
		this.target = target;
	}
	
	@Override
	public boolean isTriggered() {
		requires(Transform.class);
		
		if(target == null) return false;
		
		return this.getOwner().getTransform().getPosition().sub(target.getTransform().getPosition()).length() < 2f;
	}

}
