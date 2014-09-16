package wolf3d.window;

import static javax.media.opengl.GL.*;
import static javax.media.opengl.GL2.*;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.glu.GLU;

import wolf3d.common.Mathf;
import wolf3d.components.Sprite;
import wolf3d.components.Transform;
import wolf3d.components.renderers.Renderer;
import wolf3d.components.renderers.TextureRenderer;
import wolf3d.core.Camera;
import wolf3d.core.Entity;
import wolf3d.core.Keyboard;
import wolf3d.core.Mouse;
import wolf3d.util.ResourceLoader;

public class EntityDemo extends GamePanel {
	private static final long serialVersionUID = -5693253200279106797L;

	//Used in gluPerspective
	private static final float FIELD_OF_VIEW = 45;
	private static final float ZNEAR = 0.1f;
	private static final float ZFAR = 100;

	Entity player;
	Entity door;
	List<Entity> walls = new ArrayList<Entity>();

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
		//Create player
		player = new Entity(100, Transform.class);
		player.attachComponent(Camera.class);

		//Create entity
		door = new Entity(0);

		//add transform
		Transform t = door.attachComponent(Transform.class); //if constructor takes 0 arguments can attach this way.
		t.translate(0, 0, -10);

		//Add a sprite
		Sprite sprite = new Sprite(2, 2);
		door.attachComponent(sprite);

		//Add a renderer
		int texID = ResourceLoader.loadTexture(gl, "1.png", true);
		if(texID == -1) {
			log.error("Failed importing resource. Aborting Renderer creation.");
			return;
		}
		Renderer renderer = new TextureRenderer(texID);
		door.attachComponent(renderer);

		int wallID = ResourceLoader.loadTexture(gl, "debug_wall.png", true);
		int floorID = ResourceLoader.loadTexture(gl, "debug_floor.png", true);
		//walls
		for(int i = 0; i < 20; i++) {
			//left wall
			{
				Entity wall = new Entity(1+i, Transform.class);
				wall.getComponent(Transform.class).translate(-2, 0, -i*2);
				wall.getComponent(Transform.class).yaw(Mathf.degToRad(-90));

				wall.attachComponent(new Sprite(2, 2));

				//Add a renderer
				if(texID != -1) {
					wall.attachComponent(new TextureRenderer(wallID));
				}

				walls.add(wall);
			}
			//right wall
			{
				Entity wall = new Entity(1+i, Transform.class);
				wall.getComponent(Transform.class).translate(2, 0, -i*2);
				wall.getComponent(Transform.class).yaw(Mathf.degToRad(90));

				wall.attachComponent(new Sprite(2, 2));

				//Add a renderer
				if(texID != -1) {
					wall.attachComponent(new TextureRenderer(wallID));
				}

				walls.add(wall);
			}
			//floor
			{
				Entity wall = new Entity(1+i, Transform.class);
				wall.getComponent(Transform.class).translate(0, -1, -i*2);
				wall.getComponent(Transform.class).pitch(Mathf.degToRad(90));

				wall.attachComponent(new Sprite(4, 2));

				//Add a renderer
				if(texID != -1) {
					wall.attachComponent(new TextureRenderer(floorID));
				}

				walls.add(wall);
			}
		}
	}

	@Override
	public void dispose(GLAutoDrawable drawable) { }

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		bugFix(gl);

		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		if(Keyboard.isKeyDown(KeyEvent.VK_ESCAPE))
			System.exit(0);

		if(Keyboard.isKeyDown(KeyEvent.VK_CONTROL))
			Mouse.setGrabbed(false);
		else Mouse.setGrabbed(true);

		//camera movement WASD and J/L for yaw
		Camera camera = player.getComponent(Camera.class);
		if(Keyboard.isKeyDown(KeyEvent.VK_W))
			camera.walkXZ(0.075f);
		if(Keyboard.isKeyDown(KeyEvent.VK_S))
			camera.walkXZ(-0.075f);
		if(Keyboard.isKeyDown(KeyEvent.VK_A))
			camera.strafeXZ(-0.075f);
		if(Keyboard.isKeyDown(KeyEvent.VK_D))
			camera.strafeXZ(0.075f);

		if(Keyboard.isKeyDown(KeyEvent.VK_J))
			camera.rotateY(0.03f);
		if(Keyboard.isKeyDown(KeyEvent.VK_L))
			camera.rotateY(-0.03f);
		if(Keyboard.isKeyDown(KeyEvent.VK_I))
			camera.pitch(-0.03f);
		if(Keyboard.isKeyDown(KeyEvent.VK_K))
			camera.pitch(0.03f);
		if(Keyboard.isKeyDown(KeyEvent.VK_U))
			camera.roll(-0.03f);
		if(Keyboard.isKeyDown(KeyEvent.VK_O))
			camera.roll(0.03f);
		
		if(Keyboard.isKeyDown(KeyEvent.VK_R))
			camera.flyVertical(0.03f);
		if(Keyboard.isKeyDown(KeyEvent.VK_F))
			camera.flyVertical(-0.03f);
		float dx = Mouse.getDX();
		float dy = Mouse.getDY();

//		log.trace("({}, {})", dx, dy);

		camera.pitch(Mathf.degToRad(dy));
		camera.rotateY(Mathf.degToRad(-dx));

		gl.glPushMatrix();
			player.getComponent(Camera.class).setActive(gl);

			for(Entity wall : walls) {
				gl.glPushMatrix();
					wall.getComponent(Transform.class).applyTransform(gl);
					wall.getComponent(Renderer.class).render(gl);
				gl.glPopMatrix();
			}

			gl.glPushMatrix();
				//apply the transform to the gl context
				door.getComponent(Transform.class).applyTransform(gl);

				//renderer all available renderer components on the entity
				for(Renderer r : door.getComponents(Renderer.class)) {
					r.render(gl);
					gl.glPushMatrix();
					gl.glRotated(180, 0, 1, 0);
					r.render(gl);
					gl.glPopMatrix();
				}
			gl.glPopMatrix();
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

		gl.glViewport(0, 0, width, height);
	}

}
