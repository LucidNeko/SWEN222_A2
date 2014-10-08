package wolf3d.components.behaviours;

import wolf3d.components.behaviours.animations.MoveUpAnimation;
import wolf3d.components.sensors.ProximitySensor;
import engine.components.Behaviour;
import engine.components.Component;
import engine.components.Sensor;

public class AddAnimation extends Behaviour {
	// default animation to attach
	private Class<? extends Component> comToAttach = MoveUpAnimation.class;
	private Sensor sensor;

	@Override
	public void update(float delta) {
		requires(ProximitySensor.class);

		if (getOwner().getComponent(ProximitySensor.class).isTriggered()) {
			if (sensor != null) {
				if (sensor.isTriggered()) {
					getOwner().attachComponent(comToAttach);
					getOwner().detachComponent(this);
				}
			} else {
				getOwner().attachComponent(comToAttach);
				getOwner().detachComponent(this);
			}
		}
	}

	public void setAttachment(Class<? extends Component> comToAttach) {
		this.comToAttach = comToAttach;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

}
