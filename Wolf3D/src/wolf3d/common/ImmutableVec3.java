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
	
	public ImmutableVec3() {
		super();
	}
	
	public ImmutableVec3(float x, float y, float z) {
		super(x, y, z);
	}
	
	public ImmutableVec3(Vec3 source) {
		super(source);
	}
	
	//
	// SET
	//

	@Override
	public void set(Vec3 source) {
		throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
	}
	
	@Override
	public void set(float x, float y, float z) {
		throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
	}
	
	@Override
	public void setZero() {
		throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
	}
	
	//
	// ADDITION
	//
	
	@Override
	public Vec3 addLocal(Vec3 other) {
		throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
	}
	
	@Override
	public Vec3 addLocal(float x, float y, float z) {
		throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
	}
	
	//
	// SUBTRACTION
	//
	
	@Override
	public Vec3 subLocal(Vec3 other) {
		throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
	}
	
	@Override
	public Vec3 subLocal(float x, float y, float z) {
		throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
	}
	
	//
	// MULTIPLICATION
	//
	
	@Override
	public Vec3 mulLocal(float factor) {
		throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
	}
	
	//
	// DIVISION
	//
	
	@Override
	public Vec3 divLocal(float factor) {
		throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
	}
	
	//
	// UTIL
	//
	
	@Override
	public Vec3 negateLocal() {
		throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
	}
	
	@Override
	public Vec3 absLocal() {
		throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
	}	
	
	@Override
	public float normalize() {
		throw new UnsupportedOperationException("Cannot modify an immutable Vec3");
	}
	
}
