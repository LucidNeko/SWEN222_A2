package engine.common;

/**
 * A class representing a 3-component Vector.
 * @author Hamish
 *
 */
public class Vec3 {
	
	/** The world left vector */
	public static final Vec3 LEFT    = new UnmodifiableVec3(-1,  0,  0);
	
	/** The world right vector */
	public static final Vec3 RIGHT   = new UnmodifiableVec3( 1,  0,  0);
	
	/** The world down vector */
	public static final Vec3 DOWN    = new UnmodifiableVec3( 0, -1,  0);
	
	/** The world up vector */
	public static final Vec3 UP      = new UnmodifiableVec3( 0,  1,  0);
	
	/** The world back vector */
	public static final Vec3 BACK    = new UnmodifiableVec3( 0,  0, -1);
	
	/** The world forward vector */
	public static final Vec3 FORWARD = new UnmodifiableVec3( 0,  0,  1);
	
	/** The zero vector */
	public static final Vec3 ZERO    = new UnmodifiableVec3( 0,  0,  0);
	
	private float x;
	private float y;
	private float z;
	
	//
	// CONSTRUCTORS
	//
	
	/**
	 * Construct a new vector with (x, y, z) = (0, 0, 0).
	 */
	public Vec3() {
		x = y = z = 0;
	}
	
	/**
	 * Construct a new vector at (x, y, z).
	 * @param x The x component of the vector.
	 * @param y The y component of the vector.
	 * @param z The z component of the vector.
	 */
	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Construct a new vector such that (x, y, z) = (source.x, source.y, source.z).
	 * @param source The vector to copy (x, y, z) from.
	 */
	public Vec3(Vec3 source) {
		this.x = source.x;
		this.y = source.y;
		this.z = source.z;
	}
	
	//
	// GET
	//
	
	/**
	 * Get the x component of this vector.
	 * @return x.
	 */
	public float x() { return x; }
	
	/**
	 * Get the y component of this vector.
	 * @return y.
	 */
	public float y() { return y; }
	
	/**
	 * Get the z component of this vector.
	 * @return z.
	 */
	public float z() { return z; }
	
	/**
	 * Get the x component of this vector.
	 * @return x.
	 */
	public float getX() { return x; }
	
	/**
	 * Get the y component of this vector.
	 * @return y.
	 */
	public float getY() { return y; }
	
	/**
	 * Get the z component of this vector.
	 * @return z.
	 */
	public float getZ() { return z; }
	
	//
	// SET
	//
	
	/**
	 * Sets this vector such that (x, y, z) = (source.x, source.y, source.z).
	 * @param source The vector to copy (x, y, z) from.
	 */
	public void set(Vec3 source) {
		this.x = source.x;
		this.y = source.y;
		this.z = source.z;
	}
	
	/**
	 * Sets this vector to be (x, y, z)
	 * @param x The new x component of the vector.
	 * @param y The new y component of the vector.
	 * @param z The new z component of the vector.
	 */
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Sets this Vector such that (x, y, z) = (0, 0, 0).
	 */
	public void setZero() {
		x = y = z = 0;
	}
	
	//
	// ADDITION
	//
	
	/**
	 * Returns a new vector containing this + other.<br>
	 * DOES NOT MODIFY LOCALLY.
	 * @param other The vector to add
	 * @return The resulting vector.
	 */
	public Vec3 add(Vec3 other) {
		return new Vec3(this.x + other.x, 
						this.y + other.y, 
						this.z + other.z);
	}
	
	/**
	 * Returns a new vector containing this + (x, y, z).<br>
	 * DOES NOT MODIFY LOCALLY.
	 * @param x
	 * @param y
	 * @param z
	 * @return The resulting vector.
	 */
	public Vec3 add(float x, float y, float z) {
		return new Vec3(this.x + x,
						this.y + y,
						this.z + z);
	}
	
	/**
	 * Sets this vector to be this + other.<br>
	 * MODIFIES LOCALLY.
	 * @param other The vector to add.
	 * @return This vector.
	 */
	public Vec3 addLocal(Vec3 other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}
	
