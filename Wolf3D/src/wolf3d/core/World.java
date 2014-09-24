package wolf3d.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import wolf3d.components.Transform;
import wolf3d.core.Entity;

/**
 * The world is a factory for Entities and contains all of the Entities.
 * @author Hamish
 *
 */
public class World {

	/** All the Entities in the world */
	private Map<Integer, Entity> entities = new HashMap<Integer, Entity>();

	/**
	 * Create an Entity with the given name and register it in this world.
	 * @param name The name of the Entity.
	 * @return The Entity that was created and added to this world.
	 */
	public Entity createEntity(String name) {
		Entity entity = new Entity(getFreeID(), name);
		entity.attachComponent(Transform.class);
		entities.put(entity.getID(), entity);
		return entity;
	}

	/**
	 * Tries to create the Entity with the given ID.
	 * If that ID is already taken throws error.
	 * Otherwise creates the entity.
	 * @param id The id you want the Entity to have.
	 * @param name The name of the Entity.
	 * @return The entity.
	 */
	public Entity createEntity(int id, String name) {
		if(getEntity(id) != null)
			throw new IllegalStateException("You can't create an Entity with id=" + id + " because one already exists.");

		Entity entity = new Entity(id, name);
		entity.attachComponent(Transform.class);
		entities.put(entity.getID(), entity);
		return entity;
	}

	/**
	 * Destroy the Entity with id.
	 * @param id The id of the Entity to destroy.
	 * @return Returns true if there was an Entity with the id and it was removed.<br>
	 * 		   Returns false if there was no Entity meaning no removal.
	 */
	public boolean destroyEntity(int id) {
		return entities.remove(id) != null;
	}

	/**
	 * <b>O(1)</b> - Get the Entity in this world that has the id.
	 * @param id The id of the Entity you want.
	 * @return The Entity, or null if not found.
	 */
	public Entity getEntity(int id) {
		return entities.get(id);
	}

	/**
	 * <b>O(n)</b> - Get the Entity with the given name.
	 * @param name The name of the Entity.
	 * @return The Entity, or null if not found.
	 */
	public Entity getEntity(String name) {
		for(Entity entity : entities.values())
			if(entity.getName().equals(name))
				return entity;
		return null;
	}

	/**
	 * Gets all the Entities in the world as an UnmodifiableCollection.
	 * @return The Collection of Entities.
	 */
	public Collection<Entity> getEntities() {
		return Collections.unmodifiableCollection(entities.values());
	}

	/**
	 * Gets an ID that doesn't clash with any of the other Entities in the world.
	 * @return The available/free ID.
	 */
	private int getFreeID() {
		int id;
		while(getEntity((id = ((int)(Math.random()*Integer.MAX_VALUE)))) != null);
		return id;
	}

}
