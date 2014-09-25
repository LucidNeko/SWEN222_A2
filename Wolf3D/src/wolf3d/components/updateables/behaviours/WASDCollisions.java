package wolf3d.components.updateables.behaviours;

import java.awt.event.KeyEvent;

import wolf3d.common.Vec3;
import wolf3d.components.Transform;
import wolf3d.components.updateables.Updateable;
import wolf3d.core.Keyboard;
import wolf3d.world.Cell;

public class WASDCollisions extends Updateable {

	/** Units per Second that this controller defaults to */
	public static final float DEFAULT_MOVESPEED = 10;

	/** Units moved per second */
	private float moveSpeed = DEFAULT_MOVESPEED;
	private float playerWidth = 0;

	private Cell[][] walls;

	public WASDCollisions(Cell[][] walls) {
		this.walls = walls;
	}

	/**
	 * Sets the movement speed of this controller.
	 *
	 * @param moveSpeed
	 *            The new move speed in units per second.
	 */
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	@Override
	public void update(float delta) {
		requires(Transform.class);

		Transform t = getOwner().getComponent(Transform.class);

		float dx = 0, dy = 0;
		if (Keyboard.isKeyDown(KeyEvent.VK_A))
			dx += 1;
		if (Keyboard.isKeyDown(KeyEvent.VK_D))
			dx -= 1;
		if (Keyboard.isKeyDown(KeyEvent.VK_W))
			dy += 1;
		if (Keyboard.isKeyDown(KeyEvent.VK_S))
			dy -= 1;

		t.strafeFlat(moveSpeed * dx * delta);
		t.walkFlat(moveSpeed * dy * delta);

		Vec3 newPos = t.getPosition();


	}

}
