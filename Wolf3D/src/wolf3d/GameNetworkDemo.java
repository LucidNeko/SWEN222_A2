package wolf3d;

import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.media.opengl.GL2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.components.Health;
import wolf3d.components.Inventory;
import wolf3d.components.behaviours.AILookAtController;
import wolf3d.components.behaviours.AddAnimation;
import wolf3d.components.behaviours.AddChaseBehaviour;
import wolf3d.components.behaviours.CameraScrollBackController;
import wolf3d.components.behaviours.DropItem;
import wolf3d.components.behaviours.MouseLookController;
import wolf3d.components.behaviours.PickUp;
import wolf3d.components.behaviours.Translate;
import wolf3d.components.behaviours.WASDWalking;
import wolf3d.components.renderers.PyramidRenderer;
import wolf3d.components.sensors.ProximitySensor;
import wolf3d.networking.Client;
import wolf3d.world.Parser;
import engine.common.Mathf;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Camera;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.components.Renderer;
import engine.core.Entity;
import engine.core.GameLoop;
import engine.core.World;
import engine.display.View;
import engine.input.Keyboard;
import engine.input.Mouse;
import engine.texturing.Material;
import engine.texturing.Mesh;
import engine.texturing.Texture;
import engine.util.Resources;

/**
 * This is a copy of GameDemo
 * made to connect to a server though, and send simple messages anytime an action is performed
 * This is merely to demonstrate the network does work.
 */
public class GameNetworkDemo extends GameLoop {
	private static final Logger log = LogManager.getLogger();

	private static final int FPS = 60; //frames per second/regular updates per second.
	private static final int FUPS = 50; //fixed updates per second.

	private World world;
	private View view;

	private Camera camera;
	private Entity player;

	private Client client;

