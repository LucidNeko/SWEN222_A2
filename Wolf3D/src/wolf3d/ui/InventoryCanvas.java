package wolf3d.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import engine.core.Entity;
import engine.core.World;
import engine.util.Resources;
import wolf3d.components.*;

/**
 * @author Simon Brannigan
 *
 */
public class InventoryCanvas extends JPanel{


	/**
	 *
	 */
	private static final long serialVersionUID = -3342559989942302339L;
	private static int maxInventory =5;
	private BufferedImage a;
	private BufferedImage b;
	private boolean notDone =true;

	InventoryCanvas(){
		this.setPreferredSize(new Dimension(1000, 200));
		this.repaint();
	}

	private void loadImageList(){
		try {
			//Load the images from the disk to fields or to an arrayList of images
			Image a = Resources.getImage("objImages/item.png");
			Image b = Resources.getImage("objImages/noItem.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		/*Makes sure this is visible*/
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1000, 200);
		if(notDone){
			loadImageList();
			//Draw no item images
			notDone =false;

		}
	}
}