package engine.input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

/**
 * The Keyboard is a class with static methods to determine which keys are pressed.
 * @author Hamish
 *
 */
public class Keyboard implements KeyListener {
	
	/** Singleton instance of the Keyboard */
	private static final Keyboard instance = new Keyboard();
	
	/** Set of all currently pressed keys */
	private static final Set<Integer> pressedKeys = new HashSet<Integer>();
	
	/**
	 * Private constructor to guarantee singleton.
	 */
	private Keyboard() { }
	
	/**
	 * Register the Keyboard with the component to listen for keypresses.
	 * @param component The component to listen to.
	 */
	public static void register(Component component) {
		component.addKeyListener(instance);
	}
	
	/**
	 * Returns true if the specified key is pressed.
	 * @param keycode The key to check for. KeyEvent.VK_{?}
	 * @return True if the key is down, otherwise false.
	 */
	public static boolean isKeyDown(int keycode) {
		return pressedKeys.contains(keycode);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		pressedKeys.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) { }

}
