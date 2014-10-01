package engine.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import engine.components.Component;

/**
 * TODO: Concept. Define an EntityDef that contains everything needed to create a given entity.<br>
 * Could potentially pass an EntityDef to the World, which would then create the Entity.
 * 
 * Maybe need to make all Components cloneable? shallow only?
 * @author Hamish
 *
 */
public class EntityDef {
	
	private String name = "unnamed";
	private List<Class<? extends Component>> components = new ArrayList<Class<? extends Component>>();
	
	public EntityDef() {
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	
	/**
	 * @return the components
	 */
	public List<Class<? extends Component>> getComponents() {
		return components;
	}
	
	//This method is just to make adding all components easier
	//You can yell at me if u dont like it lol
	/**
	 * Adds all components taking in a set of components
	 * @param components components to be added
	 * @author Sameer Magan
	 */
	public void addComponents(Set<Component> components){
		components.addAll(components);
	}
	
	public <E extends Component> void addComponents(Class<? extends E>... components) {
		for(Class<? extends E> type : components)
			this.components.add(type);
	}

}
