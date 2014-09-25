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

	private static final int wallSize = 2;

	/** Units moved per second */
	private float moveSpeed = DEFAULT_MOVESPEED;
	private float playerWidth = 0;

	private Cell[][] walls;

	public WASDCollisions(Cell[][] walls) {
		this.walls = walls;
	}

	/**
	 * Sets the movement speed of this controller.
	 * @param moveSpeed The new move speed in units per second.
	 */
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	
	/**
	 * Moves the player back to starting position
	 * @param dy
	 * @param dx
	 * @param delta
	 * @param t the transform component attached to the player
	 */
	public void moveBack(float dy, float dx, float delta, Transform t){
		t.strafeFlat(-moveSpeed*dx*delta);
		t.walkFlat(-moveSpeed*dy*delta);
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

		// old position and cell
		Vec3 oldPos = t.getPosition();
		int oldCol = (int) (oldPos.getX() / wallSize);
		int oldRow = (int) (oldPos.getZ() / wallSize);
		Cell oldCell = walls[oldRow][oldCol];

		//move foward 
		t.strafeFlat(moveSpeed * dx * delta);
		t.walkFlat(moveSpeed * dy * delta);

		// new Position and cell
		Vec3 newPos = t.getPosition();
		int col = (int) (newPos.getX() / wallSize);
		int row = (int) (newPos.getZ() / wallSize);

		//check if inbounds of the cell
		if(row<0 || row >= walls.length || col < 0 || col >= walls[0].length){
			moveBack(dy, dx, delta, t);
			return;
		}

		Cell cell = walls[row][col];
		//no collision
		if (oldCell == cell) {
			return;
		} else {
			//we know were in a different cell from where we started.
			if (col>oldCol) {
				if(oldCell.hasEast()){
					moveBack(dy, dx, delta, t);
					return;
				}
			}
			if(col < oldCol){
				if(oldCell.hasWest()){
					//move back
					moveBack(dy, dx, delta, t);
					return;
				}
			}
			if(row>oldRow){
				if(oldCell.hasSouth()){
					moveBack(dy, dx, delta, t);
					return;
				}
			}
			if(row<oldRow){
				if(oldCell.hasNorth()){
					moveBack(dy, dx, delta, t);
					return;
				}
			}
		}

	}

}
