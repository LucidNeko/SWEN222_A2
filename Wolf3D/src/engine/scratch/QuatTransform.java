package engine.scratch;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import engine.common.Mathf;
import engine.common.Quaternion;
import engine.common.Vec3;
import engine.components.Component;

public class QuatTransform extends Component implements Iterable<QuatTransform> {
	private static final Logger log = LogManager.getLogger();
	
	public enum Space { LOCAL, WORLD };
	
	private boolean hasChanged = true;
	
	private QuatTransform parent = null;
	private List<QuatTransform> children = new LinkedList<QuatTransform>();
	
	private Vec3 localPosition = new Vec3();
	private Quaternion localRotation = new Quaternion();
	
	private Vec3 position = new Vec3();
	private Quaternion rotation = new Quaternion();
	
	public QuatTransform() { }
	
	public Vec3 localPosition() {
		return localPosition.clone();
	}
	
	public Quaternion localRotation() {
		return localRotation.clone();
	}
	
	public Vec3 position() {
		if(hasChanged()) 
			recalculate();
		return position.clone();
	}
	
	public Quaternion rotation() {
		if(hasChanged()) 
			recalculate();
		return rotation.clone();
	}
	
	public Vec3 forward() {
		if(hasChanged()) 
			recalculate();
		return rotation.mul(Vec3.FORWARD);
	}
	
	public Vec3 along() {
		if(hasChanged()) 
			recalculate();
		return rotation.mul(Vec3.RIGHT);
	}
	
	public Vec3 up() {
		if(hasChanged())
			recalculate();
		return rotation.mul(Vec3.UP);
	}
	
	public QuatTransform root() {
		if(parent != null)
			return parent.root();
		return this;
	}
	
	public void detachChildren() {
		if(children.size() == 0) return;
		
		for(QuatTransform child : this) {
			child.parent = null;
			child.setChanged();
		}
		
		children.clear();
	}
	
	/**
	 * Translate this transform by translation.<br>
	 * If space is Space.LOCAL then the translation happens in local space (strafe/walk/fly)<br>
	 * If space is Space.WORLD then the translation happens in world space. <br>
	 * @param Translation The amount to translate
	 * @param Space Relative space to translate to.
	 */
	public void translate(Vec3 translation, Space space) {
		translate(translation.x(), translation.y(), translation.z(), space);
	}
	
	/**
	 * Translate this transform by translation.<br>
	 * If space is Space.LOCAL then the translation happens in local space (strafe/walk/fly)<br>
	 * If space is Space.WORLD then the translation happens in world space. <br>
	 * @param dx Translation amount along x.
	 * @param dy Translation amount along y.
	 * @param dz Translation amount along z.
	 * @param space Space Relative space to translate to.
	 */
	public void translate(float dx, float dy, float dz, Space space) {
		switch(space) {
		case LOCAL :
			localPosition.addLocal(forward().mul(dz));
			localPosition.addLocal(along().mul(dx));
			localPosition.addLocal(up().mul(dy));
			setChanged();
			break;
		case WORLD :
			localPosition.addLocal(dx, dy, dz);
			setChanged();
			break;
		}
	}
	
	/**
	 * TODO: TEST ME.
	 * @param axis
	 * @param thetaRadians
	 */
	public void rotate(Vec3 axis, float thetaRadians, Space space) {
		if(hasChanged())
			recalculate();
		
		Quaternion q;
		switch(space) {
		case LOCAL :
			q = Quaternion.createRotation(axis, thetaRadians);
			localRotation.mulLocal(q);
			setChanged();
			break;
		case WORLD :
			q = Quaternion.createRotation(rotation.mul(axis), thetaRadians);
			localRotation.mulLocal(q);
			setChanged();
			break;
		}
		
	}

	/** Tests if this object has changed. */
	public boolean hasChanged() {
		return hasChanged;
	}
	
	/** 
	 * Marks this Transform as having been changed; 
	 * The hasChanged method will now return true.
	 * Recurses down through the children setting their flag too. 
	 * */
	public void setChanged() {
		hasChanged = true;
		for(QuatTransform child : this)
			child.setChanged();
	}
	
	/** Indicates that this Transform has no longer changed. So that the hasChanged method will now return false. */
	public void clearChanged() {
		hasChanged = false;
	}
	
	private void recalculate() {
		if(parent != null)
			parent.recalculate(); //first we climb to the top.
		
		if(!hasChanged())
			return;
		
		if(parent != null) {
			rotation.set(parent.rotation.mul(this.localRotation));
			position.set(parent.position.add(this.localPosition));
			setChanged(); //set all children
		} else {
			rotation.set(localRotation);
			position.set(localPosition);
			setChanged(); //set all children
		}
		
		clearChanged(); //clear for self but all children still changed.
	}
	
	@Override
	public Iterator<QuatTransform> iterator() {
		return children.iterator();
	}
	
	@Override
	public String toString() {
		if(hasChanged()) recalculate();
		return "QuatTransform [position=" + position + ", rotation=" + rotation
				+ ", localPosition=" + localPosition + ", localRotation="
				+ localRotation + "]";
	}

	public static void main(String[] args) {		
		QuatTransform a = new QuatTransform();
		QuatTransform b = new QuatTransform();
		a.children.add(b);
		b.parent = a;
		
		log.trace("a={}", a);
		log.trace("b={}", b);
		
		a.rotate(Vec3.UP, Mathf.degToRad(90), Space.WORLD);
		b.translate(1, 0, 0, Space.LOCAL);
		
		log.trace("a={}", a);
		log.trace("b={}", b);
		

		b.translate(-1, 0, 0, Space.LOCAL);
		a.rotate(Vec3.UP, Mathf.degToRad(-90), Space.WORLD);
		
		log.trace("a={}", a);
		log.trace("b={}", b);
		
		a.rotate(Vec3.UP, Mathf.degToRad(90), Space.WORLD);
		
		log.trace("a.forward={} up={}", a.forward(), a.up());
		log.trace("b.forward={} up={}", b.forward(), b.up());
		
//		b.rotate(Vec3.RIGHT, Mathf.degToRad(90), Space.WORLD);
		b.rotate(Vec3.RIGHT, Mathf.degToRad(90), Space.LOCAL);
		
		log.trace("a.forward={} up={}", a.forward(), a.up());
		log.trace("b.forward={} up={}", b.forward(), b.up());
		
	}
	
}
