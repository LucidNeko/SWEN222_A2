package wolf3d.components.behaviours;

import engine.common.Mathf;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Transform;

public class Translate extends Behaviour {

	private Vec3 start;
	private Vec3 dest;

	private float t;
	private float speed;

	public Translate(Vec3 start, Vec3 dest, float speed) {
		this.start = start;
		this.dest = dest;
		this.speed = (speed / dest.sub(start).length());///100;
	}

	@Override
	public void update(float delta) {
		requires(Transform.class);

		t = Mathf.clamp(t+(speed*delta), 0, 1);
		Transform transform = getOwner().getTransform();

		float x = Mathf.lerp(start.x(), dest.x(), t);
		float y = Mathf.lerp(start.y(), dest.y(), t);
		float z = Mathf.lerp(start.z(), dest.z(), t);
		transform.setPosition(x, y, z);

		if(t == 1) {
			getOwner().detachComponent(this);
		}
	}

}
