package wolf3d.components;

import engine.components.Component;

/**
 * This class is responsible for maintaining the heath of the player
 * @author Sameer Magan 300223776
 *
 */
public class Health extends Component{

	private int health = 100;
	private boolean alive = true;
	
	private int damageAmt = 20;

	/**
	 * 
	 * @return returns the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * 
	 * @param health the amount of health to be set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * @return returns the damage amount
	 */
	public int getDamageAmt() {
		return damageAmt;
	}

	/**
	 * Sets the damage amount for the owner
	 * @param damageAmt the amount to set
	 */
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
