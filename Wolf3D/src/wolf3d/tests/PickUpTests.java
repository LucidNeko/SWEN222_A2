package wolf3d.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import wolf3d.EntityFactory;
import wolf3d.components.Weight;
import wolf3d.components.behaviours.PickUp;
import wolf3d.components.sensors.ProximitySensor;
import engine.core.Entity;
import engine.core.World;

public class PickUpTests {
	private Entity item1, item2, item3, player;

	@Before
	public void init(){
		//create world with player and three items inside
		World world = new World();
		player = EntityFactory.createPlayer(world, "Player", -1);
		item1 = world.createEntity("motorbike");
		item1.attachComponent(ProximitySensor.class).setTarget(player);
		item1.attachComponent(new PickUp(world));
		item1.attachComponent(new Weight(100));

		item2 = world.createEntity("bottle");
		item2.attachComponent(ProximitySensor.class).setTarget(player);
		item2.attachComponent(new PickUp(world));
		item2.attachComponent(Weight.class);

		item3 = world.createEntity("jar");
		item3.attachComponent(ProximitySensor.class).setTarget(player);
		item3.attachComponent(new PickUp(world));
		item3.attachComponent(Weight.class);
	}
	/**
	 * Picking up one item
	 */
	@Test
	public void test_Valid_PickUpItem1(){
		assertTrue(item1.getComponent(PickUp.class).pickUpItem());
	}

	/**
	 * Picking up two items
	 */
	@Test
	public void test_Valid_PickUpItem2(){
		assertTrue(item2.getComponent(PickUp.class).pickUpItem());
		assertTrue(item3.getComponent(PickUp.class).pickUpItem());
	}

	/**
	 * Trying to pick up an item when you do not have enough strength
	 */
	@Test
	public void test_Invalid_PickUpItem1(){
		item1.getComponent(PickUp.class).pickUpItem();
		assertFalse(item2.getComponent(PickUp.class).pickUpItem());
	}

	/**
	 * Trying to pick up item when you do not have a Weight component
	 */
	@Test
	public void test_Invalid_PickUpItem2(){
		//removing weight component
		item3.detachComponent(item3.getComponent(Weight.class));
		assertFalse(item3.getComponent(PickUp.class).pickUpItem());
	}
}
