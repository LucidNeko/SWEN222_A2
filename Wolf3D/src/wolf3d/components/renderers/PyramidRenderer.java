package wolf3d.components.renderers;

import javax.media.opengl.GL2;

import engine.components.Renderer;
import engine.components.Transform;

/**
 * Renderer for a pyramid. For debug purposes.
 * @author Hamish Rae-Hodgson
 *
 */
public class PyramidRenderer extends Renderer {

	public void render(GL2 gl) {
		requires(Transform.class);
		
		gl.glPushMatrix();
			getOwner().getComponent(Transform.class).applyTransform(gl);
			
			gl.glBegin(GL2.GL_TRIANGLES);
				gl.glColor3f(1, 0, 0);
				gl.glVertex3f(-0.25f, -0.25f, -0.5f);
				gl.glVertex3f(0.25f, -0.25f, -0.5f);
				gl.glVertex3f(0, 0, 0.25f);
				
				gl.glColor3f(0, 1, 0);
				gl.glVertex3f(0, 0.25f, -0.5f);
				gl.glVertex3f(-0.25f, -0.25f, -0.5f);
				gl.glVertex3f(0, 0, 0.25f);
				
				gl.glColor3f(0, 0, 1);
				gl.glVertex3f(0.25f, -0.25f, -0.5f);
				gl.glVertex3f(0, 0.25f, -0.5f);
				gl.glVertex3f(0, 0, 0.25f);
				
				gl.glColor3f(1, 1, 1);
				gl.glVertex3f(0, 0.25f, -0.5f);
				gl.glVertex3f(0.25f, -0.25f, -0.5f);
				gl.glVertex3f(-0.25f, -0.25f, -0.5f);
			gl.glEnd();
		gl.glPopMatrix();
	}
	
}
