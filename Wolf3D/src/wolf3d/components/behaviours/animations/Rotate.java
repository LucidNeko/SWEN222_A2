package wolf3d.components.behaviours.animations;

import engine.common.Mathf;
import engine.components.Behaviour;
import engine.components.Transform;

public class Rotate extends Behaviour {

	float rot = Mathf.degToRad(90);

	@Override
	public void update(float delta) {
		Transform itemTrans = getOwner().getTransform();
		itemTrans.rotateY(delta* rot);

	}

}
