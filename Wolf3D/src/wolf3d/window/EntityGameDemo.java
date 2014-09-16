package wolf3d.window;

import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.common.Mathf;
import wolf3d.components.Camera;
import wolf3d.components.ICamera;
import wolf3d.components.Sprite;
import wolf3d.components.Transform;
import wolf3d.components.renderers.TextureRenderer;
import wolf3d.components.renderers.PyramidRenderer;
import wolf3d.core.Entity;
import wolf3d.core.GameLoop;
import wolf3d.core.Keyboard;
import wolf3d.core.Mouse;
import wolf3d.world.World;

public class EntityGameDemo extends GameLoop {
	private static final Logger log = LogManager.getLogger();

	private static final int FPS = 60; //frames per second/regular updates per second.
	private static final int FUPS = 50; //fixed updates per second.
	
	private World world;
	private View view;
	
	private Camera camera;
	private Entity player;
	
	public EntityGameDemo(World world) {
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
		//walls
		for(int i = 0; i < 20; i++) {
			Entity entity;
			//left wall
			entity = buildSurface(1+i, wallID);
			entity.getTransform().translate(-1, 0, -i*2);
			entity.getTransform().yaw(Mathf.degToRad(-90));
			world.register(entity);
			//right wall
			entity = buildSurface(21+i, wallID);
			entity.getTransform().translate(1, 0, -i*2);
			entity.getTransform().yaw(Mathf.degToRad(90));
			world.register(entity);
			//floor
			entity = buildSurface(41+i, floorID);
			entity.getTransform().translate(0, -1, -i*2);
			entity.getTransform().pitch(Mathf.degToRad(90));
			world.register(entity);
		}
		
		//door
		Entity door;
		door = buildSurface(61, texID);
		door.getTransform().translate(0, 0, -10);
		world.register(door);
		//reverse door
		door = buildSurface(61, texID);
		door.getTransform().translate(0, 0, -10);
		door.getTransform().yaw(Mathf.degToRad(180));
		world.register(door);
	}
	
	private Entity buildSurface(int id, int texID) {
		Entity entity = new Entity(id, Transform.class);
		entity.attachComponent(new Sprite(2,2));
		entity.attachComponent(new TextureRenderer(texID));
		return entity;
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
