package wolf3d.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

import wolf3d.EntityFactory;
import wolf3d.components.Weight;
import wolf3d.components.behaviours.PickUp;
import wolf3d.components.sensors.ProximitySensor;
import wolf3d.database.DataManagement;

import com.google.gson.Gson;

import engine.components.Transform;
import engine.components.Component;
import engine.core.Entity;
import engine.core.World;

/**
 * Test suite for the Wolf3D database.
 *
 * @author Joshua van Vliet
 *
 */
public class DatabaseTests {

	private Entity a;
	private Entity b;
	private Component component;


	@Test
	// Test component converts to Json without crashing
	public void testCompToJson() {
		component = new Component();
		Gson gson = new Gson();
		String json = gson.toJson(component);
	}

	@Test
	// Test class fromJson is correct for a component
	public void testCompToJsonAndBack() {
		component = new Component();
		Gson gson = new Gson();
		String json = gson.toJson(component);
		assertTrue(gson.fromJson(json, Component.class) instanceof Component);
	}

	@Test
	// Test a world saves without crashing.
	public void testSaveWorldCrash() {
		World world = createDummyWorldWithPlayer();
		DataManagement.saveWorld("testSaveWorldCrash.txt", world);
	}

	@Test
	// Test a world loads correctly, only checks entities and their Transform component
	public void testLoadWorld() {
		World world = null;
		World dummy = createDummyWorldWithPlayer();
		DataManagement.saveWorld("test_testLoadWorld.txt", dummy);
		try {
			world = DataManagement.loadWorld("test_testLoadWorld.txt");
		} catch (IOException e) {
			fail(e.getMessage());
		}
		Collection<Entity> loadedEntities = world.getEntities();
		Collection<Entity> controlEntities = dummy.getEntities();
		assertFalse(loadedEntities.isEmpty());		// Check something loaded
		for (Entity le : loadedEntities) {
			for (Entity ce : controlEntities) {
				if (le.getComponent(Transform.class).getPosition() ==
						ce.getComponent(Transform.class).getPosition()) {
					loadedEntities.remove(le);		// Remove matching entities
					controlEntities.remove(le);
				}
			}
		}
		assertTrue(loadedEntities.isEmpty());
		assertTrue(controlEntities.isEmpty());
	}

	//============================================================================
	//========== Helper methods ==================================================
	//============================================================================

	private World createDummyWorld() {
		World world = new World();
		a = world.createEntity("Player");
		//b = world.createEntity("Other");
		return world;
	}

	private World createDummyWorldWithPlayer(){
		Entity item1, item2, item3, player;
		//create world with player holding three items
		World world = new World();
		player = EntityFactory.createPlayer(world, "Player",-1);
		item1 = world.createEntity("motorbike");
		item1.attachComponent(ProximitySensor.class).setTarget(player);;
		item1.attachComponent(new PickUp(world));
		item1.attachComponent(new Weight(50));
		item1.getComponent(PickUp.class).pickUpItem();

		item2 = world.createEntity("bottle");
		item2.attachComponent(ProximitySensor.class).setTarget(player);;
		item2.attachComponent(new PickUp(world));
		item2.attachComponent(Weight.class);
		item2.getComponent(PickUp.class).pickUpItem();

		item3 = world.createEntity("jar");
		item3.attachComponent(ProximitySensor.class).setTarget(player);;
		item3.attachComponent(new PickUp(world));
		item3.attachComponent(Weight.class);
		item3.getComponent(PickUp.class).pickUpItem();

		return world;
	}
}
