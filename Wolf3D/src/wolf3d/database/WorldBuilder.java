package wolf3d.database;

import java.util.List;

import wolf3d.EntityFactory;
import wolf3d.WorldView;
import wolf3d.components.Health;
import wolf3d.components.Inventory;
import wolf3d.components.Strength;
import wolf3d.components.Weight;
import wolf3d.components.behaviours.AILookAtController;
import wolf3d.components.behaviours.AddChaseBehaviour;
import wolf3d.components.behaviours.Attackable;
import wolf3d.components.behaviours.DropItem;
import wolf3d.components.behaviours.PickUp;
import wolf3d.components.sensors.ProximitySensor;
import wolf3d.world.Parser;
import engine.common.Mathf;
import engine.components.Camera;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.components.Transform;
import engine.core.Entity;
import engine.core.World;
import engine.texturing.Material;
import engine.texturing.Mesh;
import engine.texturing.Texture;
import engine.util.Resources;
import engine.util.ServiceLocator;

/**
 * WorldBuilder is used as a factory to create the world and the world's entities.
 * Including players, the map (walls, floor, etc), and objects (such as those a player can pick up).
 *
 * @author Joshua van Vliet
 *
 */
public class WorldBuilder {
	private World world;
	private Parser parser;
	private Entity player;

	/**
	 * Creates a world from map and door files.
	 *
	 * @param mapFname the filename of the map file referenced.
	 * @param doorsFname the filename of the door file referenced.
	 * @return the world with the walls and doors added, but nothing else.
	 */
	public WorldBuilder(String mapDirName) {
		parser = new Parser(mapDirName);
		this.world = ServiceLocator.getService(World.class);
	}

	public void createCamera() {
		if(player == null) { throw new Error("Player is null"); }
		Camera camera = EntityFactory.createThirdPersonTrackingCamera(player).getComponent(Camera.class);
		ServiceLocator.getService(WorldView.class).setCamera(camera);;

	}

	/**
	 * Creates a player with the given paramaters (uniqueID, name, and unique components)
	 * @param uniqueID
	 * @param name
	 * @param transform
	 * @param health
	 * @param strength
	 * @param weight
	 * @param inventory
	 * @return
	 */
	public Entity createPlayer(int uniqueID, String name, Transform transform,
			Health health, Strength strength, Weight weight, Inventory inventory) {

		Entity player = EntityFactory.createPlayer(name, uniqueID);
		parser.createEntities(player);
		player.attachComponent(parser.getWallCollisionComponent());
		player.attachComponent(DropItem.class);
		parser.createDoors();

		//Transform component
		player.getTransform().set(transform);

		//Health component
		Health h = player.getComponent(Health.class);
		h.setDamageAmt(health.getDamageAmt());
		h.setHealth(health.getHealth());

		//Strength component
		Strength s = player.getComponent(Strength.class);
		s.setStrength(strength.getStrength());

		//Weight component
		Weight w = player.getComponent(Weight.class);
		w.setWeight(weight.getWeight());

		//Inventory component
		Inventory i = player.getComponent(Inventory.class);
		List<Integer> items = i.getItems();
		for (Integer item : items) {
			i.addItem(item);
		}
		this.player = player;
		return player;
	}

	/**
	 * Creates the default objects in the Wolf3D world:
	 * The skybox, as well as a motorbike and a teddy.
	 */
	public void createDefaultObjects() {
		if(player == null) { throw new Error("Player is null"); }

		EntityFactory.createSkybox(player);		// Creates a skybox on the world

		//motorbike
		Mesh testMesh = Resources.getMesh("motorbike/katana.obj");
		Texture testTex = Resources.getTexture("motorbike/katana.png", true);
		Entity test = world.createEntity("Motorbike");
		test.attachComponent(MeshFilter.class).setMesh(testMesh);
		test.attachComponent(MeshRenderer.class).setMaterial(new Material(testTex));
		test.attachComponent(ProximitySensor.class).setTarget(player);
		test.attachComponent(new PickUp());
		test.attachComponent(new Weight(100));
		test.getTransform().translate(1, 0, 5);

		//teddy
		Mesh teddyMesh = Resources.getMesh("teddy/teddy.obj").getScaledInstance(0.5f);
		Texture teddyTex = Resources.getTexture("teddy/teddy.png", true);
		Entity teddy = world.createEntity("Teddy");
		teddy.attachComponent(MeshFilter.class).setMesh(teddyMesh);
		teddy.attachComponent(MeshRenderer.class).setMaterial(new Material(teddyTex));
		teddy.attachComponent(AILookAtController.class).setTarget(player);
		teddy.attachComponent(AddChaseBehaviour.class);
		teddy.attachComponent(ProximitySensor.class).setTarget(player);;
		teddy.getTransform().translate(15, 0, 3);
		teddy.getTransform().yaw(Mathf.degToRad(180));
		teddy.attachComponent(Health.class);
		teddy.attachComponent(Attackable.class);
	}

	public World getWorld(){
		return this.world;
	}
}
