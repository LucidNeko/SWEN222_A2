package wolf3d.components.behaviours.animations.die;

import java.io.IOException;

import wolf3d.components.behaviours.AILookAtController;
import wolf3d.networking.Client;
import engine.components.Behaviour;
import engine.components.Transform;
import engine.core.World;
import engine.util.ServiceLocator;

/**
 * This animates the owner by spinning and then flying up after a set amount of time
 * @author Sameer Magan 300223776
 *
 */
public class RotateFlyDieAnimation extends Behaviour {
	private float speed = 0.1f; // 1.0 units per second.

	private float time;
	private World world = ServiceLocator.getService(World.class);

	private Client client = ServiceLocator.getService(Client.class);

	@Override
	public void update(float delta) {
		requires(Transform.class);

		time += delta;

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
