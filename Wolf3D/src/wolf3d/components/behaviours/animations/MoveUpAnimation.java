package wolf3d.components.behaviours.animations;

import engine.common.Mathf;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Transform;

public class MoveUpAnimation extends Behaviour {
	
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