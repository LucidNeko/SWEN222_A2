/**
 *
 */
package wolf3d.world;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author Simon Brannigan
 *
 */
public class MapMakerCanvas extends JPanel{

	public static MapMakerData mmd = new MapMakerData();
	private static final int cellDim = 15;
	private static final int asdf =50; // is the arraySize

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		//TODO change hardcoded values
		g.fillRect(0, 0, 1000, 1000);
		//TODO change hardcoded values
		for(int i=0; i<asdf; i++){
			for(int j=0; j<asdf; j++){
				if(mmd.data[i][j]==' '){
					g.setColor(Color.red);
					g.fillRect(i*cellDim, j*cellDim, cellDim, cellDim);
				}
			}
		}
		for(int i=0; i<50; i++){
			int lazyX=i*cellDim;
			for(int j=0; j<50; j++){
				int lazyY=j*cellDim;
				if(mmd.data[i][j]==' '){
					g.setColor(Color.red);
					g.fillRect(lazyX, lazyY, cellDim, cellDim);
				}
				if(mmd.hasNorth(i, j)){
					g.setColor(Color.green);
					g.drawLine(lazyX, lazyY+1, lazyX+cellDim, lazyY+1);
					g.drawLine(lazyX, lazyY+2, lazyX+cellDim, lazyY+2);
				}
				if(mmd.hasEast(i, j)){
					g.setColor(Color.green);
					g.drawLine((lazyX+cellDim)-1, lazyY, (lazyX+cellDim)-1, lazyY+cellDim);
					g.drawLine((lazyX+cellDim)-2, lazyY, (lazyX+cellDim)-2, lazyY+cellDim);
				}
				if(mmd.hasSouth(i, j)){
					g.setColor(Color.green);
					g.drawLine(lazyX, (lazyY+cellDim)-1, lazyX+cellDim, (lazyY+cellDim)-1);
					g.drawLine(lazyX, (lazyY+cellDim)-2, lazyX+cellDim, (lazyY+cellDim)-2);
				}
				if(mmd.hasWest(i, j)){
					g.setColor(Color.green);
					g.drawLine(lazyX+1, lazyY, lazyX+1, lazyY+cellDim);
					g.drawLine(lazyX+2, lazyY, lazyX+2, lazyY+cellDim);
				}

			}
		}
		g.setColor(Color.white);
		for(int i=0; i<=50; i++){
			for(int j=0; j<=50; j++){
				g.drawLine(i*cellDim, j*cellDim, i*cellDim, asdf*cellDim);
				g.drawLine(i*cellDim, j*cellDim, asdf*cellDim, j*cellDim);
			}
		}
	}

	/**
	 * @return the cellDim
	 */
	public static int getCellDim() {
		return cellDim;
	}
}
