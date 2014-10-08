package wolf3d.components.behaviours;

import java.awt.event.KeyEvent;

import wolf3d.components.behaviours.animations.MoveUpAnimation;
import wolf3d.components.sensors.ProximitySensor;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.input.Keyboard;

public class DoorBehaviour extends Behaviour {

	@Override
	public void update(float delta) {
		if (Keyboard.isKeyDown(KeyEvent.VK_SPACE)) {
			if (getOwner().getComponent(ProximitySensor.class).isTriggered()) {
//				Vec3 playerLook = getOwner()
//						.getComponent(ProximitySensor.class).getTarget()
//						.getTransform().getLook();
//				Vec3 doorLook = getOwner().getTransform().getLook();
//				log.trace("pl={} dl={} dot={}", playerLook, doorLook,
//						Vec3.dot(playerLook, doorLook));
//
//				if (Vec3.dot(playerLook, doorLook) < 0f) {
					getOwner().attachComponent(MoveUpAnimation.class);
					getOwner().detachComponent(this);
//				}
			}
		}

	}

}
