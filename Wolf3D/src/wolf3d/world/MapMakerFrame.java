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

	private static final int width = 1000;
	private static final int height = 1000;
	private final int cellDim = mmc.getCellDim(); // Height and width of a cell, also offset

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
		f.setResizable(false);
		f.setVisible(true);

	}

	public static int getWidth() {
		return width;
	}
	public static int getHeight() {
		return height;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		/* The cell that you clicked*/
		int cellX=(int)e.getX()/cellDim;
		int cellY=(int)e.getY()/cellDim;
		System.out.println(cellX);
		System.out.println(cellY);
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
