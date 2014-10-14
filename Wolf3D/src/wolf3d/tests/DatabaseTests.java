package wolf3d.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import wolf3d.EntityFactory;
import wolf3d.components.Weight;
import wolf3d.components.behaviours.PickUp;
import wolf3d.components.sensors.ProximitySensor;
import wolf3d.database.DataManagement;
import wolf3d.world.Parser;

import com.google.gson.Gson;

import engine.components.Component;
import engine.core.Entity;
import engine.core.World;
import engine.util.ServiceLocator;

/**
 * Test suite for the Wolf3D database.
 *
 * @author Joshua van Vliet
 *
 */
public class DatabaseTests {
	private Component component;


	@Test
	// Test component converts to Json without crashing
	public void testCompToJson() {
		component = new Component();
		Gson gson = new Gson();
		gson.toJson(component);
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
		DataManagement.loadWorld("testLoadWorld.txt");
	}

	//============================================================================
	//========== Helper methods ==================================================
	//============================================================================

	private World createDummyWorld(){
		Entity item1, item2, item3, player;
		//create world with player holding three items
		World world = ServiceLocator.getService(World.class);
		Parser parser = new Parser("map00/");

		player = EntityFactory.createPlayer("Player",-1);
		parser.createEntities(player);

		item1 = world.createEntity("motorbike");
		item1.attachComponent(ProximitySensor.class).setTarget(player);;
		item1.attachComponent(new PickUp());
		item1.attachComponent(new Weight(50));
		item1.getComponent(PickUp.class).pickUpItem();

		item2 = world.createEntity("bottle");
		item2.attachComponent(ProximitySensor.class).setTarget(player);;
		item2.attachComponent(new PickUp());
		item2.attachComponent(Weight.class);
		item2.getComponent(PickUp.class).pickUpItem();

		item3 = world.createEntity("jar");
		item3.attachComponent(ProximitySensor.class).setTarget(player);;
		item3.attachComponent(new PickUp());
		item3.attachComponent(Weight.class);
		item3.getComponent(PickUp.class).pickUpItem();

		return world;
	}
}
