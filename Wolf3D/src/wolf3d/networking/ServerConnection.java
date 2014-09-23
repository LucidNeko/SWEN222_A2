package wolf3d.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends Thread{
	private Socket soc;
	private DataInputStream in;
	private DataOutputStream out;
	
	public ServerConnection(Socket socket){
		soc=socket;
		try {
			out = new DataOutputStream(soc.getOutputStream());
			in = new DataInputStream(soc.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		try{
			try{

			
			while(true){
				if(in.available()>0){
					int length = in.readInt();
					byte[] message = new byte[length];
					in.readFully(message);
				}
			}
			
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		finally{
			try {
				soc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void pushToClient(byte[] message){
		try {
			int length = message.length;
			out.toString();
			out.writeInt(length);
			if (length > 0) {
				out.write(message);
			}} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
}
