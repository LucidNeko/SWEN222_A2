package wolf3d.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	private List<Entity> entities = new ArrayList<Entity>();
	
	/**
	 * Adds a new player to the game with given id
	 */
	public void createPlayer(int id){
		Entity player = new Entity(id, Transform.class);
		player.attachComponent(Camera.class);
		entities.add(player);
	}
	
	/**
	 * Registers the given Entity with the world.
	 * @param entity The entity to add.
	 * @return True if the entity was added to the world succesfully.
	 * @author Hamish Rae-Hodgson
	 */
	public boolean register(Entity entity) {
		return entities.add(entity);
	}
	
	/**
	 * Returns an unmodifiable List of all the Entities in the World.
	 * @return An unmodifiable list of all the entities in the world.
	 * @author Hamish Rae-Hodgson
	 */
	public List<Entity> getEntities() {
		return Collections.unmodifiableList(entities);
	}
	
	/**
	 * Return the Entity in the World with the given id.
	 * @param id The id of the entity you want.
	 * @return The Entity, or null if not present.
	 * @author Hamish Rae-Hodgson
	 */
	public Entity getEntity(int id) {
		for(Entity entity : entities)
			if(entity.getID() == id)
				return entity;
		return null;
	}
}
