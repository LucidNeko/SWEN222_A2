package wolf3d.database;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.components.Health;
import wolf3d.components.Inventory;
import wolf3d.components.Strength;
import wolf3d.components.Weight;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import engine.components.Component;
import engine.components.Transform;
import engine.core.Entity;
import engine.core.EntityDef;
import engine.core.TempEntityDef;
import engine.core.World;

/**
 * Used for saving the Wolf3D world to file using a JSON data structure.
 * Saves the map name (directory), and players with their components that have
 * unique characteristics. It also sets cameras and the default objects that exist
 * in a Wolf3D world.
 * Uses the Gson library for parsing between object and Json.
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
	public static World loadWorld(String fname) {
		String fpath = getSaveFpath()+fname;
		// check save file exists
		if (!new File (fpath).isFile()) {
			log.error("Game file unable to load: file does not exist.");
		}
		log.trace("Reading from: {}", fpath);
		WorldBuilder builder;
		Gson gson = new Gson();
		String line;
		Scanner scan;
		try {
			scan = new Scanner(new File(fpath));
			// load map
			skip(scan, 2);
			line = scan.next();
			line = line.substring(1,line.length()-2);
			log.trace("Reading: {}", line);
			builder = new WorldBuilder(line);

			// load player entities
			while(scan.hasNext()) {
				// read name and uniqueID
				while (!line.contains("name") && scan.hasNext()) { line = scan.next(); }
				skip(scan, 1);
				if (!scan.hasNext()) { break; }
				String name = scan.next();
				name = name.substring(1,name.length()-2);
				log.trace("Reading name: {}", name);
				skip(scan, 2);
				line = scan.next();
				line = line.substring(1,line.length()-2);
				int uniqueID = Integer.parseInt(line);
				log.trace("Reading uniqueID: {}", uniqueID);

				/* Read components, they will be saved in the order of:
				 * Transform,Health,Strength,Weight,Inventory
				 */
				//Transform
				while (!line.contains("Transform") && scan.hasNext()) { line = scan.next(); }
				line = scan.next();
				log.trace("Reading Transfrom: {}", line);
				Transform transform = gson.fromJson(line, Transform.class);
				// Health
				while (!line.contains("Health") && scan.hasNext()) { line = scan.next(); }
				line = scan.next();
				log.trace("Reading Health: {}", line);
				Health health = gson.fromJson(line, Health.class);
				// Strength
				while (!line.contains("Strength") && scan.hasNext()) { line = scan.next(); }
				line = scan.next();
				log.trace("Reading Strength: {}", line);
				Strength strength = gson.fromJson(line, Strength.class);
				// Weight
				while (!line.contains("Weight") && scan.hasNext()) { line = scan.next(); }
				line = scan.next();
				log.trace("Reading Weight: {}", line);
				Weight weight = gson.fromJson(line, Weight.class);
				// Inventory
				while (!line.contains("Inventory") && scan.hasNext()) { line = scan.next(); }
				line = scan.next();
				log.trace("Reading Inventory: {}", line);
				Inventory inventory = gson.fromJson(line, Inventory.class);
				log.trace("loadWorld creating player: {} []", uniqueID, name);
				builder.createPlayer(uniqueID, name, transform, health, strength, weight, inventory);
			}
			scan.close();

			// create default objects and camera
			builder.createDefaultObjects();
			builder.createCamera();

			return builder.getWorld();

		} catch (FileNotFoundException e) {
			log.error("Game file unable to load: file does not exist.");
		}
		return null;
	}

	/**
	 * Saves the current Wolf3D world map, and entities with their Transform component.
	 * For entities (such as players) that have Health, Strength, Weight, and Inventory
	 * components, it saves these too.
	 * Gets passed the world to be saved and the filename.
	 * @param fname
	 * @param world
	 */
	public static void saveWorld(String fname, World world) {
		Gson gson = new Gson();
		Collection<Entity> entities = world.getEntity("Player");
		File saveFile = new File(getSaveFpath()+fname);
		String line;
		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(saveFile)));

			//Write map directory
			String mapDir = "map00/";
			line = "\"mapDir\" : \""+mapDir+"\",";
			log.trace("Writing: {}", line);
			writer.write(line+"\n");

			//Write entities
			line = "\"entities\"";
			log.trace("Writing: {}", line);
			writer.write(line+"{\n");		//open entity records

			for (Entity entity : entities) {

				//name
				line = "\"name\" : \""+entity.getName()+"\",";
				log.trace("Writing: {}", line);
				writer.write(line+"\n");

				//uniqueID
				line = "\"uniqueID\" : \""+Integer.toString(entity.getID())+"\",";
				log.trace("Writing: {}", line);
				writer.write(line+"\n");

				//Transform component, all entities have transform
				line = "\"Transform\"\n" + gson.toJson(entity.getTransform());
				log.trace("Writing: {}", line);
				writer.write(line);

				//Health component
				if (entity.hasComponent(Health.class)) {
					line = "\n\"Health\"\n" + gson.toJson(entity.getComponent(Health.class));
					log.trace("Writing Health: {}", line);
					writer.write(line);
				}
				//Strength component
				if (entity.hasComponent(Strength.class)) {
					line = "\n\"Strength\"\n" + gson.toJson(entity.getComponent(Strength.class));
					log.trace("Writing Strength: {}", line);
					writer.write(line);
				}
				//Weight component
				if (entity.hasComponent(Weight.class)) {
					line = "\n\"Weight\"\n" + gson.toJson(entity.getComponent(Weight.class));
					log.trace("Writing Weight: {}", line);
					writer.write(line);
				}
				//Inventory component
				if (entity.hasComponent(Inventory.class)) {
					line = "\n\"Inventory\"\n" + gson.toJson(entity.getComponent(Inventory.class));
					log.trace("Writing Inventory: {}", line);
					writer.write(line);
				}
				writer.write("\n");
			}
			writer.write("}\n");		//close entity records

		} catch (IOException ex) {
			// report
			log.error("Writing world to file failed: {}", ex.getMessage());
		} finally {
			try {writer.close();} catch (Exception ex) {}
		}

	}

	/*
	 * Returns the file path within the game directory to save the world file to.
	 * If unable to get directory, and no exception thrown, returns game root path.
	 */
	private static String getSaveFpath() {
		String path = "";
		File currentDirFile = new File(".");
		path = currentDirFile.getAbsolutePath();
		path = path.substring(0, path.length()-1);
		path = path+"Wolf3D/src/wolf3d/assets/saves/";
		return path;
	}

	/*
	 * Skips the specified number of tokens for a given scanner.
	 */
	private static void skip(Scanner scan, int count) {
		for (int i=0; i<count && scan.hasNext(); i++) {
			scan.next();
		}
	}
}
