package wolf3d.components;

import java.awt.event.KeyEvent;

import engine.components.Behaviour;
import engine.components.Sensor;
import engine.input.Keyboard;

/**
 * This class is responsible for maintaining the heath of the player
 * @author Sameer Magan
 *
 */
public class Health extends Sensor{

	private int health = 100;

	private int damageAmt = 10;

	@Override
	public boolean isTriggered() {
		if (Keyboard.isKeyDown(KeyEvent.VK_X)) {
			if (health >= 0) {
				health -= damageAmt;
				System.out.println(health);
			}
		}
		return false;
	}
	
	public void increaseHealth(){
		if (health < 100) {
			health += damageAmt;
			System.out.println(health);
		}
	}
	
	public void decreaseHealth(){
		if (health > 0) {
			health -= damageAmt;
			System.out.println(health);
		}
	}

}
