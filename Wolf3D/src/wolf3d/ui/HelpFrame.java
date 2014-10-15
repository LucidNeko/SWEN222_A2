/**
 *
 */
package wolf3d.ui;

import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import wolf3d.Wolf3D;

import com.jogamp.newt.event.WindowListener;

/**
 * @author Simon Brannigan
 * Passes another frame that the user can move to be able to play the game
 * and use the help as some of the controls aren't working or not linked.
 */
public class HelpFrame extends JFrame{

	private JFrame f;
	private int width = 800;
	private int height = 600;

	public HelpFrame(){
		/*Basic frame setup*/
		f = new JFrame();
		f.setSize(width, height);
		f.setTitle("Wolf3D");
		f.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				confirmExit();
			}
		});

		f.setResizable(false);
		f.add(new HelpCanvas());
		f.setVisible(true);
	}

	private void confirmExit(){
		if(JOptionPane.showConfirmDialog(this,
				"Are you sure you want to Exit?",
				"Are you sure you want to Exit?",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			f.dispose();
		}
	}
}
