package wolf3d.components.updateables.behaviours;

import java.awt.event.KeyEvent;

import wolf3d.components.updateables.Updateable;
import wolf3d.core.Keyboard;

/**
 * This class is responsible for maintaining the heath of the player
 * 
 * @author sameermagan
 *
 */
public class Health extends Updateable {

	private static int health = 100;

	private int damageAmt = 10;

	@Override
	public void update(float delta) {
		if (Keyboard.isKeyDown(KeyEvent.VK_X)) {
			if (health >= 0) {
				health -= damageAmt;
				System.out.println(health);
			}
		}
	}

}
