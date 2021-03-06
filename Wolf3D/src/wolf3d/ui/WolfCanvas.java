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
	private static BufferedImage help;
	private static Boolean h = false;

	/**
	 *
	 * @param h whether or not the help screen has been selected
	 */
	public WolfCanvas(Boolean h){
		this.h=h;
		try {
			background = Resources.getImage("BackgroundText.png");
			help = Resources.getImage("help.png");
		} catch (IOException e){
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
		if(h==true){
			g.drawImage(help, 0, 0, width, height, this);
		}
	}
}