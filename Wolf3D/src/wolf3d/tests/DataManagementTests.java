package wolf3d.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import wolf3d.EntityFactory;
import wolf3d.components.Weight;
import wolf3d.components.behaviours.PickUp;
import wolf3d.components.sensors.ProximitySensor;
import wolf3d.database.DataManagement;
import wolf3d.world.Parser;

import engine.components.Transform;

import com.google.gson.Gson;

import engine.components.Component;
import engine.core.Entity;
import engine.core.World;
import engine.util.ServiceLocator;

/**
 * Test suite for the wolf3d.database DataManagement class.
 *
 * @author Joshua van Vliet
 *
 */
public class DataManagementTests {
	private World world;
	private Component component;

	@Before
	public void initWorld(){
		Entity item1, item2, item3, player;
		//create world with player holding three items
		World world = ServiceLocator.getService(World.class);
		Parser parser = new Parser("map00/");
		world.destroyEntity(-1);

		player = EntityFactory.createPlayer("Player",-1);
		parser.createEntities(player);

		player.getComponent(Transform.class).setPosition(10, 0, 20);;

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

		this.world = world;
	}

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
		DataManagement.saveWorld("testSaveWorldCrash.txt", world);
	}

	@Test
	// Test a world loads player with correct position
	public void testLoadWorldPlayerPos() {
		DataManagement.saveWorld("testLoadWorldCrash.txt", world);
		world.destroyEntity(-1);
		World world = DataManagement.loadWorld("testLoadWorldCrash.txt",-1);
		Entity player = world.getEntity("Player").get(0);

		assertTrue(player.getComponent(Transform.class).getPosition().getX() == 10
				&& player.getComponent(Transform.class).getPosition().getY() == 0
				&& player.getComponent(Transform.class).getPosition().getZ() == 20);
	}

}
