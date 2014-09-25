package engine.components;

/**
 * The Behaviour class encapsulates updateable behaviours.<br>
 * Example uses:
 *  - Player movement from input devices.
 *  - AI movement along a set path.
 * @author Hamish
 *
 */
public abstract class Behaviour extends Component {

	/**
	 * Update this behaviour. Should be called every tick with the delta time.
	 * @param delta Delta time. i.e move(100*delta) would move 100 units per second. 
	 */
	public abstract void update(float delta);
	
}
