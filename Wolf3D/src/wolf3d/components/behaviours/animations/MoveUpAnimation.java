package wolf3d.components.behaviours.animations;

import java.awt.event.KeyEvent;

import wolf3d.components.behaviours.WASDCollisions;
import wolf3d.components.sensors.ProximitySensor;
import wolf3d.world.Cell;
import engine.common.Mathf;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Transform;
import engine.core.Entity;
import engine.input.Keyboard;

public class MoveUpAnimation extends Behaviour {

	private float speed = 1f; // 0.1 units per second.
	private float distance = 2; // 2 units.

	private Vec3 startPos = null;
	private Vec3 endPos = null;

	private boolean start;

	@Override
	public void update(float delta) {
		requires(Transform.class);
		if(Keyboard.isKeyDownOnce(KeyEvent.VK_SPACE)){
			start = true;
		}

		if (start) {
			Transform t = getOwner().getTransform();

			if (startPos == null) {
				startPos = t.getPosition();
				endPos = startPos.add(0, distance, 0);
			}

			if (isFinished()) {
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
				getOwner().attachComponent(
						new MoveDownAnimation(row, col, door));
				getOwner().detachComponent(this);
				return;
			}
			t.translate(0, speed * delta, 0);
		}
	}

	public boolean isFinished() {
		return Mathf.abs(getOwner().getTransform().getPosition().sub(startPos)
				.y()) >= distance;
	}

}
