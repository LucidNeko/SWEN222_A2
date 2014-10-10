/**
 *
 */
package wolf3d.world;

import javax.swing.JFrame;

/**
 * @author Simon Brannigan
 *
 */
public class MapMakerFrame {

	private MapMakerCanvas mmc;
	private JFrame f;

	public MapMakerFrame(){
		f=new JFrame();
		f.setTitle("Interactive Map maker");
		mmc = new MapMakerCanvas();
	}
}
