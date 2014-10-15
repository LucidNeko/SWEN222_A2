package wolf3d.components.behaviours;

import java.awt.event.KeyEvent;

import wolf3d.components.behaviours.animations.MoveUpAnimation;
import wolf3d.components.sensors.ProximitySensor;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.core.Entity;
import engine.input.Keyboard;
/**
 * This class is responsible for setting the behaviours of the door
 * @author Sameer Magan 300223776
 *
 */
public class DoorBehaviour extends Behaviour {

	@Override
	public void update(float delta) {
		if (Keyboard.isKeyDown(KeyEvent.VK_E)) {
			if (getOwner().getComponent(ProximitySensor.class).isTriggered()) {
				getOwner().attachComponent(MoveUpAnimation.class);
				getOwner().detachComponent(this);
			}
		}

	}

}
