package wolf3d.components.behaviours.animations.die;

import wolf3d.components.Health;
import wolf3d.components.behaviours.AILookAtController;
import engine.components.Behaviour;
import engine.components.Transform;
import engine.core.World;

public class FallDieAnimation extends Behaviour {
	private float speed = 0.01f; // 1.0 units per second.
	private float rotationAmount = 90; // 2 units.

	private Transform startPos = null;

	private float time;
	private World world;

	public FallDieAnimation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(float delta) {
		requires(Transform.class);

		time += delta;
		int health = getOwner().getComponent(Health.class).getHealth();
		AILookAtController lookAt;

		if((lookAt = getOwner().getComponent(AILookAtController.class)) != null){
			getOwner().detachComponent(lookAt);
		}
		Transform t = getOwner().getTransform();
		t.rotateY(time * speed);
		if(time >= 4){
			t.fly((time -4)*speed);
		}
		if(time > 10){
			world.destroyEntity(getOwner().getID());
		}
	}

}