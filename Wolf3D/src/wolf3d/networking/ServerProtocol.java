package wolf3d.networking;

public class ServerProtocol {
	private Server theServer;
	
	
	public ServerProtocol(int port, int capacity){
		theServer = new Server(port, capacity);
		theServer.start();
	}
	
	public static void main(String[] args){
		new ServerProtocol(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
	}
	
}
