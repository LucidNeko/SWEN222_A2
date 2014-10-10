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
				else if(mmd.data[i][j]=='a'){
					g.setColor(Color.blue);
					g.fillRect(i*cellDim, j*cellDim, cellDim, cellDim);
				}
			}
		}
		for(int i=0; i<50; i++){
			for(int j=0; j<50; j++){
				if(mmd.data[i][j]==' '){
					g.setColor(Color.red);
					g.fillRect(i*cellDim, j*cellDim, cellDim, cellDim);
				}
				else if(mmd.data[i][j]=='a'){
					g.setColor(Color.blue);
					g.fillRect(i*cellDim, j*cellDim, cellDim, cellDim);
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
