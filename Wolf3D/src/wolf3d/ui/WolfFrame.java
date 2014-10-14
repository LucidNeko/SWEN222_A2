package wolf3d.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import wolf3d.Wolf3D;

/**
 *
 */

/**
 * @author brannisimo
 *
 */
public class WolfFrame extends JFrame implements MouseListener{

	//Done to match the 3D screen rather than any decent reason.
	private static final int height = 600;
	private static final int width = 800;
	private static JFrame f;

	public WolfFrame(){
		/*Basic frame setup*/
		f = new JFrame();
		f.setSize(width, height);
		f.setTitle("Wolf3D");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(true); // may replace this to be resizable but the pic may look weird

		// or just have two sizes, full screen and 800 by 600
		f.add(new WolfCanvas());
		f.setVisible(true);
		f.addMouseListener(this);
	}


	/**
	 * @author brannisimo
	 * @param x
	 * @param y
	 * @return Takes the x and y and checks if they are near a selection.
	 */
	private void hitBox(int x, int y){

		double topNewGame = ((9.0/25.0)*height);
		double bottomNewGame = ((9.0/25.0)*height);
		double topLoad = ((49.0/120.0)*height);
		double bottomLoad = ((9.0/20.0)*height);
		double topHelp = ((31.0/60.1)*height);
		double bottomHelp = ((17.0/30.0)*height);
		double topExit = ((5.0/8.0)*height);
		double bottomExit = ((2.0/3.0)*height);

		double topBound = ((37.0/120.0)*height);
		double botBound = ((1.0/2.0)*height);

		double leftBound = ((2.0/5.0)*width);
		double rightBound = ((47.0/80.0)*width);

		// TODO
		/* Make the numbers not hard coded and tweak the pic till I can
		 * have a basic hit box without hardcoded*/
		if(x<leftBound || x>rightBound || y<topBound || y>400){ // Gotta move the mouse
			System.out.println("Way outside the hit box");
		}
		else if(y>topNewGame && y< bottomNewGame){ //New game area
			//New Game method
			System.out.println("New Game Selected");
			f.dispose();
			new Wolf3D();
		}
		else if(y>topLoad && y<bottomLoad){ //Load Game area
			//Loads previous game or opens file selector to pick the game
			System.out.println("Load Game Selected");
		}
		else if(y>topHelp && y<bottomHelp){ //Help area
			//Loads help screen.
			System.out.println("Help Selected");
		}
		else if(y>topExit && y<bottomExit){ //Exit area
			System.out.println("Exit selected");
			System.exit(0);
		}
		else{ System.out.println("Almost a selection");}
	}

	@Override
	public void mouseReleased(MouseEvent e){
		hitBox(e.getX(), e.getY());
	}
	/*Will tidy these up if I get the chance but given that they're
	 * unnecessary I probably won't touch them*/
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}

	public static void main(String[] args) {
		new WolfFrame();
	}
}
