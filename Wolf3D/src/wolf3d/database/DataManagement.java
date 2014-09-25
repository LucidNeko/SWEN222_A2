package wolf3d.database;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

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

	/** base asset directory */
	private static final String WORLDS_DIR = "/wolf3d/assets/worlds/";

	/**
	 * Loads a Wolf3D world from a given filename.
	 * @param filename
	 */
	public static void loadWorld(String filename) {

	}

	/**
	 * Saves the current Wolf3D world to a given filename.
	 * @param filename
	 * @param world
	 */
	public static void saveWorld(String filename, World world) {
		Gson gson = new Gson();
		Collection<Entity> entities = world.getEntities();

		BufferedWriter writer = null;
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(WORLDS_DIR+filename)));
		    for (Entity entity : entities) {
		    	writer.write(gson.toJson(entity));
		    	writer.newLine();
		    }
		} catch (IOException ex) {
			// report
			log.error("Writing world to file failed. {}", gson);
		} finally {
		   try {writer.close();} catch (Exception ex) {}
		}

	}


}
