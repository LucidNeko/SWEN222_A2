package engine.core;

import java.util.ArrayList;
import java.util.Collection;
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

	public TempEntityDef() {

	}
	
	public TempEntityDef(Entity entity) {
		name = entity.getName();
		Set<Component> components = entity.getAllComponentsCopy();
		//need to detach all components so they can be added to new
		//entity created in world
		for(Component c: components){
			entity.detachComponent(c);
		}
		this.components.addAll(components);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * @return returns the list of components
	 */
	public List<Component> getComponents() {
		return components;
	}
	
	public void addComponents(Collection<Component> components){
		this.components.addAll(components);
	}
}
