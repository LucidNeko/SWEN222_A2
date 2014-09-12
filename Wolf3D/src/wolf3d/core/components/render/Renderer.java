package wolf3d.core.components.render;

import javax.media.opengl.GL2;

/**
 * An interface that all renderable components must implement
 * @author Hamish Rae-Hodgson
 *
 */
public interface Renderer {

	/**
	 * Render this component to the provided gl context.
	 * @param gl The OpenGL context.
	 */
	public void render(GL2 gl);
	
}
