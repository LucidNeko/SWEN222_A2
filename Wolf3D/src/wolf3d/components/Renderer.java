package wolf3d.components;

import javax.media.opengl.GL2;

/**
 * An interface that all renderable components must implement
 * @author Hamish Rae-Hodgson
 *
 */
public abstract class Renderer extends Component {

	/**
	 * Render this component to the provided gl context.
	 * @param gl The OpenGL context.
	 */
	public abstract void render(GL2 gl);
	
}