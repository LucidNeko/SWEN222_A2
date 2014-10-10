package wolf3d.networking.mechanics;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import wolf3d.networking.Server;

public class ServerConnection extends Thread{
	private Socket soc;
	private DataInputStream in;
	private DataOutputStream out;

	private Server master;

	public ServerConnection(Socket socket, Server master){
		soc=socket;
		this.master = master;
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
					String marker = in.readUTF();

					if(marker.equals("transform")){
						master.pushToAllClients("transform"); //marker
						master.pushToAllClients(in.readInt()); //ID of entity transformed.
						master.pushToAllClients(in.readUTF()); //json string of transform.
					}
					if(marker.equals("message")){
						master.pushToAllClients("message");
						master.pushToAllClients(in.readInt()); //ID of sender.
						master.pushToAllClients(in.readUTF()); // message itself.
					}
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

	//
	//TODO
	//MARK FOR DELETION.
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

	public void pushToClient(String string) {
		// TODO Auto-generated method stub
		try {
			out.writeUTF(string);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void pushToClient(int i) {
		// TODO Auto-generated method stub

		try {
			out.writeInt(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}


}
