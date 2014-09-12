package wolf3d.util;

import static javax.media.opengl.GL2.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.media.opengl.GL2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is a class with static methods for loading resources.
 * @author Hamish Rae-Hodgson
 *
 */
public class ResourceLoader {
	private static final Logger log = LogManager.getLogger();
	
	/** base asset directory */
	private static final String ASSETS_DIR = "/wolf3d/assets/";
	
	/** On error, returns -1 */
	public static int loadTexture(GL2 gl, String fname, boolean flip) {
		try {
			URL url = ResourceLoader.class.getResource(ASSETS_DIR + fname);
			BufferedImage img = ImageIO.read(url);
			DataBuffer dataBuffer = img.getRaster().getDataBuffer();
			if(dataBuffer instanceof DataBufferByte) {
				int[] id = new int[1];
				gl.glGenTextures(1, id, 0);
				gl.glBindTexture(GL_TEXTURE_2D, id[0]);
				gl.glTexParameterf(GL_TEXTURE_2D,  GL_TEXTURE_MIN_FILTER, GL_NEAREST);
				gl.glTexParameterf(GL_TEXTURE_2D,  GL_TEXTURE_MAG_FILTER, GL_NEAREST);
				
				byte[] pixelData = ((DataBufferByte) dataBuffer).getData();
				convertABGRtoRGBAinPlace(pixelData);
				if(flip) yFlipInPlace(pixelData, img.getWidth(), img.getHeight());
				ByteBuffer pixels = ByteBuffer.wrap(pixelData);
				
				gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, img.getWidth(), img.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
				
				if(gl.glGetError() == 0) {
					return id[0];
				} else {
					return -1;
				}
			} else {
				throw new RuntimeException("Underlying buffer must be of type DataBufferByte");
			}
		} catch (IOException | RuntimeException e) {
			log.error("Failed to load texture: " + fname);
			e.printStackTrace();	
			return -1;
		}
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
