package wolf3d.components.behaviours.animations.die;

import wolf3d.components.Health;
import wolf3d.components.behaviours.AILookAtController;
import wolf3d.components.behaviours.WalkForward;
import engine.common.Mathf;
import engine.components.Behaviour;
import engine.components.Transform;
import engine.core.World;
import engine.util.ServiceLocator;

/**
 * This animates its owner by rotating down and then falling through the ground
 * @author Sameer Magan 300223776
 *
 */
public class FallDieAnimation extends Behaviour {
	private float speed = 0.0001f; // 1.0 units per second.
	private float rotationAmount = 90;

	private Transform startPos = null;

	private float time;
	private World world = ServiceLocator.getService(World.class);

	@Override
	public void update(float delta) {
		requires(Transform.class);

		time += delta;
		int health = getOwner().getComponent(Health.class).getHealth();
		AILookAtController lookAt;
		if((lookAt = getOwner().getComponent(AILookAtController.class)) != null){
			getOwner().detachComponent(lookAt);
			getOwner().detachComponent(getOwner().getComponent(WalkForward.class));
		}
		Transform t = getOwner().getTransform();


		if(time < 2) {
			t.roll(Mathf.degToRad(rotationAmount)*0.1f*delta);
		} else {
//			t.walk(1*delta);
		}

//		t.pitch(rotationAmount/delta*speed);
//		if(time >= 4){
//			t.fly((time -4)*speed);
//		}
//		if(time > 10){
//			world.destroyEntity(getOwner().getID());
//		}
	}

}
