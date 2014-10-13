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

import engine.common.Color;
import engine.common.Mathf;
import engine.common.Vec3;
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
	 * Engine common tests
	 * Didn't test AABB, Color
	 * */
	//////////////////////////////////

	// Mathf class - Matrix multiplication for floats, should fail often.
	@Test
	public void engineTest1(){
		float[] a={1.0f, 4.0f, 2.0f, 3.0f,
				2.0f, 3.0f, 3.0f, 4.0f,
				3.0f,2.0f,4.0f,1.0f,
				4.0f,1.0f,1.0f,2.0f};
		float[] b={1.0f, 4.0f, 2.0f, 3.0f,
				2.0f, 3.0f, 3.0f, 4.0f,
				3.0f,2.0f,4.0f,1.0f,
				4.0f,1.0f,1.0f,2.0f};
		float[] result = {27.0f, 23.0f,25.0f,27.0f,
				33.0f, 27.0f, 29.0f, 29.0f,
				23.0f,27.0f, 29.0f, 23.0f,
				17.0f, 23.0f,17.0f,21.0f};
		float[] ans = Mathf.multiplyMatrix(a, b);
		for(int i=0; i<4; i++){
			if(ans[i]!=result[i]){
				fail("Incorrect answer");
			}
		}
	}

	// Vec3 class - Vector creation, getters & setters, addition
	@Test
	public void engineTest2(){
		Vec3 a = new Vec3(1f, 1f, 1f);
		Vec3 b = new Vec3(a);
		Vec3 aAndb = a.add(b);
		Vec3 ans = new Vec3(2f, 2f, 2f);
		if(ans.x()!=aAndb.getX()){
			fail("X's aren't equal");
		}
		if(ans.y()!=aAndb.getY()){
			fail("Y's aren't equal");
		}
		if(ans.z()!=aAndb.getZ()){
			fail("Z's aren't equal");
		}
	}

	@Test
	public void engineTest3(){
		Vec3 a = new Vec3(1f, 1f, 1f);
		Vec3 b = new Vec3(a);
		Vec3 aAndb = a.sub(b);
		Vec3 ans = new Vec3(2f, 2f, 2f);
		if(ans.x()!=aAndb.getX()){
			fail("X's aren't equal");
		}
		if(ans.y()!=aAndb.getY()){
			fail("Y's aren't equal");
		}
		if(ans.z()!=aAndb.getZ()){
			fail("Z's aren't equal");
		}
	}

	@Test
	public void engineTest4(){
		fail("Not yet implemented");
	}

	@Test
	public void engineTest5(){
		fail("Not yet implemented");
	}

	@Test
	public void engineTest6(){
		fail("Not yet implemented");
	}

	@Test
	public void engineTest7(){
		fail("Not yet implemented");
	}

	@Test
	public void engineTest8(){
		fail("Not yet implemented");
	}

	@Test
	public void engineTest9(){
		fail("Not yet implemented");
	}

	@Test
	public void engineTest10(){
		fail("Not yet implemented");
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
		f = new File("src/wolf3d/assets/map.txt");
		assertTrue(f.exists());
	}

	//If there is no door map, the test fails.
	@Test
	public void packageTest2() {
		f = new File("src/wolf3d/assets/doors.txt");
		assertTrue(f.exists());
	}

	// If there is no texture map, the test fails
	@Test
	public void packageTest3() {
		f = new File("src/wolf3d/assets/textureFiles/walls.txt");
		assertTrue(f.exists());
	}

	// Model files present in the folder
	//Link
	@Test
	public void packageTest4() {
		fail("Not yet implemented");
	}

	//Teddy Bear
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