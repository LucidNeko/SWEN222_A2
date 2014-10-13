package wolf3d.components.behaviours.animations;

import wolf3d.components.behaviours.DoorBehaviour;
import wolf3d.components.behaviours.Translate;
import wolf3d.components.behaviours.WASDCollisions;
import wolf3d.components.sensors.ProximitySensor;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Transform;
import engine.core.Entity;

public class MoveDownAnimation extends Behaviour {
	private float speed = 1f; //0.1 units per second.
	private float distance = 2; //2 units.

	private Vec3 startPos = null;
	private Vec3 endPos = null;

	private int row, col, door;

	private Translate translate;

	public MoveDownAnimation(int row, int col, int door){
		this.row = row;
		this.col = col;
		this.door = door;
	}

	@Override
	public void update(float delta) {
		requires(Transform.class);

		Transform t = getOwner().getTransform();

		if(startPos == null) {
			startPos = t.getPosition();
			endPos = startPos.sub(0, distance, 0);
			translate = new Translate(startPos, endPos, speed);
			getOwner().attachComponent(translate);
		}

		if(!getOwner().contains(translate)){
			Entity player = getOwner().getComponent(ProximitySensor.class).getTarget();
			player.getComponent(WASDCollisions.class).setDoor(row, col, door);
			getOwner().attachComponent(DoorBehaviour.class);
			getOwner().detachComponent(this);
		}


	}


}
