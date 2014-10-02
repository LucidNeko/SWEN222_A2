package engine.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import engine.components.Component;
/**
 * This class contains everything needed to create a given entity in the world
 * @author Sameer Magan
 *
 */
public class TempEntityDef {
	private String name = "unnamed";
	private List<Component> components = new ArrayList<Component>();

	public TempEntityDef() {}
	
	public TempEntityDef(Entity entity) {
		name = entity.getName();
		List<Component> components = entity.getComponents(Component.class);
		//need to detach all components so they can be added to new
		//entity created in world
		for(Component c: components){
			entity.detachComponent(c);
		}
		this.components.addAll(components);
	}

	/**
	 * Sets the name
	 * @param name the name to set to
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the given EntityDef
	 * @return returns the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return returns the list of components
	 */
	public List<Component> getComponents() {
		return components;
	}
	
	/**
	 * Adds the given components to the components list
	 * @param components the collection of components to be added
	 */
	public void addComponents(Collection<Component> components){
		this.components.addAll(components);
	}
}
