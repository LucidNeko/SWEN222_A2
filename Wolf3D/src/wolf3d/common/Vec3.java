package wolf3d.common;

/**
 * A class representing a 3-component Vector.
 * @author Hamish Rae-Hodgson
 *
 */
public class Vec3 {
	
	public static final ImmutableVec3 LEFT  = new ImmutableVec3(-1,  0,  0);
	public static final ImmutableVec3 RIGHT = new ImmutableVec3( 1,  0,  0);
	public static final ImmutableVec3 DOWN  = new ImmutableVec3( 0, -1,  0);
	public static final ImmutableVec3 UP    = new ImmutableVec3( 0,  1,  0);
	public static final ImmutableVec3 IN    = new ImmutableVec3( 0,  0, -1);
	public static final ImmutableVec3 OUT   = new ImmutableVec3( 0,  0,  1);
	
	private float x;
	private float y;
	private float z;
	
	//
	// CONSTRUCTORS
	//
	
	public Vec3() {
		x = y = z = 0;
	}
	
	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3(Vec3 source) {
		this.x = source.x;
		this.y = source.y;
		this.z = source.z;
	}
	
	//
	// GET
	//
	
	public float x() { return x; }
	public float y() { return y; }
	public float z() { return z; }
	
	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }
	
	//
	// SET
	//
	
	public void set(Vec3 source) {
		this.x = source.x;
		this.y = source.y;
		this.z = source.z;
	}
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setZero() {
		x = y = z = 0;
	}
	
	//
	// ADDITION
	//
	
	public Vec3 add(Vec3 other) {
		return new Vec3(this.x + other.x, 
						this.y + other.y, 
						this.z + other.z);
	}
	
	public Vec3 add(float x, float y, float z) {
		return new Vec3(this.x + x,
						this.y + y,
						this.z + z);
	}
	
	public Vec3 addLocal(Vec3 other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}
	
	public Vec3 addLocal(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	//
	// SUBTRACTION
	//
	
	public Vec3 sub(Vec3 other) {
		return new Vec3(this.x - other.x, 
						this.y - other.y, 
						this.z - other.z);
	}
	
	public Vec3 sub(float x, float y, float z) {
		return new Vec3(this.x - x,
						this.y - y,
						this.z - z);
	}
	
	public Vec3 subLocal(Vec3 other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		return this;
	}
	
	public Vec3 subLocal(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}
	
	//
	// MULTIPLICATION
	//
	
	public Vec3 mul(float factor) {
		return new Vec3(this.x * factor,
						this.y * factor,
						this.z * factor);
	}
	
	public Vec3 mulLocal(float factor) {
		this.x *= factor;
		this.y *= factor;
		this.z *= factor;
		return this;
	}
	
	//
	// DIVISION
	//
	
	public Vec3 div(float factor) {
		if(factor == 0) throw new IllegalArgumentException("Cannot divide by zero");
		float delta = 1/factor;
		return new Vec3(this.x * delta,
						this.y * delta,
						this.z * delta);
	}
	
	public Vec3 divLocal(float factor) {
		if(factor == 0) throw new IllegalArgumentException("Cannot divide by zero");
		float delta = 1/factor;
		this.x *= delta;
		this.y *= delta;
		this.z *= delta;
		return this;
	}
	
	//
	// UTIL
	//
	
	public Vec3 negate() {
		return new Vec3(-x, -y, -z);
	}
	
	public Vec3 negateLocal() {
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;
		return this;
	}
	
	public Vec3 abs() {
		return new Vec3(x < 0 ? -x : x,
						y < 0 ? -y : y,
						z < 0 ? -z : z);
	}
	
	public Vec3 absLocal() {
		this.x = x < 0 ? -x : x;
		this.y = y < 0 ? -y : y;
		this.z = z < 0 ? -z : z;
		return this;
	}
	
	public float length() {
		return (float) Math.sqrt(x*x + y*y+ z*z);
	}
	
	public float lengthSquared() {
		return x*x + y*y + z*z;
	}	
	
	/**
	 * <b><i>Modifies this Vector.</i></b><br><br>
	 * Normalizes this Vector and returns it's length before normalization.
	 * @return length of vector before normalization.
	 */
	public float normalize() {
		float lengthSquared = lengthSquared();
		if(lengthSquared <= 0) return 0;
		float length = (float) Math.sqrt(lengthSquared);
		this.mulLocal(1/length);
		return length;
	}
	
	public static Vec3 cross(Vec3 a, Vec3 b) {
		return new Vec3(a.y*b.z - a.z*b.y,
						a.z*b.x - a.x*b.z,
						a.x*b.y - a.y*b.x);
	}
	
	public static float dot(Vec3 a, Vec3 b) {
		return a.x*b.x + a.y*b.y + a.z*b.z;
	}
	
	/** Is this really in radians? */
	public static float angleInRadians(Vec3 a, Vec3 b) {
		return dot(a, b) / (a.length() * b.length());
	}
	
	public static float angleInDegrees(Vec3 a, Vec3 b) {
		return (float) Math.acos(angleInRadians(a, b));
	}
	
	
	@Override
	public Vec3 clone() {
		return new Vec3(x, y, z);
	}
	
	@Override
	public String toString() {
		return String.format("(%.2f,  %.2f, %.2f)", x, y, z);
	}
	

}
