package wolf3d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.database.DataManagement;
import wolf3d.networking.Server;
import wolf3d.ui.IGCanvas;
import wolf3d.ui.WolfCanvas;
import engine.core.World;
import engine.input.Keyboard;
import engine.input.Mouse;
import engine.scratch.MiniMap;
import engine.util.ServiceLocator;

/**
 * The entry point into the system.
 * @author Hamish Rae-Hodgson
 *
 */
public class Wolf3D extends JFrame {

	private static final long serialVersionUID = 3938139405286328585L;
	private static final Logger log = LogManager.getLogger();

	private static final String DEFAULT_TITLE = "Wolf3D";

	public static final int DEFAULT_GL_WIDTH = 800;
	public static final int DEFAULT_GL_HEIGHT = 600;
	private BufferedImage inGameMenu;

	public static String ip;
	public static int port;

	public Wolf3D(){
		super(DEFAULT_TITLE);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				confirmExit();
			}
		});

		//Create the World
		//		final World world = ServiceLocator.getService(World.class);
		//		final World world = DataManagement.loadWorld("defaultWorld.txt");


		//create views
		final WorldView worldView = ServiceLocator.getService(WorldView.class);

		JPanel mainPanel = new JPanel();
		mainPanel.add(worldView);

		log.trace(this.getGlassPane());

		//Adds glass pane
//		JComponent c = new JComponent() {
//			{
//				this.setPreferredSize(new Dimension(800, 600));
//			}
//			protected void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				g.drawima
//			}
//		};
//
//		c.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				log.trace("Moused");
//			}
//		});
//		c.setFocusable(false);

		((JPanel)(this.getGlassPane())).add(new IGCanvas(200, 300));
		this.getGlassPane().setVisible(true);
		this.getContentPane().add(mainPanel);

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
