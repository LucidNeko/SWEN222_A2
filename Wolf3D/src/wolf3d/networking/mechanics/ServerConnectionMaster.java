package wolf3d.networking.mechanics;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is a dumb server, i.e. it does no logic.
 * It has multiple connections and methods for sending and receiving messages.
 * @author Michael Nelson (300276118)
 *
 */
public class ServerConnectionMaster extends Thread{
	ServerSocket ss;
	ServerConnection[] connections;
	private boolean listening = true;
	private int index = 0;
	private int capacity;

	/**
	 * Construct a new server listening on a port and with a capacity.
	 * @param port
	 * @param capacity maximum players (game will start at first connection)
	 */
	public ServerConnectionMaster(int port, int capacity) {
		try {
			ss = new ServerSocket(port);
			connections = new ServerConnection[capacity];
			this.capacity = capacity;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Send a message to client number [id]
	 * @param id The client to send the message to. If id=-1, send to all clients.
	 * @param msg the message.
	 */
	public void pushToClient(int id, byte[] msg){
		if(id==-1){
			for(ServerConnection sc : connections){
				sc.pushToClient(msg);
			}
		}
		else{
			connections[id].pushToClient(msg);
		}
	}

	/**
	 * Server run thread, starts up the server and listens for new connections.
	 */
	public void run(){
		System.out.println("Server listening for connections...");
		while(listening){
			Socket sock;
			try {
				sock = ss.accept();
				System.out.println("Accepted a connection from " + sock.getInetAddress() + "...");
				connections[index] = new ServerConnection(sock);
				connections[index].start();
				if(index==(capacity-1)){
					listening = false;
				}

				byte[] ms = new byte[5];
				//Just test code, send byte to client.
				ms[0] = (byte) 1;
				connections[index].pushToClient(ms);

				index++;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
