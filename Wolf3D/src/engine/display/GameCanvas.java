package engine.display;

import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_TRIANGLES;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.Dimension;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Base class for all OpenGl renderable (awt)components
 * @author Hamish
 */
public abstract class GameCanvas extends GLJPanel implements GLEventListener { //TODO: GLJPanel vs GLCanvas....... needs to be GLJPanel if want to overlay anything.
	private static final long serialVersionUID = -1372862841511384090L;

	protected static final Logger log = LogManager.getLogger();

	/**
	 * Creates a new GameCanvas. Uses a default GLCompatabilities.
	 * @param width
	 * @param height
	 */
	public GameCanvas(int width, int height) {
		this(null, width, height);
	}

	/**
	 * Creates a new GameCanvas with the given capabilities and a preferred size of (width, height).
	 * @param glCapabilities The GL Capabilities.
	 * @param width The preferred width.
	 * @param height The preferred height.
	 */
	public GameCanvas(GLCapabilities glCapabilities, int width, int height) {
		super(glCapabilities);
		this.setPreferredSize(new Dimension(width, height));
		this.addGLEventListener(this);
	}

	/**
	 * Enters 2D rendering mode. Builds projection matrix using gluOrtho2D.
	 * Pushes the current projection and modelview matrices.
	 * @param gl the gl context
	 * @param width
	 * @param height
	 */
	protected void enter2DMode(GL2 gl, float left, float right, float bottom, float top) {
		gl.glMatrixMode(GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		GLU.createGLU(gl).gluOrtho2D(left, right, bottom, top);
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glDisable(GL_DEPTH_TEST);
		gl.glDepthMask(false);
	}

	/**
	 * Exits 2D rendering mode by popping the projection matrix and then the modelview matrix
	 * @param gl the gl context
	 */
	protected void exit2DMode(GL2 gl) {
		gl.glMatrixMode(GL_PROJECTION);
		gl.glPopMatrix();
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glPopMatrix();
		gl.glEnable(GL_DEPTH_TEST);
		gl.glDepthMask(true);
	}

	/**
	 * When you do all drawing with calls to glDrawArrays. rendering is warped. Calling this fixes the issue
	 * @param gl the gl context
	 */
	protected void bugFix(GL2 gl) {
		gl.glBegin(GL_TRIANGLES);
			gl.glVertex2i(0, 0);
			gl.glVertex2i(0, 0);
			gl.glVertex2i(0, 0);
		gl.glEnd();
	}

	protected void checkError(GL2 gl) {
		int errorCode = gl.glGetError();
		if(errorCode != GL2.GL_NO_ERROR)
			log.error("GL_ERROR: {}", errorCode);
	}

	@Override
	public abstract void init(GLAutoDrawable drawable);

	@Override
	public abstract void dispose(GLAutoDrawable drawable);

	@Override
	public abstract void display(GLAutoDrawable drawable);

	@Override
	public abstract void reshape(GLAutoDrawable drawable, int x, int y, int width, int height);

}
