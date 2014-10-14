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
				return;
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
	public void pushToClient(String string) throws IOException{
		out.writeUTF(string);
	}

	/**
	 * Send an int to the client.
	 * @param i
	 */
	public void pushToClient(int i) throws IOException{
		out.writeInt(i);
	}

	/**
	 * Send a float to the client.
	 * @param f
	 */
	public void pushToClient(float f) throws IOException{
		out.writeFloat(f);
	}

	/**
	 * Return the DataInputStream that this ServerConnection holds.
	 * @return
	 */
	public DataInputStream getInputStream() {
		return in;
	}

	/**
	 * Public method to close the socket. (i.e. when server shutsdown a connection.)
	 * @throws IOException
	 */
	public void closeSocket() throws IOException {
		soc.close();
	}

}
