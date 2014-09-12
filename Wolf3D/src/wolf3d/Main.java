package wolf3d;


import javax.swing.SwingUtilities;

import wolf3d.window.AppWindow;

/**
 * The entry point into the system.
 * @author Hamish Rae-Hodgson
 *
 */
public class Main {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AppWindow();
			}
		});
	}

}
