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

	// Vec3 class addition
	@Test
	public void engineTest2(){
		Vec3 a = new Vec3(0f, 0f, 0f);
		a=a.add(1f,1f,1f);
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

	//Local addition vec3
	@Test
	public void engineTest3(){
		Vec3 a = new Vec3(0f, 0f, 0f);
		Vec3 b = new Vec3(1f,1f,1f);
		a = a.addLocal(b);
		if(!a.equals(b)){
			fail("Local Addition doesn't work");
		}

	}

	// Vec3 subtraction
	@Test
	public void engineTest4(){
		Vec3 a = new Vec3(1f, 1f, 1f);
		Vec3 b = new Vec3(a);
		a.setZero(); // left this in for coverage but really pointless
		a.set(2f,2f,2f);
		Vec3 aSubb = a.sub(b);
		Vec3 ans = new Vec3(1f, 1f, 1f);
		if(!ans.equals(aSubb)){
			fail("Subtraction doesn't work");
		}
	}

	//Local subtraction
	@Test
	public void engineTest4b(){
		Vec3 a = new Vec3(1f, 1f, 1f);
		Vec3 b = new Vec3(a);
		a = a.subLocal(b);
		Vec3 ans = new Vec3(0f, 0f, 0f);
		if(!ans.equals(a)){
			fail("Local subtraction doesn't work");
		}
	}

	//Absolute Value
	@Test
	public void engineTest5(){
		Vec3 a = new Vec3(-1f, -1f, -1f);
		a.abs();
		Vec3 ans = new Vec3(-1f, -1f, -1f);
		ans.absLocal();
		if(!ans.equals(ans)){
			fail("Absolute value doesn't work");
		}
	}

	//Cross Product
	@Test
	public void engineTest6(){
		Vec3 a = new Vec3(0f, 0f, 1f);
		Vec3 b = new Vec3(0f, 1f, 0f);
		Vec3 ans = new Vec3(-1f, 0f,0f);
		if(!Vec3.cross(a, b).equals(ans)){
			fail("Cross product works");
		}
	}

	//Unmodifiable Vec3 exception
	@Test (expected=UnsupportedOperationException.class)
	public void engineTest7(){
		Vec3.UP.addLocal(1f, 0f, 0f);
	}

	// Unmodifiable Vec3 exception
	@Test (expected=UnsupportedOperationException.class)
	public void engineTest8(){
		Vec3.DOWN.divLocal(1f);
	}

	// Vec3 Local Vector Division
	@Test
	public void engineTest9(){
		Vec3 a = new Vec3(2f, 2f, 2f);
		a.divLocal(2f);
		Vec3 ans = new Vec3(1f, 1f, 1f);
		if(!ans.equals(a)){
			fail("Local Vector division doesn't work");
		}
	}

	// Vec3 division
	@Test
	public void engineTest10(){
		Vec3 a = new Vec3(1f,1f, 1f);
		Vec3 b = a.div(2f);
		Vec3 ans = new Vec3(0.5f, 0.5f, 0.5f);
		if(!ans.equals(b)){
			fail("Vector Division works");
		}
	}

	// Vec3 hashcode method
	@Test
	public void engineTest11(){
		Vec3 a = new Vec3(1f,1f, 1f);
		Vec3 b = new Vec3(1.01f, 1f, 1f);
		Vec3 c = new Vec3(1f,1f, 1f);
		if(c.hashCode()!=a.hashCode()){
			fail("Hashcode is different");
		}
		if(a.hashCode()==b.hashCode()){
			fail("Hashcode is the same");
		}
	}

	// Vec3 Dot product method
	@Test
	public void engineTest12(){
		Vec3 a = new Vec3(1f,1f,1f);
		Vec3 b = new Vec3(2f,2f,2f);
		float c = Vec3.dot(a, b);
		float ans = 6f;
		if(c!=ans){
			fail();
		}
	}

	//Vec3 toString method
	@Test
	public void engineTest13(){
		Vec3 x = new Vec3(1f, 2f, 3f);
		String ans = "(1.00,  2.00, 3.00)";
		if(!ans.equals(x.toString())){
			fail("toString method fails");
		}
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
		f = new File("src/wolf3d/assets/map00/walls.txt");
		assertTrue(f.exists());
	}

	//If there is no door map, the test fails.
	@Test
	public void packageTest2() {
		f = new File("src/wolf3d/assets/map00/doors.txt");
		assertTrue(f.exists());
	}

	// If there is no texture map, the test fails
	@Test
	public void packageTest3() {
		f = new File("src/wolf3d/assets/map00/walls.txt");
		assertTrue(f.exists());
	}

	// No ceiling map
	@Test
	public void packageTest4() {
		f = new File("src/wolf3d/assets/map00/ceilings.txt");
		assertTrue(f.exists());
	}

	// Model files present in the folder
	//Link
	@Test
	public void packageTest5() {
		fail("Not yet implemented");
	}

	//Teddy Bear files
	@Test
	public void packageTest6() {
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