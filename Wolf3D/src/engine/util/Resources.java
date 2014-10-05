package engine.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import engine.texturing.Mesh;
import engine.texturing.Texture;

/**
 * The Resource class has static methods for loading assets.<br>
 * Assets must be located in the root assets directory/package (or a subfolder therein).
 * @author Hamish
 *
 */
public class Resources {
	private static final Logger log = LogManager.getLogger();

	/** base asset directory */
	private static final String ASSETS_DIR = "/wolf3d/assets/";
	
	//Maps to reuse objects.
	private static Map<String, Texture> textureMap = new HashMap<String, Texture>();

	/**
	 * Gets an InputStream over the file at fname.
	 * @param fname The file name.
	 * @return The InputStream.
	 */
	public static InputStream getInputStream(String fname) {
		return Resources.class.getResourceAsStream(ASSETS_DIR + fname);
	}

	/**
	 * Gets a URL representing the file located at fname.
	 * @param fname The file name
	 * @return The URL.
	 */
	public static URL getURL(String fname) {
		return Resources.class.getResource(ASSETS_DIR + fname);
	}

	/** 
	 * Loads an image from the assets directory.
	 * @param fname The file name.
	 * @return The loaded image.
	 * @throws IOException If there is an error loading the image.
	 */
	public static BufferedImage getImage(String fname) throws IOException {
		return ImageIO.read(getURL(fname));
	}

	/**
	 * Loads a png image as a Texture.
	 * @param fname The filename (including path from the assests directory base)
	 * @param flip flip this image or not.
	 * @return
	 */
	public static Texture getTexture(String fname, boolean flip) {
		//if the Texture has already been loaded.
		if(textureMap.containsKey(fname)) {
			return textureMap.get(fname); 
		}
		
		//Otherwise load in and put in textureMap.
		try {
			BufferedImage image = getImage(fname);
			
			//@ensures image is of the write underlying format.
			BufferedImage buffy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = buffy.createGraphics();
			g.drawImage(image, null, 0, 0);
			g.dispose();
			image = buffy;
			
			DataBuffer imageBuffer = image.getRaster().getDataBuffer();
			//DataBuffer must be of type DataBufferByte
			byte[] pixels = ((DataBufferByte)imageBuffer).getData();
			convertABGRtoRGBAinPlace(pixels);
			if(flip) yFlipInPlace(pixels, image.getWidth(), image.getHeight());
			textureMap.put(fname, new Texture(image.getWidth(), image.getHeight(), pixels)); //put in the map.
			return textureMap.get(fname);
		} catch(Exception e) {
			log.error("Failed to load texture: " + e.getMessage());
			e.printStackTrace();
			throw new Error(e);
		}
	}

	/**
	 * Loads a wavefont.obj file into a Mesh and returns the Mesh.
	 * @param fnameOBJ The fname (i.e: model.obj)
	 * @return The Mesh.
	 */
	public static Mesh getMesh(String fnameOBJ) {
		return new OBJBuilder(getInputStream(fnameOBJ)).getMesh();
	}

	/**
	 * Modifies data in place. Flips an image upsidedown.
	 * @param data The byte array containing pixel data
	 * @param width The width of the image.
	 * @param height The height of the image.
	 */
	private static void yFlipInPlace(byte[] data, int width, int height) {
		int colorsPerRow = 4*width; //How many array indicies per row
		int half = height/2; //We travel down half the rows swapping over
		for(int i = 0; i < half; i++) {
			int top = i*colorsPerRow; //index of the start of the row on the top side
			int bot = ((height-1)-i)*colorsPerRow; //index of the start of the row on the bottom side
			//travel across the columns swapping top and bot
			for(int x = 0; x < colorsPerRow; x++) {
				byte tmp = data[top+x];
				data[top+x] = data[bot+x];
				data[bot+x] = tmp;
			}
		}
	}

	/**
	 * Modifies data in place. Chages the byte order for each pixel from ABGR to RGBA.
	 * @param data The byte array containing pixel data.
	 */
	private static void convertABGRtoRGBAinPlace(byte[] data) {
		for(int i = 0; i < data.length; i+=4) {
			byte tmp;

			//swap (x, 0, 0, y)
			tmp = data[i+0];
			data[i+0] = data[i+3];
			data[i+3] = tmp;

			//swap (0, x, y, 0)
			tmp = data[i+1];
			data[i+1] = data[i+2];
			data[i+2] = tmp;
		}
	}

}
