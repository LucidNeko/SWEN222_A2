package wolf3d.components.updateables.behaviours;

import java.awt.event.KeyEvent;

import wolf3d.components.Transform;
import wolf3d.core.Keyboard;

/**
 * The WASDFlying class when added to an entity adds flying movement based on WASD.
 * @author Hamish Rae-Hodgson
 *
 */
public class WASDFlying extends Updateable {
	
	/** Units per Second that this controller defaults to */
	public static final float DEFAULT_MOVESPEED = 10;
	
	/** Units moved per second */
	private float moveSpeed = DEFAULT_MOVESPEED;
	
	/** 
	 * Sets the movement speed of this controller.
	 * @param moveSpeed The new move speed in units per second. 
	 */
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	@Override
	public void update(float delta) {
		requires(Transform.class);
		
		Transform t = getOwner().getComponent(Transform.class);
		
		if(Keyboard.isKeyDown(KeyEvent.VK_A))
			t.strafe(moveSpeed*delta);
		if(Keyboard.isKeyDown(KeyEvent.VK_D))
			t.strafe(-moveSpeed*delta);
		if(Keyboard.isKeyDown(KeyEvent.VK_W))
			t.walk(moveSpeed*delta);
		if(Keyboard.isKeyDown(KeyEvent.VK_S))
			t.walk(-moveSpeed*delta);
	}

}
