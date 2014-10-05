package wolf3d.components;

import engine.components.Component;

/**
 * This class is responsible for maintaining the heath of the player
 * @author Sameer Magan
 *
 */
public class Health extends Component{

	private int health = 100;
	private boolean alive = true;
	
	private int damageAmt = 20;

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getDamageAmt() {
		return damageAmt;
	}

	public void setDamageAmt(int damageAmt) {
		this.damageAmt = damageAmt;
	}
	
	/**
	 * Increases health by damageAmt
	 * @return returns true if increased, false if not
	 */
	public boolean increaseHealth(){
		if (health < 100) {
			health += damageAmt;
			System.out.println(health);
			return true;
		}
		return false;
	}
	
	/**
	 * Decreases health by damageAmt
	 * @return returns true if still alive, false if not
	 */
	public boolean decreaseHealth(int hitAmt){
		if (health - hitAmt >0) {
			health -= hitAmt;
			return alive;
		}
		else{
			health = 0;
			alive = false;
			return alive;
		}
	}

}
