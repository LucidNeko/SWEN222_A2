package wolf3d.world;

import java.util.ArrayList;
import java.util.List;

import wolf3d.components.Transform;
import wolf3d.core.Camera;
import wolf3d.core.Entity;

/**
 * This class is responsible for containing the state of the
 * world and the conditions and restrictions for the world
 * @author Sameer Magan
 *
 */
public class World {

	private List<Entity> players = new ArrayList<Entity>();
	
	/**
	 * Adds a new player to the game with given id
	 */
	public void createPlayer(int id){
		Entity player = new Entity(id, Transform.class);
		player.attachComponent(Camera.class);
		players.add(player);
	}
}
