package wolf3d.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread{
	private Socket soc;
	private DataOutputStream out;
	private DataInputStream in;
	
	private boolean uncollectedMsg = false;;
	private byte[] msg;

	public Client(Socket socket){
		soc = socket;
	}

	public void run(){
		try{
			try {

				out = new DataOutputStream(soc.getOutputStream());
				in = new DataInputStream(soc.getInputStream());

				//listen
				while(true){
					if(in.available()>0){
						int length = in.readInt();
						msg = new byte[length];
						in.readFully(msg);
						uncollectedMsg = true;
					}
				}
			}
			catch (IOException e) {
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

	public boolean doWeNeedToCollect(){
		return uncollectedMsg;
	}
	
	public byte[] message(){
		uncollectedMsg = false;
		return msg;
	}
	
	public void writeToServer(byte[] message){
		try {
			int length = message.length;

			out.writeInt(length);
			if (length > 0) {
				out.write(message);
			}} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
