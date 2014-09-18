package wolf3d.window;

import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.common.Mathf;
import wolf3d.components.Camera;
import wolf3d.components.Transform;
import wolf3d.components.behaviours.Behaviour;
import wolf3d.components.behaviours.CameraScrollBackController;
import wolf3d.components.behaviours.MouseLookController;
import wolf3d.components.behaviours.WASDController;
import wolf3d.components.renderers.TextureRenderer;
import wolf3d.components.renderers.PyramidRenderer;
import wolf3d.core.Entity;
import wolf3d.core.GameLoop;
import wolf3d.core.Keyboard;
import wolf3d.core.Mouse;
import wolf3d.world.World;

/**
 * GameDemo is a demo game that shows off the GameLoop class and the Entity/Component system.
 * @author Hamish Rae-Hodgson
 *
 */
public class GameDemo extends GameLoop {
	private static final Logger log = LogManager.getLogger();

	private static final int FPS = 60; //frames per second/regular updates per second.
	private static final int FUPS = 50; //fixed updates per second.
	
	private World world;
	private View view;
	
	private Camera camera;
	private Entity player;
	
	/**
	 * Create a new GameDemo with the given world as it's world.
	 * @param world The world.
	 */
	public GameDemo(World world) {
		super(FPS, FUPS);
		this.world = world;
		createEntities();
	}
	
	/**
	 * Set the View that is the renderer of the world. So we can call display() as required.
	 * @param view The View.
	 */
	public void setView(View view) {
		view.setCamera(camera);
		this.view = view;
	}
	
	private void createEntities() {
		//Create player
		player = new Entity(0, Transform.class,   Camera.class, PyramidRenderer.class, 
							   WASDController.class, MouseLookController.class, 
							   CameraScrollBackController.class);
		camera = player.getComponent(Camera.class);
		player.getTransform().translate(0, 0, -10);
		world.register(player);		

		int texID = 1;
		int wallID = 2;
		int floorID = 3;
		
		Entity entity;
		//left wall
		entity = new Entity(1, Transform.class);
		entity.getTransform().translate(-1, 0, 0);
		entity.getTransform().yaw(Mathf.degToRad(-90));
		entity.attachComponent(new TextureRenderer(wallID, 0, -1, 20, 1, 10, 1));
		world.register(entity);
		//right wall		
		entity = new Entity(2, Transform.class);
		entity.getTransform().translate(1, 0, 0);
		entity.getTransform().yaw(Mathf.degToRad(90));
		entity.attachComponent(new TextureRenderer(wallID, -20, -1, 0, 1, 10, 1));
		world.register(entity);
		//floor		
		entity = new Entity(3, Transform.class);
		entity.getTransform().translate(0, -1, 0);
		entity.getTransform().pitch(Mathf.degToRad(90));
		entity.attachComponent(new TextureRenderer(floorID, -1, 0, 1, 20, 1, 10));
		world.register(entity);
		//door
		entity = new Entity(4, Transform.class);
		entity.getTransform().translate(0, 0, -10);
		entity.attachComponent(new TextureRenderer(texID, -1, -1, 1, 1, 1, 1));
		world.register(entity);
		//reverse door
		entity = new Entity(5, Transform.class);
		entity.getTransform().translate(0, 0, -10);
		entity.getTransform().yaw(Mathf.degToRad(180));
		entity.attachComponent(new TextureRenderer(texID, -1, -1, 1, 1, 2, 1));
		world.register(entity);
	}

	@Override
	protected void tick(float delta) {
		//escape closes the game.
		if(Keyboard.isKeyDown(KeyEvent.VK_ESCAPE))
			System.exit(0);

		//if control is held down frees the mouse.
		if(Keyboard.isKeyDown(KeyEvent.VK_CONTROL))
			Mouse.setGrabbed(false);
		else Mouse.setGrabbed(true);
		
		//Update all the behaviours attached to the entities.
		for(Entity entity : world.getEntities()) {
			for(Behaviour behaviour : entity.getComponents(Behaviour.class)) {
				behaviour.update(delta);
			}
		}
	}

	@Override
	protected void fixedTick(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void render() {
		if(view != null) view.display();
	}
	
}
