package wolf3d.networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import wolf3d.GameDemoNet2;
import wolf3d.networking.mechanics.ClientConnection;

import com.google.gson.Gson;

import engine.components.Camera;
import engine.components.Transform;
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
	
	private boolean msgWaiting = false;


	private GameDemoNet2 gameloop;


	/**
	 * Construct a new client object connecting to the given ipAddress on the port supplied.
	 * @param ipAddress
	 * @param port
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public Client(String playerName, String ipAddress, int port, GameDemoNet2 gameloop) throws UnknownHostException, IOException{
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
//		msgWaiting = true;
		gameloop.receiveMessage(msg);
		/*
		if(gameStart){
			gameloop.receiveMessage(msg);
		}
		
		
			String st = msg.readUTF();
			System.out.println(st);
			if(st.equals("transform")){
				int id = msg.readInt();
				Entity ent = world.getEntity(id);
				Transform t;
				Gson g = new Gson();
				t = g.fromJson(msg.readUTF(), Transform.class);
				ent.getTransform().set(t);
			}
			if(st.equals("message")){
				
			}
			if(st.equals("ids")){
				System.out.println("Your ID is: "+msg.readInt());
				System.out.println("Other IDs are: ");
				int noOthers = msg.readInt();
				for(int i = 0; i< noOthers; i++){
					System.out.println(msg.readInt());
				}
			}
			if(st.equals("begin")){
				startGame();
			}
			//System.out.println(msg.readUTF());
		
		
//		else{
//			if(msg.readUTF()=="start"){
//				gameStart = true;
//				gameloop.beginGame();
//			}
//		}
 * 
 */
	}
	
	/*
	public boolean doWeHaveAMessage(){
		if(msgWaiting){
			
		}
	}
	*/

	public void startGame(){
		gameStart = true;
		gameloop.start();
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

	public void sendToServer(String string) throws IOException {
		// TODO Auto-generated method stub
		connection.sendToServer(string);
	}
	
	public void sendToServer(int i) throws IOException{
		connection.sendToServer(i);
	}

	public DataInputStream getInputStream() {
		// TODO Auto-generated method stub
		return connection.getInputStream();
	}


}
