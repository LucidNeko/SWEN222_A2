package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wolf3d.components.Component;
import wolf3d.core.Entity;

/**
 * Test suite for the Wolf3D database.
 *
 * @author Joshua van Vliet
 *
 */
public class DatabaseTests {

	private Entity a;
	private Component component;

	@Before
	public void init() {
		
	}

	@Test
	public void testWriteComponent() {
		a.attachComponent(component);
	}


}
