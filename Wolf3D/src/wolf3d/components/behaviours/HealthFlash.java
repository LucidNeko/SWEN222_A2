package wolf3d.components.behaviours;

import wolf3d.components.Health;
import engine.common.Color;
import engine.components.Behaviour;
import engine.components.MeshRenderer;
/**
 * This class is responsible for flashing a color at different speeds depending
 * on the owner of this components health
 * @author Sameer Magan 300223776
 *
 */
public class HealthFlash extends Behaviour {
	float speed = 1;// 0.01f;
	float time;
	Color baseColor = Color.WHITE;
	Color flashColor = Color.RED;

	Color[] colors = { baseColor, flashColor };

	@Override
	public void update(float delta) {
		requires(Health.class, MeshRenderer.class);

		time += delta;

		float health = getOwner().getComponent(Health.class).getHealth();
		MeshRenderer mesh = getOwner().getComponent(MeshRenderer.class);

		if (health < 100) {
			mesh.getMaterial().setColor(
					colors[(int) (time * speed % colors.length)]);
		}
		if (health < 80) {
			speed = 2.5f;
		}
		if (health < 60) {
			speed = 5.0f;
		}
		if (health < 40) {
			speed = 7.5f;
		}
		if (health < 20) {
//			speed = 10;
//			mesh.getMaterial().setColor(
//					colors[1]);
			mesh.getMaterial().setColor(new Color(0.1f, 1, 0.8f, 0.7f));
		}

	}

}
