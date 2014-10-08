package engine.scratch;

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
import javax.swing.SwingUtilities;

import engine.common.Mathf;
import engine.components.Camera;
import engine.components.GL2Renderer;
import engine.components.MeshFilter;
import engine.components.Transform;
import engine.core.Entity;
import engine.core.World;
import engine.display.GameCanvas;
import engine.display.View;

public class MiniMap extends GameCanvas implements View {
	
	//gluPerspective params
	private static final float FIELD_OF_VIEW = 60;
	private static final float ZNEAR = 0.1f;
	private static final float ZFAR = 200;
	
	private World world;
	private Entity camera;

	public MiniMap(int width, int height, World world) {
		super(width, height);
		this.world = world;
		
		camera = world.createEntity("TopDownCamera");
		camera.attachComponent(Camera.class);
		camera.getTransform().pitch(Mathf.degToRad(-90));
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glClearColor(0, 0, 0, 1);
		gl.glEnable(GL_CULL_FACE);
		gl.glClearDepth(1f);
		gl.glDepthFunc(GL_LEQUAL);
		gl.glEnable(GL_DEPTH_TEST);

		gl.glEnable(GL_BLEND);
		gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Thread(new Runnable() {
					public void run() {
						while(true) {
							MiniMap.this.display();
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}).start();
			}
		});
	}

	@Override
	public void dispose(GLAutoDrawable drawable) { }

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

//		bugFix(gl); //Maybe only required for GLJPanel not GLCanvas.

		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		Transform t = null;
		try {
			t = world.getEntity("Player").get(0).getTransform();
		} catch(Exception e) { }
		
		if(t != null) {
			camera.getTransform().setPosition(t.getPosition());
			camera.getTransform().translate(0, 10, 0);
		}

		for(Entity e : world.getEntities()) {
			if(e.getName().equals("skybox")) continue;
			gl.glPushMatrix();
				camera.getComponent(Camera.class).setActive(gl);
				if(e.hasComponent(MeshFilter.class)) {
					e.getTransform().applyTransform(gl);
					WireframeMeshRenderer wmr = e.attachComponent(WireframeMeshRenderer.class);
					wmr.render(gl);
					e.detachComponent(wmr);
				}
			gl.glPopMatrix();
		}

		checkError(gl); //prints out error code if we get an error.
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
	public void setCamera(Camera camera) {
		throw new UnsupportedOperationException();
	}

}
