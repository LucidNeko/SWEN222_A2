package engine.scratch;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.GameDemo;
import wolf3d.WorldView;
import engine.common.Vec3;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.components.Transform;
import engine.core.Entity;
import engine.core.World;
import engine.input.Keyboard;
import engine.input.Mouse;
import engine.texturing.Material;
import engine.texturing.Mesh;
import engine.util.Resources;

/**
 * Working Designer.
 * @author Hamish
 *
 */
public class WorldDesigner extends JFrame {
//	private final static Logger log = LogManager.getLogger();
//
//	private static final int SCALE = 50;
//	private static final int DEFAULT_WIDTH = 1600;
//	private static final int DEFAULT_HEIGHT = 450;
//	private final static String[] OBJS = new String[] { "wall.obj", "link/young_link_s.obj", "link/link.obj", "m9/M9.obj", "motorbike/katana.obj", "teddy/teddy.obj" };
//	private final static String[] TEXTURES = new String[] { "debug_floor.png", "debug_wall.png", "link/link.png", "link/young_link.png", "m9/M9.png", "motorbike/katana.png", "teddy/teddy.png" };
//
//	private final World world;
//	private final Entity player;
//	private Entity selection = null;
//
//	private Mesh currentMesh = Resources.getMesh("link/young_link_s.obj");
//	private Material currentMaterial = new Material(Resources.getTexture("link/young_link.png", true));
//
//	private View2D view2D;
//	private JTextArea nameTextArea;
//
//	private Vec3 mousePressedAt = new Vec3();
//
//	protected boolean dragging;
//
//	public WorldDesigner(final World world) {
//		super("World Designer v1.1");
//		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//		this.world = world;
//
//		JPanel views = new JPanel();
//		views.setLayout(new BorderLayout());
//		this.getContentPane().add(views, BorderLayout.CENTER);
//
//		this.view2D = new View2D();
//		JScrollPane scrollPane = new JScrollPane(view2D,
//												 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//												 JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		scrollPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
//
//		views.add(scrollPane, BorderLayout.NORTH);
//
//		//Build OpenGL panel.
//		final WorldView view = new WorldView(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//		views.add(view, BorderLayout.SOUTH);
//
//		//Register input devices. If GLCanvas have to register to canvas.
//  		view.setFocusable(true);
//  		Keyboard.register(view);
//  		Mouse.register(view);
//
//  		//build menubar
//  		JMenuBar menuBar = new JMenuBar();
//  			JMenu fileMenu = new JMenu("File");
//				JMenuItem create = new JMenuItem("Create");
//					create.addActionListener(new ActionListener() {
//						public void actionPerformed(ActionEvent e) {
//							//create 1 unit in front of player
//							create(player.getTransform().getPosition().add(player.getTransform().getLook().x(), 0, player.getTransform().getLook().z()));						}
//					});
//				fileMenu.add(create);
//  				JMenuItem save = new JMenuItem("Save");
//  					save.addActionListener(new ActionListener() {
//						public void actionPerformed(ActionEvent e) {
//							save();
//						}
//  					});
//  				fileMenu.add(save);
//  				JMenuItem exit = new JMenuItem("Exit");
//  					exit.addActionListener(new ActionListener() {
//						public void actionPerformed(ActionEvent e) {
//							System.exit(0);
//						}
//  					});
//  				fileMenu.add(exit);
//  			menuBar.add(fileMenu);
//
//  			JMenu meshMenu = new JMenu("Mesh");
//  				for(String s : OBJS) {
//  					final String m_name = s;
//  					JMenuItem item = new JMenuItem(s);
//	  					item.addActionListener(new ActionListener() {
//	  						public void actionPerformed(ActionEvent e) {
//	  							currentMesh = Resources.getMesh(m_name);
//	  						}
//	  					});
//	  				meshMenu.add(item);
//  				}
//  			menuBar.add(meshMenu);
//
//  			JMenu texMenu = new JMenu("Texture");
//				for(String s : TEXTURES ) {
//					final String t_name = s;
//					JMenuItem item = new JMenuItem(s);
//  						item.addActionListener(new ActionListener() {
//  							public void actionPerformed(ActionEvent e) {
//  								currentMaterial = new Material(Resources.getTexture(t_name, true));
//  							}
//  						});
//  					texMenu.add(item);
//				}
//			menuBar.add(texMenu);
//
//  			nameTextArea = new JTextArea();
//  				nameTextArea.setText("Name");
//  			menuBar.add(nameTextArea);
//  		this.setJMenuBar(menuBar);
//
//  		view2D.addMouseListener(new MouseAdapter() {
//  			public void mousePressed(MouseEvent e) {
//  				mousePressedAt.set((float)e.getX()/SCALE, 0, (float)e.getY()/SCALE);
//
//  				dragging = false;
//  				switch(e.getButton()) {
//  				case MouseEvent.BUTTON1 :
//  					selectEntity();
//  					dragging = true;
//  					break;
//  				case MouseEvent.BUTTON2 :
//  					create(mousePressedAt);
//  					break;
//  				case MouseEvent.BUTTON3 :
//  					//select the entity near mouse.
//  					selectEntity();
//  					break;
//  				}
//  			}
//  		});
//
//  		view2D.addMouseMotionListener(new MouseMotionAdapter() {
//  			public void mouseDragged(MouseEvent e) {
//  				if(dragging) {
//  					selection.getTransform().setPosition((float)e.getX()/SCALE, selection.getTransform().getPosition().y(), (float)e.getY()/SCALE);
//  				} else {
//  					Vec3 releasedAt = new Vec3((float)e.getX()/SCALE, 0, (float)e.getY()/SCALE);
//  					Vec3 dir = releasedAt.sub(mousePressedAt);
//  					dir.normalize();
//
//  					selection.getTransform().lookInDirection(dir);
//  				}
//  			}
//  		});
//
//  		view2D.addMouseWheelListener(new MouseWheelListener() {
//  			public void mouseWheelMoved(MouseWheelEvent e) {
//  				selection.getTransform().translate(0, 0.01f * (e.getWheelRotation() < 0 ? 1 : -1), 0);
//  			}
//  		});
//
//		//Pack and display window.
//		this.pack();
//		this.setVisible(true);
//
//		//the game. This is a thread. You need to start it.
//		GameDemo game = new GameDemo(world);
//		game.setView(view); // give it the view so it can call it's display method appropriately.
//		game.start();
//
//		selection = player = world.getEntity("Player").get(0);
//
//		//Repaint every 10ms.
//		new Thread(new Runnable(){public void run() { while(true) {WorldDesigner.this.repaint(); try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }}}}).start();
//	}
//
//	private void selectEntity() {
//		for(Entity entity : world.getEntities()) {
//			Vec3 a = entity.getTransform().getPosition(); a.setY(0);
//			Vec3 b = mousePressedAt;
//			if(a.sub(b).length() < 0.2f) {
//				selection = entity;
//			}
//		}
//	}
//
//	private void save() {
//
//	}
//
//	private void create(Vec3 position) {
//		selection = world.createEntity(nameTextArea.getText());
//		selection.attachComponent(MeshFilter.class).setMesh(currentMesh);
//		selection.attachComponent(MeshRenderer.class).setMaterial(currentMaterial);
//		selection.getTransform().setPosition(position);
//	}
//
//	private class View2D extends JComponent {
//
//		public View2D() {
//			super();
//			this.setPreferredSize(new Dimension(DEFAULT_WIDTH*10, DEFAULT_HEIGHT*10));
//			this.setFocusable(true);
//		}
//
//		protected void paintComponent(Graphics g) {
//			super.paintComponent(g);
//			Graphics2D g2d = (Graphics2D)g;
//
//			for(Entity e : world.getEntities())
//				drawEntity(g2d, e);
//		}
//
//		private void drawEntity(Graphics2D g, Entity e) {
//			Transform t = e.getTransform();
//			Vec3 pos = t.getPosition();
//			Vec3 look = t.getLook();
//
//			int cx = (int) (pos.x()*SCALE);
//			int cy = (int) (pos.z()*SCALE);
//			int x = cx - 5;
//			int y = cy - 5;
//			int lx = (int) (cx + look.x()*(SCALE/2));
//			int ly = (int) (cy + look.z()*(SCALE/2));
//
//			g.setColor(Color.RED);
//			g.setStroke(new BasicStroke(3));
//			g.drawLine(cx, cy, lx, ly);
//			if(e == selection)
//				g.setColor(Color.GREEN);
//			else g.setColor(Color.BLUE);
//			g.fillOval(x, y, 11, 11);
//
//		}
//	}
//
//	public static void main(String[] args) {
//		new WorldDesigner(new World());
//	}

}
