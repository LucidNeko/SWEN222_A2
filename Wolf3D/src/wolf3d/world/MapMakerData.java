/**
 *
 */
package wolf3d.world;

/**
 * @author Simon Brannigan
 *
 */
public class MapMakerData {

	public char[][] data;
	public static final int height =50; // is the arraySize
	public static final int width =50; // is the arraySize
	private static final int[] masks = {8,4,2,1};

	public MapMakerData(){
		/*Hardcoded 50 so that I can debug quicker*/
		data = new char[height][width];
		/*' ' because it wasn't comparing null and chars*/
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				data[i][j]=' ';
			}
		}
		/* To double check width and height
		 * Also to test whether the walls are being drawn in the right place is in the right place
		 * */
		data[5][10]='a';
		data[10][5]='4';
		data[5][5]='2';
		data[0][0]='1';
		data[7][7]='f';
	}

	public boolean hasNorth(int x, int y){
		String str = Character.toString(data[x][y]);
		if(str.equals(" "))return false;
		if((Integer.parseInt(str, 16)&masks[0])==8){
			return true;
		}
		return false;
	}
	public boolean hasEast(int x, int y){
		String str = Character.toString(data[x][y]);
		if(str.equals(" "))return false;
		if((Integer.parseInt(str, 16)&masks[1])==4){
			return true;
		}
		return false;
	}
	public boolean hasSouth(int x, int y){
		String str = Character.toString(data[x][y]);
		if(str.equals(" "))return false;
		if((Integer.parseInt(str, 16)&masks[2])==2){
			return true;
		}
		return false;
	}
	public boolean hasWest(int x, int y){
		String str = Character.toString(data[x][y]);
		if(str.equals(" "))return false;
		if((Integer.parseInt(str, 16)&masks[3])==1){
			return true;
		}
		return false;
	}

	public char getData(int x, int y){
		return data[x][y];
	}

	public void setData(int x, int y, char c){
		data[x][y]=c;
	}

}
