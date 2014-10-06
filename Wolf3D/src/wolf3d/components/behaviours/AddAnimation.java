package wolf3d.components.behaviours;

import com.jogamp.newt.event.KeyEvent;

import engine.components.Behaviour;
import engine.input.Keyboard;
import wolf3d.components.behaviours.animations.MoveUpAnimation;
import wolf3d.components.sensors.ProximitySensor;

public class AddAnimation extends Behaviour {

	@Override
	public void update(float delta) {
		requires(ProximitySensor.class);

		if (getOwner().getComponent(ProximitySensor.class).isTriggered()) {
			if (Keyboard.isKeyDownOnce(KeyEvent.VK_SPACE)) {
				getOwner().attachComponent(MoveUpAnimation.class);
				getOwner().detachComponent(this);
			}
		}
	}

}
