package wolf3d.components;

import engine.components.Component;
/**
 * This class is responsible for storing and updating the strength of the entity
 * that it is attached to
 * @author Sameer Magan 300223776
 *
 */
public class Strength extends Component{
	private int strength = 100;
	
	public int getStrength() {
		return strength;
	}

	/**
	 * Reduces the carryWeight by the given amount
	 * 
	 * @param weight the amount to reduce by
	 * @return true if it can be reduced, false if not
	 */
	public boolean reduceStrength(int weight) {
		if ((strength - weight) >= 0) {
			strength -= weight;
			return true;
		}
		return false;
	}

	/**
	 * Releases the given amount of weight to Strength
	 * @param weight the amount to be released
	 */
	public void releaseWeight(int weight) {
		strength += weight;
	}
}
