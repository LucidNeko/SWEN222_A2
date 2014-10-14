package engine.scratch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.locks.LockSupport;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import engine.common.Mathf;
import engine.input.Keyboard;

public class MapBuilderSingle extends JFrame implements ActionListener {
	private static final long serialVersionUID = -9166187539200705700L;

	private static final Logger log = LogManager.getLogger();

	private static final int CELL_SIZE = 20;

	private static final int NONE  = 0x0;
	private static final int WEST  = 0x1;
	private static final int SOUTH = 0x2;
	private static final int EAST  = 0x4;
	private static final int NORTH = 0x8;

	private int mapWidth = 50;
	private int mapHeight = 30;

	private CellInfo[][] cells = new CellInfo[mapHeight][mapWidth];

	private int selectionX = 0;
	private int selectionY = 0;

	private JComponent mapWindow;

	public MapBuilderSingle() {
		super("Map Builder v1.0");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel buttons = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(0, 0, 127, 64));
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		};
		addButton("New", buttons);
		addButton("Save", buttons);
		addButton("Load", buttons);
		addButton("Clear", buttons);
		this.add(buttons, BorderLayout.NORTH);

		mapWindow = new JComponent() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(0, 0, 32, 16));
				g.fillRect(0, 0, mapWidth*CELL_SIZE, mapHeight*CELL_SIZE);
				g.setColor(new Color(255, 0, 0, 64));
				g.fillRect(selectionX*CELL_SIZE, selectionY*CELL_SIZE, CELL_SIZE, CELL_SIZE);
				g.setColor(Color.BLACK);
				for(int row = 0; row < cells.length; row++) {
					for(int col = 0; col < cells[0].length; col++) {
						CellInfo cell = cells[row][col];
						if((cell.walls & NORTH) != 0) { //has north
							g.drawLine(col*CELL_SIZE, row*CELL_SIZE,
									   col*CELL_SIZE + CELL_SIZE, row*CELL_SIZE);
						}
						if((cell.walls & EAST) != 0) { //has east
							g.drawLine(col*CELL_SIZE + CELL_SIZE, row*CELL_SIZE,
									   col*CELL_SIZE + CELL_SIZE, row*CELL_SIZE + CELL_SIZE);
						}
						if((cell.walls & SOUTH) != 0) { //has south
							g.drawLine(col*CELL_SIZE, row*CELL_SIZE + CELL_SIZE,
									   col*CELL_SIZE + CELL_SIZE, row*CELL_SIZE + CELL_SIZE);
						}
						if((cell.walls & WEST) != 0) { //has west
							g.drawLine(col*CELL_SIZE, row*CELL_SIZE,
									   col*CELL_SIZE, row*CELL_SIZE + CELL_SIZE);
						}
					}
				}
			}
		};
		mapWindow.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				selectionX = (int) Mathf.clamp(x/CELL_SIZE, 0, mapWidth-1);
				selectionY = (int) Mathf.clamp(y/CELL_SIZE, 0, mapHeight-1);
				MapBuilderSingle.this.repaint();
			}
		});
		mapWindow.setFocusable(true);
		Keyboard.register(mapWindow);

		this.getContentPane().add(mapWindow, BorderLayout.CENTER);

		actionNew();

		this.pack();
		this.setVisible(true);

		//logic
		new Thread(new Runnable() {
			public void run() {
				while(true) {
					int dx = 0, dy = 0;
					if(Keyboard.isKeyDownOnce(KeyEvent.VK_LEFT)) dx -= 1;
					if(Keyboard.isKeyDownOnce(KeyEvent.VK_RIGHT)) dx += 1;
					if(Keyboard.isKeyDownOnce(KeyEvent.VK_UP)) dy -= 1;
					if(Keyboard.isKeyDownOnce(KeyEvent.VK_DOWN)) dy += 1;

					if(dx != 0 || dy != 0) {
						selectionX = (int) Mathf.clamp(selectionX+dx, 0, mapWidth-1);
						selectionY = (int) Mathf.clamp(selectionY+dy, 0, mapHeight-1);
					}

					int walls = 0;
					if(Keyboard.isKeyDownOnce(KeyEvent.VK_W)) walls |= NORTH;
					if(Keyboard.isKeyDownOnce(KeyEvent.VK_A)) walls |= WEST;
					if(Keyboard.isKeyDownOnce(KeyEvent.VK_S)) walls |= SOUTH;
					if(Keyboard.isKeyDownOnce(KeyEvent.VK_D)) walls |= EAST;


					if(walls != NONE) {
						cells[selectionY][selectionX].walls ^= walls; //this
//						if(selectionX > 0)
//							cells[selectionY][selectionX-1].walls ^= opposite(WEST & walls); //left
//						if(selectionX < mapWidth-1)
//							cells[selectionY][selectionX+1].walls ^= opposite(EAST & walls); //right
//						if(selectionY > 0)
//							cells[selectionY-1][selectionX].walls ^= opposite(NORTH & walls); //up
//						if(selectionY < mapHeight-1)
//							cells[selectionY+1][selectionX].walls ^= opposite(SOUTH & walls); //down
					}

					MapBuilderSingle.this.repaint();

					//sleep 10ms
					LockSupport.parkNanos(10000000);
				}
			}
		}).start();
	}

	private void addButton(String name, JPanel panel) {
		JButton button = new JButton(name);
		button.setFocusable(false);
		button.addActionListener(this);
		panel.add(button);
	}

	private int opposite(int flag) {
		if(flag == NONE) return NONE;
		if(flag == NORTH) return SOUTH;
		if(flag == SOUTH) return NORTH;
		if(flag == EAST) return WEST;
		if(flag == WEST) return EAST;
		throw new IllegalArgumentException(Integer.toHexString(flag));
	}

	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "New" : actionNew(); break;
		case "Save" : actionSave(); break;
		case "Load" : actionLoad(); break;
		case "Clear" : actionClear(); break;
		}
	}

	private void actionNew() {
		mapWidth = Integer.parseInt(JOptionPane.showInputDialog(MapBuilderSingle.this, "Enter the width:"));
		mapHeight = Integer.parseInt(JOptionPane.showInputDialog(MapBuilderSingle.this, "Enter the height:"));
		cells = new CellInfo[mapHeight][mapWidth];
		for(int row = 0; row < cells.length; row++)
			for(int col = 0; col < cells[0].length; col++)
				cells[row][col] = new CellInfo();
		resize();
	}

	private void actionSave() {
		StringBuffer s = new StringBuffer();
		s.append(mapWidth).append("\n");
		s.append(mapHeight).append("\n");
		for(int row = 0; row < cells.length; row++) {
			for(int col = 0; col < cells[0].length; col++) {
				s.append(Integer.toHexString(cells[row][col].walls));
			}
			s.append(" ").append(0); //to satisfy format.
			s.append("\n");
		}
		s.append(0); //to satisfy format.
		System.out.println(s.toString().toUpperCase());
	}

	private void actionLoad() {
		JFileChooser jfc = new JFileChooser(new File(MapBuilderSingle.class.getResource("/wolf3d/assets").getPath()));
		jfc.showDialog(this, "Choose map file");
		if(jfc.getSelectedFile() != null) {
			try {
				Scanner in = new Scanner(jfc.getSelectedFile());
				mapWidth = Integer.parseInt(in.nextLine());
				mapHeight = Integer.parseInt(in.nextLine());
				cells = new CellInfo[mapHeight][mapWidth];
				for(int row = 0; row < cells.length; row++)
					for(int col = 0; col < cells[0].length; col++)
						cells[row][col] = new CellInfo();
				for(int row = 0; row < mapHeight; row++) {
					String[] line = in.nextLine().split("");
					if(line[0].equals("")) line = Arrays.copyOfRange(line, 1, line.length); //fix for java7.
					for(int col = 0; col < mapWidth; col++) {
						cells[row][col].walls = Integer.decode("0x"+line[col]);
					}
				}
				resize();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void actionClear() {
		for(int row = 0; row < cells.length; row++)
			for(int col = 0; col < cells[0].length; col++)
				cells[row][col].clear();
		MapBuilderSingle.this.repaint();
	}

	private void resize() {
		selectionX = selectionY = 0;
		mapWindow.setPreferredSize(new Dimension(mapWidth*CELL_SIZE, mapHeight*CELL_SIZE));
		SwingUtilities.updateComponentTreeUI(this);
		MapBuilderSingle.this.pack();
		MapBuilderSingle.this.repaint();
	}

	private class CellInfo {
		public int walls = NONE;

		public void clear() {
			walls = 0;
		}
	}

	public static void main(String[] args) {
		new MapBuilderSingle();
	}

}
