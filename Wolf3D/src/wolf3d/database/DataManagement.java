package wolf3d.database;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Collection;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import engine.common.Vec3;
import engine.components.Component;
import engine.components.Transform;
import engine.core.Entity;
import engine.core.World;
import engine.util.Resources;

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
	//private static final String ASSETS_DIR = "/wolf3d/assets/";

	/**
	 * Loads a Wolf3D world from a given filename.
	 * @param filename
	 */
	public static World loadWorld(String fname) {
		Vec3 c = new Vec3();
		Gson gson = new Gson();
		World world = new World();
		Scanner scan = new Scanner(Resources.getInputStream(fname));

		int name = 0;
		while(scan.hasNext()) {
			Entity e = world.createEntity(Integer.toString(name));
			String json = scan.nextLine();
			c = gson.fromJson(json, Vec3.class);
			e.getComponent(Transform.class).setPosition(c.x(), c.y(), c.z());
			log.trace("Creating entity '{}' at: {}", name, c);
			name++;
		}
		scan.close();
		return world;
	}

	/**
	 * Saves the current Wolf3D world to a given filename.
	 * @param filename
	 * @param world
	 */
	public static void saveWorld(String fname, World world) {
		Gson gson = new Gson();
		Collection<Entity> entities = world.getEntities();

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fname)));
			for (Entity entity : entities) {
				writer.write(gson.toJson(entity.getComponent(Transform.class).getPosition()));
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
