package wolf3d.components.sensors;

import wolf3d.components.Component;

/**
 * Classes that extend Sensor define the isTriggered method which returns whether this<br>
 * sensor should be considered activated or not.
 * @author Hamish Rae-Hodgson
 *
 */
public abstract class Sensor extends Component {
	
	/**
	 * Returns true if this sensor is triggered.
	 * @return True if triggered, false if not triggered.
	 */
	public abstract boolean isTriggered();

}
