package engine.scratch;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JComponent;

import wolf3d.components.Health;
import wolf3d.components.Strength;
import engine.core.Entity;
import engine.core.World;
import engine.util.ServiceLocator;

public class StatView extends JComponent {

	private static final int WIDTH = 200;
	private static final int HEIGHT = 100;

	public StatView() {
		super();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		render((Graphics2D)g);
	}

	private void render(Graphics2D g) {
		World world = ServiceLocator.getService(World.class);
		List<Entity> list = world.getEntity("Player");

		if(list.isEmpty()) return;

		Entity player = list.get(0);

		Health health = player.getComponent(Health.class);
		Strength strength = player.getComponent(Strength.class);

		g.setColor(Color.RED);
		g.fillRect(10, 10, (int) (180*(health.getHealth()/100f)), 30);
		g.setColor(Color.GREEN);
		g.fillRect(10, 60, (int) (180*(strength.getStrength()/100f)), 30);
		g.setColor(Color.BLACK);
		g.drawString("Health", 10, 10);
		g.drawString("Strength", 10, 60);
	}


}
