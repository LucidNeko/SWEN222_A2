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
		parser = new Parser("map00/");
		world = new World();
	}

	@Test
	public void test_passWallFileToArray(){
		parser.parseWallFileToArray();
		parser.createWalls();
	}

	@Test
	public void test_passDoorFileToArray(){
		parser.parseDoorFileToArray();
	}

	@Test
	public void test_passTextures(){
		parser.parseTextures();
		parser.parseWallFileToArray();
		parser.createWalls();
	}
	
	@Test
	public void test_createFloor(){
		parser.parsefloorFileToArray();
		parser.createFloor();
	}
}
