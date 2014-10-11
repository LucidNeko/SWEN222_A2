package engine.scratch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.locks.LockSupport;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import engine.common.Mathf;
import engine.input.Keyboard;

/**
 * 
 * @author Hamish
 *
 */
public class MapBuilder extends JFrame {
	private static final long serialVersionUID = -9166187539200705700L;
	
	private static final int NONE  = 0x0;
	private static final int WEST  = 0x1;
	private static final int SOUTH = 0x2;
	private static final int EAST  = 0x4;
	private static final int NORTH = 0x8;
	
	private static final int CELL_SIZE = 20;
	private static int MAP_WIDTH = 50;
	private static int MAP_HEIGHT = 30;
	
	private int[][] cells = new int[MAP_HEIGHT][MAP_WIDTH];
	
	private int selectionX = 0;
	private int selectionY = 0;
	
	public MapBuilder() {
		super("Map Builder v1.0");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Keyboard.register(this);
		
		JPanel buttonPanel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(0, 0, 127, 64));
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		};
		JButton printButton = new JButton("Print");
		printButton.setFocusable(false);
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringBuffer s = new StringBuffer();
				s.append(MAP_WIDTH).append("\n");
				s.append(MAP_HEIGHT).append("\n");
				for(int row = 0; row < cells.length; row++) {
					for(int col = 0; col < cells[0].length; col++) {
						s.append(Integer.toHexString(cells[row][col]));
					}
					s.append(" ").append(0); //to satisfy format.
					s.append("\n");
				}
				s.append(0); //to satisfy format.
				System.out.println(s.toString().toUpperCase());
			}
		});
		buttonPanel.add(printButton);
		
		JButton clearButton = new JButton("Clear");
		clearButton.setFocusable(false);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int row = 0; row < cells.length; row++)
					for(int col = 0; col < cells[0].length; col++)
						cells[row][col] = 0;
				MapBuilder.this.repaint();
			}
		});
		buttonPanel.add(clearButton);
		
		JButton loadButton = new JButton("Load");
		loadButton.setFocusable(false);
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = null;
				try {
					jfc = new JFileChooser(new File(MapBuilder.class.getResource("/wolf3d/assets/").toURI()));
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				jfc.showDialog(MapBuilder.this, "Choose");
				File f = jfc.getSelectedFile();
				if(f != null) {
					try {
						Scanner scan = new Scanner(f);
						MAP_WIDTH = Integer.parseInt(scan.nextLine());
						MAP_HEIGHT = Integer.parseInt(scan.nextLine());
						cells = new int[MAP_HEIGHT][MAP_WIDTH];
						for(int row = 0; row < MAP_HEIGHT; row++) {
							String[] line = scan.nextLine().split("");
							if(line[0].equals("")) line = Arrays.copyOfRange(line, 1, line.length); //fix for java7.
							for(int col = 0; col < MAP_WIDTH; col++) {
								cells[row][col] = Integer.decode("0x"+line[col]);
							}
						}
						MapBuilder.this.repaint();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		buttonPanel.add(loadButton);
		
		this.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		
		this.getContentPane().add(new JComponent() {
			{
				this.setPreferredSize(new Dimension(MAP_WIDTH*CELL_SIZE, MAP_HEIGHT*CELL_SIZE));
			}
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255, 0, 0, 64));
				g.fillRect(selectionX*CELL_SIZE, selectionY*CELL_SIZE, CELL_SIZE, CELL_SIZE);
				g.setColor(Color.BLACK);
				for(int row = 0; row < cells.length; row++) {
					for(int col = 0; col < cells[0].length; col++) {
						int cell = cells[row][col];
						if((cell & NORTH) != 0) { //has north
							g.drawLine(col*CELL_SIZE, row*CELL_SIZE, 
									   col*CELL_SIZE + CELL_SIZE, row*CELL_SIZE);
						}
						if((cell & EAST) != 0) { //has east
							g.drawLine(col*CELL_SIZE + CELL_SIZE, row*CELL_SIZE, 
									   col*CELL_SIZE + CELL_SIZE, row*CELL_SIZE + CELL_SIZE);
						}
						if((cell & SOUTH) != 0) { //has south
							g.drawLine(col*CELL_SIZE, row*CELL_SIZE + CELL_SIZE, 
									   col*CELL_SIZE + CELL_SIZE, row*CELL_SIZE + CELL_SIZE);
						}
						if((cell & WEST) != 0) { //has west
							g.drawLine(col*CELL_SIZE, row*CELL_SIZE, 
									   col*CELL_SIZE, row*CELL_SIZE + CELL_SIZE);
						}
					}
				}
			}
		}, BorderLayout.CENTER);
		
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
						selectionX = (int) Mathf.clamp(selectionX+dx, 0, MAP_WIDTH-1);
						selectionY = (int) Mathf.clamp(selectionY+dy, 0, MAP_HEIGHT-1);
					}
					
					int walls = 0;
					if(Keyboard.isKeyDownOnce(KeyEvent.VK_W)) walls |= NORTH;
					if(Keyboard.isKeyDownOnce(KeyEvent.VK_A)) walls |= WEST;
					if(Keyboard.isKeyDownOnce(KeyEvent.VK_S)) walls |= SOUTH;
					if(Keyboard.isKeyDownOnce(KeyEvent.VK_D)) walls |= EAST;
					
					//TODO:
					if(walls != NONE) {
						cells[selectionY][selectionX] ^= walls; //this
						if(selectionX > 0)
							cells[selectionY][selectionX-1] ^= opposite(WEST & walls); //left
						if(selectionX < MAP_WIDTH-1)
							cells[selectionY][selectionX+1] ^= opposite(EAST & walls); //right
						if(selectionY > 0)
							cells[selectionY-1][selectionX] ^= opposite(NORTH & walls); //up
						if(selectionY < MAP_HEIGHT-1)
							cells[selectionY+1][selectionX] ^= opposite(SOUTH & walls); //down
					}
					
					//this cell
//					if()
//					
//					if(Keyboard.isKeyDownOnce(KeyEvent.VK_W)) {
//						if((cell & NORTH) != 0) 
//							cells[selectionY][selectionX] &= ~NORTH; //remove
//						else cells[selectionY][selectionX] |= NORTH; //add
//					}
//					if(Keyboard.isKeyDownOnce(KeyEvent.VK_A)) {
//						if((cell & WEST) != 0) 
//							cells[selectionY][selectionX] &= ~WEST;
//						else cells[selectionY][selectionX] |= WEST;
//					}
//					if(Keyboard.isKeyDownOnce(KeyEvent.VK_S)) {
//						if((cell & SOUTH) != 0) 
//							cells[selectionY][selectionX] &= ~SOUTH;
//						else cells[selectionY][selectionX] |= SOUTH;
//					}
//					if(Keyboard.isKeyDownOnce(KeyEvent.VK_D)) {
//						if((cell & EAST) != 0) 
//							cells[selectionY][selectionX] &= ~EAST;
//						else cells[selectionY][selectionX] |= EAST;
//					}
					
					MapBuilder.this.repaint();
					
					//sleep 10ms
					LockSupport.parkNanos(10000000);
				}
			}
		}).start();
	}
	
	private int opposite(int flag) {
		if(flag == NONE) return NONE;
		if(flag == NORTH) return SOUTH;
		if(flag == SOUTH) return NORTH;
		if(flag == EAST) return WEST;
		if(flag == WEST) return EAST;
		throw new IllegalArgumentException(Integer.toHexString(flag));
	}
	
	public static void main(String[] args) {
		new MapBuilder();
	}

}
