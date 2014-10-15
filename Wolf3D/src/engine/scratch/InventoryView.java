package engine.scratch;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.components.Inventory;
import engine.core.Entity;
import engine.core.World;
import engine.util.Resources;
import engine.util.ServiceLocator;

/**
 *
 * @author Hamish
 *
 */
public class InventoryView extends JComponent {
	private Logger log = LogManager.getLogger();

	private BufferedImage teddy;

	{
		try {
			teddy = Resources.getImage("teddy/teddyinv.png");
		} catch(IOException e) {
			log.error("failed loading teddy image for inventory");
		}
	}

	public InventoryView() {
		super();
		this.setPreferredSize(new Dimension(200,400));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		render((Graphics2D)g);
	}

	private void render(Graphics2D g) {
		g.setColor(Color.black);
		g.drawString("Inventory", 70, 10);
		g.drawImage(teddy, null, 20, 15);
		g.setColor(new Color(0,0,255,64));
		g.fillRect(80, 170, 40, 40);

		World world = ServiceLocator.getService(World.class);
		List<Entity> list = world.getEntity("Player");
		if(!list.isEmpty()) {
			Entity player = list.get(0);
			Inventory inventory = player.getComponent(Inventory.class);
			int count = inventory.getItems().size();
			g.setColor(Color.BLACK);
			g.drawString(String.valueOf(count), 95, 192);
		}
	}


}
