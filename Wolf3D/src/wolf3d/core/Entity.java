package wolf3d.core;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import wolf3d.core.components.Component;
import wolf3d.core.components.ComponentContainer;

/**
 * The Entity class is a container for Components
 * @author Hamish Rae-Hodgson
 *
 */
public class Entity implements ComponentContainer {
	
	//HashSet in order of additions
	private final Set<Component> components = new LinkedHashSet<Component>();
	
	private final String name;
	
	public Entity(String name) {
		this.name = name;
	}
	
	public Entity(String name, Component... components) {
		this.name = name;
		for(Component component : components) {
			attachComponent(component);
		}
	}
	
	@SafeVarargs
	public Entity(String name, Class<? extends Component>... components) {
		this.name = name;
		for(Class<? extends Component> type : components) {
			attachComponent(type);
		}
	}
	
	public String getName() {
		return name;
	}
	
	
	@Override
	public <E extends Component> E attachComponent(E component) {
		if(component == null) 
			throw new NullPointerException();
		if(component.getOwner() != null)
			throw new RuntimeException("Component is already attached to something");
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

	public String toString() {
		return name;
	}	
	
}
