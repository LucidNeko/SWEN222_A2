package wolf3d.tests;

import org.junit.Before;
import org.junit.Test;

import wolf3d.world.Parser;
import engine.core.World;

public class ParserTests {
	private Parser parser;
	private World world;

	@Before
	public void init(){
		parser = new Parser("Map.txt", "Doors.txt");
		world = new World();
	}

	@Test
	public void test_passWallFileToArray(){
		parser.passWallFileToArray();
		parser.createWalls(world);
	}

	@Test
	public void test_passDoorFileToArray(){
		parser.passDoorFileToArray();
	}

	@Test
	public void test_passTextures(){
		parser.passTextures();
		parser.passWallFileToArray();
		parser.createWalls(world);
	}
}
