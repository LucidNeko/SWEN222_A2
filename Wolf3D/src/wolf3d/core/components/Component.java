package wolf3d.core.components;

import java.util.Observable;

/**
 * A Component is put inside a ComponentContainer. A Component can only be in one container at a time.
 * @author Hamish Rae-Hodgson
 */
public class Component extends Observable {
	
	/** The Container that this Component is in - null if not in anything */
	private ComponentContainer owner;
	
	/**
	 * Sets the ComponentContainer that contains this component<br>
	 * INVERIENT: Must be contained in the provided owner AND must no longer be contained in the old owner.<br>
	 * INVERIENT: <br>a=oldOwner:null b=newOwner:nul c1=containedInOldOwner c2=containedInNewOwner<br>
	 * (~a -> ~c1)(~b -> c2)<br>
	 * Throws RuntimeException if: (~a^c1)v(~b^~c2)
	 * @param owner The owner of this Component
	 */
	public void setOwner(ComponentContainer owner) {
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
	public ComponentContainer getOwner() {
		return owner;
	}
	
}
