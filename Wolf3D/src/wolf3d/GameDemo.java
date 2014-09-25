package wolf3d;

import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.components.behaviours.AILookAtController;
import wolf3d.components.behaviours.AddAnimation;
import wolf3d.components.behaviours.AddChaseBehaviour;
import wolf3d.components.behaviours.CameraScrollBackController;
import wolf3d.components.behaviours.Health;
import wolf3d.components.behaviours.MouseLookController;
import wolf3d.components.behaviours.Translate;
import wolf3d.components.behaviours.WASDFlying;
import wolf3d.components.behaviours.WASDWalking;
import wolf3d.components.renderers.PyramidRenderer;
import wolf3d.components.sensors.ProximitySensor;
import wolf3d.world.Parser;

import com.jogamp.opengl.util.awt.TextureRenderer;

import engine.common.Mathf;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Camera;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
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
		Parser parser = new Parser("Map.txt");
		parser.passFileToArray();
		parser.createWalls(world);
		parser.createFloor(world);
		
		//Create player
		player = world.createEntity("Player");
		player.attachComponent(Camera.class);
		player.attachComponent(PyramidRenderer.class);
		player.attachComponent(parser.getWallCollisionComponent());
//		player.attachComponent(WASDWalking.class);
		player.attachComponent(MouseLookController.class);
		player.attachComponent(CameraScrollBackController.class);
		player.attachComponent(Health.class);

		camera = player.getComponent(Camera.class);
		player.getTransform().translate(1, 0, 1);

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
