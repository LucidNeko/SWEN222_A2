package wolf3d.tests;

import org.junit.Test;

import wolf3d.components.Health;
import wolf3d.components.Inventory;
import wolf3d.components.Strength;
import wolf3d.components.Weight;
import wolf3d.database.WorldBuilder;
import engine.components.Transform;

/**
 * Test suite for the wolf3d.database WorldBuilder class.
 *
 * @author Joshua van Vliet
 *
 */
public class WorldBuilderTests {
	private WorldBuilder builder;

	@Test
	//Test player creates without crashing.
	public void testCreatePlayer() {
		builder = new WorldBuilder("map00/");
		//Create required parameters to call createPlayer
		int uniqueID = (-2);
		String name = "Player";
		Transform transform = new Transform();
		Health health = new Health();
		Strength strength = new Strength();
		Weight weight = new Weight();
		Inventory inventory = new Inventory();
		//Create player
		builder.createPlayer(uniqueID, name, transform, health, strength, weight, inventory);
	}

	@Test
	//Test camera creates without crashing, needs a player to be created first.
	public void testCreateCamera() {
		builder = new WorldBuilder("map00/");
		//Create required parameters to call createPlayer
		int uniqueID = (-3);
		String name = "Player";
		Transform transform = new Transform();
		Health health = new Health();
		Strength strength = new Strength();
		Weight weight = new Weight();
		Inventory inventory = new Inventory();
		//Create player then camera
		builder.createPlayer(uniqueID, name, transform, health, strength, weight, inventory);
		builder.createCamera();
	}

	@Test
	//Test default objects are created without crashing, needs a player to be created first.
	public void testCreateDefaultObjects() {
		builder = new WorldBuilder("map00/");
		//Create required parameters to call createPlayer
		int uniqueID = (-4);
		String name = "Player";
		Transform transform = new Transform();
		Health health = new Health();
		Strength strength = new Strength();
		Weight weight = new Weight();
		Inventory inventory = new Inventory();
		//Create player then default objects
		builder.createPlayer(uniqueID, name, transform, health, strength, weight, inventory);
		builder.createDefaultObjects();
	}
}
