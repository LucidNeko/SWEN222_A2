package engine.components;

import javax.media.opengl.GL2;

/**
 * An interface that all OpenGL renderable components must implement
 * @author Hamish
 *
 */
public abstract class GL2Renderer extends Component {

	/**
	 * Render this component to the provided gl context.
	 * @param gl The OpenGL context.
	 */
	public abstract void render(GL2 gl);
	
}
