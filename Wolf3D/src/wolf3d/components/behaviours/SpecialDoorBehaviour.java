package wolf3d.components.behaviours;

import java.awt.event.KeyEvent;

import wolf3d.components.sensors.ProximitySensor;
import engine.components.Behaviour;
import engine.core.World;
import engine.input.Keyboard;

public class SpecialDoorBehaviour extends Behaviour {
	//this is if world is not static
	private World world;

	@Override
	public void update(float delta) {
		if (Keyboard.isKeyDown(KeyEvent.VK_SPACE)) {
			if (getOwner().getComponent(ProximitySensor.class).isTriggered()) {
				
			}
		}

	}

}