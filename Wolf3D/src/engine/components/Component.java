package engine.components;

import java.util.Observable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import engine.core.Entity;

/**
 * A Component is put inside an Entity. A Component can only be in one Entity at a time.
 * @author Hamish
 */
public class Component extends Observable {
	protected static final Logger log = LogManager.getLogger();

	/** The Entity that this component is attached to - null if not attached to anything */
	private transient Entity owner;

	/**
	 * Sets the Entity that contains this component<br>
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
	 * Gets the Entity this Component is attached to.
	 * @return Returns the Entity that owns this Component - or null if not attached to anything.
	 */
	public Entity getOwner() {
		return owner;
	}

	/**
	 * Tests whether the Entity that owns this Component also owns an instance of each of the provided Component Classes.
	 * @param components The components you need on this Components owner Entity.
	 */
	@SafeVarargs
	protected final <E extends Component> boolean requires(Class<? extends E>... components) {
		boolean result = true;
		if(owner != null) {
			for(Class<? extends E> type : components) {
				if(owner.getComponent(type) == null) {
					log.error(this.getClass().getSimpleName() + " requires a " + type.getSimpleName() + " component to also be attached!");
					result = false;
				}
			}
		} else {
			log.error("Should " + this.getClass().getSimpleName() + " really not have an owner?");
			result = false;
		}
		return result;
	}

}
