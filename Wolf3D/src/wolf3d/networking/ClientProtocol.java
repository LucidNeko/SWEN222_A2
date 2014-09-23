package wolf3d.networking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class ClientProtocol extends Thread implements Observer{
	Client connection;
	byte[] msg;
	
	public ClientProtocol(Socket sock, Observable ob){
		connection = new Client(sock);
		ob.addObserver(this);
	}
	
	public void sendMessage(byte[] message){
		connection.writeToServer(message);
	}
	
	public void run(){
		while(true){
			//READ
			if(connection.doWeNeedToCollect()){
				msg = connection.message();
				System.out.println(msg.toString());
			}
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
		ClientProtocol pc = new ClientProtocol(sock, null);
		pc.run();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO
		//cast O as component and get id
		
		//cast arg to string and get the message.
		
		//send to the server.
	}
}
