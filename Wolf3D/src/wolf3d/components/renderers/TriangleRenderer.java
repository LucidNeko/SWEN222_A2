package wolf3d.components.renderers;

import java.awt.Color;

import javax.media.opengl.GL2;
import static javax.media.opengl.GL2.GL_TRIANGLES;

import wolf3d.components.Transform;

public class TriangleRenderer extends Renderer {
	
	private float hw;
	private float hh;
	private float r, g, b;

	public TriangleRenderer(int width, int height, float r, float g, float b) {
		this.hw = width/2f;
		this.hh = height/2f;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Override
	public void render(GL2 gl) {
		Transform t = getOwner().getComponent(Transform.class);
		
		gl.glPushMatrix();
			t.applyTransform(gl);
			gl.glColor3f(r, g, b);
			gl.glBegin(GL_TRIANGLES);
				gl.glVertex2f(-hw, -hh);
				gl.glVertex2f(hw, -hh);
				gl.glVertex2f(0, hh);
			gl.glEnd();
		gl.glPopMatrix();
	}

}
