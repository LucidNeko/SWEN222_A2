package wolf3d.networking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientProtocol extends Thread{
	Client connection;
	byte[] msg;
	
	public ClientProtocol(Socket sock){
		connection = new Client(sock);
	}
	
	public void sendMessage(byte[] message){
		connection.writeToServer(message);
	}
	
	public void run(){
		while(true){
			if(connection.doWeNeedToCollect()){
				msg = connection.message();
				System.out.println(msg.toString());
			}
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException{
		Socket sock = new Socket(args[0],Integer.parseInt(args[1]));
		ClientProtocol pc = new ClientProtocol(sock);
		pc.run();
	}
}
