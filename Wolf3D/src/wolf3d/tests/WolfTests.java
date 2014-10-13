/**
 * @author Simon Brannigan
 *	Should test most methods created
 *
 */

package wolf3d.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Test;

import engine.core.World;

public class WolfTests {

	private static World world = new World();
	private static File f;

	//////////////////////////////////
	/*
	 * Helper methods
	 */
	//////////////////////////////////

	//////////////////////////////////
	/*
	 * Ensure that the package has all the items and textures that it needs
	 * */
	//////////////////////////////////

	/* .txt files present */

	//If there is no wall map, the test fails.
	@Test
	public void packageTest1(){
		f = new File("src/wolf3d/assets/Mapk.txt");
		assertTrue(f.exists());
	}

	//If there is no door map, the test fails.
	@Test
	public void packageTest2() {
		f = new File("src/wolf3d/assets/Doors.txt");
		assertTrue(f.exists());
	}

	// If there is no texture map, the test fails
	@Test
	public void packageTest3() {
		f = new File("src/wolf3d/assets/textureFiles/0.txt");
		assertTrue(f.exists());
	}

	// Model files present in the folder
	@Test
	public void packageTest4() {
		fail("Not yet implemented");
	}

	@Test
	public void packageTest5() {
		fail("Not yet implemented");
	}

	//////////////////////////////////
	/*
	 * Map creation tests
	 * */
	//////////////////////////////////

	@Test
	public void creationTest1() {
		fail("Not yet implemented");
	}

	@Test
	public void creationTest2() {
		fail("Not yet implemented");
	}

	@Test
	public void creationTest3() {
		fail("Not yet implemented");
	}

	@Test
	public void creationTest4() {
		fail("Not yet implemented");
	}

	@Test
	public void creationTest5() {
		fail("Not yet implemented");
	}

	/*
	 * Database Tests
	 * */

	@Test
	public void databaseTest1() {
		fail("Not yet implemented");
	}

	@Test
	public void databaseTest2() {
		fail("Not yet implemented");
	}

	@Test
	public void databaseTest3() {
		fail("Not yet implemented");
	}

	@Test
	public void databaseTest4() {
		fail("Not yet implemented");
	}

	@Test
	public void databaseTest5() {
		fail("Not yet implemented");
	}

	/*
	 * Network Tests
	 * */

	@Test
	public void networkTest1() {
		fail("Not yet implemented");
	}

	@Test
	public void networkTest2() {
		fail("Not yet implemented");
	}

	@Test
	public void networkTest3() {
		fail("Not yet implemented");
	}

	@Test
	public void networkTest4() {
		fail("Not yet implemented");
	}

	@Test
	public void networkTest5() {
		fail("Not yet implemented");
	}

	/*
	 * Game logic tests
	 * */

	@Test
	public void logicTest1() {
		fail("Not yet implemented");
	}

	@Test
	public void logicTest2() {
		fail("Not yet implemented");
	}

	@Test
	public void logicTest3() {
		fail("Not yet implemented");
	}

	@Test
	public void logicTest4() {
		fail("Not yet implemented");
	}

	@Test
	public void logicTest5() {
		fail("Not yet implemented");
	}

	@Test
	public void logicTest6() {
		fail("Not yet implemented");
	}

	@Test
	public void logicTest7() {
		fail("Not yet implemented");
	}

	@Test
	public void logicTest8() {
		fail("Not yet implemented");
	}

	@Test
	public void logicTest9() {
		fail("Not yet implemented");
	}

	@Test
	public void logicTest10() {
		fail("Not yet implemented");
	}
}