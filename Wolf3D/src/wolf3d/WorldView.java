package wolf3d;

import static javax.media.opengl.GL2.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.glu.GLU;

import engine.components.Camera;
import engine.components.GL2Renderer;
import engine.components.MeshRenderer;
import engine.components.Transform;
import engine.core.Entity;
import engine.core.World;
import engine.display.GameCanvas;
import engine.display.View;
import engine.texturing.Material;
import engine.texturing.Texture;

/**
 * The WorldView is a View of a World. Renders all the Renderer components on every Entity in the world.
 * @author Hamish
 *
 */
public class WorldView extends GameCanvas implements View{
	private static final long serialVersionUID = 8996675374479682200L;

	//gluPerspective params
	private static final float FIELD_OF_VIEW = 60;
	private static final float ZNEAR = 0.1f;
	private static final float ZFAR = 200;

	private World world;
	private Camera camera;

	/** Create a new WorldView over the given World. */
	public WorldView(int width, int height, World world) {
		super(width, height);
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

		gl.glEnable(GL_LIGHTING);
		

//		gl.glLightf(GL_LIGHT0, GL_CONSTANT_ATTENUATION, 0.0075f);
//		gl.glLightf(GL_LIGHT0, GL_LINEAR_ATTENUATION, 0.0025f);
//		gl.glLightf(GL_LIGHT0, GL_QUADRATIC_ATTENUATION, 0.001f);
		gl.glEnable(GL_LIGHT0);
		gl.glEnable(GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
		
//		float[] light1_ambient = { 0f, 0f, 0f, 1.0f };
//		float[] light1_diffuse = { 1.0f, 1.0f, 1.0f, 1.0f };
//		float[] light1_specular = { 1.0f, 1.0f, 1.0f, 1.0f };
//		float[] position = { 0, 0, 0, 1 };
//		float[] direction = { 0, 0, 1 };
//
//		gl.glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 0);
//		gl.glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 180);
//		gl.glLightf(GL_LIGHT1, GL_CONSTANT_ATTENUATION, 1);
//		gl.glLightf(GL_LIGHT1, GL_LINEAR_ATTENUATION, 0);
//		gl.glLightf(GL_LIGHT1, GL_QUADRATIC_ATTENUATION, 0);
//		
//		gl.glLightfv(GL_LIGHT1, GL_AMBIENT, light1_ambient, 0);
//		gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, light1_diffuse, 0);
//		gl.glLightfv(GL_LIGHT1, GL_SPECULAR, light1_specular, 0);
//		gl.glLightfv(GL_LIGHT1, GL_POSITION, position, 0);
//		gl.glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, direction, 0);
		

//		gl.glLightModelfv(GL_LIGHT_MODEL_AMBIENT, new float[] { 0.5f, 0.5f, 0.5f, 1}, 0);
//		gl.glLightModelfv(GL_LIGHT_MODEL_AMBIENT, new float[] { 0f, 0f, 0f, 1}, 0);
		 
//		gl.glEnable(GL_LIGHT1);
//		gl.glEnable(GL_COLOR_MATERIAL);
//		gl.glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
//		gl.glMaterialfv(GL_FRONT, GL_SPECULAR, new float[]{ 1.0f, 1.0f, 1.0f, 1.0f }, 0);
//        gl.glMateriali(GL_FRONT, GL_SHININESS, 128);
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
			renderEntities(gl, world.getEntities());
		gl.glPopMatrix();
	}
	
	private void renderEntities(GL2 gl, Collection<Entity> entities) {
		List<Entity> transparent = new LinkedList<Entity>();
		for(Entity entity : entities) {
			Transform t = entity.getTransform();
			if(t == null) { 
				log.error("Entity {} doesn't have a Transform!", entity);
				continue; //next entity.
			}
			
			//skip entities with transparency.
			MeshRenderer mr = entity.getComponent(MeshRenderer.class);
			if(mr != null) {
				Material m = mr.getMaterial();
				if(m != null) {
					Texture tex = m.getTexture();
					if(tex != null) {
						if(tex.hasTransparency()) {
							transparent.add(entity);
							continue;
						}
					}
				}
			}
			
			//if not skipped render.
			gl.glPushMatrix();
				t.applyTransform(gl);
				for(GL2Renderer renderer : entity.getComponents(GL2Renderer.class)) {
					renderer.render(gl);
				}
//				renderEntities(gl, entity.getChildren()); //recurse through children. Compounding transforms.
			gl.glPopMatrix();
		}
		
		//now we go back and render the transparent ones last so they overlap properly.
		for(Entity e : transparent) {
			Transform t = e.getTransform();
			
			gl.glPushMatrix();
				t.applyTransform(gl);
				for(GL2Renderer renderer : e.getComponents(GL2Renderer.class)) {
					renderer.render(gl);
				}
			gl.glPopMatrix();
		}
	}

}
