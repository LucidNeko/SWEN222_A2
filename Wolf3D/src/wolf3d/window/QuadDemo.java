package wolf3d.window;

//GL Constants
import static javax.media.opengl.GL.*;
import static javax.media.opengl.GL2.*;

import java.nio.FloatBuffer;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.glu.GLU;

import wolf3d.util.ResourceLoader;

import com.jogamp.common.nio.Buffers;

/**
 * A simple Demo rending some spinning quads and testing HUD renderering.
 * @author Hamish Rae-Hodgson
 *
 */
public class QuadDemo extends GamePanel {
	private static final long serialVersionUID = 3301034542715199479L;

	//Used in gluPerspective
	private static final float FIELD_OF_VIEW = 45f;
	private static final float ZNEAR = 0.1f;
	private static final float ZFAR = 100;
	
	private float rot = 0;	
	
	private FloatBuffer verts;
	private FloatBuffer colors;
	private FloatBuffer coords;
	
	private int texID;
	
	public QuadDemo(GLCapabilities glCapabilities, int width, int height) {
		super(glCapabilities, width, height);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glClearColor(0, 0, 0.2f, 1);
		gl.glEnable(GL_CULL_FACE);
		gl.glClearDepth(1f);
		gl.glDepthFunc(GL_LEQUAL);
		gl.glEnable(GL_DEPTH_TEST);
		
		gl.glEnable(GL_BLEND);
		gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		texID = ResourceLoader.loadTexture(gl, "flipped.png", false);
		log.trace(texID);
		
		verts = Buffers.newDirectFloatBuffer(new float[] {
				-1, -1, 0,
				1, -1, 0,
				1, 1, 0,
				-1, 1, 0
		});
		
		colors = Buffers.newDirectFloatBuffer(new float[] {
			1, 1, 1,
			1, 1, 1,
			1, 1, 1,
			0, 0, 1
		});
		
		coords = Buffers.newDirectFloatBuffer(new float[] {
			0, 0,
			1, 0,
			1, 1,
			0, 1
		});
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		log.trace("dispose");
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		bugFix(gl);
		
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		gl.glPushMatrix();
			gl.glTranslatef(0, 0, -10);
			gl.glRotatef(rot/2f, 0, 1, 0);		
			gl.glColor3f(0, 1, 0);
			gl.glBegin(GL_QUADS);
				gl.glVertex3f(2, -1, 0);
				gl.glVertex3f(-2, -1, 0);
				gl.glVertex3f(-2, 1, 0);
				gl.glVertex3f(2, 1, 0);
			gl.glEnd();
			
			gl.glBegin(GL_QUADS);
				gl.glVertex3f(-2, -1, 0);
				gl.glVertex3f(2, -1, 0);
				gl.glVertex3f(2, 1, 0);
				gl.glVertex3f(-2, 1, 0);
			gl.glEnd();
		gl.glPopMatrix();
		
		//transparent objects last
		gl.glPushMatrix();
			gl.glTranslatef(0, 0, -5);
			gl.glRotatef(rot++%360, 0.4f, 0.4f, 0);			
			
			gl.glEnable(GL_TEXTURE_2D);
			gl.glBindTexture(GL_TEXTURE_2D,  texID);
			gl.glColor3f(1,1,1);
			gl.glEnableClientState(GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL_COLOR_ARRAY);
			gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);
			gl.glVertexPointer(3, GL_FLOAT, 0, verts);
			gl.glColorPointer(3, GL_FLOAT, 0, colors);
			gl.glTexCoordPointer(2, GL_FLOAT, 0, coords);
			gl.glDrawArrays(GL_QUADS, 0, 4);
			gl.glRotatef(180, 1, 1, 0);
			gl.glDrawArrays(GL_QUADS, 0, 4);
			gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY);
			gl.glDisableClientState(GL_COLOR_ARRAY);
			gl.glDisableClientState(GL_VERTEX_ARRAY);
			gl.glDisable(GL_TEXTURE_2D);
		gl.glPopMatrix();
		
		//render HUD
		enter2DMode(gl, 1, 1); //unit square
			gl.glColor3f(0.12f, 0.86f, 1);
			
			for(float x = 0; x < 1; x+=0.05f) {
				gl.glBegin(GL_QUADS);
					gl.glVertex2f(x, 0);
					gl.glVertex2f(x+0.01f, 0);
					gl.glVertex2f(x+0.01f, 1f);
					gl.glVertex2f(x, 0.5f);
				gl.glEnd();
			}
		exit2DMode(gl);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.createGLU(gl).gluPerspective(FIELD_OF_VIEW, width/height, ZNEAR, ZFAR);
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();
		
		
	}
}
