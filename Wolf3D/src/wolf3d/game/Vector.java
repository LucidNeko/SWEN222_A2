package wolf3d.game;
/**
 * Class to hold the players position and rotation
 * and responsible for updating the players movements
 * 
 * @author Sameer Magan
 *
 */
public class Vector {
	private int x, y, z; //position in 3d space
	private float rotation; //rotation only in y axis
	private int stepAmt = 5;
	
	public Vector(int x, int y, int z, float rotation){
		this.x = x;
		this.y = y;
		this.z = z;
		this.rotation = rotation;
	}
	
	public void moveFoward(){
		
	}
}
