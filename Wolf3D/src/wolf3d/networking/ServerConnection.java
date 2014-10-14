package wolf3d.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Michael Nelson (300276118)
 */
public class ServerConnection extends Thread{
	private Socket soc;

	private DataInputStream in;
	private DataOutputStream out;


	/**
	 * Constructor for ServerConnection class.
	 * Each ServerConnection has input and output streams for communicating with a client
	 *
	 * @param socket The socket on which this connection should listen.
	 */
	public ServerConnection(Socket socket){
		soc=socket;
		try {
			out = new DataOutputStream(soc.getOutputStream());
			in = new DataInputStream(soc.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method returns true if the socket is still open and connected.
	 * @return
	 */
	public boolean areWeAlive(){
		return (!(soc.isClosed()) && soc.isConnected());
	}

	public void run(){
		try{
			while(true){
			}
		}
		finally{
			try {
				soc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Send a string to the client.
	 * @param string UTF string.
	 */
	public void pushToClient(String string) {
		try {
			out.writeUTF(string);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Send an int to the client.
	 * @param i
	 */
	public void pushToClient(int i) {
		try {
			out.writeInt(i);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Send a float to the client.
	 * @param f
	 */
	public void pushToClient(float f) {
		try {
			out.writeFloat(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Return the DataInputStream that this ServerConnection holds.
	 * @return
	 */
	public DataInputStream getInputStream() {
		return in;
	}

}
