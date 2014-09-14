package wolf3d.window;

import static javax.media.opengl.GL.*;
import static javax.media.opengl.GL2.*;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.event.KeyEvent;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.glu.GLU;

import wolf3d.core.Entity;
import wolf3d.core.Keyboard;
import wolf3d.core.components.Transform;
import wolf3d.core.components.render.Renderer;
import wolf3d.core.components.render.Sprite;
import wolf3d.core.components.render.TextureRenderer;
import wolf3d.util.ResourceLoader;

public class EntityDemo extends GamePanel {
	private static final long serialVersionUID = -5693253200279106797L;
	
	//Used in gluPerspective
	private static final float FIELD_OF_VIEW = 45f;
	private static final float ZNEAR = 0.1f;
	private static final float ZFAR = 100;
	
	Entity door;

	public EntityDemo(GLCapabilities glCapabilities, int width, int height) {
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
		
		initEntities(gl);
	}
	
	public void initEntities(GL2 gl) {
		//Create entity
		door = new Entity(0);
		
		//add transform
		Transform t = door.attachComponent(Transform.class); //if constructor takes 0 arguments can attach this way.
		
		//Add a sprite
		Sprite sprite = new Sprite(2, 2);
		door.attachComponent(sprite);
		
		//Add a renderer
		int texID = ResourceLoader.loadTexture(gl, "1024x1024_64x64.png", true);
		if(texID == -1) {
			log.error("Failed importing resource. Aborting Renderer creation.");
			return;
		}
		Renderer renderer = new TextureRenderer(texID);
		door.attachComponent(renderer);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) { }

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		bugFix(gl);
		
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		//get the Transform component and walk it backwards a bit each frame
//		door.getComponent(Transform.class).translate(0, 0, -0.01f);
		
		//roll pitch and yaw it by 0.01f radians
		if(Keyboard.isKeyDown(KeyEvent.VK_Q))
			door.getComponent(Transform.class).roll(-0.01f);
		if(Keyboard.isKeyDown(KeyEvent.VK_R))
			door.getComponent(Transform.class).roll(0.01f);
		if(Keyboard.isKeyDown(KeyEvent.VK_W))
			door.getComponent(Transform.class).pitch(0.01f);
		if(Keyboard.isKeyDown(KeyEvent.VK_S))
			door.getComponent(Transform.class).pitch(-0.01f);
		if(Keyboard.isKeyDown(KeyEvent.VK_A))
			door.getComponent(Transform.class).yaw(-0.01f);
		if(Keyboard.isKeyDown(KeyEvent.VK_D))
			door.getComponent(Transform.class).yaw(0.01f);
		
		if(Keyboard.isKeyDown(KeyEvent.VK_U))
			door.getComponent(Transform.class).walk(0.01f);
		if(Keyboard.isKeyDown(KeyEvent.VK_J))
			door.getComponent(Transform.class).walk(-0.01f);
		if(Keyboard.isKeyDown(KeyEvent.VK_I))
			door.getComponent(Transform.class).fly(0.01f);
		if(Keyboard.isKeyDown(KeyEvent.VK_K))
			door.getComponent(Transform.class).fly(-0.01f);
		if(Keyboard.isKeyDown(KeyEvent.VK_O))
			door.getComponent(Transform.class).strafe(0.01f);
		if(Keyboard.isKeyDown(KeyEvent.VK_L))
			door.getComponent(Transform.class).strafe(-0.01f);
		
		log.trace(door.getComponent(Transform.class));
		
		gl.glColor3f(0, 1, 0);
		gl.glBegin(GL_QUADS);
			gl.glVertex3f(-1, -1, 0);
			gl.glVertex3f(-1, -1, -50);
			gl.glVertex3f(-1, 1, -50);
			gl.glVertex3f(-1, 1, 0);
			
			gl.glVertex3f(1, -1, -50);
			gl.glVertex3f(1, -1, 0);
			gl.glVertex3f(1, 1, 0);
			gl.glVertex3f(1, 1, -50);
		gl.glEnd();
		
		gl.glPushMatrix();
			//apply the transform to the gl context
			door.getComponent(Transform.class).applyTransform(gl);
			
			//renderer all available renderer components on the entity
			for(Renderer r : door.getComponents(Renderer.class)) {
				r.render(gl);
				gl.glPushMatrix();
				
				//draw flipside image (hacky test)
//				gl.glRotatef(180, 0, 1, 0);
//				r.render(gl);
//				gl.glPopMatrix();
			}
		gl.glPopMatrix();
		
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
