package wolf3d.networking.mechanics;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import wolf3d.networking.Client;
import engine.core.World;

/**
 * This class represents the client. It's a dumb client class, it can send and receive messages but does absolutely no interpretation.
 *
 * @author Michael Nelson (300276118)
 *
 */

public class ClientConnection extends Thread{
	private Socket soc;
	private DataOutputStream out;
	private DataInputStream in;

	private Client master;

	private boolean uncollectedMsg = false;;
	private byte[] msg;

	/**
	 * Create a new client on the given socket.
	 * @param socket
	 */
	public ClientConnection(Socket socket, Client master){
		soc = socket;
		this.master = master;
	}

	/**
	 * Client thread.
	 * Listens for incoming messages.
	 */
	public void run(){
		try{
			try {

				out = new DataOutputStream(soc.getOutputStream());
				in = new DataInputStream(soc.getInputStream());

				//listen
				while(true){
					if(in.available()>0){
						master.receivedMessage(in);
						}
//						int length = in.readInt();
//						msg = new byte[length];
//						in.readFully(msg);
//						System.out.println(new String(msg));
//						uncollectedMsg = true;
					}

			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	 * Boolean that returns true if there's an uncollected message.
	 * @return
	 */
	public boolean doWeNeedToCollect(){
		return uncollectedMsg;
	}

	/**
	 * Returns the message.
	 *
	 * Note, this method will return old messages.
	 * @return
	 */
	public byte[] message(){
		uncollectedMsg = false;
		return msg;
	}

	/**
	 * Write a message to the server
	 * @param message Message to write
	 */
	public void writeToServer(byte[] message){
		try {
			int length = message.length;

			out.writeInt(length);
			if (length > 0) {
				out.write(message);
			}} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void giveMeACopyOfTheWorldPlease(World world) {
		// TODO Auto-generated method stub

	}

}
