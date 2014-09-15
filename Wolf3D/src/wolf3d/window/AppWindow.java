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

import wolf3d.core.Keyboard;
import wolf3d.core.Mouse;

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
		
		//Sets the mouse to be a white crosshair
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Point hotSpot = new Point(2,2);
	    BufferedImage cursorImage = new BufferedImage(5, 5, BufferedImage.TRANSLUCENT); 
	    Graphics2D graphics = cursorImage.createGraphics();
	    graphics.setColor(Color.WHITE);
	    graphics.drawLine(0, 2, 4, 2); //leftright line
	    graphics.drawLine(2, 0, 2, 4); //updown line
	    graphics.dispose();
	    Cursor crosshair = toolkit.createCustomCursor(cursorImage, hotSpot, "Crosshair");        
	    setCursor(crosshair);
		
		//Register input devices.
		this.setFocusable(true);
		Keyboard.register(this);
		Mouse.register(this);
		
		//Build OpenGL panel.
		GLProfile glProfile = GLProfile.getDefault();
		GLCapabilities glCapabilities = new GLCapabilities(glProfile);
		gamePanel = new EntityDemo(glCapabilities, DEFAULT_GL_WIDTH, DEFAULT_GL_HEIGHT);
		animator = new FPSAnimator(gamePanel, 60);
		animator.start();
		this.getContentPane().add(gamePanel);
		
		this.pack();
		this.setVisible(true);
		
		// Must grab after component is visable on screen
		Mouse.setGrabbed(true);
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
