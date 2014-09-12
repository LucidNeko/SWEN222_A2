package wolf3d.core.components;

import java.util.List;

/**
 * The ComponentContainer class.<br>
 * TODO: Is it nescessary? Could simplyfy things to have Entity as the sole 'ComponentContainer'
 * @author Hamish Rae-Hodgson
 *
 */
public interface ComponentContainer {

	public <E extends Component> E attachComponent(E component);
	public <E extends Component> E attachComponent(Class<E> type);
	public <E extends Component> E getComponent(Class<E> type);
	public <E extends Component> List<E> getComponents(Class<E> type);
	public <E extends Component> boolean detachComponent(E component);
	public <E extends Component> boolean contains(E component);

}
