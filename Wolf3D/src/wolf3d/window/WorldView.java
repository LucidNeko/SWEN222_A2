package wolf3d.window;

import static javax.media.opengl.GL.GL_BLEND;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_CULL_FACE;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_LEQUAL;
import static javax.media.opengl.GL.GL_ONE_MINUS_SRC_ALPHA;
import static javax.media.opengl.GL.GL_SRC_ALPHA;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.glu.GLU;

import wolf3d.components.ICamera;
import wolf3d.components.renderers.Renderer;
import wolf3d.core.Entity;
import wolf3d.core.World;
import wolf3d.util.ResourceLoader;

/**
 * The WorldView class is a View that renders a World as seen from the given Camera.
 * @author Hamish Rae-Hodgson
 *
 */
public class WorldView extends GamePanel {
	private static final long serialVersionUID = -3162253973028786392L;

	//gluPerspective params
	private static final float FIELD_OF_VIEW = 45;
	private static final float ZNEAR = 0.1f;
	private static final float ZFAR = 100;

	private World world = null;
	private ICamera camera = null;

	public WorldView(GLCapabilities glCapabilities, World world, int width, int height) {
		super(glCapabilities, width, height);
		setWorld(world);
	}

	/**
	 * Set the World that this View renders.
	 * @param world The world.
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	/** Sets the active Camera that we View the World through.
	 * @param camera The camera.
	 */
	public void setCamera(ICamera camera) {
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

		//TODO: ResourceLoader better.
		int texID = ResourceLoader.loadTexture(gl, "1.png", true);
		int wallID = ResourceLoader.loadTexture(gl, "debug_wall.png", true);
		int floorID = ResourceLoader.loadTexture(gl, "debug_floor.png", true);

		log.trace("texID={}", texID);
		log.trace("wallID={}", wallID);
		log.trace("floorID={}", floorID);
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

		bugFix(gl); //Maybe only required for GLJPanel not GLCanvas.

		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		renderWorld(gl);
		renderHUD(gl);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) { 	}

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

	private void renderHUD(GL2 gl) {
		enter2DMode(gl, 0, 1, 0, 1);

		gl.glColor4f(0.5f, 0.5f, 1, 0.75f);
		gl.glBegin(GL2.GL_QUADS);
			gl.glVertex2f(0, 0);
			gl.glVertex2f(1, 0);
			gl.glVertex2f(1, 0.1f);
			gl.glVertex2f(0, 0.1f);
		gl.glEnd();

		exit2DMode(gl);
	}

}
