package wolf3d.common;

/**
 * A wrapper class over Vec3 which disables all the functions that could modify the Vector.
 * @author Hamish Rae-Hodgson
 *
 */
public class ImmutableVec3 extends Vec3 {
	
	//
	// CONSTRUCTORS
	//
	
	/**
	 * Construct a new vector with (x, y, z) = (0, 0, 0).
	 */
	public ImmutableVec3() {
		super();
	}
	
	/**
	 * Construct a new vector at (x, y, z).
	 * @param x The x component of the vector.
	 * @param y The y component of the vector.
	 * @param z The z component of the vector.
	 */
	public ImmutableVec3(float x, float y, float z) {
		super(x, y, z);
	}
	
	/**
	 * Construct a new vector such that (x, y, z) = (source.x, source.y, source.z).
	 * @param source The vector to copy (x, y, z) from.
	 */
	public ImmutableVec3(Vec3 source) {
		super(source);
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
