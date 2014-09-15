package wolf3d.networking;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * A class that implements the Transmittable interface ensures that it can write itself out
 * in a way that it can then read it self in. Using the corresponding write() and read() methods.<br><br>
 * For example. A class representing a 3D vector (x, y, z) should be able to write itself in a way
 * that by reading in the outputted data we end up with an identical vector of (x, y, z).
 * @author Hamish Rae-Hodgson
 *
 */
public interface Transmittable {

	/**
	 * Writes this object to the OutputStream.
	 * @param outputStream
	 */
	public void write(OutputStream outputStream);
	
	/**
	 * Builds this object from the InputStream.
	 * @param inputStream
	 */
	public void read(InputStream inputStream);
	
}
