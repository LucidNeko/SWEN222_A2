package wolf3d.components.behaviours;

import java.awt.event.KeyEvent;

import wolf3d.components.behaviours.animations.MoveUpAnimation;
import wolf3d.components.sensors.ProximitySensor;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.core.Entity;
import engine.input.Keyboard;

public class DoorBehaviour extends Behaviour {

	@Override
	public void update(float delta) {
		if (Keyboard.isKeyDown(KeyEvent.VK_SPACE)) {
			if (getOwner().getComponent(ProximitySensor.class).isTriggered()) {
				// Vec3 playerLook = getOwner()
				// .getComponent(ProximitySensor.class).getTarget()
				// .getTransform().getLook();
				// Vec3 doorLook = getOwner().getTransform().getLook();
				// log.trace("pl={} dl={} dot={}", playerLook, doorLook,
				// Vec3.dot(playerLook, doorLook));
				//
				// if (Vec3.dot(playerLook, doorLook) < 0f) {
				// remove collision for this door
				Entity player = getOwner().getComponent(ProximitySensor.class)
						.getTarget();
				Vec3 pos = player.getTransform().getPosition();
				int wallSize = player.getComponent(WASDCollisions.class)
						.getWallsize();
				int col = (int) ((pos.getX() / wallSize));
				int row = (int) ((pos.getZ() / wallSize));
				int door = player.getComponent(WASDCollisions.class).getDoor(
						row, col);
				// zero out door at you position
				player.getComponent(WASDCollisions.class).zeroDoor(row, col);
				getOwner().attachComponent(MoveUpAnimation.class).setCollision(row, col, door);
				getOwner().detachComponent(this);
				// }
			}
		}

	}

}
