package wolf3d.components.collision;

import wolf3d.components.AABB;
import wolf3d.components.Component;

public abstract class Collider extends Component {

	public abstract AABB getAABB();
	
}
