package wolf3d.networking;

import wolf3d.networking.mechanics.ServerConnectionsMaster;

public class Server{
	private ServerConnectionsMaster theServer;


	public Server(int port, int capacity){
		theServer = new ServerConnectionsMaster(port, capacity);
		theServer.start();
	}

	/**
	 * Test method.
	 * @param args
	 */
	public static void main(String[] args){
		new Server(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
	}


}
