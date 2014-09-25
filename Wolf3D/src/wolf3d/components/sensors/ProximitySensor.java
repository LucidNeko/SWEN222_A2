package wolf3d.components.sensors;

import engine.components.Sensor;
import engine.components.Transform;
import engine.core.Entity;

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
