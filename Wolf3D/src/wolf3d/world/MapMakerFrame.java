/**
 *
 */
package wolf3d.world;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

/**
 * @author Simon Brannigan
 *
 */
public class MapMakerFrame implements MouseListener {

	private MapMakerCanvas mmc;
	private JFrame f;

	private final int cellDim = mmc.getCellDim(); // Height and width of a cell, also offset
	private final int width = mmc.getCellDim()*50+5; // the 5 accounts for the sideways slant
	private final int height = mmc.getCellDim()*50+27; //25 is the bar accounts for the bar at the top

	public MapMakerFrame(){
		/*Basic Frame Setup*/
		f=new JFrame();
		f.setTitle("Interactive Map maker");
		f.setSize(width, height);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*Sets up the canvas*/
		mmc = new MapMakerCanvas();
		f.add(mmc);
		f.addMouseListener(this);
		f.setResizable(true);
		f.setVisible(true);
	}

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		/* The cell that you clicked*/
		int cellX=(int)(e.getX()/cellDim);
		int cellY=(int)(((e.getY()-((int)cellDim/2))/cellDim))-1;//Y location does not scale properly, this puts it roughly in the right area
		if(cellY<0){
			cellY=0;
		}
		System.out.println(e.getX());
		System.out.println(e.getY());
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		/* The cell that you clicked*/
//		int cellX=(int)e.getX()/cellDim;
//		int cellY=(int)e.getY()/cellDim;
//		System.out.println(cellX);
//		System.out.println(cellY);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
