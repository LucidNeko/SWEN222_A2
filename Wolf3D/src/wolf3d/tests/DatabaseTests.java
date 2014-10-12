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
	// Test a world saves without crashing.
	public void testSaveWorldCrash() {
		World world = createDummyWorld();
		DataManagement.saveWorld("worldSave.txt", world);
	}

	@Test
	// Test a world loads without crashing
	public void testLoadWorldCrash() {
		try {
			World world = DataManagement.loadWorld("worldSave.txt");
		} catch (IOException e) {
			fail(e.getMessage());
		}		
	}
	
	@Test
	// Test a world loads correctly
	public void testLoadWorld() {
		World world = null;
		try {
			world = DataManagement.loadWorld("worldSave.txt");
		} catch (IOException e) {
			fail(e.getMessage());
		}		
		// CHECK EACH ENTITY IS THE SAME AS IN CREATEDUMMYWORLD()
	}

	//============================================================================
	//========== Helper methods ==================================================
	//============================================================================

	private World createDummyWorld() {
		World world = new World();
		a = world.createEntity("entA");
		b = world.createEntity("entB");
		return world;
	}
}
