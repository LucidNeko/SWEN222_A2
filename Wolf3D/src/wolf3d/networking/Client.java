package wolf3d.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import wolf3d.GameNetworkDemo;
import wolf3d.networking.mechanics.ClientConnection;
import engine.components.Camera;
import engine.core.Entity;
import engine.core.World;
import engine.display.View;

/**
 * This class is a wrapper for the Client connection that performs logic necessary for the game to run.
 *
 * @author Michael Nelson (300276118)
 *
 */
public class Client extends Thread{
	private ClientConnection connection;

	private World world; //this is the clients local copy of the world

	private Entity player;
	private View view;
	private Camera camera;

	private boolean gameStart = false;


	private GameNetworkDemo gameloop;


	/**
	 * Construct a new client object connecting to the given ipAddress on the port supplied.
	 * @param ipAddress
	 * @param port
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public Client(String playerName, String ipAddress, int port, GameNetworkDemo gameloop) throws UnknownHostException, IOException{
		Socket sock = new Socket(ipAddress,port);
		connection = new ClientConnection(sock,this);

		this.gameloop = gameloop;

		//Create this player on the server
		//createMe(playerName);

		//Get a copy of the gameworld
		//connection.giveMeACopyOfTheWorldPlease(world);

		//Begin listening
		connection.start();
	}

	private void createMe(String playerName) {
		player = world.createEntity(playerName);
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

			//simple write messagew for testing.
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
			//
		}
	}

	public void receivedMessage(DataInputStream msg) throws IOException{
		if(gameStart){
			gameloop.receiveMessage(msg);
		}
//		else{
//			if(msg.readUTF()=="start"){
//				gameStart = true;
//				gameloop.beginGame();
//			}
//		}
	}

	public void startGame(DataOutputStream os){
		gameloop.beginGame(os);
	}

	public boolean hasGameStarted() {
		// TODO Auto-generated method stub
		return gameStart;

	}


	/**
	 * Test method.
	 * @param args
	 * @throws NumberFormatException
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException{
		Client pc = new Client("Bob McBob", args[0],Integer.parseInt(args[1]), null);
		pc.start();
	}


}
