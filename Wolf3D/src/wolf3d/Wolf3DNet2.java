package wolf3d;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import engine.core.World;
import engine.input.Keyboard;
import engine.input.Mouse;
import engine.scratch.MiniMap;
import engine.util.Resources;
import wolf3d.ui.*;

/**
 * The entry point into the system.
 * @author Hamish Rae-Hodgson
 *
 */
public class Wolf3DNet2 extends JFrame {

	private static final long serialVersionUID = 3938139405286328585L;
	private static final Logger log = LogManager.getLogger();

	private static final String DEFAULT_TITLE = "Wolf3D";
	private static final int DEFAULT_GL_WIDTH = 800;
	private static final int DEFAULT_GL_HEIGHT = 600;
	
	public static String ip;
	public static int port;

	public Wolf3DNet2() {
		super(DEFAULT_TITLE);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				confirmExit();
			}
		});
		

		//Create the World
		final World world = new World();
		world.equals("hi");
		
		//create views
		final WorldView worldView = new WorldView(DEFAULT_GL_WIDTH, DEFAULT_GL_HEIGHT, world);
		final MiniMap minimap = new MiniMap(200, 200, world);

		JPanel mainPanel = new JPanel();
		mainPanel.add(worldView);
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		//Build OpenGL panel.
		/*
		JPanel sidePanel = new JPanel();
		sidePanel.add(minimap);
		this.getContentPane().add(sidePanel, BorderLayout.EAST);
		*/

		//Register input devices. If GLCanvas have to register to canvas.
		worldView.setFocusable(true);
  		Keyboard.register(worldView);
  		Mouse.register(worldView);
  		Mouse.setCursor(Mouse.CURSOR_INVISIBLE);

		//Pack and display window.
		this.pack();
		this.setVisible(true);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//the game. This is a thread. You need to start it.
				GameDemoNet2 game = new GameDemoNet2(world, Wolf3DNet2.ip, Wolf3DNet2.port);
				game.setView(worldView); // give it the view so it can call it's display method appropriately.
				//game.start();
			}
		});
	}

	/** Exits after confirming with the user if they really want to exit */
	private void confirmExit() {
		if(JOptionPane.showConfirmDialog(Wolf3DNet2.this,
				"Are you sure you want to Exit?",
				"Are you sure you want to Exit?",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
	}

	/** Create a new instance of App */
	public static void main(String[] args) {
		Wolf3DNet2.ip = args[0];
		Wolf3DNet2.port = Integer.parseInt(args[1]);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				/*Starts the GUI frame*/
				new Wolf3DNet2();
			}
		});
	}

}
