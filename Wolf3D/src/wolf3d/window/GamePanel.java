package wolf3d.window;

import java.awt.Dimension;


import static javax.media.opengl.GL.GL_DEPTH_TEST;
//GL Constants
import static javax.media.opengl.GL2.*;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Base class for all OpenGl renderable components
 * @author Hamish Rae-Hodgson
 */
public abstract class GamePanel extends GLJPanel implements GLEventListener, View {
	private static final long serialVersionUID = 2122462715974304566L;
	
	protected static final Logger log = LogManager.getLogger();
	
	/**
	 * Creates a new GLJPanel with the capabilities specified in glcapabilities and preffered size of width/height
	 * @param glCapabilities
	 * @param width
	 * @param height
	 */
	public GamePanel(GLCapabilities glCapabilities, int width, int height) {
		super(glCapabilities);
		this.setPreferredSize(new Dimension(width, height));
		this.addGLEventListener(this);
	}
	
//	/**
//	 * Enters 3D rendering mode. Builds projection matrix using gluPerspective. 
//	 * Pushes the current projection and modelview matrices.
//	 * @param gl
//	 * @param width
//	 * @param height
//	 */
//	protected void enter3DMode(GL2 gl, int width, int height) {
//		gl.glMatrixMode(GL_PROJECTION);
//		gl.glPushMatrix();
//		gl.glLoadIdentity();
//		GLU.createGLU(gl).gluPerspective(DEFAULT_FIELD_OF_VIEW, width/height, DEFAULT_ZNEAR, DEFAULT_ZFAR);
//		gl.glMatrixMode(GL_MODELVIEW);
//		gl.glPushMatrix();
//		gl.glLoadIdentity();
//	}
	
//	/**
//	 * Exits 3D rendering mode by popping the projection matrix and then the modelvview matrix
//	 * @param gl the gl context
//	 */
//	protected void exit3DMode(GL2 gl) {
//		gl.glMatrixMode(GL_PROJECTION);
//		gl.glPopMatrix();
//		gl.glMatrixMode(GL_MODELVIEW);
//		gl.glPopMatrix();
//	}	
	
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
	
	@Override
	public abstract void init(GLAutoDrawable drawable);

	@Override
	public abstract void dispose(GLAutoDrawable drawable);

	@Override
	public abstract void display(GLAutoDrawable drawable);

	@Override
	public abstract void reshape(GLAutoDrawable drawable, int x, int y, int width, int height);

}
