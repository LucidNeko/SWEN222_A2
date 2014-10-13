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

	//////////////////////////////////
	/*
	 * Helper methods
	 */
	//////////////////////////////////

	/**
	 * scanShort is a method with two String arguments, returns false if there is an exception, otherwise true.
	 * @param p
	 * @param f
	 * @return Method with two String arguments, returns false if there is an exception, otherwise true.
	 */
	public boolean scanShort(String p, String f){ // f for file, p for path
		Path path = FileSystems.getDefault().getPath(p, f);
		try (Scanner scan = new Scanner(path)) {
			while(scan.hasNext()){
				if(scan.hasNext()){
					break;
				}
			}
			scan.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		    return false;
		}
		return true;
	}

	//////////////////////////////////
	/*
	 * Ensure that the package has all the items and textures that it needs
	 * */
	//////////////////////////////////

	/* .txt files present */

	//If there is no wall map, the test fails.
	@Test
	public void packageTest1(){
		assert(scanShort("src/wolf3d/assets", "Map.txt"));
	}

	//If there is no door map, the test fails.
	@Test
	public void packageTest2() {
		Path path = FileSystems.getDefault().getPath("src/wolf3d/assets", "Doors.txt");
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
	public void packageTest3() {
		Path path = FileSystems.getDefault().getPath("src/wolf3d/assets/textureFiles", "0.txt");
		try (Scanner scan = new Scanner(path)) {
			while(scan.hasNext()){
				if(scan.hasNext()){
					break;
				}
			}
			scan.close();
		} catch (Exception e) {
			System.out.println(e.toString());
			fail("Textures.txt does not exist");
		}
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