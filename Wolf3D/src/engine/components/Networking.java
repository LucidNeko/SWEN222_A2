package engine.components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.core.Entity;

/**
 * Networking component.
 *
 * Any entity which has stuff it needs to transport over the network will have one of these.
 * It will check for changes from networkable components belonging to the entity owner (i.e., it will check it's Transform component, etc.)
 * It has methods to append those changes to an OutputDataStream, and read in from an InputDataStream, for passing over the network.
 * @author nelsonmich3
 *
 */
public class Networking extends Component{
	private static List<Networking> netList = new ArrayList<Networking>();

	public Networking(){
		addThisToNetworkingComponentsList();
	}

	/**
	 * This method might not be needed...
	 * TODO
	 * @return
	 */
	public static List<Networking> getAllNetworkingComponents(){
		return netList;
	}

	/**
	 * This method is auto-called when a new networking component is created.
	 */
	public void addThisToNetworkingComponentsList(){
		netList.add(this);
	}

	public void removeThisFromNetworkingComponentsList(){
		netList.remove(this);
	}

	/**
	 * Change this method's name...
	 *
	 * Returns null if no message recipient.
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static Networking getTheNetworkingComponentWhichThisMessageIsIntendedFor(DataInputStream is) throws IOException{
		int entID = is.readInt();
		for(Networking nc : netList){
			if(nc.getOwner().getID() == entID){
				return nc;
			}
		}
		return null;
	}

	/**
	 * Message format:::
	 *
	 * [int - owner id][][][][][]
	 *
	 * @param os
	 * @throws IOException
	 */
	public void writeChanges(DataOutputStream os) throws IOException{
		Entity owner = this.getOwner();
		os.writeInt(owner.getID());
		for(Component c : owner.getComponents(Component.class)){
				if(c.hasChanged()){

				}
		}
	}

	/**
	 * The ID of the entity has already been read.
	 * This will change values of some components owned by the Entity who owns this Networking component.
	 * @param is
	 */
	public void readAndEnactChanges(DataInputStream is){
		Entity owner = this.getOwner();

	}
}
