package wolf3d.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

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
		World world = createDummyWorld();
		DataManagement.saveWorld("testSaveWorldCrash.txt", world);
	}

	@Test
	// Test a world loads correctly, only checks entities and their Transform component
	public void testLoadWorld() {
		World world = null;
		World dummy = createDummyWorld();
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
		a = world.createEntity("entA");
		//b = world.createEntity("entB");
		return world;
	}
}
