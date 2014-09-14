package wolf3d.core;

import java.util.List;

import wolf3d.components.Component;

/**
 * The ComponentContainer class.<br>
 * TODO: Is it nescessary? Could simplyfy things to have Entity as the sole 'ComponentContainer'
 * @author Hamish Rae-Hodgson
 *
 */
public interface ComponentContainer {

	/**
	 * Attach the given component to this Entity.<br>
	 * Respectively sets the owner of the component to be this Entity.
	 * @throws NullPointerException If param is null.
	 * @throws IllegalStateException If the Component is already attached to an Entity
	 * @param component The component to add to this Entity.
	 * @return The attached Component.
	 */
	public <E extends Component> E attachComponent(E component);
	
	/**
	 * Attach the given component to this Entity.<br>
	 * Respectively sets the owner of the component to be this Entity.
	 * @throws NullPointerException If param is null.
	 * @throws IllegalStateException If the Component is already attached to an Entity
	 * @param type The Class of the Component you wish to instantiate and add to this Entity.
	 * @return The attached Component.
	 */
	public <E extends Component> E attachComponent(Class<E> type);
	
	/**
	 * Returns the first Component that matches type.
	 * @param type Class of the Component you are looking for.
	 * @return The matching component - or null if not found.
	 */
	public <E extends Component> E getComponent(Class<E> type);
	
	/**
	 * Returns a List containing all the Components that match the provided type.
	 * @param type Class of the Components you are looking for.
	 * @return The List of matching components - or an empty list if none were found.
	 */
	public <E extends Component> List<E> getComponents(Class<E> type);
	
	/**
	 * Detaches the specified component from this Entity.<br>
	 * If the component was present, the components Owner gets set to null.<br>
	 * Then the component can be freely attached to other Entities.
	 * @param component The component to remove.
	 * @return true if the component was present and successfully detached - otherwise false.
	 */
	public <E extends Component> boolean detachComponent(E component);
	
	/**
	 * Checks if the component is attached to this Entity.
	 * @param component The component to check for.
	 * @return Returns true if the component was present - false otherwise.
	 */
	public <E extends Component> boolean contains(E component);

}
