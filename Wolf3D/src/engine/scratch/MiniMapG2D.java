package engine.scratch;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.world.Cell;
import wolf3d.world.MapData;
import engine.common.Vec3;
import engine.core.Entity;
import engine.core.World;
import engine.util.ServiceLocator;

public class MiniMapG2D extends JComponent {
//	private static final Logger log = LogManager.getLogger();
//
//	private static final int WIDTH = 200;
//	private static final int HEIGHT = 200;
//	private static final float SCALE = 20;
//
//	/** size of map tokens */
//	private static final int token = 9;
//
//	{
//		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
//	}
//
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		render((Graphics2D)g);
//	}
//
//	private void render(Graphics2D g) {
//		Vec3 center = new Vec3();
//
//		//box for player at center
//		g.setColor(Color.BLUE);
//		g.fillRect((int)((WIDTH/2f)-(token/2f)), (int)((HEIGHT/2f)-(token/2f)), token, token);
//
//		//if player exists set center to player
//		List<Entity> player = ServiceLocator.getService(World.class).getEntity("Player");
//		if(!player.isEmpty()) {
//			center.set(player.get(0).getTransform().getPosition());
//		}
//
//		//draw entities
//		for(Entity e : ServiceLocator.getService(World.class).getEntity("Teddy")) {
//			Vec3 pos = center.sub(e.getTransform().getPosition());
//			pos.mulLocal(SCALE);
////			log.trace(pos);
//			g.setColor(Color.RED);
//			g.fillOval((int)((pos.x()+(WIDTH/2f))-(token/2f)), (int)((pos.z()+(HEIGHT/2f))-(token/2f)), token, token);
//		}
//		//draw walls
//		if(ServiceLocator.hasService(MapData.class)) {
//			MapData map = ServiceLocator.getService(MapData.class);
//			for(int row = 0; row < map.getWallsHeight(); row++) {
//				for(int col = 0; col < map.getWallsWidth(); col++) {
//					Cell cell = map.getWall(row, col);
//					int SCALE = (int)this.SCALE;
//					float x = (col-center.x());
//					float y = (row-center.z());
//					if(cell.hasNorth()) { //has north
//						g.drawLine((int)((WIDTH/2f)+x*SCALE), (int)((HEIGHT/2f)+y*SCALE),
//								(int)((WIDTH/2f)+x*SCALE + SCALE), (int)((HEIGHT/2f)+y*SCALE));
//					}
//					if(cell.hasEast()) { //has east
//						g.drawLine((int)((WIDTH/2f)+x*SCALE + SCALE), (int)((HEIGHT/2f)+y*SCALE),
//								(int)((WIDTH/2f)+x*SCALE + SCALE), (int)((HEIGHT/2f)+y*SCALE + SCALE));
//					}
//					if(cell.hasSouth()) { //has south
//						g.drawLine((int)((WIDTH/2f)+x*SCALE), (int)((HEIGHT/2f)+y*SCALE + SCALE),
//								(int)((WIDTH/2f)+x*SCALE + SCALE), (int)((HEIGHT/2f)+y*SCALE + SCALE));
//					}
//					if(cell.hasWest()) { //has west
//						g.drawLine((int)((WIDTH/2f)+x*SCALE), (int)((HEIGHT/2f)+y*SCALE),
//								(int)((WIDTH/2f)+x*SCALE), (int)((HEIGHT/2f)+y*SCALE + SCALE));
//					}
//				}
//			}
//		}
//
//		//draw border
//		g.setColor(Color.BLACK);
//		g.setStroke(new BasicStroke(4));
//		g.drawRect(0, 0, WIDTH, HEIGHT);
//	}

}
