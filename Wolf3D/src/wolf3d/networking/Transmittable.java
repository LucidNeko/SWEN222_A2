package wolf3d.networking;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * A class that implements the Transmittable interface ensures that it can write itself out
 * in a way that it can then read it self in. Using the corresponding write() and read() methods.<br><br>
 * For example. A class representing a 3D vector (x, y, z) should be able to write itself in a way
 * that by reading in the outputted data we end up with an identical vector of (x, y, z).
 * </br></br>
 * Please refer to wolf3d.networking.tests.TransmittableDemo.java for an example implementation.
 * 
 * @author Hamish Rae-Hodgson
 * @author Mike Nelson
 *
 *	19/09 - Mike: Added writeToOS and buildFromIS methods, so that objects implementing Transmittable can be constructed to and from the streams.
 *			i.e. can construct copies on client side from the server, and can save/load the game to disk.
 */
public interface Transmittable {
	
	/**
	 * Writes a change to the OutputStream
	 * (This method will often be the same as writeToOS, but it can be different
	 * in the case where we have some fields that we do not want to change
	 * after a Transmittable object has been constructed.)
	 * @param outputStream
	 */
	public void writeChange(OutputStream outputStream);
	
	/**
	 * Reads a change from the OutputStream
	 * @param outputStream
	 */
	public void readChange(InputStream inputStream);
	
	/**
	 * Returns the classes unique ID.
	 * @return The Transmittable classes' unique id.
	 */
	public int getTransmittableTypeID();

	/**
	 * Writes this object to the OutputStream.
	 * @param outputStream
	 */
	public void writeToOS(OutputStream outputStream);
}
