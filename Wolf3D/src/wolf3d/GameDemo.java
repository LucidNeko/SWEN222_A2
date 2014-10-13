package wolf3d;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.components.Health;
import wolf3d.components.Weight;
import wolf3d.components.behaviours.AILookAtController;
import wolf3d.components.behaviours.AddAnimation;
import wolf3d.components.behaviours.AddChaseBehaviour;
import wolf3d.components.behaviours.Attackable;
import wolf3d.components.behaviours.DropItem;
import wolf3d.components.behaviours.HealthFlash;
import wolf3d.components.behaviours.PickUp;
import wolf3d.components.behaviours.Translate;
import wolf3d.components.behaviours.animations.die.RotateFlyDieAnimation;
import wolf3d.components.renderers.LightlessMeshRenderer;
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
import engine.components.Transform;
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
 * GameDemo is a demo game that shows off the GameLoop class and the Entity/Component system.
 * @author Hamish Rae-Hodgson
 *
 */
public class GameDemo extends GameLoop {
	private static final Logger log = LogManager.getLogger();

	private static final int FPS = 60; //frames per second/regular updates per second.
	private static final int FUPS = 30; //fixed updates per second.

	private World world;
	private View view;

	private Camera camera;
	private Entity player;

	private Client client;

	/**
	 * Create a new GameDemo with the given world as it's world.
	 * @param world The world.
	 */
	public GameDemo(World world, String ip, int port) {
		super(FPS, FUPS);
		this.world = world;
		try {
			client = new Client("Joe",ip,port,world, this);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void setPlayer(Entity e){
		this.player = e;
	}

	/**
	 * Set the View that is the renderer of the world. So we can call display() as required.
	 * @param view The View.
	 */
	public void setView(View view) {
		view.setCamera(camera);
		this.view = view;
	}

	public void updateCamView(){
		view.setCamera(camera);
	}

	/*
	 * public because network needs to call this...
	 */
	public void createEntities() {
		Parser parser = new Parser("Map3.txt", "Doors2.txt");
//		Parser parser = new Parser("Map.txt", "Doors.txt");
		parser.passWallFileToArray();
		parser.passDoorFileToArray();
		parser.passTextures();
		parser.passfloorFileToArray();
		parser.createWalls(world);
		parser.createFloor(world);


		//player = EntityFactory.create(EntityFactory.PLAYER, world, "Player");
		player.attachComponent(parser.getWallCollisionComponent());
		player.attachComponent(new DropItem(world));
		parser.createDoors(world, player);


//		camera = EntityFactory.createThirdPersonTrackingCamera(world, player).getComponent(Camera.class);
		camera = EntityFactory.createFirstPersonCamera(world, player).getComponent(Camera.class);//
		updateCamView();

//		camera = player.getComponent(Camera.class);
		player.getTransform().translate(1, 0, 1);

		Entity skybox = EntityFactory.createSkybox(world, player);

		Mesh testMesh = Resources.getMesh("motorbike/katana.obj");
		Texture testTex = Resources.getTexture("motorbike/katana.png", true);

		//teddy
		Entity test = world.createEntity("Test");
		test.attachComponent(MeshFilter.class).setMesh(testMesh);
		test.attachComponent(MeshRenderer.class).setMaterial(new Material(testTex));
		test.attachComponent(ProximitySensor.class).setTarget(player);
		test.attachComponent(new PickUp(world));
		test.attachComponent(new Weight(100));
		test.getTransform().translate(1, 0, 5);

		Mesh teddyMesh = Resources.getMesh("teddy/teddy.obj").getScaledInstance(0.5f);
		Texture teddyTex = Resources.getTexture("teddy/teddy.png", true);


		Entity teddy = world.createEntity("Teddy");
		teddy.attachComponent(MeshFilter.class).setMesh(teddyMesh);
		teddy.attachComponent(MeshRenderer.class).setMaterial(new Material(teddyTex));
		teddy.attachComponent(AILookAtController.class).setTarget(player);
		teddy.attachComponent(AddChaseBehaviour.class);
		teddy.attachComponent(ProximitySensor.class).setTarget(player);;
//		teddy.getTransform().translate(15, 0, 3);
		teddy.getTransform().translate(2, 0, 2);
		teddy.getTransform().yaw(Mathf.degToRad(180));

		//testing pickup
//		teddy.attachComponent(new PickUp(world));
//		teddy.attachComponent(Weight.class);
		//testing attack
		teddy.attachComponent(Health.class);
		teddy.attachComponent(new Attackable(world));

		teddy.attachComponent(HealthFlash.class);

		for(Entity e : world.getEntities()) {
			e.getTransform().clearChanged();
		}
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

		//if control is pressed (toggles) frees the mouse.
		if(Keyboard.isKeyDownOnce(KeyEvent.VK_CONTROL))
			Mouse.setGrabbed(!Mouse.isGrabbed());

		//stop the Mouse from freeing itself by going out of the bounds of the component.
		if(Mouse.isGrabbed())
			Mouse.centerMouseOverComponent();

		//Update all the behaviours attached to the entities.
		for(Entity entity : world.getEntities()) {
			for(Behaviour behaviour : entity.getComponents(Behaviour.class)) {
				behaviour.update(delta);
				}

		}
			for(Entity entity : world.getEntities()) {
				Transform transform = entity.getTransform();
				if(transform.hasChanged()) {
					try {
						String name = transform.getOwner().getName();
						if(!(name.equals("skybox") || name.equals("Camera"))){
							client.sendTransform(transform);
							transform.clearChanged();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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


	public Entity getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}


	public void createPlayer(int ID){
		player = EntityFactory.createPlayer(world, "Player", ID);
	}

	public void createOtherPlayer(int ID){
		Entity other = EntityFactory.createOtherPlayer(world, "other", ID);
		other.getTransform().translate(1, 0, 1);
	}

}
