package wolf3d.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import engine.core.World;
import engine.util.ServiceLocator;
import wolf3d.Wolf3D;
import wolf3d.database.DataManagement;

/**
 * @author brannisimo
 * WolfFrame is the startup screen
 */
public class WolfFrame extends JFrame implements MouseListener{
	private static final long serialVersionUID = 2267807810965738503L;
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
		f.setResizable(false);
		f.add(new WolfCanvas(false));
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

		/*Bounds*/
		double topNewGame = ((3.0/10.0)*height);
		double bottomNewGame = ((9.0/25.0)*height);
		double topLoad = ((49.0/120.0)*height);
		double bottomLoad = ((9.0/20.0)*height);
		double topHelp = ((31.0/60.1)*height);
		double bottomHelp = ((17.0/30.0)*height);
		double topExit = ((5.0/8.0)*height);
		double bottomExit = ((2.0/3.0)*height);

		double topBound = ((1.0/4.0)*height);
		double botBound = ((3.0/4.0)*height);

		double leftBound = ((2.0/5.0)*width);
		double rightBound = ((47.0/80.0)*width);

		/*Made the bounds scalable for later use*/
		if(x<leftBound || x>rightBound || y<topBound || y>botBound){ // Gotta move the mouse
			System.out.println("Way outside the hit box");
		}
		else if(y>topNewGame && y< bottomNewGame){ //New game area
			//New Game method
			f.dispose();
			new Wolf3D();
		}
		else if(y>topLoad && y<bottomLoad){ //Load Game area
			//Loads last game
			//Wolf3D creates a new client which....
			System.out.println("Load Game Selected");
			World w = ServiceLocator.getService(World.class);

			DataManagement.loadWorld("defaultWorld.txt", w.getEntity("Player").get(0).getID());
		}
		else if(y>topHelp && y<bottomHelp){ //Help area
			//Loads help screen.
			//Drop the canvas onto the screen
			//Another frame so can have help on one side and game on the other.
			//new HelpFrame();
			f.add(new WolfCanvas(true));
			f.setVisible(true);
		}
		else if(y>topExit && y<bottomExit){ //Exit area
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
