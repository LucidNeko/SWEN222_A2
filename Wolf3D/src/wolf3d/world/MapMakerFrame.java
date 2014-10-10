/**
 *
 */
package wolf3d.world;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.JButton;
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
	private JButton jb;

	private final int cellDim = mmc.getCellDim(); // Height and width of a cell, also offset
	private final int width = mmc.getCellDim()*50+5; // the 5 accounts for the sideways slant
	private final int height = mmc.getCellDim()*50+27; //27 is the bar accounts for the bar at the top

	private String walls;
	private static boolean[] dirUsed= {false, false, false, false};//whether there is already an entry for W, D, S or A in the string
	private static final int[] masks = {8,4,2,1};

	public MapMakerFrame(){
		/*Basic Frame Setup*/
		f=new JFrame();
		f.setTitle("Interactive Map maker");
		f.setSize(width, height);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.addMouseListener(this);
		f.setResizable(true);

		/*Sets up the canvas*/
		mmc = new MapMakerCanvas();
		f.add(mmc);

		/*Adds button*/
		jb = new JButton();
		f.add(jb, BorderLayout.NORTH);
		jb.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	printToFile();
            }
        });
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

	public void printToFile(){
    	try {
			//Map.txt will always be in the workspace folder
			PrintStream ps = new PrintStream("Map.txt");
			//TODO change from 50
			char[]cArray=new char[50];
			for(int i=0; i<50; i++){
				for(int j=0; j<50; j++){
					cArray[j]=mmc.mmd.getData(i,j);
					if(cArray[j]== ' '){
						cArray[j]='0';
					}
					//TODO change from 50
					if(j==50-1){
						String str = new String(cArray);
						ps.println(str);
					}
				}
			}
			ps.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
