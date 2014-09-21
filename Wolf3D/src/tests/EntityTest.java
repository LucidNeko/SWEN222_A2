package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wolf3d.components.Component;
import wolf3d.core.Entity;

public class EntityTest {
	
	private Entity a;
	private Entity b;
	private Component component;
	
	@Before
	public void init() {
		a = new Entity(0);
		b = new Entity(1);
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
