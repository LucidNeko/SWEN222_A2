package wolf3d.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import wolf3d.GameDemo;
import engine.common.Vec3;
import engine.components.MeshRenderer;
import engine.components.Transform;
import engine.core.Entity;
import engine.core.World;
import engine.util.ServiceLocator;

/**
 * @author Michael Nelson (300276118)
 */
public class Client extends Thread{
	private Socket sock;

	private World world;
	private GameDemo gl;

	private DataInputStream in;
	private DataOutputStream out;


	/**
	 * Construct a new client object connecting to the given ipAddress on the port supplied.
	 * @param ipAddress Ip address to connect to
	 * @param port the port to connect too.
	 * @param gameDemo the GameLoop that this client makes modifications to.
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public Client(String playerName, String ipAddress, int port, GameDemo gameDemo) throws UnknownHostException, IOException{
		this.sock = new Socket(ipAddress,port);
		this.world = ServiceLocator.getService(World.class);
		this.gl = gameDemo;
		this.start();
	}

	/**
	 * Listens for new messages from the server, then makes modifications to the world.
	 */
	public void run(){
		try{
			try{
				out = new DataOutputStream(sock.getOutputStream());
				in = new DataInputStream(sock.getInputStream());

				while(true){
					String marker = in.readUTF();
					switch(marker){
					case "transform":
						int id = in.readInt();
						if(id==gl.getPlayer().getID()){
							for(int i = 0; i<6; i++){
								in.readFloat(); //chuck the message away
							}
						}
						else{
							Entity e = world.getEntity(id);
							if(e!=null){
								Transform t = e.getTransform();
								t.setPositionNoFlag(in.readFloat(),in.readFloat(), in.readFloat());
								t.lookAtDirectionNoFlag(new Vec3(in.readFloat(), in.readFloat(), in.readFloat()));
							}
							else{
								for(int i = 0; i<6; i++){
									in.readFloat();
								}
							}
						}
						break;
						
					case "ids":
						int playerID = in.readInt();
						gl.createPlayer(playerID);
						int noOthers = in.readInt();
						for(int i = 0; i< noOthers; i++){
							int otherID = in.readInt();
							gl.createOtherPlayer(otherID);
						}
						break;

						/*
						 * In the case of a disconnect,
						 * make the disconnected player invisible
						 * but we keep him "alive" so when a save is made
						 * that player will be saved, and could rejoin a new
						 * loaded game.
						 */
					case "disconnect":
						System.out.println("player disce");
						int who = in.readInt();
						Entity e = world.getEntity(who);
						e.detachComponent(e.getComponent(MeshRenderer.class));
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
				e.printStackTrace();
			}
		}
		finally{
			try {
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sends a Transform to the server via floats, much more efficient than using JSON.
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

	/**
	 * Send a string to the server
	 * @param string UTF string
	 * @throws IOException
	 */
	public void sendToServer(String string) throws IOException {
		out.writeUTF(string);
	}

	/**
	 * Send an int to the server.
	 * @param i
	 * @throws IOException
	 */
	public void sendToServer(int i) throws IOException{
		out.writeInt(i);
	}

	/**
	 * Get the inputStream this client is listening on.
	 * @return
	 */
	public DataInputStream getInputStream() {
		return in;
	}



}
