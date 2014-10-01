package engine.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import engine.components.Component;

public class TempEntityDef {
	private String name = "unnamed";
	private List<Component> components = new ArrayList<Component>();

	public TempEntityDef() {

	}
	
	public TempEntityDef(Entity entity) {
		name = entity.getName();
		for(Component c: entity.getAllComponentsCopy()){
			entity.detachComponent(c);
		}
		this.components.addAll(entity.getAllComponents());
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * @return the components
	 */
	public List<Component> getComponents() {
		return components;
	}
	
	public void addComponents(Collection<Component> components){
		this.components.addAll(components);
	}
}
