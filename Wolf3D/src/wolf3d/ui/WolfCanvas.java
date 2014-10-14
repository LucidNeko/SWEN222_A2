package wolf3d.ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import engine.util.Resources;

/**
 * @author Simon Brannigan
 *BackgroundText pic taken straight from http://ezwallpapers.com/wallpapersdownload2014/flames-wallpaper-wolfenstein-background-wallpapers-ps3-black.jpg
 *I added the text but otherwise that's it.
 */

public class WolfCanvas extends JPanel{

	private static final int height = 600;
	private static final int width = 800;
	private static BufferedImage background;

	public WolfCanvas(){
		try {
			background = Resources.getImage("BackgroundText.png");

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
		g.drawImage(background, 0, 0, width, height, this);
	}
}