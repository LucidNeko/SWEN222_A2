/**
 * @author Simon Brannigan
 *	Should test most methods created
 *
 */

package wolf3d.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Test;

import engine.core.World;

public class WolfTests {

	private World world = new World();
	private static Path path;
	//////////////////////////////////
	/*
	 * Ensure that the package has all the items and textures that it needs
	 * */
	//////////////////////////////////

	/* .txt files present */

	//If there is no wall map, the test fails.
	@Test
	public void apackageTest1(){
		path = FileSystems.getDefault().getPath("src/wolf3d/assets", "Map.txt");
		try (Scanner scan = new Scanner(path)) {
			while(scan.hasNext()){
				if(scan.hasNext()){
					break;
				}
			}
			scan.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		    fail("Map.txt does not exist");
		}
	}

	//If there is no door map, the test fails.
	@Test
	public void apackageTest2() {
		path = FileSystems.getDefault().getPath("src/wolf3d/assets", "Doors.txt");
		try (Scanner scan = new Scanner(path)) {
			while(scan.hasNext()){
				if(scan.hasNext()){
					break;
				}
			}
			scan.close();
		} catch (Exception e) {
			System.out.println(e.toString());
			fail("Doors.txt does not exist");
		}
	}

	// If there is no texture map, the test fails
	@Test
	public void apackageTest3() {
		fail("Not yet implemented");
	}

	// Model files present in the folder
	@Test
	public void apackageTest4() {
		fail("Not yet implemented");
	}

	@Test
	public void apackageTest5() {
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
	public void enetworkTest1() {
		fail("Not yet implemented");
	}

	@Test
	public void enetworkTest2() {
		fail("Not yet implemented");
	}

	@Test
	public void enetworkTest3() {
		fail("Not yet implemented");
	}

	@Test
	public void enetworkTest4() {
		fail("Not yet implemented");
	}

	@Test
	public void enetworkTest5() {
		fail("Not yet implemented");
	}

	/*
	 * Game logic tests
	 * */

	@Test
	public void flogicTest1() {
		fail("Not yet implemented");
	}

	@Test
	public void flogicTest2() {
		fail("Not yet implemented");
	}

	@Test
	public void flogicTest3() {
		fail("Not yet implemented");
	}

	@Test
	public void flogicTest4() {
		fail("Not yet implemented");
	}

	@Test
	public void flogicTest5() {
		fail("Not yet implemented");
	}

	@Test
	public void flogicTest6() {
		fail("Not yet implemented");
	}

	@Test
	public void flogicTest7() {
		fail("Not yet implemented");
	}

	@Test
	public void flogicTest8() {
		fail("Not yet implemented");
	}

	@Test
	public void flogicTest9() {
		fail("Not yet implemented");
	}

	@Test
	public void flogicTest10() {
		fail("Not yet implemented");
	}
}