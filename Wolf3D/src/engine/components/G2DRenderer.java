package engine.components;

import java.awt.Graphics2D;

/**
 * An interface that all Graphics2D renderable components must implement
 * @author Hamish
 *
 */
public abstract class G2DRenderer extends Component {

	/**
	 * Render this component to the provided Graphics2D.
	 * @param g The Graphics object to render to.
	 */
	public abstract void render(Graphics2D g);
	
}
