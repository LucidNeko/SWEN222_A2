package engine.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Hamish
 *
 */
public class Quaternion {
	private static final Logger log = LogManager.getLogger();
	
	private float w;
	private float x;
	private float y;
	private float z;
	
	/** Construct a Quaternion initialized to the identity Quaternion. */
	public Quaternion() {
		setIdentity();
	}
	
	/** Construct a new Quaternion from the values. */
	public Quaternion(float w, float x, float y, float z) {
		set(w, x, y, z);
	}	
	
	public float w() { return w; }
	public float x() { return x; }
	public float y() { return y; }
	public float z() { return z; }
	
	public void set(Quaternion source) {
		this.w = source.w;
		this.x = source.x;
		this.y = source.y;
		this.z = source.z;
	}
	
	public void set(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setIdentity() {
		x = y = z = 0;
		w = 1;
	}
	
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
	
	public Quaternion mulLocal(Quaternion other) {
		float w = this.w*other.w - this.x*other.x - this.y*other.y - this.z*other.z;
		float x = this.w*other.x + this.x*other.w + this.y*other.z - this.z*other.y;
		float y = this.w*other.y - this.x*other.z + this.y*other.w + this.z*other.x;
		float z = this.w*other.z + this.x*other.y - this.y*other.x + this.z*other.w;
		set(w, x, y, z);
		return this;
	}
	
	/**
	 * Multiplies the given UNIT vector by this quaternion. this * v
	 * @param v The UNIT vector.
	 * @return A new vector containing the result.
	 */
	public Vec3 mul(Vec3 v) {
		Vec3 u = new Vec3(x, y, z);
		float s = w;
		
		float dotUV = Vec3.dot(u, v);
		float dotUU = Vec3.dot(u, u);
		return u.mul(dotUV+dotUV).addLocal(v.mul(s*s - dotUU)).addLocal(Vec3.cross(u, v).mul(s+s));
	}
	
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}
	
	public Quaternion conjugateLocal() {
		x = -x;
		y = -y;
		z = -z;
		return this;
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
		float invMag = 1/magnitude;
		w *= invMag;
		x *= invMag;
		y *= invMag;
		z *= invMag;
		return magnitude;
	}
	
//	/** Returns this Quaternion as a 4x4 rotation matrix. */
//	public Mat44 toMatrix() {
//		return new Mat44(this);
//	}
	
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
	
	/**
	 * q*v*(q's conjugate)
	 * Seems to drift way faster than other mul (mul2)
	 * @param q Rotation Quaternion
	 * @return The rotated vector. Does not modify param.
	 */
	public static Vec3 mul(Quaternion q, Vec3 v) {
		Quaternion V = new Quaternion(0, v.x(), v.y(), v.z());
		Quaternion R = q.mul(V).mul(q.conjugate());
		return new Vec3(R.x, R.y, R.z);
	}
	
	/**
	 * Efficient mul. Seemingly no drift. Twice as fast and no need to normalize v. 
	 * http://gamedev.stackexchange.com/a/50545
	 * @param q
	 * @param v
	 * @return
	 */
	public static Vec3 mul2(Quaternion q, Vec3 v) {
		Vec3 u = new Vec3(q.x, q.y, q.z);
		float s = q.w;
		
		float dotUV = Vec3.dot(u, v);
		float dotUU = Vec3.dot(u, u);
		return u.mul(dotUV+dotUV).addLocal(v.mul(s*s - dotUU)).addLocal(Vec3.cross(u, v).mul(s+s));
	}
	
	public static Quaternion createRotation(Vec3 axis, float thetaRadians) {
		return createRotation(thetaRadians, axis.x(), axis.y(), axis.z());
	}
	
	/**
	 * Create a new Quaternion of theta/2 radians about the angle defined by the UNIT vector (x, y, z)
	 * @param theta The angle of rotation in radians.
	 * @param x Axis x component.
	 * @param y Axis y component.
	 * @param z Axis z component.
	 * @return The Quaternion.
	 */
	public static Quaternion createRotation(float theta, float x, float y, float z) {
		float halfTheta = theta/2;
		return new Quaternion(Mathf.cos(halfTheta), 
							x*Mathf.sin(halfTheta), 
							y*Mathf.sin(halfTheta), 
							z*Mathf.sin(halfTheta));
	}

	@Override
	public String toString() {
		return "Quaternion [w=" + w + ", x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	public Quaternion clone() {
		return new Quaternion(w, x, y, z);
	}

}
