/**
 *
 */
package wolf3d.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import engine.util.Resources;

/**
 * @author Simon Brannigan
 *
 */
public class HelpCanvas extends JPanel{

	private static final int height = 600;
	private static final int width = 800;
	private static BufferedImage help;

	public HelpCanvas(){
		try {
			help = Resources.getImage("help.png");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.repaint();
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(width, height);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(help, 0, 0, width, height, this);
	}
}