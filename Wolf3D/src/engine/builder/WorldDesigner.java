package engine.builder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.Wolf3D;

import engine.common.Vec3;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.core.Entity;
import engine.core.World;
import engine.texturing.Material;
import engine.util.Resources;

/**
 * (on top down view)
 * Left click to select and drag around an entity
 * Middle click to select and entity
 * Middle click (hold + drag around) to rotate and entity
 * Right click to create an entity at the location of the click.
 * Scroll wheel to move an entity up/down.
 * 
 * (on 3d view)
 * Press control to capture the mouse so you can look aroung.
 * Pressing control again will free the mouse.
 * @author Hamish
 *
 */
public class WorldDesigner extends JFrame implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	private final static Logger log = LogManager.getLogger();
	
	private final Vec3 pressedAt = new Vec3();
	
	private WorldDisplay2D display;
	private final World world;
	
	private String currentName = "Entity";
	
	private Entity currentEntity = null;

	private boolean dragging;
	
	public WorldDesigner() {
		super("World Designer v1.0");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		Wolf3D w3d = new Wolf3D();
		world = w3d.getWorld();
		
		display = new WorldDisplay2D(world);
		this.getContentPane().add(display);
		
		display.addKeyListener(this);
		display.addMouseListener(this);
		display.addMouseMotionListener(this);
		display.addMouseWheelListener(this);
		
		this.pack();
		this.setVisible(true);
		
		new Thread(new Runnable() {
			public void run() {
				while(true) {
					display.repaint();
					try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
		}).start();
	}
	
	private void createEntity() {
		currentEntity = world.createEntity(currentName);
		currentEntity.attachComponent(MeshFilter.class).setMesh(Resources.getMesh("link/young_link_s.obj"));
		currentEntity.attachComponent(MeshRenderer.class).setMaterial(new Material(Resources.getTexture("link/young_link.png", true)));
		Vec3 pos = pressedAt.mul(1f/display.SCALE);
		currentEntity.getTransform().setPosition(pos.x(), 0, pos.z());
		display.setSelectedEntity(currentEntity);
	}
	
	private void selectEntity() {
		for(Entity e : world.getEntities()) {
			Vec3 a = e.getTransform().getPosition(); a.setY(0);
			Vec3 b = pressedAt.mul(1f/display.SCALE);
			if(a.sub(b).length() < 0.2f) {
				currentEntity = e;
				display.setSelectedEntity(e);
			}
		}
	}
	
	

	@Override
	public void mousePressed(MouseEvent e) { 
		pressedAt.set(e.getX(), 0, e.getY());
		
		dragging = false;
		switch(e.getButton()) {
		case MouseEvent.BUTTON1 : 
			dragging = true;
		case MouseEvent.BUTTON2 :
			selectEntity();
			break;
		case MouseEvent.BUTTON3 : 
			createEntity();
			break;
		}
		display.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) { }
	
	/** Unused */
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void keyTyped(KeyEvent e) { }
	
	public static void main(String[] args) {
		new WorldDesigner();
	}



	@Override
	public void mouseDragged(MouseEvent e) {
		if(dragging) {
			currentEntity.getTransform().setPosition((float)e.getX()/display.SCALE, currentEntity.getTransform().getPosition().y(), (float)e.getY()/display.SCALE);
		} else {
			Vec3 releasedAt = new Vec3(e.getX(), 0, e.getY());
			Vec3 dir = releasedAt.sub(pressedAt);
			dir.normalize();
			
			currentEntity.getTransform().lookInDirection(dir);
		}
		display.repaint();
	}



	@Override
	public void mouseMoved(MouseEvent e) { }



	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		currentEntity.getTransform().translate(0, 0.01f * (e.getWheelRotation() < 0 ? 1 : -1), 0);
	}

}
