package wolf3d.networking;


import java.io.DataOutputStream;
import java.io.IOException;



import engine.components.Component;
import wolf3d.components.Health;

/**
 * The MessageBuilder class is used by the networking classes to construct messages
 * in a friendly to send over network message.
 * <br />
 * <br />
	It's quite procedural, it has a lot of static methods whereby 
	you pass in the dataoutputstream to be written to.
 * <br />
 * <br />
 * The message format is as follows:
 * [1 byte (char: e,c or n (entity, component, or named entity)][4 bytes: (int: id)][**(IF CHAR=N)** 32 bytes (String: name)]
 * [1 byte (ch... ...
 * until
 * [1 byte (char = c)][32 bytes (String: component type name)][4 bytes: (int: component id)]
 * [XXX Bytes: Message] This last needs to be constructed based upon the type of component.
 * 
 * @author Michael Nelson (300276118)
 *
 */
public class MessageBuilder {

	/**
	 * Add an entity to the outputstream
	 * @param os
	 * @param entID
	 */
	public static void appendEntity(DataOutputStream os, int entID){
		try {
			os.writeChar('e');
			os.writeInt(entID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Add a named entity to the outputstream
	 * @param os
	 * @param entID
	 */
	public static void appendNamedEntity(DataOutputStream os, int entID, String name){
		try {
			os.writeChar('n');
			os.writeInt(entID);
			os.writeUTF(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Add a component to the outputstream (this is a leaf node, so the message follows)
	 * @param os
	 * @param entID
	 */
	public static void appendComponent(DataOutputStream os, Component comp, int compID){
		try {
			os.writeChar('c');
			os.writeUTF(comp.getClass().getSimpleName());
			os.writeInt(compID);
			appendMessage(os, comp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method will create the message by getting all fields in the component and adding them to message builder
	 * This method is going to get quite large, but it will be ordered by pakcage name and then by class alphabetically (eclipse order),
	 * so it won't be a pain to maintain. 
	 * @param os
	 * @param comp
	 */
	public static void appendMessage(DataOutputStream os, Component comp){
		//get 
		try {
			String compType = comp.getClass().getSimpleName();
			switch(compType){
			
			//wolf3d.components
			case "Health":
				Health healthComp = (Health)comp;
				os.writeInt(healthComp.getHealth());
				os.writeInt(healthComp.getDamageAmt());
				break;
				
			//wolf3d.components.behaviours
				
			//wolf3d.components.behaviours.animations
				
			//wolf3d.components.renderers
				
			//wolf3d.components.sensors
				
			//engine.components
				
				
			}
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
