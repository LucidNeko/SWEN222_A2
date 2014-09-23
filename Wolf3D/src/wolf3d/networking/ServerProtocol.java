package wolf3d.networking;

import java.util.Observable;
import java.util.Observer;

public class ServerProtocol implements Observer {
	private Server theServer;


	public ServerProtocol(int port, int capacity, Observable ob){
		theServer = new Server(port, capacity);
		theServer.start();
		if(ob!=null){
			ob.addObserver(this);
		}
	}

	/**
	 * Test method.
	 * @param args
	 */
	public static void main(String[] args){
		new ServerProtocol(Integer.parseInt(args[0]),Integer.parseInt(args[1]), null);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO

		//cast O as component and get id

		//cast arg to string and get the message.

		//send to the client. (which client? all of them?)
	}

}
