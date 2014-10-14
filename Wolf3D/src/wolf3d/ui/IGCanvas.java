/**
 *
 */
package wolf3d.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import engine.util.Resources;

/**
 * @author brannisimo
 *
 */
public class IGCanvas extends JPanel{
	private static int height;
	private static int width;
	private static BufferedImage options;

	private static double rightBound;
	private static double leftBound;

	private static double resumeTop;
	private static double resumeBot;
	private static double mainTop;
	private static double mainBot;
	private static double helpTop;
	private static double helpBot;
	private static double saveTop;
	private static double saveBot;
	private static double loadTop;
	private static double loadBot;
	private static double exitTop;
	private static double exitBot;


	public IGCanvas(int width, int height){
		super();
		this.height=height;
		this.width=width;

		rightBound = (7.0/8.0)*width;
		leftBound = (3.0/20.0)*width;

		resumeTop = (1.0/10.0)*height;
		resumeBot = (11.0/60.0)*height;
		mainTop = (1.0/4.0)*height;
		mainBot = (3.0/10.0)*height;
		helpTop = (11.0/30.0)*height;
		helpBot = (9.0/20.0)*height;
		saveTop = (31.0/60.0)*height;
		saveBot = (7.0/12.0)*height;
		loadTop = (2.0/3.0)*height;
		loadBot = (11.0/15.0)*height;
		exitTop = (49.0/60.0)*height;
		exitBot = (53.0/60.0)*height;
		this.setPreferredSize(new Dimension(width, height));
		this.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("X: "+ e.getX() + " Y: "+ e.getY());
				if(e.getX()<rightBound && e.getX()>leftBound){
					System.out.println("Made it into the hitbox area");
					if((double)e.getY() < resumeBot && (double)e.getY() > resumeTop){
						System.out.println("Resume");
						//Resume game
					}
					if(e.getY() < mainBot && e.getY() > mainTop){
						//Go back to main menu
						System.out.println("Go to main menu");
					}
					if(e.getY() < helpBot && e.getY() > helpTop){
						//Help Screen
						System.out.println("Help");
					}
					if(e.getY() < saveBot && e.getY() > saveTop){
						//Save Game
						System.out.println("Save");
					}
					if(e.getY() < loadBot && e.getY() > loadTop){
						//Load Game
						System.out.println("Load");
					}
					if(e.getY() < exitBot && e.getY() > exitTop){
						//System.exit(0);
						System.out.println("Exit");
					}
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		try {
			options = Resources.getImage("InGameMenu.png");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(options, 0, 0, width, height, this);
	}
}
