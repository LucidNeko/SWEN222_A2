/**
 *
 */
package wolf3d.ui;

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
	private static final int height = 600;
	private static final int width = 800;

	public IGCanvas(){
		this.repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			BufferedImage background = Resources.getImage("InGameMenu.png");
			g.drawImage(background, 0, 0, width, height, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
