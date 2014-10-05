package wolf3d.components.behaviours;

import java.awt.event.KeyEvent;

import wolf3d.world.Cell;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Transform;
import engine.input.Keyboard;

public class WASDCollisions extends Behaviour {

	/** Units per Second that this controller defaults to */
	public static final float DEFAULT_MOVESPEED = 3;

	private static final int wallSize = 2;

	/** Units moved per second */
	private float moveSpeed = DEFAULT_MOVESPEED;
	private float playerWidth = 0.25f;

	private Cell[][] walls;
	private Cell[][] doors;

	public WASDCollisions(Cell[][] walls, Cell[][] doors) {
		this.walls = walls;
		this.doors = doors;
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

	/**
	 * Moves the player back to starting position
	 *
	 * @param dy
	 * @param dx
	 * @param delta
	 * @param t
	 *            the transform component attached to the player
	 */
	public void moveBack(float dy, float dx, float delta, Transform t) {
		t.strafeFlat(-moveSpeed * dx * delta);
		t.walkFlat(-moveSpeed * dy * delta);
		//clears the initial change, meaning the the player hasn't actually moved
		clearChanged();
	}

	private void movePlayer(float dy, float dx, float delta) {
		Transform t = getOwner().getComponent(Transform.class);

		// old position and cell
		Vec3 oldPos = t.getPosition();
		int oldCol = (int) ((oldPos.getX()) / wallSize);
		int oldRow = (int) ((oldPos.getZ()) / wallSize);
		Cell oldCell = walls[oldRow][oldCol];
		Cell doorCell = doors[oldRow][oldCol];

		// move foward
		t.strafeFlat(moveSpeed * dx * delta);
		t.walkFlat(moveSpeed * dy * delta);
		// lets the networking know that something has changed
		setChanged();

		// new Position and cell
		Vec3 newPos = t.getPosition();
		int col;
		int row;
		// this tests the players position +- the width of the player, so that
		// you can no longer look through walls
		if (oldPos.getX() > newPos.getX()) {
			col = (int) ((newPos.getX() - playerWidth) / wallSize);
		} else {
			col = (int) ((newPos.getX() + playerWidth) / wallSize);
		}
		if (oldPos.getZ() > newPos.getZ()) {
			row = (int) ((newPos.getZ() - playerWidth) / wallSize);
		} else {
			row = (int) ((newPos.getZ() + playerWidth) / wallSize);
		}
		// checks the special case of position being negative,
		// this should detect collisions for the border north and
		// west wall
		if (newPos.getX() - playerWidth < 0 || newPos.getZ() - playerWidth < 0) {
			moveBack(dy, dx, delta, t);
			return;
		}

		// check if inbounds of the walls array
		if (row < 0 || row >= walls.length || col < 0 || col >= walls[0].length) {
			moveBack(dy, dx, delta, t);
			return;
		}

		Cell cell = walls[row][col];
		// no collision
		if (oldCell == cell) {
			return;
		} else {
			// we know were in a different cell from where we started.
			if (col > oldCol) {
				if (oldCell.hasEast()) {
					moveBack(dy, dx, delta, t);
					return;
				}
			}
			if (col < oldCol) {
				if (oldCell.hasWest()) {
					// move back
					moveBack(dy, dx, delta, t);
					return;
				}
			}
			if (row > oldRow) {
				if (oldCell.hasSouth()) {
					moveBack(dy, dx, delta, t);
					return;
				}
			}
			if (row < oldRow) {
				if (oldCell.hasNorth()) {
					moveBack(dy, dx, delta, t);
					return;
				}
			}
		}
	}

	@Override
	public void update(float delta) {
		requires(Transform.class);
		
		float dx = 0, dy = 0;
		if (Keyboard.isKeyDown(KeyEvent.VK_A))
			dx += 1;
		if (Keyboard.isKeyDown(KeyEvent.VK_D))
			dx -= 1;
		if (Keyboard.isKeyDown(KeyEvent.VK_W))
			dy += 1;
		if (Keyboard.isKeyDown(KeyEvent.VK_S))
			dy -= 1;

		// only move if player has actually pressed WASD
		if (dx != 0 || dy != 0) {
			movePlayer(dy, dx, delta);
		}
		
		//checking notifications
//		if(hasChanged()){
//			System.out.println("player moved");
//		}
//		clearChanged();
	}
}
