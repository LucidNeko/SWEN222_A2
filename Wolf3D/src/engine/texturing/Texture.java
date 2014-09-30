package engine.texturing;

import static javax.media.opengl.GL2.*;

import java.nio.ByteBuffer;

import javax.media.opengl.GL2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Texture represents a Texture. The first time you bind it it will load it into graphics memory
 * storing the handle returned and nullifying the picel array that was passed in.
 * @author Hamish
 *
 */
public class Texture {
	private static final Logger log = LogManager.getLogger();

	private int id = -1; //-1 means not yet registered with OpenGL
	private int width;
	private int height;

	private byte[] pixels;

	/**
	 * Create a new Texture with the width and height and pixels containing the image data.
	 * @param width
	 * @param height
	 * @param pixels
	 */
	public Texture(int width, int height, byte[] pixels) {
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}

	/**
	 * Bind this Texture to the gl context.
	 * @param gl The gl context.
	 */
	public void bind(GL2 gl) {
		if(id == -1) loadImageIntoGL(gl);

		gl.glBindTexture(GL_TEXTURE_2D, id);
	}

	/**
	 * The first time we try bind, the image isn't loaded into graphics memory. Load it.
	 * @param gl The gl context.
	 */
	private void loadImageIntoGL(GL2 gl) {
		int[] id = new int[1];
		gl.glGenTextures(1, id, 0);
		log.trace("loadImageIntoGL: id={}", id[0]);
		gl.glBindTexture(GL_TEXTURE_2D, id[0]);
		gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, ByteBuffer.wrap(pixels));
		int errorCode = gl.glGetError();
		if(gl.glGetError() == GL_NO_ERROR) {
			this.id = id[0];
			pixels = null;
		} else {
			log.error("Failed binding texture. GL_ERROR={}", errorCode);
		}
	}

}
