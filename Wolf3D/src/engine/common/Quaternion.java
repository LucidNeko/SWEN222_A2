package engine.common;

/**
 * 
 * @author Hamish
 *
 */
public class Quaternion {
	
	private float w;
	private float x;
	private float y;
	private float z;
	
	/** Construct a unit Quaternion representing no rotation. */
	public Quaternion() {
		x = y = z = 0;
		w = 1;
	}
	
	/**
	 * Construct a unit Quaternion defining the rotation about axis by theta radians.
	 * @param axis The axis to rotate around (UNIT VECTOR).
	 * @param theta The rotation amount in radians.
	 */
	public Quaternion(Vec3 axis, float theta) {
		theta = theta/2;
		this.w = Mathf.cos(theta);
		this.x = axis.x()*Mathf.sin(theta);
		this.y = axis.y()*Mathf.sin(theta);
		this.z = axis.z()*Mathf.sin(theta);
	}
	
	/** Construct a new Quaternion from the values. Private so it cannot be abused! */
	private Quaternion(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float w() { return w; }
	public float x() { return x; }
	public float y() { return y; }
	public float z() { return z; }
	
	/**
	 * Multiply this Quaternion by the other Quaternion.
	 * @param other The Quaternion to multiply by. this * other
	 * @return The resultion Quaternion.
	 */
	public Quaternion mul(Quaternion other) {
		return new Quaternion(this.w*other.w - this.x*other.x - this.y*other.y - this.z*other.z,
							  this.w*other.x + this.x*other.w + this.y*other.z - this.z*other.y,
							  this.w*other.y - this.x*other.z + this.y*other.w + this.z*other.x,
							  this.w*other.z + this.x*other.y - this.y*other.x + this.z*other.w);
	}
	
	public float magnitude() {
		return Mathf.sqrt(w*w + x*x + y*y + z*z);
	}
	
	public float magnitudeSquared() {
		return w*w + x*x + y*y + z*z;
	}
	
	/**
	 * Normalize this Quaternion and return the length before normalization.
	 * @return The length before the Quaternion was normalized.
	 */
	public float normalize() {
		float magnitudeSquared = magnitudeSquared();
		if(magnitudeSquared <= 0) return 0;
		float magnitude = Mathf.sqrt(magnitudeSquared);
		float delta = 1/magnitude;
		w *= delta;
		x *= delta;
		y *= delta;
		z *= delta;
		return magnitude;
	}
	
	/** Returns this Quaternion as a 4x4 rotation matrix. */
	public Mat44 toMatrix() {
		return new Mat44(this);
	}
	
	/**
	 * nlerps between a and b. t is clamped between 0..1.
	 * @return The resulting Quaternion.
	 */
	public static Quaternion nlerp(Quaternion a, Quaternion b, float t) {
		t = Mathf.clamp(t, 0, 1);
		return new Quaternion((1-t)*a.w + t*b.w,
							  (1-t)*a.x + t*b.x,
							  (1-t)*a.y + t*b.y,
							  (1-t)*a.z + t*b.z);
	}

	@Override
	public String toString() {
		return "Quaternion [w=" + w + ", x=" + x + ", y=" + y + ", z=" + z + "]";
	}

}
