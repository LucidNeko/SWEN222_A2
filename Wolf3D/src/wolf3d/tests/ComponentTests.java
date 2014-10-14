/**
 * @author brannisimo
 * Tests components
 */

package wolf3d.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import wolf3d.components.behaviours.WASDCollisions;
import wolf3d.components.behaviours.WASDWalking;
import wolf3d.world.Cell;
import engine.core.Entity;
import engine.core.World;

public class ComponentTests {

	private static final int as = 3; //Array size will always be three
	private static Cell[][] testmap = new Cell[as][as]; //walls
	private static Cell[][] testdoormap = new Cell[as][as]; //doors
	private World world;
	private Entity e; // The test entity, will be anything

	//////////////////////////////
	//
	// HELPER METHODS
	//
	//////////////////////////////

	// Creates a 3x3 2D array of cells for the world to help test collisions
	@Before
	public void setUpMaps(){
		for(int i=0; i<as; i++){
			for(int j=0; j<as; j++){
				testmap[i][j] = new Cell(0);
				testdoormap[i][j] = new Cell(0);
			}
		}
		testmap[1][1]=new Cell(15);
	}

	@Test
	public void test(){
		world = new World();
		e = world.createEntity(0, "Test");
		e.attachComponent(new WASDWalking());
		e.attachComponent(new WASDCollisions(testmap, testdoormap));
	}
}
