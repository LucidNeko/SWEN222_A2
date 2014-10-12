package wolf3d.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

import wolf3d.database.DataManagement;

import com.google.gson.Gson;

import engine.components.Camera;
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
	// Tests Gson parsing a world's entity collection
	public void testParseEntities() {
		Gson gson = new Gson();
		World world = createDummyWorld();
		Collection<Entity> entities = world.getEntities();
		String es;

		for (Entity entity : entities) {
			// get each component
		}
	}

	@Test
	// Test a world saves without crashing.
	// Will overwrite existing saved world.
	public void testSaveWorld() {
		Gson gson = new Gson();
		World world = createDummyWorld();
		DataManagement.saveWorld(world);
	}

	@Test
	// Test a world loads without crashing
	public void testLoadWorld() {
		Gson gson = new Gson();
		try {
			World world = DataManagement.loadWorld();
		} catch (IOException e) {
			fail(e.getMessage());
		}

	}

	//============================================================================
	//========== Helper methods ==================================================
	//============================================================================

	private World createDummyWorld() {
		World world = new World();
		component = new Component();
		Component component2 = new Component();
		Camera cc = new Camera();
		a = world.createEntity("entA");
		b = world.createEntity("entB");
		return world;
	}
}
