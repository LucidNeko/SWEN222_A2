package wolf3d.components.behaviours;

import wolf3d.components.behaviours.animations.MoveUpAnimation;
import wolf3d.components.sensors.ProximitySensor;

import com.jogamp.newt.event.KeyEvent;

import engine.components.Behaviour;
import engine.components.Component;
import engine.input.Keyboard;

public class AddAnimation extends Behaviour {
	// default animation to attach
	private Class<? extends Component> comToAttach = MoveUpAnimation.class;

	@Override
	public void update(float delta) {
		requires(ProximitySensor.class);

		if (getOwner().getComponent(ProximitySensor.class).isTriggered()) {
			getOwner().attachComponent(comToAttach);
			getOwner().detachComponent(this);
		}
	}

	public void setAttachment(Class<? extends Component> comToAttach) {
		this.comToAttach = comToAttach;
	}

}
