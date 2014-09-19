package wolf3d.networking;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * A class that implements the Transmittable interface ensures that it can write itself out
 * in a way that it can then read it self in. Using the corresponding write() and read() methods.<br><br>
 * For example. A class representing a 3D vector (x, y, z) should be able to write itself in a way
 * that by reading in the outputted data we end up with an identical vector of (x, y, z).
 * @author Hamish Rae-Hodgson
 * @author Mike Nelson
 *
 *	19/09 - Mike: Added writeToOS and buildFromIS methods, so that objects implementing Transmittable can be constructed to and from the streams.
 *			i.e. can construct copies on client side from the server, and can save/load the game to disk.
 */
public interface Transmittable {

	/**
	 * Writes a change to the OutputStream
	 * @param outputStream
	 */
	public void writeChange(OutputStream outputStream);
	
	/**
	 * Reads a change from the OutputStream
	 * @param outputStream
	 */
	public void readChange(InputStream inputStream);

	
	/**
	 * Writes this object to the OutputStream.
	 * @param outputStream
	 */
	public void writeToOS(OutputStream outputStream);
	
	/**
	 * Builds this object from the InputStream.
	 * @param inputStream
	 */
	public Transmittable buildFromIS(InputStream inputStream);

	
}