	/**
	 * Sets this vector to be this + (x, y, z).<br>
	 * MODIFIES LOCALLY.
	 * @param x
	 * @param y
	 * @param z
	 * @return This vewctor.
	 */
	public Vec3 addLocal(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	//
	// SUBTRACTION
	//
	
	/**
	 * Returns a new vector containing this - other.<br>
	 * DOES NOT MODIFY LOCALLY.
	 * @param other The vector to subtract
	 * @return The resulting vector.
	 */
	public Vec3 sub(Vec3 other) {
		return new Vec3(this.x - other.x, 
						this.y - other.y, 
						this.z - other.z);
	}
	
	/**
	 * Returns a new vector containing this - (x, y, z).<br>
	 * DOES NOT MODIFY LOCALLY.
	 * @param x
	 * @param y
	 * @param z
	 * @return The resulting vector.
	 */
	public Vec3 sub(float x, float y, float z) {
		return new Vec3(this.x - x,
						this.y - y,
						this.z - z);
	}
	
	/**
	 * Sets this vector to be this - other.<br>
	 * MODIFIES LOCALLY.
	 * @param other The vector to subtract
	 * @return This vector.
	 */
	public Vec3 subLocal(Vec3 other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		return this;
	}
	
	/**
	 * Sets this vector to be this - (x, y, z).<br>
	 * MODIFIES LOCALLY.
	 * @param x
	 * @param y
	 * @param z
	 * @return This vector.
	 */
	public Vec3 subLocal(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}
	
	//
	// MULTIPLICATION
	//
	
	/**
	 * Returns a new vector containing this * factor.<br>
	 * DOES NOT MODIFY LOCALLY.
	 * @param factor The factor to multiply by.
	 * @return The resulting vector.
	 */
	public Vec3 mul(float factor) {
		return new Vec3(this.x * factor,
						this.y * factor,
						this.z * factor);
	}
	
	/**
	 * Sets this vector to be this * factor.<br>
	 * MODIFIES LOCALLY
	 * @param factor The factor to multiply by.
	 * @return This Vector.
	 */
	public Vec3 mulLocal(float factor) {
		this.x *= factor;
		this.y *= factor;
		this.z *= factor;
		return this;
	}
	
	//
	// DIVISION
	//
	
	/**
	 * Returns a new vector containing this / factor.<br>
	 * DOES NOT MODIFY LOCALLY.
	 * @param factor The factor to divide by.
	 * @throws IllegalArgumentException If factor is 0.
	 * @return The resulting vector.
	 */
	public Vec3 div(float factor) {
		if(factor == 0) throw new IllegalArgumentException("Cannot divide by zero");
		float delta = 1/factor;
		return new Vec3(this.x * delta,
						this.y * delta,
						this.z * delta);
	}
	
	/**
	 * Sets this vector to be this / factor<br>
	 * MODIFIES LOCALLY.
	 * @param factor The factor to divide by.
	 * @throws IllegalArgumentException If factor is 0.
	 * @return This vector.
	 */
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
	
	/**
	 * Returns a new vector such that (x, y, z) = (-x, -y, -z).<br>
	 * DOES NOT MODIFY LOCALLY.
	 * @return The resulting vector.
	 */
	public Vec3 negate() {
		return new Vec3(-x, -y, -z);
	}
	
	/**
	 * Sets this vector such that (x, y, z) = (-x, -y, -z).<br>
	 * MODIFIES LOCALLY.
	 * @return This vector.
	 */
	public Vec3 negateLocal() {
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;
		return this;
	}
	
	/**
	 * Returns a new vector containing (x, y, z) = (|x|, |y|, |z|).<br>
	 * DOES NOT MODIFY LOCALLY.
	 * @return The resulting vector.
	 */
	public Vec3 abs() {
		return new Vec3(x < 0 ? -x : x,
						y < 0 ? -y : y,
						z < 0 ? -z : z);
	}
	
	/**
	 * Sets this vector to be (|x|, |y|, |z|).<br>
	 * MODIFIES LOCALLY.
	 * @return This vector.
	 */
	public Vec3 absLocal() {
		this.x = x < 0 ? -x : x;
		this.y = y < 0 ? -y : y;
		this.z = z < 0 ? -z : z;
		return this;
	}
	
	/**
	 * Returns the magnitude of this vector.
	 * @return The magnitude.
	 */
	public float length() {
		return (float) Math.sqrt(x*x + y*y+ z*z);
	}
	
	/**
	 * Return the squared magnitude of this vector.
	 * @return The magnitude ^2
	 */
	public float lengthSquared() {
		return x*x + y*y + z*z;
	}	
	
	/**
	 * Normalizes this Vector and returns it's length before normalization.<br>
	 * MODIFIES LOCALLY
	 * @return length of vector before normalization.
	 */
	public float normalize() {
		float lengthSquared = lengthSquared();
		if(lengthSquared <= 0) return 0;
		float length = (float) Math.sqrt(lengthSquared);
		this.mulLocal(1/length);
		return length;
	}
	
	/**
	 * Returns the cross product of a x b.<br>
	 * DOES NOT MODIFY EITHER VECTOR.
	 * @param a Vector a.
	 * @param b Vector b.
	 * @return The resulting vector.
	 */
	public static Vec3 cross(Vec3 a, Vec3 b) {
		return new Vec3(a.y*b.z - a.z*b.y,
						a.z*b.x - a.x*b.z,
						a.x*b.y - a.y*b.x);
	}
	
	/**
	 * Returns the dot product of a . b<br>
	 * DOES NOT MODIFY EITHER VECTOR.
	 * @param a Vector a.
	 * @param b Vector b.
	 * @return The result.
	 */
	public static float dot(Vec3 a, Vec3 b) {
		return a.x*b.x + a.y*b.y + a.z*b.z;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vec3 other = (Vec3) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		return true;
	}

	@Override
	public Vec3 clone() {
		return new Vec3(x, y, z);
	}
	
	@Override
	public String toString() {
		return String.format("(%.2f,  %.2f, %.2f)", x, y, z);
	}
	
	/** Returns an unmodifiable view of the vector. TODO, actually make a view instead of a copy. */
	public static Vec3 unmodifiableVec3(Vec3 vector) {
		return new UnmodifiableVec3(vector);
	}
	
	private static class UnmodifiableVec3 extends Vec3 {
		UnmodifiableVec3(Vec3 vector) {
			super(vector);
		}
		
		UnmodifiableVec3(float x, float y, float z) {
			super(x, y, z);
		}
		
		//
		// SET
		//

		/** Throws unsupported operation exception. */
		@Override
		public void set(Vec3 source) {
			throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
		}
		
		/** Throws unsupported operation exception. */
		@Override
		public void set(float x, float y, float z) {
			throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
		}
		
		/** Throws unsupported operation exception. */
		@Override
		public void setZero() {
			throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
		}
		
		//
		// ADDITION
		//
		
		/** Throws unsupported operation exception. */
		@Override
		public Vec3 addLocal(Vec3 other) {
			throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
		}
		
		/** Throws unsupported operation exception. */
		@Override
		public Vec3 addLocal(float x, float y, float z) {
			throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
		}
		
		//
		// SUBTRACTION
		//
		
		/** Throws unsupported operation exception. */
		@Override
		public Vec3 subLocal(Vec3 other) {
			throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
		}
		
		/** Throws unsupported operation exception. */
		@Override
		public Vec3 subLocal(float x, float y, float z) {
			throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
		}
		
		//
		// MULTIPLICATION
		//
		
		/** Throws unsupported operation exception. */
		@Override
		public Vec3 mulLocal(float factor) {
			throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
		}
		
		//
		// DIVISION
		//
		
		/** Throws unsupported operation exception. */
		@Override
		public Vec3 divLocal(float factor) {
			throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
		}
		
		//
		// UTIL
		//
		
		/** Throws unsupported operation exception. */
		@Override
		public Vec3 negateLocal() {
			throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
		}
		
		/** Throws unsupported operation exception. */
		@Override
		public Vec3 absLocal() {
			throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
		}	
		
		/** Throws unsupported operation exception. */
		@Override
		public float normalize() {
			throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
		}
	}

}
