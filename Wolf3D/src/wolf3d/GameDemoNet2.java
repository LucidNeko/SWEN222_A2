package wolf3d;

import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import wolf3d.components.Health;
import wolf3d.components.Weight;
import wolf3d.components.behaviours.AILookAtController;
import wolf3d.components.behaviours.AddAnimation;
import wolf3d.components.behaviours.AddChaseBehaviour;
import wolf3d.components.behaviours.Attackable;
import wolf3d.components.behaviours.DropItem;
import wolf3d.components.behaviours.PickUp;
import wolf3d.components.behaviours.Translate;
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
public class GameDemoNet2 extends GameLoop {
	private static final Logger log = LogManager.getLogger();

	private static final int FPS = 40; //frames per second/regular updates per second.
	private static final int FUPS = 40; //fixed updates per second.

	private World world;
	private View view;

	private Camera camera;
	private Entity player;
	
	private Client client;

	/**
	 * Create a new GameDemo with the given world as it's world.
	 * @param world The world.
	 */
	public GameDemoNet2(World world) {
		super(FPS, FUPS);
		this.world = world;
		createEntities();
	}

	public GameDemoNet2(World world, String ip, int port) {
		// TODO Auto-generated constructor stub

		super(FPS, FUPS);
		this.world = world;
		
		//dont create the entities until the game starts
		//createEntities();
		
		try {
			client = new Client("Joe",ip,port,this);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		Parser parser = new Parser("Map.txt", "Doors.txt");
		parser.passWallFileToArray();
		parser.passDoorFileToArray();
		parser.passTextures();
		parser.passfloorFileToArray();
		parser.createWalls(world);
		parser.createFloor(world);


		//TODO: Entity Factory?
		
		//already made.
		//player = EntityFactory.create(EntityFactory.PLAYER, world, "Player");
		player.attachComponent(parser.getWallCollisionComponent());
		player.attachComponent(new DropItem(world));
		
		
		parser.createDoors(world, player);


		camera = EntityFactory.createThirdPersonTrackingCamera(world, player).getComponent(Camera.class);
//		camera = EntityFactory.createFirstPersonCamera(world, player).getComponent(Camera.class);//

		EntityFactory.createSun(world);

		//camera = player.getComponent(Camera.class);
		player.getTransform().translate(1, 0, 1);

		Entity skybox = world.createEntity("skybox");
		skybox.attachComponent(MeshFilter.class).setMesh(Resources.getMesh("skybox.obj"));
		skybox.attachComponent(LightlessMeshRenderer.class).setMaterial(new Material(Resources.getTexture("skybox3.png", true)));
		skybox.attachComponent(new Behaviour() {
			//moves the box around with player so they can't come close to the edges.
			@Override
			public void update(float delta) {
				Vec3 pos = player.getTransform().getPosition();
				this.getOwner().getTransform().setPosition(pos);
			}

		});

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

		Mesh teddyMesh = Resources.getMesh("teddy/teddy.obj").getScaledInstance(4);
		Texture teddyTex = Resources.getTexture("teddy/teddy.png", true);


		Entity teddy = world.createEntity("Teddy");
		teddy.attachComponent(MeshFilter.class).setMesh(teddyMesh);
		teddy.attachComponent(MeshRenderer.class).setMaterial(new Material(teddyTex));
//		teddy.attachComponent(engine.scratch.WireframeMeshRenderer.class);
		teddy.attachComponent(AILookAtController.class).setTarget(player);
		teddy.attachComponent(AddChaseBehaviour.class);
		teddy.attachComponent(ProximitySensor.class).setTarget(player);;
		//animation
//		teddy.attachComponent(AddAnimation.class).setAttachment(DieAnimation.class);
		teddy.getTransform().translate(15, 0, 3);
		teddy.getTransform().yaw(Mathf.degToRad(180));

		//testing pickup
//		teddy.attachComponent(new PickUp(world));
//		teddy.attachComponent(Weight.class);
		//testing attack
		teddy.attachComponent(Health.class);
		teddy.attachComponent(new Attackable(world));

		//Create enemy.
		Entity enemy = world.createEntity("Enemy");
		enemy.attachComponent(PyramidRenderer.class);
		enemy.attachComponent(AILookAtController.class);
		enemy.attachComponent(ProximitySensor.class);
		enemy.attachComponent(AddChaseBehaviour.class);
		enemy.getComponent(AILookAtController.class).setTarget(player);
		enemy.getComponent(ProximitySensor.class).setTarget(player);
		enemy.attachComponent(Health.class);
		enemy.attachComponent(new Attackable(world));
		enemy.getTransform().translate(0, 0, -10);
		enemy.attachComponent(new Translate(enemy.getTransform().getPosition(), new Vec3(0, 0, -20), 1));
		enemy.getTransform().yaw(Mathf.degToRad(180));

		Texture wallTex = Resources.getTexture("debug_wall.png", true);
		Texture floorTex = Resources.getTexture("floorTextures/0.png", true);
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
				if(behaviour.hasChanged()){
					//send over network.

					try {
						client.sendToServer("transform");
						Entity owner = behaviour.getOwner();
						client.sendToServer(owner.getID());
						Gson gs = new Gson();
						client.sendToServer(gs.toJson(owner.getTransform()));
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	protected void fixedTick(float delta) {
		// TODO Auto-generated method stub

	}
	
	public void createPlayer(int ID){
			player = EntityFactory.createPlayerWithID(world, "Player", ID);
	}
	
	public void createOtherPlayer(int ID){
		Entity other = EntityFactory.createOtherPlayer(world, "other", ID);
		other.getTransform().translate(1, 0, 1);
	}

	@Override
	protected void render() {
		if(view != null) view.display();
	}

	public void receiveMessage(DataInputStream msg) throws IOException {
		// TODO Auto-generated method stub

		String st = msg.readUTF();
		System.out.println(st);
		if(st.equals("transform")){
			int id = msg.readInt();
			System.out.println("On id: "+id);
			Entity ent = world.getEntity(id);
			Transform t;
			Gson g = new Gson();
			t = g.fromJson(msg.readUTF(), Transform.class);
			System.out.println("The created transform: " + t.toString());
			ent.getTransform().set(t);
		}
		if(st.equals("message")){
			
		}
		if(st.equals("ids")){
			int playerID = msg.readInt();
			createPlayer(playerID);
			System.out.println("Your ID is: "+playerID);
			System.out.println("Other IDs are: ");
			int noOthers = msg.readInt();
			for(int i = 0; i< noOthers; i++){
				int otID = msg.readInt();
				createOtherPlayer(otID);
				System.out.println(otID);
			}
		}
		if(st.equals("begin")){
			createEntities();
			this.run();
		}

			// TODO Auto-generated method stub
	}

}