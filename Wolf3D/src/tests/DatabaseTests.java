package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;

import wolf3d.components.Camera;
import wolf3d.components.Component;
import wolf3d.core.Entity;
import wolf3d.database.DataManagement;
import wolf3d.world.World;

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
		component = new Component();
		Component component2 = new Component();
		Camera cc = new Camera();
		a = new Entity(0);
		b = new Entity(1);
		a.attachComponent(component);
		b.attachComponent(component2);
		b.attachComponent(cc);
		World world = new World();
		world.register(a);
		world.register(b);
		String filename = "Wolf3D/src/wolf3d/assets/worlds/testWorld01";
		DataManagement.saveWorld(filename, world);
	}

}
