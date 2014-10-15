package wolf3d.components.behaviours.animations;

import java.awt.event.KeyEvent;

import wolf3d.components.behaviours.Translate;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Transform;
import engine.input.Keyboard;

public class JumpAnimation extends Behaviour {
	private int jumpDistance = 1;
	private float speed = 0.01f;
	private boolean up, start;

	Vec3 startPos;
	Vec3 endPos;

	Translate translate;



	private float alpha = 0;
	private boolean onGround = true;

	@Override
	public void update(float delta) {

		if(onGround && Keyboard.isKeyDownOnce(KeyEvent.VK_SPACE)) {
			onGround = false;
			alpha = 5;
		}



		if(!onGround) {
			//fall
			getOwner().getTransform().fly(alpha*delta);
			alpha -= 0.5f;
			Vec3 pos = getOwner().getTransform().getPosition();
			if(pos.y() < 0) {
				getOwner().getTransform().setPosition(pos.x(), 0, pos.z());
				onGround = true;
				alpha = 0;
			}
		}

//		if(Keyboard.isKeyDownOnce(KeyEvent.VK_SPACE)){
//			start = true;
//		}
//
//		if (start) {
//			if (startPos == null) {
//				startPos = getOwner().getTransform().getPosition();
//				endPos = startPos.add(0, jumpDistance, 0);
//
//				translate = new Translate(startPos, endPos, speed);
//			}
//
//			if (!up) {
//				if (!getOwner().contains(translate)) {
//					up = true;
//					startPos = getOwner().getTransform().getPosition();
//					endPos = startPos.add(0, -jumpDistance, 0);
//					translate = new Translate(startPos, endPos, speed);
//				}
//			} else {
//				if (!getOwner().contains(translate)) {
//					start = false;
//					startPos = null;
//				}
//			}
//
//		}
	}
}
