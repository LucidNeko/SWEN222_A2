package wolf3d.components.behaviours;

import java.awt.event.KeyEvent;

import wolf3d.world.Cell;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Transform;
import engine.input.Keyboard;

/**
 * This class is responsible for detecting wall collision for when
 * the WASD keys are used, and then stops the player from moving if
 * a collision is detected
 * @author Sameer Magan 300223776
 *
 */
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
		// clears the initial change, meaning the the player hasn't actually
		// moved
		clearChanged();
	}

	/**
	 *
	 * @param dy
	 * @param dx
	 * @param delta
	 * @return dy is the sideways movement, dx is the forward movement, delta is the distance
	 */
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
				if (oldCell.hasEast() || doorCell.hasEast()) {
					moveBack(dy, dx, delta, t);
					return;
				}
			}
			if (col < oldCol) {
				if (oldCell.hasWest() || doorCell.hasWest()) {
					// move back
					moveBack(dy, dx, delta, t);
					return;
				}
			}
			if (row > oldRow) {
				if (oldCell.hasSouth() || doorCell.hasSouth()) {
					moveBack(dy, dx, delta, t);
					return;
				}
			}
			if (row < oldRow) {
				if (oldCell.hasNorth() || doorCell.hasNorth()) {
					moveBack(dy, dx, delta, t);
					return;
				}
			}
		}
	}

	public int getDoor(int row, int col) {
		return doors[row][col].getWalls();
	}

	/**
	 * Sets the door at the given row and column to 0
	 * @param row the row that is referenced
	 * @param col the column that is referenced
	 */
	public void zeroDoor(int row, int col) {
		doors[row][col].setWalls(0);
	}

	/**
	 * Sets the door at the given position to the value specified in door iff
	 * there is no value already in this cell
	 *
	 * @param row
	 *            the row in which to reference
	 * @param col
	 *            the column in which to refrence
	 * @param door
	 *            the door value to be set
	 * @return true if update was made, false if there was already a value
	 *         contained in this cell
	 */
	public boolean setDoor(int row, int col, int door) {
		if (doors[row][col].getWalls() == 0) {
			doors[row][col].setWalls(door);
			return true;
		}
		return false;
	}

	/**
	 * @return the wallsize
	 */
	public static int getWallsize() {
		return wallSize;
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

		// checking notifications
		// if(hasChanged()){
		// System.out.println("player moved");
		// }
		// clearChanged();
	}
}
