package wolf3d.core;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import wolf3d.components.Component;
import wolf3d.components.Transform;

/**
 * The Entity class is a container for Components.
 * @author Hamish Rae-Hodgson
 *
 */
public class Entity  {
	
	//HashSet in the order Components were added
	private final Set<Component> components = new LinkedHashSet<Component>();
	
	/** A unique ID in the system. */
	private final int uniqueID;
	
	/**
	 * Creates a new empty Entity with the given unique ID.
	 * @param uniqueID A unique ID to define this Entity.
	 */
	public Entity(int uniqueID) {
		this.uniqueID = uniqueID;
	}
	
	/**
	 * Creates a new Entity with the given unique ID and all the provided Components.
	 * @param uniqueID A unique ID to define this Entity.
	 * @param components The components to give this Entity.
	 */
	public Entity(int uniqueID, Component... components) {
		this.uniqueID = uniqueID;
		for(Component component : components) {
			attachComponent(component);
		}
	}
	
	/**
	 * Creates a new Entity with the given unique ID and new instances of the provided Components.
	 * @param uniqueID A unique ID to define this Entity.
	 * @param components The classes of the components you are giving to this Entity.
	 */
	@SafeVarargs
	public Entity(int uniqueID, Class<? extends Component>... components) {
		this.uniqueID = uniqueID;
		for(Class<? extends Component> type : components) {
			attachComponent(type);
		}
	}
	
	/** Get the unique ID bound to this Entity */
	public int getID() {
		return uniqueID;
	}
	
	/**
	 * Attach the given component to this Entity.<br>
	 * Respectively sets the owner of the component to be this Entity.
	 * @throws NullPointerException If param is null.
	 * @throws IllegalStateException If the Component is already attached to an Entity
	 * @param component The component to add to this Entity.
	 * @return The attached Component.
	 */
	public <E extends Component> E attachComponent(E component) {
		if(component == null) 
			throw new NullPointerException();
		if(component.getOwner() != null)
			throw new IllegalStateException("Component is already attached to something");
		this.components.add(component);
		component.setOwner(this);
		return component;
	}

	/**
	 * Attach a new instance of the given component to this Entity.<br>
	 * Respectively sets the owner of the component to be this Entity.
	 * @throws NullPointerException If param is null.
	 * @throws IllegalStateException If the Component is already attached to an Entity
	 * @param type The Class of the Component you wish to instantiate and add to this Entity.
	 * @return The attached Component.
	 */
	public <E extends Component> E attachComponent(Class<E> type) {
		try {
			return attachComponent((E)(type.newInstance()));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

	
	/**
	 * Returns the first Component that matches type.
	 * @param type Class of the Component you are looking for.
	 * @return The matching component - or null if not found.
	 */
	@SuppressWarnings("unchecked")
	public <E extends Component> E getComponent(Class<E> type) {
		for(Component component : components)
			if(type.isAssignableFrom(component.getClass()))
				return (E)component;
		return null;
	}

	/**
	 * Returns a List containing all the Components that match the provided type.
	 * @param type Class of the Components you are looking for.
	 * @return The List of matching components - or an empty list if none were found.
	 */
	@SuppressWarnings("unchecked")
	public <E extends Component> List<E> getComponents(Class<E> type) {
		List<E> out = new LinkedList<E>();
		for(Component component : components)
			if(type.isAssignableFrom(component.getClass()))
				out.add((E)component);
		return out;
	}
	
	/**
	 * Detaches the specified component from this Entity.<br>
	 * If the component was present, the components Owner gets set to null.<br>
	 * Then the component can be freely attached to other Entities.
	 * @param component The component to remove.
	 * @return true if the component was present and successfully detached - otherwise false.
	 */
	public <E extends Component> boolean detachComponent(E component) {
		if(this.components.remove(component)) {
			component.setOwner(null);
			return true;
		} else return false;
	}
	
	/**
	 * Checks if the component is attached to this Entity.
	 * @param component The component to check for.
	 * @return Returns true if the component was present - false otherwise.
	 */
	public <E extends Component> boolean contains(E component) {
		return components.contains(component);
	}
	
	//
	// Convenience Methods
	//
	
	/**
	 * Get the Transform attached to this entity. Identical in function to getComponent(Transform.class);
	 * @return Return the Transform, or null if there is no Transform attached.
	 */
	public Transform getTransform() {
		return getComponent(Transform.class);
	}

	@Override
	public String toString() {
		return "Entity [id=" + uniqueID + "]";
	}	
	
}
