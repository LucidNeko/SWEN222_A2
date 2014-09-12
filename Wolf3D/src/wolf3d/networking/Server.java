package wolf3d.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
	ServerSocket ss;
	ServerConnection[] connections;
	private boolean listening = true;
	private int index = 0;
	private int capacity;

	public Server(int port, int capacity) {
		try {
			ss = new ServerSocket(port);
			connections = new ServerConnection[capacity];
			this.capacity = capacity;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pushToClient(int id, byte[] msg){
		connections[id].pushToClient(msg);
	}

	public void run(){
		System.out.println("Server listening for connections...");
		while(listening){
			Socket sock;
			try {
				sock = ss.accept();
				System.out.println("Accepted a connection from " + sock.getInetAddress() + "...");
				connections[index] = new ServerConnection(sock);
				connections[index++].start();
				if(index>=capacity){
					listening = false;
				}

				byte[] ms = new byte[5];
				ms[0] = (byte) 200;
				connections[index-1].pushToClient(ms);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
