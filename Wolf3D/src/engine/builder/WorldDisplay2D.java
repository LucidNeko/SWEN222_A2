package engine.builder;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import engine.common.Vec3;
import engine.components.Transform;
import engine.core.Entity;
import engine.core.World;

/**
 * 
 * @author Hamish
 *
 */
public class WorldDisplay2D extends JComponent {
	private final static Logger log = LogManager.getLogger();
	
	public static final int SCALE = 50;
	
	private World world;
	
	public Entity selection = null;

	public WorldDisplay2D(World world) {
		super();
		this.setPreferredSize(new Dimension(800, 600));
		this.setFocusable(true);
		this.world = world;
	}
	
	public void setSelectedEntity(Entity e) {
		this.selection = e;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		for(Entity e : world.getEntities())
			drawEntity(g2d, e);
	}

	private void drawEntity(Graphics2D g, Entity e) {
//		if(e.getName().equalsIgnoreCase("wall")) 
		
		Transform t = e.getTransform();
		Vec3 pos = t.getPosition();
		Vec3 look = t.getLook();
		
		int cx = (int) (pos.x()*SCALE);
		int cy = (int) (pos.z()*SCALE);
		int x = cx - 5;
		int y = cy - 5;
		int lx = (int) (cx + look.x()*(SCALE/2));
		int ly = (int) (cy + look.z()*(SCALE/2));
		
		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(3));
		g.drawLine(cx, cy, lx, ly);
		if(e == selection)
			g.setColor(Color.GREEN);
		else g.setColor(Color.BLUE);
		g.fillOval(x, y, 11, 11);
		
	}
}
