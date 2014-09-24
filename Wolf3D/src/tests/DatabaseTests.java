package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gson.Gson;

import wolf3d.components.Camera;
import wolf3d.components.Component;
import wolf3d.core.Entity;
import wolf3d.database.DataManagement;
import wolf3d.core.World;

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
	// Test a world saves without crashing
	public void testSaveWorld() {
		Gson gson = new Gson();
		World world = createDummyWorld();
		DataManagement.saveWorld("testWorld01", world);
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
