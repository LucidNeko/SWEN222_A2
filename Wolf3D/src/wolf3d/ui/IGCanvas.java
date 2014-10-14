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
 * @author brannisimo
 *
 */
public class IGCanvas extends JPanel{
	private static int height;
	private static int width;
	private static BufferedImage options;

	public IGCanvas(int width, int height){
		super();
		this.setPreferredSize(new Dimension(width, height));

		this.height=height;
		this.width=width;
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
