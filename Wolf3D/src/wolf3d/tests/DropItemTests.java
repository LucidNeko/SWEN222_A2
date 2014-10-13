package wolf3d.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import wolf3d.EntityFactory;
import wolf3d.components.Weight;
import wolf3d.components.behaviours.DropItem;
import wolf3d.components.behaviours.PickUp;
import wolf3d.components.sensors.ProximitySensor;
import engine.core.Entity;
import engine.core.World;

public class DropItemTests {
	private Entity item1, item2, item3, player;

	@Before
	public void init(){
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
	}

	@Test
	public void test_Valid_DropItem(){
		assertTrue(player.getComponent(DropItem.class).drop(item1.getID()));
	}
}
