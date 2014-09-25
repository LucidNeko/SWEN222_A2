package wolf3d.components.updateables.behaviours;

import wolf3d.components.updateables.Updateable;

public class Health extends Updateable {

	private static int health = 100;
	
	private int damageAmt = 10;
	@Override
	public void update(float delta) {
		health -= damageAmt;
	}

}
