package wolf3d.networking.mechanics;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import engine.components.Component;
import engine.core.Entity;
import engine.core.World;

public class MessageReader {

	//add n later
	/**
	 * This method will change the component inside the world that was changed.
	 * Be warned.
	 * @param is
	 * @param world
	 * @return
	 */
	public static void readMessage(InputStream is, World world){
		try {
			if(is.read()!='e'){
				//rubbish message, bin it.
				System.out.println("We received a rubbish message, all messages should start with an 'e' or an 'n'");
				//need to destory reast of inputstream.
			}
			else{
				int id = is.read()<<24 + is.read()<<16 + is.read()<<8 + is.read(); //this just reads the next four bytes as an int.
				Entity ent = world.getEntity(id);
				if(ent==null){
					//that entity does not exist, better make it.
					world.createEntity(id, "");
					ent = world.getEntity(id);
				}
				//readEntity(is, root);
				
				if(is.read()!='c'){
					System.out.println("We got a rubbish message, I expected a component here");
				}
				String type;
				int lengthOfTypeString = is.read()<<24 + is.read()<<16 + is.read()<<8 + is.read();
				byte[] typeB = new byte[lengthOfTypeString];
				for(int i = 0; i<lengthOfTypeString; i++){
					typeB[i] = (byte)is.read();
				}
				type = new String(typeB);
				id = is.read()<<24 + is.read()<<16 + is.read()<<8 + is.read(); 
				
				//check this component exists in entity
				List<Component> comps = ent.getComponents(Component.class); //ent.getAllComponents();
				
				for(Component comp : comps){
					
				}
				//if not create it
				//if yes then read it.
				
				//mark this point as last successful read
				is.mark(Integer.MAX_VALUE);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
