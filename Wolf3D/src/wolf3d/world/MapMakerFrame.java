/**
 *
 */
package wolf3d.world;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Simon Brannigan
 * This is a beast of a class and if done differently
 * I would take some of the logic out.
 */
public class MapMakerFrame implements MouseListener {

	private MapMakerCanvas mmc;
	private JFrame f;

	private final int cellDim = mmc.getCellDim(); // Height and width of a cell, also offset
	private final int width = mmc.getCellDim()*50+5; // the 5 accounts for the sideways slant
	private final int height = mmc.getCellDim()*50+27; //25 is the bar accounts for the bar at the top

	private String walls;
	private static boolean[] dirUsed= {false, false, false, false};//whether there is already an entry for W, D, S or A in the string
	private static final int[] masks = {8,4,2,1};

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

	private int wallNibble(String s){
		int wCount = 0;
		if(s.equals(""))return wCount;
		s=s.toUpperCase();
		char[]curSegment = s.toCharArray();
		for(int i=0; i<curSegment.length; i++){
			if(curSegment[i]=='W'){
				if(dirUsed[0]==false){
					wCount+=masks[0];
					dirUsed[0]=true;
				}
			}
			else if(curSegment[i]=='D'){
				if(dirUsed[1]==false){
					wCount+=masks[1];
					dirUsed[1]=true;
				}
			}
			else if(curSegment[i]=='S'){
				if(dirUsed[2]==false){
					wCount+=masks[2];
					dirUsed[2]=true;
				}
			}
			else if(curSegment[i]=='A'){
				if(dirUsed[3]==false){
					wCount+=masks[3];
					dirUsed[3]=true;
				}
			}
		}
		for(int i=0;i<dirUsed.length; i++){
			dirUsed[i]=false;
		}
		return wCount;
	}

	private char putWalls(String str){
		int x = wallNibble(str);
		String a = Integer.toHexString(x);
		char[]c = a.toCharArray();
		return c[0];
	}

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		/* The cell that you clicked*/
		int cellX=(int)(e.getX()/cellDim);
		int cellY=(int)(((e.getY()-((int)cellDim/2))/cellDim))-1;//Y location does not scale properly, this puts it roughly in the right area
		if(cellY<0){
			cellY=0;
		}
		String walls = JOptionPane.showInputDialog("Enter the walls", null);
		char c= putWalls(walls);
		mmc.mmd.setData(cellX, cellY, c);
		System.out.println(mmc.mmd.getData(cellX, cellY) + "\nat cell: "+cellX+ ". y:"+cellY);
		mmc.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
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
