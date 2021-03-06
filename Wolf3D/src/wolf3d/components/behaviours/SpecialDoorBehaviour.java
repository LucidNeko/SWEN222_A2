package wolf3d.components.behaviours;

import java.awt.event.KeyEvent;
import java.util.List;

import wolf3d.components.behaviours.animations.MoveUpAnimation;
import wolf3d.components.sensors.ProximitySensor;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.core.Entity;
import engine.core.World;
import engine.input.Keyboard;
import engine.util.Messenger;
import engine.util.ServiceLocator;

/**
 * This class is responsible for specifying the behaviours that must be met
 * before the special door will open
 *
 * @author Sameer Magan 300223776
 *
 */
public class SpecialDoorBehaviour extends Behaviour {
	// this is if world is not static
	private World world = ServiceLocator.getService(World.class);

	private int minAccessAmt = 3;

	@Override
	public void update(float delta) {
		if(getOwner().getComponent(ProximitySensor.class).isTriggered()){
			ServiceLocator.getService(Messenger.class).sendMessage("Drop 3 Teddys Items to open door");
		}
		List<Entity> teddys = world.getEntity("TeddyItem");
		// SpecialDoors position
		Vec3 pos = getOwner().getTransform().getPosition();
		int count = 0;

		// adding up how many teddys are in front of the door
		for (Entity t : teddys) {
			if (pos.sub(t.getTransform().getPosition()).length() < 1.0f) {
				count++;
			}
		}
		//Open if theres the correct amount
		if (count >= minAccessAmt) {
			getOwner().attachComponent(MoveUpAnimation.class)
					.setMoveDown(false);
			getOwner().detachComponent(this);

		}
	}

}
