package wolf3d.networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author Michael Nelson (300276118)
 */
public class Server extends Thread{
	ServerSocket ss;

	ServerConnection[] connections;
	DataInputStream[] dis;
	boolean[] alive; //client on this index is alive or not.


	private boolean listening = true;

	private int index = 0;
	private int capacity;

	private int noConnected = 0; //once game has started, if this number reaches 0 close the server. (all clients have disconnected.)

	/**
	 * Construct a new server listening on a port and with a capacity.
	 * The game will not begin until all the players have connected
	 * at what point the server will broadcast IDS to all the players
	 * followed by the string "begin"
	 * <br />
	 *
	 * This server acts as a repeated essentially, clients send messages to the
	 * server which then forwards it to all the other clients.
	 *
	 *This has the advantage of being simple and efficient, but it's obviously
	 *very hackable.
	 * @param port Port we are listning on. (Note 0 will assign a free port).
	 * @param capacity maximum players (game will start only when all players have connected
	 */
	public Server(int port, int capacity) {
		try {
			ss = new ServerSocket(port);
			connections = new ServerConnection[capacity];
			dis = new DataInputStream[capacity];
			alive = new boolean[capacity];
			this.capacity = capacity;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the port the server is listening on.
	 * @return
	 */
	public int getSocketPort(){
		return ss.getLocalPort();
	}


	/**
	 * Server run thread, starts up the server and listens for new connections.
	 */
	public void run(){
		while(listening){

			pushToAllClients("wait");
			pushToAllClients((capacity-index));
			System.out.println("Server listening for " + (capacity-index) + " more connections...");

			Socket sock;
			try {
				sock = ss.accept();
				System.out.println("Accepted a connection from " + sock.getInetAddress() + "...");
				connections[index] = new ServerConnection(sock);
				dis[index] = connections[index].getInputStream();
				alive[index] = true;
				noConnected++;

				if(index==(capacity-1)){
					listening = false;

					//we need to start the thread solely so the socket closes on close.
					for(ServerConnection c : connections){
						c.start();
					}

					//let the clients know the game can now begin.
					assignIDs();
					pushToAllClients("begin");
				}
				index++;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//no longer listening for connections...
		//listen for messages instead then.
		while(!listening){
			for(int i = 0; i<capacity; i++){
				try {
					if(alive[i] && dis[i].available()>0){
						String marker = dis[i].readUTF();

						if(marker.equals("transform")){
							pushToAllClients("transform"); //marker
							pushToAllClients(dis[i].readInt()); //ID of entity transformed.
							pushToAllClients(dis[i].readFloat());
							pushToAllClients(dis[i].readFloat());
							pushToAllClients(dis[i].readFloat());
							pushToAllClients(dis[i].readFloat());
							pushToAllClients(dis[i].readFloat());
							pushToAllClients(dis[i].readFloat());
						}
						if(marker.equals("message")){
							pushToAllClients("message");
							pushToAllClients(dis[i].readInt()); //ID of sender.
							pushToAllClients(dis[i].readUTF()); // message itself.
						}
					}
				} catch (IOException e) {
					e.printStackTrace();

					//dis[i].close();
					try {
						dis[i].close();
						connections[i].closeSocket();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					alive[i] = false;
					pushToAllClients("disconnect");
					pushToAllClients(-(i+1));
				}
			}
		}

	}

	/**
	 * This method will send some IDs over the network for the clients to construct
	 * themselves and other players. THe message looks as follows:
	 *
	 * "ids"
	 * [int - player id]
	 * [int - # other clients]
	 * [int - client 1 id]
	 * [int - client 2 id]
	 * ....
	 * ....
	 * ....
	 *
	 */
	private void assignIDs() {
		// TODO Auto-generated method stub

		//first give every client their id
		for(int i = 0; i<capacity; i++){
			System.out.println("SendTo"+i+": \"ids\"");
			connections[i].pushToClient("ids");
			System.out.println("SendTo"+i+": [int]"+-(i+1));
			connections[i].pushToClient((-(i+1)));

			//let player know how many other players there are
			System.out.println("SendTo"+i+": [int]"+(capacity-1));
			connections[i].pushToClient(capacity-1);

			//then give the other clients ids of other players, we dont care about order.
			for(int j = 0; j<capacity; j++){
				if(j!=i){
					System.out.println("SendTo"+i+": [int]"+(-(j+1)));
					connections[i].pushToClient(-(j+1));
				}
			}
		}
	}

	/**
	 * Sends a string to every client.
	 * @param string
	 */
	public void pushToAllClients(String string) {
		for(int i = 0; i<capacity; i++){
			if(alive[i]){
				if(connections[i] != null){
					connections[i].pushToClient(string);
				}
			}
		}
	}

	/**
	 * Sends an int to every client.
	 * @param i
	 */
	public void pushToAllClients(int i) {
		for(int j = 0; j<capacity; j++){
			if(alive[j]){
				if(connections[j] != null){
					connections[j].pushToClient(i);
				}
			}
		}
	}

	/**
	 * Sends a float to every client.
	 * @param f
	 */
	public void pushToAllClients(float f) {
		for(int i = 0; i<capacity; i++){
			if(alive[i]){
				if(connections[i] != null){
					connections[i].pushToClient(f);
				}
			}
		}
	}


	/**
	 * Code to start the server.
	 * @param args
	 */
	public static void main(String[] args){
		Server serber = new Server(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		serber.start();
	}

}
