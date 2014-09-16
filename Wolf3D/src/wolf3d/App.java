package wolf3d;

import java.awt.Cursor;
import java.awt.event.WindowAdapter;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.components.Transform;
import wolf3d.components.renderers.Triangle3DRenderer;
import wolf3d.core.Entity;
import wolf3d.core.Keyboard;
import wolf3d.core.Mouse;
import wolf3d.window.EntityGameDemo;
import wolf3d.window.GameDemo;
import wolf3d.window.WorldView;
import wolf3d.world.World;

/**
 * The entry point into the system.
 * @author Hamish Rae-Hodgson
 *
 */
public class App extends JFrame {
	
	private static final long serialVersionUID = 3938139405286328585L;
	private static final Logger log = LogManager.getLogger();
	
	private static final String DEFAULT_TITLE = "Wolf3D";
	private static final int DEFAULT_GL_WIDTH = 800;
	private static final int DEFAULT_GL_HEIGHT = 600;

	public App() {
		super(DEFAULT_TITLE);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(java.awt.event.WindowEvent e) {
				confirmExit();
			}
		});
		
		//Sets the mouse to be a crosshair     
	    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		
		//Register input devices.
		this.setFocusable(true);
		Keyboard.register(this);
		Mouse.register(this);
		
		//Create the World
		final World world = new World();
		
		//Build OpenGL panel.
		GLProfile glProfile = GLProfile.getDefault();
		GLCapabilities glCapabilities = new GLCapabilities(glProfile);
		final WorldView view = new WorldView(glCapabilities, world, DEFAULT_GL_WIDTH, DEFAULT_GL_HEIGHT);
		this.getContentPane().add(view);
		
		//Pack and display window.
		this.pack();
		this.setVisible(true);
		
		//the game. This is a thread. You need to start it.
		EntityGameDemo game = new EntityGameDemo(world);
		game.setView(view); // give it rhe view so it can call it's display method appropriately.
		game.start();
	}
	
	/** Exits after confirming with the user if they really want to exit */
	private void confirmExit() {
//		if(JOptionPane.showConfirmDialog(AppWindow.this, 
//				"Are you sure you want to Exit?", 
//				"Are you sure you want to Exit?", 
//				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			System.exit(0);
//		}
	}
	
	/** Create a new instance of App */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new App();
			}
		});
	}

}
