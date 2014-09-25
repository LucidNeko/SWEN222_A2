package wolf3d;

import static javax.media.opengl.GL2.*;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.glu.GLU;

import engine.components.Camera;
import engine.components.Renderer;
import engine.core.Entity;
import engine.core.World;
import engine.display.GameCanvas;
import engine.display.View;

/**
 * The WorldView is a View of a World. Renders all the Renderer components on every Entity in the world.
 * @author Hamish
 *
 */
public class WorldView extends GameCanvas implements View{
	private static final long serialVersionUID = 8996675374479682200L;

	//gluPerspective params
	private static final float FIELD_OF_VIEW = 45;
	private static final float ZNEAR = 0.1f;
	private static final float ZFAR = 100;

	private World world;
	private Camera camera;

	/** Create a new WorldView over the given World. */
	public WorldView(GLCapabilities glCapabilities, int width, int height, World world) {
		super(glCapabilities, width, height);
		this.world = world;
	}
	
	@Override
	public void setCamera(Camera camera) {
		this.camera = camera;
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
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.createGLU(gl).gluPerspective(FIELD_OF_VIEW, width/height, ZNEAR, ZFAR);
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glViewport(0, 0, width, height);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

//		bugFix(gl); //Maybe only required for GLJPanel not GLCanvas.

		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		renderWorld(gl);
		
		checkError(gl); //prints out error code if we get an error.
	}

	@Override
	public void dispose(GLAutoDrawable drawable) { 	}

	/** Render the world. */
	private void renderWorld(GL2 gl) {
		if(this.world == null) return; //no world to render

		gl.glPushMatrix();
			if(camera != null) camera.setActive(gl);
			for(Entity entity : world.getEntities()) {
				for(Renderer renderer : entity.getComponents(Renderer.class)) {
					renderer.render(gl);
				}
			}
		gl.glPopMatrix();
	}

}