	/**
	 * Create a new GameDemo with the given world as it's world.
	 * @param world The world.
	 */
	public GameNetworkDemo(World world) {
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
		Parser parser = new Parser("Map.txt");
		parser.passFileToArray();
		parser.createWalls(world);
		parser.createFloor(world);
//ghfix for sameer

//		Mesh linkMesh = Resources.getMesh("link/young_link_s.obj");
//		Texture linkTex = Resources.getTexture("link/young_link.png", true);
//
//		//Create player
//		player = world.createEntity("Player");
//		player.attachComponent(Camera.class);
////		player.attachComponent(PyramidRenderer.class);
//		player.attachComponent(MeshFilter.class).setMesh(linkMesh);
//		player.attachComponent(MeshRenderer.class).setMaterial(new Material(linkTex));
//		player.attachComponent(parser.getWallCollisionComponent());
////		player.attachComponent(WASDWalking.class);
//		player.attachComponent(MouseLookController.class);
//		player.attachComponent(CameraScrollBackController.class);
//		player.attachComponent(Health.class);
//		//Testing pickup behavior
//		player.attachComponent(Inventory.class);
//		player.attachComponent(new Renderer() {
//
//			@Override
//			public void render(GL2 gl) {
//				//because renderering like a scenegraph (0,0,0) is transformed to the entities position.
//				gl.glLightfv(GL_LIGHT0, GL_POSITION, new float[] {0, 0, 0, 1}, 0); //1 signifies positional light
//			}
//
//		});

		//TODO: Entity Factory?
		player = EntityFactory.create(EntityFactory.PLAYER, world, "Player");
//		player.attachComponent(parser.getWallCollisionComponent());
		player.attachComponent(WASDWalking.class);

		camera = EntityFactory.createFirstPersonCamera(world, player).getComponent(Camera.class);

		camera = player.getComponent(Camera.class);
		player.getTransform().translate(1, 0, 1);

		Mesh testMesh = Resources.getMesh("motorbike/katana.obj");
		Texture testTex = Resources.getTexture("motorbike/katana.png", true);

		//teddy
		Entity test = world.createEntity("Test");
		test.attachComponent(MeshFilter.class).setMesh(testMesh);
		test.attachComponent(MeshRenderer.class).setMaterial(new Material(testTex));
		test.attachComponent(ProximitySensor.class).setTarget(player);
		test.attachComponent(new PickUp(world));
		test.attachComponent(new DropItem(world));
		test.getTransform().translate(1, 0, 5);

		Mesh teddyMesh = Resources.getMesh("teddy/teddy.obj");
		Texture teddyTex = Resources.getTexture("teddy/teddy.png", true);


		Entity teddy = world.createEntity("Teddy");
		teddy.attachComponent(MeshFilter.class).setMesh(teddyMesh);
		teddy.attachComponent(MeshRenderer.class).setMaterial(new Material(teddyTex));
		teddy.attachComponent(AILookAtController.class).setTarget(player);
		teddy.attachComponent(AddChaseBehaviour.class);
		teddy.attachComponent(ProximitySensor.class).setTarget(player);;
		teddy.getTransform().translate(15, 0, 3);
		teddy.getTransform().yaw(Mathf.degToRad(180));

		//testing pickup
		teddy.attachComponent(new PickUp(world));
		teddy.attachComponent(new DropItem(world));


		//Create enemy.
		Entity enemy = world.createEntity("Enemy");
		enemy.attachComponent(PyramidRenderer.class);
		enemy.attachComponent(AILookAtController.class);
		enemy.attachComponent(ProximitySensor.class);
		enemy.attachComponent(AddChaseBehaviour.class);
		enemy.getComponent(AILookAtController.class).setTarget(player);
		enemy.getComponent(ProximitySensor.class).setTarget(player);
		enemy.getTransform().translate(0, 0, -10);
		enemy.attachComponent(new Translate(enemy.getTransform().getPosition(), new Vec3(0, 0, -20), 1));
		enemy.getTransform().yaw(Mathf.degToRad(180));

		Texture wallTex = Resources.getTexture("debug_wall.png", true);
		Texture floorTex = Resources.getTexture("debug_floor.png", true);
		Texture doorTex = Resources.getTexture("1.png", true);
		Mesh mesh = Resources.getMesh("wall.obj");



		Entity entity;
		for(int i = 0; i < 20; i+=2) {
			//left wall
			entity = world.createEntity("wall");
			entity.getTransform().translate(-1, 0, -i);
			entity.getTransform().yaw(Mathf.degToRad(-90));
			entity.attachComponent(MeshFilter.class).setMesh(mesh);
			entity.attachComponent(MeshRenderer.class).setMaterial(new Material(wallTex));
			//right wall
			entity = world.createEntity("wall");
			entity.getTransform().translate(1, 0, -i);
			entity.getTransform().yaw(Mathf.degToRad(90));
			entity.attachComponent(MeshFilter.class).setMesh(mesh);
			entity.attachComponent(MeshRenderer.class).setMaterial(new Material(wallTex));
			//floor
			entity = world.createEntity("wall");
			entity.getTransform().translate(0, -1, -i);
			entity.getTransform().pitch(Mathf.degToRad(90));
			entity.attachComponent(MeshFilter.class).setMesh(mesh);
			entity.attachComponent(MeshRenderer.class).setMaterial(new Material(floorTex));
		}
		//door
		entity = world.createEntity("door");
		entity.attachComponent(ProximitySensor.class);
		entity.attachComponent(AddAnimation.class);
		entity.getComponent(ProximitySensor.class).setTarget(player);
		entity.getTransform().translate(0, 0, -5);
		entity.attachComponent(MeshFilter.class).setMesh(mesh);
		entity.attachComponent(MeshRenderer.class).setMaterial(new Material(doorTex));
		//reverse door
		entity = world.createEntity("door");
		entity.attachComponent(ProximitySensor.class);
		entity.attachComponent(AddAnimation.class);
		entity.getComponent(ProximitySensor.class).setTarget(player);
		entity.getTransform().translate(0, 0, -5);
		entity.getTransform().yaw(Mathf.degToRad(180));
		entity.attachComponent(MeshFilter.class).setMesh(mesh);
		entity.attachComponent(MeshRenderer.class).setMaterial(new Material(doorTex));
	}

	@Override
	protected void tick(float delta) {
		//escape closes the game.
		if(Keyboard.isKeyDown(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}

		if(Keyboard.isKeyDownOnce(KeyEvent.VK_X)) {
			log.trace("Pressed X");
		}

		//if control is held down frees the mouse.
		if(Keyboard.isKeyDown(KeyEvent.VK_CONTROL))
			Mouse.setGrabbed(false);
		else Mouse.setGrabbed(true);

		//Update all the behaviours attached to the entities.
		for(Entity entity : world.getEntities()) {
			for(Behaviour behaviour : entity.getComponents(Behaviour.class)) {
				behaviour.update(delta);
				//SEND things, Simon, TO NETWORK HERE.
				if(behaviour.hasChanged()){
					//send over network.
					String msg = "Player has moved";
					client.sendMessage(msg.getBytes());
					behaviour.notifyObservers();
				}
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

	public void initConnection(int port, String ip) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		client = new Client("Bob", ip, port);
	}

}
