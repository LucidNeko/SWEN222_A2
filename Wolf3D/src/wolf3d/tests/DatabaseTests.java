package wolf3d.tests;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

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
	// Test a world saves without crashing
	public void testSaveWorld() {
//		Gson gson = new Gson();
//		World world = createDummyWorld();
//		DataManagement.saveWorld("testWorld01", world);
	}

	//============================================================================
	//========== Helper methods ==================================================
	//============================================================================

	private World createDummyWorld() {
		World world = new World();
		component = new Component();
		Component component2 = new Component();
		Camera cc = new Camera();
		a = world.createEntity("0");
		b = world.createEntity("1");
		a.attachComponent(component);
		b.attachComponent(component2);
		b.attachComponent(cc);
		return world;
	}
}
