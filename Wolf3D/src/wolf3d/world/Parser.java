package wolf3d.world;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * This is responsible for turning the text file containing all the walls for
 * the world into objects
 *
 * @author magansame
 *
 */
public class Parser {

	private File file;
	private String filePath;

	private int[][] walls;
	private short northMask = 8;
	private short eastMask = 4;
	private short southMask = 2;
	private short westMask = 1;

	public Parser(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * passes file into 2d array of ints
	 */
	public void passFile() {
		file = new File(filePath);

		try {
			Scanner sc = new Scanner(file);
			int total;

			int width = sc.nextInt();
			int height = sc.nextInt();
			int x=2*height;
			walls = new int[height][width];
			int col = 0;
			while (sc.hasNext()) {
				String line = sc.next();
				char[] row = line.toCharArray();
				int rowCheck = sc.nextInt();
				for (int i = 0; i < row.length; i++) {
					walls[col][i] = Integer.decode("0x" + row[i]);
				}
				if(2*height==x-1){
					total=sc.nextInt();
				}
				col++;
				System.out.println(walls);

			}

		} catch (IOException e) {
			System.out.println("Something went wrong with the scanner!!!");
		}
	}

	public static void main(String[] args) {
		Parser p = new Parser("Map.txt");
		p.passFile();
	}
}
