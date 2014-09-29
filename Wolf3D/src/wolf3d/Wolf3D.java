package wolf3d;

import java.awt.Cursor;
import java.awt.event.WindowAdapter;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import engine.core.World;
import engine.input.Keyboard;
import engine.input.Mouse;
import wolf3d.ui.*;

/**
 * The entry point into the system.
 * @author Hamish Rae-Hodgson
 *
 */
public class Wolf3D extends JFrame {

	private static final long serialVersionUID = 3938139405286328585L;
	private static final Logger log = LogManager.getLogger();

	private static final String DEFAULT_TITLE = "Wolf3D";
	private static final int DEFAULT_GL_WIDTH = 800;
	private static final int DEFAULT_GL_HEIGHT = 600;

	public Wolf3D() {
		super(DEFAULT_TITLE);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				confirmExit();
			}
		});

		//Sets the mouse to be a crosshair
	    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

		//Create the World
		final World world = new World();

		//Build OpenGL panel.
		GLProfile glProfile = GLProfile.getDefault();
		GLCapabilities glCapabilities = new GLCapabilities(glProfile);
		final WorldView view = new WorldView(glCapabilities, DEFAULT_GL_WIDTH, DEFAULT_GL_HEIGHT, world);
		this.getContentPane().add(view);

		//Register input devices. If GLCanvas have to register to canvas.
  		view.setFocusable(true);
  		Keyboard.register(view);
  		Mouse.register(view);

		//Pack and display window.
		this.pack();
		this.setVisible(true);

		//the game. This is a thread. You need to start it.
		GameDemo game = new GameDemo(world);
		game.setView(view); // give it the view so it can call it's display method appropriately.
		game.start();
	}

	/** Exits after confirming with the user if they really want to exit */
	private void confirmExit() {
		if(JOptionPane.showConfirmDialog(Wolf3D.this,
				"Are you sure you want to Exit?",
				"Are you sure you want to Exit?",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
	}

	/** Create a new instance of App */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				/*Starts the GUI frame*/
				WolfFrame wf = new WolfFrame();
			}
		});
	}

}
