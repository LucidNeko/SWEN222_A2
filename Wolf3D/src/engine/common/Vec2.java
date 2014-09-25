package engine.common;

/**
 * Vec2 is a class representing a 2 component Vector. (x, y)
 * @author Hamish
 *
 */
public class Vec2 {
	
	private float x;
	private float y;
	
	/** Construct a new vector with (x, y) = (0, 0) */
	public Vec2() {
		x = y = 0;
	}
	
	/** Construct a new vector at (x, y) */
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/** Construct a new vector with (x, y) = (source.x, source.y) */
	public Vec2(Vec2 source) {
		this.x = source.x;
		this.y = source.y;
	}
	
	/** Get the x component of this vector */
	public float x() {
		return x;
	}
	
	/** Get the y component of this vector */
	public float y() {
		return y;
	}
	
	/** Get the x component of this vector */
	public float getX() {
		return x;
	}
	
	/** Get the y component of this vector */
	public float getY() {
		return y;
	}
	
	public String toString() {
		return String.format("(%.2f,  %.2f)", x, y);
	}
	
	public Vec2 clone() {
		return new Vec2(x, y);
	}

}
