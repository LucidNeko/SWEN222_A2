package wolf3d.components.behaviours.animations;

import wolf3d.components.behaviours.DoorBehaviour;
import wolf3d.components.behaviours.Translate;
import wolf3d.components.behaviours.WASDCollisions;
import wolf3d.components.sensors.ProximitySensor;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Transform;
import engine.core.Entity;

/**
 * This class is responsible for moving animating the attached component up by
 * the set amount of distance and then attaching a MoveDown animation
 *
 * @author Sameer Magan 300223776
 *
 */
public class MoveUpAnimation extends Behaviour {

	private float speed = 1f; // 0.1 units per second.
	private float distance = 2; // 2 units.

	private Vec3 startPos = null;
	private Vec3 endPos = null;

	private Translate translate;

	private int row, newRow, col, newCol, door;

	// sets the default to be true
	private boolean moveDown = true;

	// default behaviour if nothing gets set
	private Class<? extends Behaviour> resetBehaviour = DoorBehaviour.class;

	@Override
	public void update(float delta) {
		requires(Transform.class);

		Transform t = getOwner().getTransform();
		if (startPos == null) {
			startPos = t.getPosition();
			endPos = startPos.add(0, distance, 0);
			translate = new Translate(startPos, endPos, speed);
			// starts translation/animation
			getOwner().attachComponent(translate);
		}

		// when translation is finished
		if (!getOwner().contains(translate)) {
			// remove collision for this door
			Entity player = getOwner().getComponent(ProximitySensor.class)
					.getTarget();
			Vec3 pos = player.getTransform().getPosition();
			int wallSize = player.getComponent(WASDCollisions.class)
					.getWallsize();
			//get collisions
			WASDCollisions collisions = player.getComponent(WASDCollisions.class);

			col = (int) ((pos.getX() / wallSize));
			row = (int) ((pos.getZ() / wallSize));
			newCol = col;
			newRow = row;
			door = collisions.getDoor(row, col);
			// zero out door at you position
			collisions.zeroDoor(row, col);
			//increment to opposite cell
			oppCell();
			int oppositeDoor = collisions.getDoor(newRow, newCol);
			collisions.zeroDoor(newRow, newCol);
			if (moveDown) {
				MoveDownAnimation mov = new MoveDownAnimation();
				mov.setDoor(row, col, door);
				mov.setOppDoor(newRow, newCol, oppositeDoor);
				mov.setResetBehaviour(resetBehaviour);
				getOwner().attachComponent(mov);
			}
			getOwner().detachComponent(this);
			return;
		}

	}

	private void oppCell(){
		Vec3 look = getOwner().getTransform().getLook();

		if(look.x() == 1){
			newCol = col - 1;
		}
		if(look.x() == -1){
			newCol = col + 1;
		}
		if(look.z() == 1){
			newRow = row + 1;
		}
		if(look.z() == -1){
			newRow = row - 1;
		}
	}

	public void setResetBehaviour(Class<? extends Behaviour> resetBehaviour) {
		this.resetBehaviour = resetBehaviour;
	}

	public void setMoveDown(boolean moveDown) {
		this.moveDown = moveDown;
	}

	public void setCollision(int row, int col, int door) {
		this.row = row;
		this.col = col;
		this.door = door;
	}

}
