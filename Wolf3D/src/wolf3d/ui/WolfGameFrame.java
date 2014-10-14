package wolf3d.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;

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
import engine.util.ServiceLocator;
import wolf3d.GameDemo;
import wolf3d.WorldView;
import wolf3d.networking.Server;

/**
 * The entry point into the system.
 * @author Hamish Rae-Hodgson modified by Simon Brannigan
 *
 */
public class WolfGameFrame extends JFrame {

	private static final long serialVersionUID = 3938139405286328585L;
	private static final Logger log = LogManager.getLogger();

	private static final String DEFAULT_TITLE = "Wolf3D";
	private static final int DEFAULT_GL_WIDTH = 800;
	private static final int DEFAULT_GL_HEIGHT = 800;

	public static String ip;
	public static int port;

	public WolfGameFrame() {
		super(DEFAULT_TITLE);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				confirmExit();
			}
		});

		//Create the World
		final World world = new World();

		//Create views
		final WorldView worldView = ServiceLocator.getService(WorldView.class);
		final MiniMap minimap = new MiniMap(200, 200);

		JPanel mainPanel = new JPanel();
		mainPanel.add(worldView);
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);

		//Build OpenGL panel.
		JPanel eastPanel = new JPanel();
		eastPanel.add(minimap, BorderLayout.NORTH);
		this.getContentPane().add(eastPanel, BorderLayout.EAST);

		JPanel botPanel = new JPanel();
		botPanel.setPreferredSize(new Dimension(800, 200));

		//Inventory
		botPanel.add(new InventoryCanvas());

		// i<getSize will have to be changed later on. Currently used for testing
		this.getContentPane().add(botPanel, BorderLayout.SOUTH);

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
				GameDemo game = new GameDemo("Player", -1);
				game.setView(worldView); // give it the view so it can call it's display method appropriately.
				game.start();
			}
		});
	}

	/** Exits after confirming with the user if they really want to exit */
	private void confirmExit() {
		if(JOptionPane.showConfirmDialog(this,
				"Are you sure you want to Exit?",
				"Are you sure you want to Exit?",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	/** Create a new instance of App */
	public static void main(String[] args) {
		if(args.length==2){
			WolfGameFrame.ip = args[0];
			WolfGameFrame.port = Integer.parseInt(args[1]);
		}
		else{
			WolfGameFrame.ip = "localhost";
			Server s = new Server(0,1); //0 as port will listen on any free port.
			WolfGameFrame.port = s.getSocketPort();
			s.start();
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				/*Starts the GUI frame*/
				new WolfGameFrame();
			}
		});
	}
}
