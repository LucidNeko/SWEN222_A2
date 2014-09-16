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

import java.awt.event.KeyEvent;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.glu.GLU;

import wolf3d.components.renderers.Renderer;
import wolf3d.core.Entity;
import wolf3d.core.Keyboard;
import wolf3d.world.World;

public class WorldView extends GamePanel {
	private static final long serialVersionUID = -3162253973028786392L;
	
	//gluPerspective params
	private static final float FIELD_OF_VIEW = 45;
	private static final float ZNEAR = 0.1f;
	private static final float ZFAR = 100;
	
	private World world = null;

	public WorldView(GLCapabilities glCapabilities, World world, int width, int height) {
		super(glCapabilities, width, height);
		setWorld(world);
	}
	
	public void setWorld(World world) {
		this.world = world;
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
		
		bugFix(gl);

		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		renderWorld(gl);
		renderHUD(gl);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) { 	}
	
	private void renderWorld(GL2 gl) {
		if(this.world == null) return; //no world to render
		
		for(Entity entity : world.getEntities()) {
			for(Renderer renderer : entity.getComponents(Renderer.class)) {
				renderer.render(gl);
			}
		}
	}
	
	private void renderHUD(GL2 gl) {
		
	}

}
