package engine.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import engine.components.Component;
import engine.components.Transform;
import engine.scratch.WireframeMeshRenderer;

/**
 * The world is a factory for Entities and contains all of the Entities.
 * @author Hamish
 *
 */
public class World implements Service{

	/** All the Entities in the world */
	private Map<Integer, Entity> entities = Collections.synchronizedMap(new HashMap<Integer, Entity>());

	/**
	 * Create an Entity with the given name and register it in this world.
	 * @param name The name of the Entity.
	 * @return The Entity that was created and added to this world.
	 */
	public Entity createEntity(String name) {
		Entity entity = new Entity(getFreeID(), name);
		entity.attachComponent(Transform.class);
		synchronized(entities) {
			entities.put(entity.getID(), entity);
		}
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
		synchronized(entities) {
			entities.put(entity.getID(), entity);
		}
		return entity;
	}

	/**
	 * Destroy the Entity with id.
	 * @param id The id of the Entity to destroy.
	 * @return Returns true if there was an Entity with the id and it was removed.<br>
	 * 		   Returns false if there was no Entity meaning no removal.
	 */
	public boolean destroyEntity(int id) {
		synchronized(entities) {
			return entities.remove(id) != null;
		}
	}

	/**
	 * <b>O(1)</b> - Get the Entity in this world that has the id.
	 * @param id The id of the Entity you want.
	 * @return The Entity, or null if not found.
	 */
	public Entity getEntity(int id) {
		synchronized(entities) {
			return entities.get(id);
		}
	}

	/**
	 * <b>O(n)</b> - Gets a List of Entities from this world that have the given name..
	 * @param name The name of the Entity.
	 * @return The list of entities. An empty list if none found. Never null.
	 */
	public List<Entity> getEntity(String name) {
		List<Entity> out = new ArrayList<Entity>(2);
		synchronized(entities) {
			for(Entity entity : entities.values())
				if(entity.getName().equals(name))
					out.add(entity);
		}
		return out;
	}

	/**
	 * Gets a snapshot of the entities currently in the world.
	 * @return The Collection of Entities.
	 */
	public synchronized Collection<Entity> getEntities() {
		synchronized(entities) {
			return new LinkedList<Entity>(entities.values());
		}
	}

	/**
	 * Adds an EntityDef to the world
	 * @param entityDef the EntityDef to be added to the world
	 * @return false if there was an entity already contained with the same key
	 * true if it was added successfully
	 *
	 * @author Sameer Magan
	 */
	public boolean addEntityDef(TempEntityDef entityDef){
		int id = getFreeID();
		Entity entity = new Entity(id, entityDef.getName());
		for(Component com: entityDef.getComponents()){
			entity.attachComponent(com);
		}
		synchronized(entities) {
			return entities.put(id, entity) == null;
		}
	}

	private int nextID = 0;
	/**
	 * Gets an ID that doesn't clash with any of the other Entities in the world.
	 * @return The available/free ID.
	 */
	private int getFreeID() {
		int id;
//		while(getEntity((id = ((int)(Math.random()*Integer.MAX_VALUE)))) != null);
		while(getEntity((id = nextID++)) != null);
		return id;
	}

	@Override
	public String getName() {
		return "World";
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
