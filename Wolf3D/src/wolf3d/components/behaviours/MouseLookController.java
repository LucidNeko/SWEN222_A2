package wolf3d.components.behaviours;

import engine.common.Mathf;
import engine.components.Behaviour;
import engine.components.Transform;
import engine.input.Mouse;

/**
 * Adds Mouse look functionality to the entity.
 * @author Hamish Rae-Hodgson
 *
 */
public class MouseLookController extends Behaviour {

	/** speed of looking around. Units per second. Low number for low dpi. */
	private float speed = 10;
	
	/**
	 * Set the speed that you look around at.
	 * @param speed The speed.
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	@Override
	public void update(float delta) {
		requires(Transform.class);
		
		Transform t = getOwner().getTransform();
		
		float dx = Mouse.getDX(); //calls to getDX/getDY wipe out the value.. TODO logical fix?
		float dy = Mouse.getDY();
		t.pitch(Mathf.degToRad(dy*speed*delta));
		t.rotateY(Mathf.degToRad(dx*speed*delta)); 
	}

}
