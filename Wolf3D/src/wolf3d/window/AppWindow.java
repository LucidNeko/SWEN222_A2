package wolf3d.window;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.components.Transform;
import wolf3d.components.renderers.TriangleRenderer;
import wolf3d.core.Entity;
import wolf3d.core.Keyboard;
import wolf3d.core.Mouse;
import wolf3d.world.World;

import com.jogamp.opengl.util.FPSAnimator;

/**
 * The root JFrame for the application. It contains the GLJPanel.
 * @author Hamish Rae-Hodgson
 *
 */
public class AppWindow extends JFrame {
	private static final long serialVersionUID = 3938139405286328585L;
	private static final Logger log = LogManager.getLogger();
	
	private static final String DEFAULT_TITLE = "Wolf3D";
	private static final int DEFAULT_GL_WIDTH = 800;
	private static final int DEFAULT_GL_HEIGHT = 600;
	
	/** the panel where OpenGl renderering occurs */
	private GLJPanel gamePanel;
	private FPSAnimator animator;

	public AppWindow() {
		super(DEFAULT_TITLE);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(java.awt.event.WindowEvent e) {
				confirmExit();
			}
		});
		
		//Sets the mouse to be a crosshair     
	    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	    
	    //Sets the mouse to be a hand
//	    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		//Register input devices.
		this.setFocusable(true);
		Keyboard.register(this);
		Mouse.register(this);
		
		//Create the World
		World world = new World();
		//Create test entity.
		Entity tri = new Entity(0);
		tri.attachComponent(Transform.class).walk(-5);
		tri.attachComponent(new TriangleRenderer(1, 1, 1, 0, 0));
		world.register(tri);
		
		//Build OpenGL panel.
		GLProfile glProfile = GLProfile.getDefault();
		GLCapabilities glCapabilities = new GLCapabilities(glProfile);
//		gamePanel = new EntityDemo(glCapabilities, DEFAULT_GL_WIDTH, DEFAULT_GL_HEIGHT);
		WorldView view = new WorldView(glCapabilities, DEFAULT_GL_WIDTH, DEFAULT_GL_HEIGHT);
		view.setWorld(world);
		animator = new FPSAnimator(view, 60);
		animator.start();
		this.getContentPane().add(view);
		
		this.pack();
		this.setVisible(true);
		
		// Must grab after component is visable on screen
//		Mouse.setGrabbed(true);
	}
	
	private void confirmExit() {
//		if(JOptionPane.showConfirmDialog(AppWindow.this, 
//				"Are you sure you want to Exit?", 
//				"Are you sure you want to Exit?", 
//				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			if(animator.isStarted()) animator.stop();
			System.exit(0);
//		}
	}
	
}
