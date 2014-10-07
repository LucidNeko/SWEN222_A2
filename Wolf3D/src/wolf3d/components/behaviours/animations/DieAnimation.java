package wolf3d.components.behaviours.animations;

import engine.common.Mathf;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Transform;

public class DieAnimation extends Behaviour {
	private float speed = 10f; //1.0 units per second.
	private float rotationAmount = 90; //2 units.

	private Transform startPos = null;

	@Override
	public void update(float delta) {
		requires(Transform.class);

		Transform t = getOwner().getTransform();

		if(startPos == null) {
			startPos = t;
		}

		t.rotateY(delta*speed);

	}

}
