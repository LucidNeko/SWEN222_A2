package wolf3d.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wolf3d.components.Camera;
import wolf3d.components.Transform;
import wolf3d.core.Entity;

/**
 * This class is responsible for containing the state of the
 * world and the conditions and restrictions for the world
 * @author Sameer Magan
 *
 */
public class World {

//	private List<Entity> players = new ArrayList<Entity>();
	private Map<Integer, Entity> entities = new HashMap<Integer, Entity>();
	

	/**
	 * Registers the given entity with the world with a given id
	 * @param id unique id that identifies the entity
	 * @param entity 
	 * @return the previous value associated with key, 
	 * or null if there was no mapping for key.
	 * @author Sameer Magan
	 */
	public Entity register(Entity entity){
		int id = entity.getID();
		return entities.put(id, entity);
	}
	
	//TODO this method needs to replace the original getEntity()
	/**
	 * Return the entity in the world with the given id
	 * @param id The id of the entity that you want
	 * @return The entity, or null if does not exist
	 * @author Sameer Magan
	 */
	public Entity getEntity(int id){
		return entities.get(id);
	}
	
	/**
	 * Returns an unmodifiable List of all the Entities in the World.
	 * @return An unmodifiable list of all the entities in the world.
	 * @author Sameer Magan
	 */
	public List<Entity> getEntities() {
		ArrayList<Entity> en = new ArrayList<Entity>();
		en.addAll(entities.values());
		return Collections.unmodifiableList(en);
	}
	
	/**
	 * Registers the given Entity with the world.
	 * @param entity The entity to add.
	 * @return True if the entity was added to the world succesfully.
	 * @author Hamish Rae-Hodgson
	 */
//	public boolean register(Entity entity) {
//		return players.add(entity);
//	}
	
	/**
	 * Returns an unmodifiable List of all the Entities in the World.
	 * @return An unmodifyable list of all the entities in the world.
	 * @author Hamish Rae-Hodgson
	 */
//	public List<Entity> getEntities() {
//		return Collections.unmodifiableList(players);
//	}
	
	/**
	 * Return the Entity in the World with the given id.
	 * @param id The id of the entity you want.
	 * @return The Entity, or null if not present.
	 * @author Hamish Rae-Hodgson
	 */
//	public Entity getEntity(int id) {
//		for(Entity entity : players)
//			if(entity.getID() == id)
//				return entity;
//		return null;
//	}
}
