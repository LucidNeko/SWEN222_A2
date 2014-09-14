package wolf3d.core;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashSet;
import java.util.Set;

import wolf3d.common.Mathf;

/**
 * The Mouse is a class with static methods to determine mouse behaviour.
 * @author Hamish Rae-Hodgson
 *
 */
public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	public static final int LEFT_BUTTON = MouseEvent.BUTTON1;
	public static final int MIDDLE_BUTTON = MouseEvent.BUTTON2;
	public static final int RIGHT_BUTTON = MouseEvent.BUTTON3;
	
	/** Singleton instance of Mouse */
	private static final Mouse instance = new Mouse();
	
	private static final Set<Integer> pressedButtons = new HashSet<Integer>();
	
	private static float x = 0;
	private static float y = 0;
	private static float dx = 0;
	private static float dy = 0;
	
	/** private singleton constructor */
	private Mouse() { }
	
	/**
	 * Register the Mouse with the component so we can listen to mouse behaviour.
	 * @param component The component to attach the Mouse to.
	 */
	public static void register(Component component) {
		component.addMouseListener(instance);
		component.addMouseMotionListener(instance);
		component.addMouseWheelListener(instance);
	}
	
	public static boolean isButtonDown(int button) {
		return pressedButtons.contains(button);
	}
	
	public static float getX() {
		return x;
	}
	
	public static float getY() {
		return y;
	}
	
	/**
	 * Movement on the x axis since last time getDX() was called.
	 * @return The movement on the x axis.
	 */
	public synchronized static float getDX() {
		float out = dx;
		dx = 0;
		return out;
	}
	
	/**
	 * Movement on the y axis since last time getDY() was called.
	 * @return The movement on the y axis.
	 */
	public synchronized static float getDY() {
		float out = dy;
		dy = 0;
		return out;
	}

	private static int lockX, lockY;
	private static Robot robot;
	private static boolean robotMove = false;
	static {
		try { robot = new Robot(); } catch (AWTException e) { }
	}
	public static void lockPos(int x, int y) {
		lockX = x;
		lockY = y;
	}
	
	public synchronized static void lock() {
		robotMove = true;
		robot.mouseMove(lockX, lockY);
		dx = 0;
		dy = 0;
	}
	
	///////////////
	// Listeners //
	///////////////

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//ignore robot move
//		float t = 1;
//		if(e.getX() > lockX-t && e.getX() < lockX+t && e.getY() > lockY-t && e.getY() < lockY+t) return;
		
		float nx = e.getX();
		float ny = e.getY();
		dx = nx-x;
		dy = y-ny;
		x = nx;
		y = ny;
		
		//normalise (dx, dy)
//		float length = Mathf.sqrt(dx*dx + dy*dy);
//		float multiplier = 1/length;
//		dx *= multiplier;
//		dy *= multiplier;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressedButtons.add(e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressedButtons.remove(e.getButton());
	}
	
	public void mouseDragged(MouseEvent e) { }
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }

}
