package wolf3d.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import wolf3d.components.Health;
import wolf3d.components.Inventory;
import wolf3d.components.Strength;
import wolf3d.components.Weight;
import wolf3d.database.WorldBuilder;
import engine.components.Transform;
import engine.core.World;
import engine.util.ServiceLocator;

/**
 * Test suite for the wolf3d.database WorldBuilder class.
 *
 * @author Joshua van Vliet
 *
 */
public class WorldBuilderTests {
	private WorldBuilder builder;
	private World world;

	int uniqueId;
	String name;
	Transform transform;
	Health health;
	Strength strength;
	Weight weight;
	Inventory inventory;

	@Before
	//Create required parameters to call createPlayer
	public void initPlayerParams() {
		world = ServiceLocator.getService(World.class);
		world.destroyEntity(-1);
		uniqueId = (-1);
		name = "Player";
		transform = new Transform();
		health = new Health();
		strength = new Strength();
		weight = new Weight();
		inventory = new Inventory();
	}

	@Test
	//Test player creates without crashing.
	public void testCreatePlayerCrash() {
		builder = new WorldBuilder("map00/");
		//Create player
		builder.createPlayer(uniqueId, name, transform, health, strength, weight, inventory);
	}

	@Test
	//Test camera creates without crashing, needs a player to be created first.
	public void testCreateCameraCrash() {
		builder = new WorldBuilder("map00/");

		//Create player then camera
		builder.createPlayer(uniqueId, name, transform, health, strength, weight, inventory);
		builder.createCamera();
	}

	@Test
	//Test default objects are created without crashing, needs a player to be created first.
	public void testCreateDefaultObjectsCrash() {
		builder = new WorldBuilder("map00/");
		//Create player then default objects
		builder.createPlayer(uniqueId, name, transform, health, strength, weight, inventory);
		builder.createDefaultObjects();
	}

	@Test
	//Test player is actually created.
	public void testCreatePlayerExists() {
		builder = new WorldBuilder("map00/");
		//Create required parameters to call createPlayer
		//Create player
		builder.createPlayer(uniqueId, name, transform, health, strength, weight, inventory);
		//Does player exist by that ID?
		assertTrue(builder.getWorld().getEntity(uniqueId)!=null);
	}
}
