package wolf3d.components.renderers;

import javax.media.opengl.GL2;

import wolf3d.components.Transform;

public class Triangle3DRenderer extends Renderer {

	public void render(GL2 gl) {
		Transform t = getOwner().getComponent(Transform.class);
		gl.glPushMatrix();
			t.applyTransform(gl);
			gl.glColor3f(1, 0, 0);
			gl.glBegin(GL2.GL_TRIANGLES);
				gl.glVertex3f(-1, -1, -2);
				gl.glVertex3f(1, -1, -2);
				gl.glVertex3f(0, 0, 0);
			gl.glEnd();
			gl.glColor3f(0, 1, 0);
			gl.glBegin(GL2.GL_TRIANGLES);
				gl.glVertex3f(0, 1, -2);
				gl.glVertex3f(-1, -1, -2);
				gl.glVertex3f(0, 0, 0);
			gl.glEnd();
			gl.glColor3f(0, 0, 1);
			gl.glBegin(GL2.GL_TRIANGLES);
				gl.glVertex3f(1, -1, -2);
				gl.glVertex3f(0, 1, -2);
				gl.glVertex3f(0, 0, 0);
			gl.glEnd();
			gl.glColor3f(1, 1, 1);
			gl.glBegin(GL2.GL_TRIANGLES);
				gl.glVertex3f(0, 1, -2);
				gl.glVertex3f(1, -1, -2);
				gl.glVertex3f(-1, -1, -2);
			gl.glEnd();
		gl.glPopMatrix();
	}
	
}
