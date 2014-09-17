package wolf3d.window;

import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.common.Mathf;
import wolf3d.components.Camera;
import wolf3d.components.Transform;
import wolf3d.components.renderers.TextureRenderer;
import wolf3d.components.renderers.PyramidRenderer;
import wolf3d.core.Entity;
import wolf3d.core.GameLoop;
import wolf3d.core.Keyboard;
import wolf3d.core.Mouse;
import wolf3d.world.World;

public class GameDemo extends GameLoop {
	private static final Logger log = LogManager.getLogger();

	private static final int FPS = 60; //frames per second/regular updates per second.
	private static final int FUPS = 50; //fixed updates per second.
	
	private World world;
	private View view;
	
	private Camera camera;
	private Entity player;
	
	public GameDemo(World world) {
		super(FPS, FUPS);
		this.world = world;
		createEntities();
	}
	
	public void setView(View view) {
		view.setCamera(camera);
		this.view = view;
	}
	
	private void createEntities() {
		//Create player
		player = new Entity(0, Transform.class);
		player.attachComponent(PyramidRenderer.class);
		player.getTransform().translate(0, 0, -10);
		camera = player.attachComponent(Camera.class);
		world.register(player);		

		int texID = 2;
		int wallID = 3;
		int floorID = 4;
		
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
		if(Keyboard.isKeyDown(KeyEvent.VK_ESCAPE))
			System.exit(0);

		if(Keyboard.isKeyDown(KeyEvent.VK_CONTROL))
			Mouse.setGrabbed(false);
		else Mouse.setGrabbed(true);

		
		Transform transform = player.getTransform();
		//camera movement WASD and J/L for yaw
		if(Keyboard.isKeyDown(KeyEvent.VK_W))
			transform.walkFlat(0.075f);
		if(Keyboard.isKeyDown(KeyEvent.VK_S))
			transform.walkFlat(-0.075f);
		if(Keyboard.isKeyDown(KeyEvent.VK_A))
			transform.strafeFlat(0.075f);
		if(Keyboard.isKeyDown(KeyEvent.VK_D))
			transform.strafeFlat(-0.075f);

		if(Keyboard.isKeyDown(KeyEvent.VK_J))
			transform.rotateY(0.03f);
		if(Keyboard.isKeyDown(KeyEvent.VK_L))
			transform.rotateY(-0.03f);
		if(Keyboard.isKeyDown(KeyEvent.VK_I))
			transform.pitch(-0.03f);
		if(Keyboard.isKeyDown(KeyEvent.VK_K))
			transform.pitch(0.03f);
		if(Keyboard.isKeyDown(KeyEvent.VK_U))
			transform.roll(-0.03f);
		if(Keyboard.isKeyDown(KeyEvent.VK_O))
			transform.roll(0.03f);
		
		if(Keyboard.isKeyDown(KeyEvent.VK_R))
			transform.flyVertical(0.03f);
		if(Keyboard.isKeyDown(KeyEvent.VK_F))
			transform.flyVertical(-0.03f);
		
		//Mouse-look
		float dx = Mouse.getDX();
		float dy = Mouse.getDY();
		transform.pitch(Mathf.degToRad(dy));
		transform.rotateY(Mathf.degToRad(dx)); 
		
		//camera
		float dw = Mouse.getDWheel();
		if(dw != 0) {
			player.getComponent(Camera.class).getTransform().walk(-dw);
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
