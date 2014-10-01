package engine.core;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import engine.components.Component;
import engine.components.Transform;

/**
 * The Entity class is a container for Components.
 * @author Hamish
 *
 */
public class Entity {
	
	//HashSet in the order Components were added
	private final Set<Component> components = new LinkedHashSet<Component>();
	
	/** A unique ID in the system. */
	private final int uniqueID;
	
	/** The name of this Entity */
	private String name;
	
	/**
	 * Creates a new empty Entity with the given unique ID.<br>
	 * Package-private constructor.
	 * @param uniqueID A unique ID to define this Entity.
	 */
	Entity(int uniqueID, String name) {
		this.uniqueID = uniqueID;
		this.name = name;
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
	 * Returns a List containing all the Components.
	 */
	public Set<Component> getAllComponents() {
		return components;
	}
	
	/**
	 * Returns a copy of List containing all the Components.
	 */
	public Set<Component> getAllComponentsCopy() {
		return new LinkedHashSet<Component>(components);
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
	
	/**
	 * Returns true if this Entity has at least one instance of a component of the type specified.
	 * @param type The type of Component to enquire about.
	 * @return True if this Entity has an instance of the Component. False if it doesn't.
	 */
	public <E extends Component> boolean hasComponent(Class<E> type) {
		return getComponent(type) != null;
	}
	
	/** Get the unique ID bound to this Entity */
	public int getID() {
		return uniqueID;
	}
	
	/** Get the name of this Entity */
	public String getName() {
		return name;
	}
	
	/** Set the name of this Entity */
	public void setName(String name) {
		this.name = name;
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
		return "Entity [id=" + uniqueID + ", name=" + name + "]";
	}
	
}
