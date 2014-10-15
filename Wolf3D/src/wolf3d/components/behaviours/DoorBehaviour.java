package wolf3d.components.behaviours;

import java.awt.event.KeyEvent;

import wolf3d.components.behaviours.animations.MoveUpAnimation;
import wolf3d.components.sensors.ProximitySensor;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.core.Entity;
import engine.input.Keyboard;
import engine.util.Messenger;
import engine.util.ServiceLocator;

/**
 * This class is responsible for setting the behaviours of the door
 *
 * @author Sameer Magan 300223776
 *
 */
public class DoorBehaviour extends Behaviour {

	@Override
	public void update(float delta) {
		if (getOwner().getComponent(ProximitySensor.class).isTriggered()) {
			ServiceLocator.getService(Messenger.class).sendMessage("Press E to open door");
			if (Keyboard.isKeyDown(KeyEvent.VK_E)) {
				getOwner().attachComponent(MoveUpAnimation.class);
				getOwner().detachComponent(this);
			}
		}

	}

}
