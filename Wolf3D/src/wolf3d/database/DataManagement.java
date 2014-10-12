package wolf3d.database;

import java.io.BufferedWriter;
import java.io.File;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import engine.components.Component;
import engine.components.Transform;
import engine.core.Entity;
import engine.core.EntityDef;
import engine.core.TempEntityDef;
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
		log.trace("Reading from: {}", fpath);
		Gson gson = new Gson();
		String json = "";
		World world = new World();
		Scanner scan = new Scanner(new File(fpath));
		String line = "";		
		while(scan.hasNext()) {
			log.trace("Scanner read: {}", line);
			// recreate each entity from the file
			// construct JSON string
			while (scan.hasNext()) {
				Collection<Component> components = new ArrayList<Component>();	// collection of components to add to the new entity
				line = scan.nextLine();
				log.trace("Scanner read: {}", line);
				//=====================================================
				// Read, create, then add components to collection
				if (line.contains("components")) {
					log.trace("Scanner reading component...");
					line = scan.nextLine();
					log.trace("Scanner read: {}", line);
					while (!line.contains("],")) {			// '],' signifies end of component JSON string						
						log.trace("Scanner read: {}", line);
						json += line;
						line = scan.nextLine();
					}
					log.trace("JSON string read: {}", json);
					Transform t = gson.fromJson(json, Transform.class);
					components.add(t);
					// get entity ID
					while (!line.contains("uniqueID")) {
						line = scan.nextLine();
						log.trace("Scanner read: {}", line);
					}
					int id = Integer.parseInt(line.substring(line.indexOf(':')+2, line.indexOf(',')));
					line = scan.nextLine();
					log.trace("Scanner read: {}", line);
					// get entity name
					while (!line.contains("name")) {
						line = scan.nextLine();
						log.trace("Scanner read: {}", line);
					}
					String name = line.substring(line.indexOf(':')+3);
					name = name.substring(0, name.indexOf('"'));
					log.trace("New entity id: '{}', name: '{}'", id, name);
					line = scan.nextLine();
					log.trace("Scanner read: {}", line);
					TempEntityDef ted = new TempEntityDef();
//					ted.setId(id);		//method not yet created in TempEntityDef
					ted.setName(name);
					ted.addComponents(components);
					log.trace("Adding entity '{}' '{}' to world.", id, name);
					world.addEntityDef(ted);
				}
			}
		}
		scan.close();
		return world;

		//=================================================
		//FOR INTEGRATION ONLY: DELETE ME
		// return a dummy world
//				World dummyWorld = new World();
//				dummyWorld.createEntity("entA");
//				dummyWorld.createEntity("entB");
//				return dummyWorld;
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
				writer.write(gson.toJson(entity));
				writer.write("\n*\n\n");		// Asterisk indicates end of entity record
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
