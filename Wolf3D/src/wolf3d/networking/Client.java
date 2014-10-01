package wolf3d.networking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import wolf3d.networking.mechanics.ClientConnection;

/**
 * This class is a wrapper for the Client connection that performs logic necessary for the game to run.
 * 
 * @author Michael Nelson (300276118)
 *
 */
public class Client extends Thread implements Observer{
	ClientConnection connection;
	byte[] msg;

	/**
	 * Construct a new ClientProtocol on the given socket, with an observable (probably removed in future?)
	 * @param sock
	 * @param ob
	 */
	public Client(Socket sock, Observable ob){
		connection = new ClientConnection(sock);
		connection.start();
		if(ob!=null){
			ob.addObserver(this);
		}
	}

	/**
	 * Dumb message method, will probably be deleted in future.
	 * @param message
	 */
	public void sendMessage(byte[] message){
		connection.writeToServer(message);
	}

	/**
	 * Polls the client to see if we have messages to connect. Then if we do it does something. 
	 * (NOTE, MAY BE WORTHWILE TO MAKE THIS CLASS OBSERVABLE?)
	 */
	public void run(){
		while(true){
			//READ

			Scanner input = new Scanner(System.in);
			while(true){
				String s = input.nextLine();
				System.out.println(s);
				sendMessage(s.getBytes());
			}
			/*
			if(connection.doWeNeedToCollect()){
				msg = connection.message();
				System.out.println(msg.toString());
			}
			*/
		}
	}

	/**
	 * Test method.
	 * @param args
	 * @throws NumberFormatException
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException{
		Socket sock = new Socket(args[0],Integer.parseInt(args[1]));
		Client pc = new Client(sock, null);
		pc.start();
		System.out.println("Reached?");
		//input.close();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO
		//cast O as component and get id

		//cast arg to string and get the message.

		//send to the server.
	}
}
