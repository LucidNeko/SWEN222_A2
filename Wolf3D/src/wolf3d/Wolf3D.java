package wolf3d;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.networking.Server;
import engine.core.ServiceLocator;
import engine.core.World;
import engine.input.Keyboard;
import engine.input.Mouse;
import engine.scratch.MiniMap;

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

	public static String ip;
	public static int port;

	public Wolf3D() {
		super(DEFAULT_TITLE);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				confirmExit();
			}
		});

		//Create the World
		final World world = ServiceLocator.getService(World.class);

		//create views
		final WorldView worldView = new WorldView(DEFAULT_GL_WIDTH, DEFAULT_GL_HEIGHT);

		JPanel mainPanel = new JPanel();
		mainPanel.add(worldView);
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);

		//Build OpenGL panel.

		final MiniMap minimap = new MiniMap(200, 200);
		JPanel sidePanel = new JPanel();
		sidePanel.add(minimap);
		this.getContentPane().add(sidePanel, BorderLayout.EAST);

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
				GameDemo game = new GameDemo(Wolf3D.ip, Wolf3D.port);
				game.setView(worldView); // give it the view so it can call it's display method appropriately.
			}
		});
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
		if(args.length==2){
			Wolf3D.ip = args[0];
			Wolf3D.port = Integer.parseInt(args[1]);
		}
		else{
			Wolf3D.ip = "localhost";
			Server s = new Server(0,1); //0 as port will listen on any free port.
			Wolf3D.port = s.getSocketPort();
			s.start();
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				/*Starts the GUI frame*/
				new Wolf3D();
			}
		});
	}

}
