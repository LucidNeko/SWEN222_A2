package wolf3d.components.behaviours;

import engine.components.Behaviour;
import engine.components.Transform;

/**
 * This class is responsible for constantly walking forward at a fixed
 * rate when attached
 * @author Sameer Magan 300223776
 *
 */
public class WalkForward extends Behaviour {
	private float speed = 1;

	@Override
	public void update(float delta) {
		requires(Transform.class);
		getOwner().getTransform().walk(speed*delta);

	}

}
