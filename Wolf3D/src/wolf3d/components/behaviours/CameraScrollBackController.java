package wolf3d.components.behaviours;

import wolf3d.components.Camera;
import wolf3d.core.Mouse;

/**
 * Adds Camera scrollback functionality to the entity.<br>
 * i.e scrolling down will walk the camera backwards away from the entity.
 * @author Hamish Rae-Hodgson
 *
 */
public class CameraScrollBackController extends Behaviour {
	
	private float speed = 1;
	
	/**
	 * Set the speed scroll back should occur.
	 * @param speed The speed.
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public void update(float delta) {
		requires(Camera.class);

		Camera camera = getOwner().getComponent(Camera.class);
		
		float dw = Mouse.getDWheel();
		if(dw != 0) {
			dw = dw < 0 ? -1 : 1; //normalize.
			camera.getTransform().walk(-dw*speed); //minus so scroll down is walk backwards.
		}
	}

}
