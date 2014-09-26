package wolf3d.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * This class represents the client. It's a dumb client class, it can send and receive messages but does absolutely no interpretation.
 * 
 * @author Michael Nelson (300276118)
 *
 */
public class Client extends Thread{
	private Socket soc;
	private DataOutputStream out;
	private DataInputStream in;
	
	private boolean uncollectedMsg = false;;
	private byte[] msg;

	/**
	 * Create a new client on the given socket.
	 * @param socket
	 */
	public Client(Socket socket){
		soc = socket;
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
						int length = in.readInt();
						msg = new byte[length];
						in.readFully(msg);
						uncollectedMsg = true;
					}
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
}
