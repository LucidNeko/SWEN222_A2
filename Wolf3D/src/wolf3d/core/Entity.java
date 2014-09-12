package wolf3d.core;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import wolf3d.core.components.Component;
import wolf3d.core.components.ComponentContainer;

/**
 * The Entity class is a container for Components.
 * @author Hamish Rae-Hodgson
 *
 */
public class Entity implements ComponentContainer {
	
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
	
	@Override
	public <E extends Component> E attachComponent(E component) {
		if(component == null) 
			throw new NullPointerException();
		if(component.getOwner() != null)
			throw new IllegalStateException("Component is already attached to something");
		this.components.add(component);
		component.setOwner(this);
		return component;
	}

	@Override
	public <E extends Component> E attachComponent(Class<E> type) {
		try {
			return attachComponent((E)(type.newInstance()));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Component> E getComponent(Class<E> type) {
		for(Component component : components)
			if(type.isAssignableFrom(component.getClass()))
				return (E)component;
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Component> List<E> getComponents(Class<E> type) {
		List<E> out = new LinkedList<E>();
		for(Component component : components)
			if(type.isAssignableFrom(component.getClass()))
				out.add((E)component);
		return out;
	}
	
	@Override
	public <E extends Component> boolean detachComponent(E component) {
		if(this.components.remove(component)) {
			component.setOwner(null);
			return true;
		} else return false;
	}
	
	@Override
	public <E extends Component> boolean contains(E component) {
		return components.contains(component);
	}

	@Override
	public String toString() {
		return "Entity [id=" + uniqueID + "]";
	}	
	
}
