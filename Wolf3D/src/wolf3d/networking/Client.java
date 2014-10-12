package wolf3d.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import wolf3d.GameDemoNet2;

import com.google.gson.Gson;

import engine.common.Vec3;
import engine.components.Transform;
import engine.core.Entity;
import engine.core.World;

/**
 * This class is a wrapper for the Client connection that performs logic necessary for the game to run.
 *
 * @author Michael Nelson (300276118)
 *
 */
public class Client extends Thread{
	private Socket sock;

	private World world;
	private GameDemoNet2 gl;

	private String state;

	private DataInputStream in;
	private DataOutputStream out;


	/**
	 * Construct a new client object connecting to the given ipAddress on the port supplied.
	 * @param ipAddress
	 * @param port
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public Client(String playerName, String ipAddress, int port, World world, GameDemoNet2 gl) throws UnknownHostException, IOException{
		this.sock = new Socket(ipAddress,port);
		this.state = "connected";
		this.world = world;
		this.gl = gl;
		this.start();
	}

	/**
	 * Polls the client to see if we have messages to connect. Then if we do it does something.
	 * (NOTE, MAY BE WORTHWILE TO MAKE THIS CLASS OBSERVABLE?)
	 */
	public void run(){
		try{
			try{
				out = new DataOutputStream(sock.getOutputStream());
				in = new DataInputStream(sock.getInputStream());

				while(true){
					String marker = in.readUTF();
					switch(marker){
					/*
					 * This be the JSON way, Is slow.
					 
					case "transform":
						int id = in.readInt();
						System.out.println("On id: "+id);
						if(id==gl.getPlayer().getID()){
							System.out.println("Hey, that's our own player ID. Let's not apply this transform...");
							in.readUTF();
						}
						else{
							Entity ent = world.getEntity(id);
							Transform t;
							Gson g = new Gson();
							t = g.fromJson(in.readUTF(), Transform.class);
							System.out.println("The created transform: " + t.toString());
							ent.getTransform().set(t, false);
						}
						break;
						*/
					case "transform":
						int id = in.readInt();
						System.out.println("On id: "+id);
						if(id==gl.getPlayer().getID()){
							System.out.println("Hey, that's our own player ID. Let's not apply this transform...");
							in.readFloat();
							in.readFloat();
							in.readFloat();
							in.readFloat();
							in.readFloat();
							in.readFloat();
							//this is bordering on a for loop...
						}
						else{
							Transform t = world.getEntity(id).getTransform();
							t.setPosition(in.readFloat(),in.readFloat(), in.readFloat());
							t.lookInDirection(new Vec3(in.readFloat(), in.readFloat(), in.readFloat()));
							System.out.println("The created transform: " + t.toString());
						}
						break;
					case "ids":
						int playerID = in.readInt();
						System.out.println("Your ID is: "+playerID);
						//REPLACE THIS WITH THE METHOD IN GAME LOOP
						//TODO
						gl.createPlayer(playerID);
						//	Entity player = EntityFactory.createPlayerWithID(world, "Bob For Now", playerID);
						//	gl.setPlayer(player);
						System.out.printf("Other IDs are: ");
						int noOthers = in.readInt();
						for(int i = 0; i< noOthers; i++){
							int otherID = in.readInt();
							System.out.printf("%d, ",otherID);
							gl.createOtherPlayer(otherID);
							//	EntityFactory.createOtherPlayer(world, "Joe ForNow", otherID);
						}
						System.out.printf("\n");
						break;

					case "begin":
						gl.createEntities();
						gl.start();
						break;

					case "wait":
						int waitNo = in.readInt();
						System.out.println("Waiting for "+waitNo+" more player/s to join.");
						break;
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
				sock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		new Client("Bob McBob", args[0],Integer.parseInt(args[1]), null, null);
		//pc.start();
	}

	/*
	 * THIS IS THE JSON WAY, ITS SLOW.
	public void sendTransform(Transform t) throws IOException{
		out.writeUTF("transform");
		out.writeInt(t.getOwner().getID());
		Gson g = new Gson();
		out.writeUTF(g.toJson(t));
	}
	*/
	
	/**
	 * Sends via floats, much more efficient.
	 * @param t
	 * @throws IOException
	 */
	public void sendTransform(Transform t) throws IOException{
		out.writeUTF("transform");
		out.writeInt(t.getOwner().getID());
		Vec3 pos = t.getPosition();
		Vec3 look = t.getLook();
		out.writeFloat(pos.x());
		out.writeFloat(pos.y());
		out.writeFloat(pos.z());
		out.writeFloat(look.x());
		out.writeFloat(look.y());
		out.writeFloat(look.z());
	}

	public void sendToServer(String string) throws IOException {
		out.writeUTF(string);
	}

	public void sendToServer(int i) throws IOException{
		out.writeInt(i);
	}

	public DataInputStream getInputStream() {
		return in;
	}



}
