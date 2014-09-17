package wolf3d.components;

import java.util.Observable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.core.Entity;

/**
 * A Component is put inside a ComponentContainer. A Component can only be in one container at a time.
 * @author Hamish Rae-Hodgson
 */
public class Component extends Observable {
	protected static final Logger log = LogManager.getLogger();
	
	/** The Container that this Component is in - null if not in anything */
	private Entity owner;
	
	/**
	 * Sets the ComponentContainer that contains this component<br>
	 * INVARIANT: Must be contained in the provided owner AND must no longer be contained in the old owner.<br>
	 * INVARIANT: <br>a=oldOwner:null b=newOwner:nul c1=containedInOldOwner c2=containedInNewOwner<br>
	 * (~a -> ~c1)(~b -> c2)<br>
	 * Throws RuntimeException if: (~a^c1)v(~b^~c2)
	 * @param owner The owner of this Component
	 */
	public void setOwner(Entity owner) {
		if(this.owner != owner) {
			if((this.owner != null && this.owner.contains(this)) || (owner != null &&  !owner.contains(this)))
				throw new RuntimeException("This component should only be in the new owner at this point");
			this.owner = owner;
			this.setChanged();
			this.notifyObservers();
		}
	}
	
	/** 
	 * Gets the Container this Component is in.
	 * @return Returns the Container that contains this Component - or null if not attached to anything.
	 */
	public Entity getOwner() {
		return owner;
	}
	
}
