package wolf3d.components;

import javax.media.opengl.GL2;

/**
 * ICamera is a class that all Cameras should implement. 
 * @author Hamish Rae-Hodgson
 *
 */
public interface ICamera {

	/**
	 * Applies this camera to the gl context
	 * @param gl The gol context.
	 */
	public void setActive(GL2 gl);
	
}
