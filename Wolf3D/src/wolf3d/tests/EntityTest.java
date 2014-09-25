package wolf3d.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import engine.components.Component;
import engine.core.Entity;
import engine.core.World;

public class EntityTest {

	private Entity a;
	private Entity b;
	private Component component;

	@Before
	public void init() {
		World world = new World();
		a = world.createEntity("a");
		b = world.createEntity("b");
		component = new Component();
		a.attachComponent(component);
	}

	@Test(expected=RuntimeException.class)
	public void testAdd1() {
		b.attachComponent(component);
	}

	@Test
	public void testAdd2() {
		a.detachComponent(component);
		a.attachComponent(component);
	}

	@Test
	public void testAdd3() {
		a.detachComponent(component);
		b.attachComponent(component);
	}

	@Test
	public void testContains1() {
		assertTrue(a.contains(component));
	}

	@Test
	public void testContains2() {
		a.detachComponent(component);
		assertFalse(a.contains(component));
	}

	@Test
	public void testContains3() {
		a.detachComponent(component);
		b.attachComponent(component);
		assertFalse(a.contains(component));
		assertTrue(b.contains(component));
	}

	@Test
	public void testGet1() {
		assertTrue(a.getComponent(component.getClass()) == component);
	}

	@Test
	public void testGets1() {
		List<Component> list = a.getComponents(Component.class);
		assertTrue(list.size() == 1);
		assertTrue(list.contains(component));
	}

}
