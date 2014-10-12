package wolf3d.networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is a dumb server, i.e. it does no logic.
 * It has multiple connections and methods for sending and receiving messages.
 * @author Michael Nelson (300276118)
 *
 */
public class Server extends Thread{
	ServerSocket ss;
	ServerConnection[] connections;
	DataInputStream[] dis;
	private boolean listening = true;
	private int index = 0;
	private int capacity;

	/**
	 * Construct a new server listening on a port and with a capacity.
	 * @param port
	 * @param capacity maximum players (game will start only when all players have connected
	 */
	public Server(int port, int capacity) {
		try {
			ss = new ServerSocket(port);
			connections = new ServerConnection[capacity];
			dis = new DataInputStream[capacity];
			this.capacity = capacity;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				connections[index] = new ServerConnection(sock,this);
				dis[index] = connections[index].getInputStream();

				if(index==(capacity-1)){
					listening = false;


					//begin listening
					for(ServerConnection c : connections){
						c.start();
					}

					//let the clients know the game can now begin.
					assignIDs();
					pushToAllClients("begin");
					//	return;
				}
				index++;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//no longer listening for connections...
		while(!listening){
			for(DataInputStream in: dis){
				try {
					if(in.available()>0){
						String marker = in.readUTF();

						if(marker.equals("transform")){
							pushToAllClients("transform"); //marker
							pushToAllClients(in.readInt()); //ID of entity transformed.
							pushToAllClients(in.readFloat());
							pushToAllClients(in.readFloat());
							pushToAllClients(in.readFloat());
							pushToAllClients(in.readFloat());
							pushToAllClients(in.readFloat());
							pushToAllClients(in.readFloat());
						}
						if(marker.equals("message")){
							pushToAllClients("message");
							pushToAllClients(in.readInt()); //ID of sender.
							pushToAllClients(in.readUTF()); // message itself.
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		// TODO Auto-generated method stub
		for(ServerConnection sc : connections){
			if(sc != null){
				if(sc.areWeAlive()){
					sc.pushToClient(string);
				}
			}
		}
	}

	/**
	 * Testing code.
	 * @param args
	 */
	public static void main(String[] args){
		Server serber = new Server(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		serber.start();
	}

	public void pushToAllClients(int i) {
		// TODO Auto-generated method stub
		for(ServerConnection sc : connections){
			if(sc != null){
				if(sc.areWeAlive()){
					sc.pushToClient(i);
				}
			}
		}
	}
	
	public void pushToAllClients(float f) {
		// TODO Auto-generated method stub
		for(ServerConnection sc : connections){
			if(sc != null){
				if(sc.areWeAlive()){
					sc.pushToClient(f);
				}
			}
		}
	}
}
