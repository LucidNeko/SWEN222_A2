package wolf3d.components.behaviours;

import engine.components.Behaviour;
import engine.components.Transform;
import engine.core.Entity;


/**
 * The AILookAtController is a behaviour that makes the entity it is attached to always point at the target.
 * @author Hamish Rae-Hodgson
 *
 */
public class AILookAtController extends Behaviour {
	
	/** The target to look at */
	private Entity target = null;
	
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
		
		Transform transform = getOwner().getTransform();
		transform.lookAt(target.getTransform()); //look at the target.
	}

}
