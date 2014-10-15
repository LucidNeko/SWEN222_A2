package wolf3d.components.behaviours;

import java.util.List;

import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Transform;
import engine.core.Entity;
import engine.core.World;
import engine.util.ServiceLocator;


/**
 * The AILookAtController is a behaviour that makes the entity it is attached to always point at the target.
 * @author Hamish Rae-Hodgson
 *
 */
public class AILookAtController extends Behaviour {

	/** The target to look at */
	private Entity target = null;
	private World world = ServiceLocator.getService(World.class);

	/**
	 * Set the target to make this entity look at.
	 * @param target The target.
	 */
	public void setTarget(Entity target) {
		this.target = target;
	}

	@Override
	public void update(float delta) {
		requires(Transform.class);

		if(target == null) return;

		//Sets the target to be the closest player
		List<Entity> players = world.getEntity("Player");
		players.addAll(world.getEntity("other"));
		Vec3 enemyPos = getOwner().getTransform().getPosition();
		Vec3 closestPos = new Vec3(1, 1, 1).mulLocal(Float.MAX_VALUE);
		for(Entity p: players){
			Vec3 playerPos = p.getTransform().getPosition();
			if(enemyPos.sub(playerPos).length() < enemyPos.sub(closestPos).length()){
				closestPos = playerPos;
			}
		}
		Transform transform = getOwner().getTransform();
		transform.lookAt(closestPos); //look at the target.
	}

}
