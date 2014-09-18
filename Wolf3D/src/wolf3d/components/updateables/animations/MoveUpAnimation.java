package wolf3d.components.updateables.animations;

import wolf3d.common.Mathf;
import wolf3d.common.Vec3;
import wolf3d.components.Transform;
import wolf3d.components.updateables.Updateable;

public class MoveUpAnimation extends Updateable {
	
	private float speed = 1f; //0.1 units per second.
	private float distance = 2; //2 units.
	
	private Vec3 startPos = null;
	private Vec3 endPos = null;
	
	@Override
	public void update(float delta) {
		requires(Transform.class);
		
		Transform t = getOwner().getTransform();
		
		if(startPos == null) {
			startPos = t.getPosition();
			endPos = startPos.add(0, distance, 0);
		}
		
		if(isFinished()) {
			getOwner().detachComponent(this);
			return;
		}
		
		t.translate(0, speed*delta, 0);
	}
	
	public boolean isFinished() {
		return Mathf.abs(getOwner().getTransform().getPosition().sub(startPos).y()) >= distance;
	}
	
}
