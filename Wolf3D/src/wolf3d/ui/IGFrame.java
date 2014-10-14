/**
 *
 */
package wolf3d.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author brannisimo
 *
 */
public class IGFrame extends JPanel implements MouseListener{
	private static final int height = 300;
	private static final int width = 300;
	public static int count=0; // This should be taken out in the final version of the game.

	public IGFrame(){
		/*Basic frame setup*/
		JFrame f = new JFrame();
		f.setSize(width, height);
		f.setTitle("In Game Menu");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false); // may replace this to be resizable but the pic may look weird
		// or just have two sizes, full screen and 800 by 600
		f.add(new IGCanvas(width, height));
		//f.requestFocusInWindow(); //needs to grab the focus away from the gamescreen
		f.setVisible(true);
		f.addMouseListener(this);
	}


	/**
	 * @author brannisimo
	 * Takes the x and y and checks if they are near a selection.
	 */
	private void hitBox(int x, int y){
		/* Make the numbers not hard coded and tweak the pic till I can
		 * have a basic hit box algorithm*/
//		if(x>470 || x<320 || y<185 || y>400){ // Gotta move the mouse
//			System.out.println("Way outside the hit box");
//		}
//		if(y<216 && y>189){ //New game area
//			//New Game method
//			System.out.println("New Game Selected");
//			new Wolf3D();
//		}
//		else if(y<270 && y>244){ //Load Game area
//			//Loads previous game or opens file selector to pick the game
//			System.out.println("Load Game Selected");
//		}
//		else if(y<341 && y>312){ //Help area
//			//Loads help screen.
//			System.out.println("Help Selected");
//		}
//		else if(y<399 && y>373){ //Exit area
//			System.out.println("Exit selected");
//			System.exit(0);
//		}
//		else{ System.out.println("Almost a selection");}
	}

	@Override
	public void mouseReleased(MouseEvent e){
		if(count==10){System.exit(0);}
		System.out.println("x: "+e.getX()+ " y: "+ e.getY());
		count++;
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
}
