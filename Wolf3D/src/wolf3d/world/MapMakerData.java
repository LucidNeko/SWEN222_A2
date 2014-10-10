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
	private static final int asdf =50; // is the arraySize


	public MapMakerData(){
		/*Hardcoded 50 so that I can debug quicker*/
		data = new char[asdf][asdf];
		/*' ' because it wasn't comparing null and chars*/
		for(int i=0; i<asdf; i++){
			for(int j=0; j<asdf; j++){
				data[i][j]=' ';
			}
		}
		/*To double check width and height*/
		data[5][10]='a';
		data[10][5]='a';
		data[5][5]='a';
	}

	public char getData(int x, int y){
		return data[x][y];
	}

	public void setData(int x, int y, char c){
		data[x][y]=c;
	}

}
