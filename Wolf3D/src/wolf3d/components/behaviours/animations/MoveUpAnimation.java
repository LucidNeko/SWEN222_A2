package wolf3d.components.behaviours.animations;

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

	private int row, col, door;
	
	private boolean halfwaydown;

	@Override
	public void update(float delta) {
		requires(Transform.class);

		Transform t = getOwner().getTransform();
		if (startPos == null) {
			startPos = t.getPosition();
			endPos = startPos.add(0, distance, 0);
			translate = new Translate(startPos, endPos, speed);
			//starts translation/animation
			getOwner().attachComponent(translate);
		}

		//when translation is finished
		if (!getOwner().contains(translate)) {
			// remove collision for this door
			Entity player = getOwner().getComponent(ProximitySensor.class)
					.getTarget();
			Vec3 pos = player.getTransform().getPosition();
			int wallSize = player.getComponent(WASDCollisions.class)
					.getWallsize();
			int col = (int) ((pos.getX() / wallSize));
			int row = (int) ((pos.getZ() / wallSize));
			int door = player.getComponent(WASDCollisions.class).getDoor(row,
					col);
			// zero out door at you position
			player.getComponent(WASDCollisions.class).zeroDoor(row, col);
			getOwner().attachComponent(new MoveDownAnimation(row, col, door));
			getOwner().detachComponent(this);
			return;
		}
		

	}

	public void setCollision(int row, int col, int door) {
		this.row = row;
		this.col = col;
		this.door = door;
	}

}
