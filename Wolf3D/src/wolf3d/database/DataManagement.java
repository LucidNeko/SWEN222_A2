package wolf3d.database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import engine.components.Transform;
import engine.core.Entity;
import engine.core.World;


/**
 * Saves the Wolf3D world to file, can also
 * load the world from a file.
 * Uses the Gson library for parsing between object
 * and Json.
 * Gson:  http://code.google.com/p/google-gson/
 *
 * @author Joshua van Vliet
 *
 */

public class DataManagement {
	private static final Logger log = LogManager.getLogger();

	/**
	 * Loads the Wolf3D world from the file specified.
	 * @throws IOException
	 * @param fname
	 */
	public static World loadWorld(String fname) throws IOException {
		String fpath = getSaveFpath()+fname;
		// check save file exists
		if (!new File (fpath).isFile()) {
			log.error("Game file unable to load: file does not exist.");
			throw new IOException("Game file unable to load: file does not exist.");
		}

		Gson gson = new Gson();
		World world = new World();
		Scanner scan = new Scanner(new File(fpath));
		while(scan.hasNext()) {
			while(scan.hasNext()) {
				// recreate each entity from the file
				if (scan.next().equals('#')) {	// '#' indicates start of entity record
					int id = scan.nextInt();
					String name = scan.next();
					Entity e = world.createEntity(name);
					String json = scan.nextLine();
					e.getComponent(Transform.class).set(gson.fromJson(json, Transform.class));
					log.trace("Creating entity '{}' '{}' at: {}", id, name, e.getComponent(Transform.class).getPosition());
					if (scan.next().equals('*')) { break; }	// '*' indicates end of entity record
				}
			}
		}
		scan.close();
		return world;

		//=================================================
		//FOR INTEGRATION ONLY: DELETE ME
		// return a dummy world
		//		World dummyWorld = new World();
		//		dummyWorld.createEntity("entA");
		//		dummyWorld.createEntity("entB");
		//		return dummyWorld;
		//=================================================
	}

	/**
	 * Saves the current Wolf3D world's entities and their Transform
	 * component using JSON, entity IDs and names are not stored in
	 * JSON formatting.
	 * Gets passed the world to be saved and the filename.
	 * @param fname
	 * @param world
	 */
	public static void saveWorld(String fname, World world) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Collection<Entity> entities = world.getEntities();

		File saveFile = new File(getSaveFpath()+fname);		

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(saveFile)));
			for (Entity entity : entities) {
				writer.write("#\n");			// '#' indicates start of entity record
				writer.write(Integer.toString(entity.getID())+"\n");
				writer.write(entity.getName()+"\n");
				writer.write(gson.toJson(entity.getComponent(Transform.class)));
				writer.write("\n*\n\n");		// '*' indicates end of entity record
			}
		} catch (IOException ex) {
			// report
			log.error("Writing world to file failed: {}", ex.getMessage());
		} finally {
			try {writer.close();} catch (Exception ex) {}
		}

	}


	// Returns the file path within the game directory to save the world file to.
	// If unable to get directory, and no exception thrown, returns game root path.
	private static String getSaveFpath() {
		String path = "";
		File currentDirFile = new File(".");
		path = currentDirFile.getAbsolutePath();
		path = path.substring(0, path.length()-1);	
		path = path+"Wolf3D\\src\\wolf3d\\assets\\saves\\";
		return path;
	}


}
