package wolf3d.components.behaviours;

import java.awt.event.KeyEvent;

import engine.components.Behaviour;
import engine.components.Transform;
import engine.input.Keyboard;

/**
 * The WASDWalking class when added to an entity adds movement based on WASD.<br>
 * Walking and strafing along the XZ plane.
 * @author Hamish Rae-Hodgson
 *
 */
public class WASDWalking extends Behaviour {

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

	/*
	 * Backup(non-Javadoc)  (15:25 02/Oct)
	 * @see engine.components.Behaviour#update(float)

	@Override
	public void update(float delta) {
		requires(Transform.class);

		Transform t = getOwner().getComponent(Transform.class);

		if(Keyboard.isKeyDown(KeyEvent.VK_A))
			t.strafeFlat(moveSpeed*delta);
		if(Keyboard.isKeyDown(KeyEvent.VK_D))
			t.strafeFlat(-moveSpeed*delta);
		if(Keyboard.isKeyDown(KeyEvent.VK_W))
			t.walkFlat(moveSpeed*delta);
		if(Keyboard.isKeyDown(KeyEvent.VK_S))
			t.walkFlat(-moveSpeed*delta);


	}
		 */


	@Override
	public void update(float delta) {
		requires(Transform.class);

		Transform t = getOwner().getComponent(Transform.class);

		if(Keyboard.isKeyDown(KeyEvent.VK_A)){
			t.strafeFlat(moveSpeed*delta);
			this.setChanged();
		}
		if(Keyboard.isKeyDown(KeyEvent.VK_D)){
			t.strafeFlat(-moveSpeed*delta);
			this.setChanged();
		}
		if(Keyboard.isKeyDown(KeyEvent.VK_W)){
			t.walkFlat(moveSpeed*delta);
			this.setChanged();
		}
		if(Keyboard.isKeyDown(KeyEvent.VK_S)){
			t.walkFlat(-moveSpeed*delta);
			this.setChanged();
		}


	}


}
