package engine.components;

import java.nio.FloatBuffer;

import static javax.media.opengl.GL2.*;

import javax.media.opengl.GL2;

import engine.common.Mathf;
import engine.common.Vec3;

/**
 * The Transform class represents a position and orientation in 3D space.
 * @author Hamish
 *
 */
public class Transform extends Component {

	private final Vec3 position;
	private final Vec3 up;
	private final Vec3 along;
	private final Vec3 look;

	/**
	 * Create a new Transform with position=(0,0,0) and zero rotation.
	 */
	public Transform() {
		position = new Vec3();
		up = new Vec3();
		along = new Vec3();
		look = new Vec3();
		reset(); //sets up vectors correctly
	}

	/**
	 * Resets this transform to (0,0,0) and zero rotation
	 */
	public void reset() {
		position.setZero();
		up.set(Vec3.UP);
		along.set(Vec3.RIGHT);
		look.set(Vec3.FORWARD);
		setChanged();
	}

	/**
	 * Sets this transform to match the source Transform.
	 * @param source The Transform to copy.
	 */
	public void set(Transform source) {
		set(source, true);
	}
	
	/** Sets this transform to the source transform, but only sets hasChanged if you tell it to. */
	public void set(Transform source, boolean setChanged) {
		this.position.set(source.position);
		this.up.set(source.up);
		this.along.set(source.along);
		this.look.set(source.look);
		if(setChanged) setChanged();
	}

	/**
	 * Sets the position of this Transform
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setPosition(float x, float y, float z) {
		this.position.set(x, y, z);
		setChanged();
	}

	/**
	 * Sets the position of this Transform
	 * @param position The (x, y, z) to set this Transforms position to.
	 */
	public void setPosition(Vec3 position) {
		this.position.set(position);
		setChanged();
	}

	/**
	 * Strafe this transform. i.e left<->right movement.
	 * @param delta Amount to move.
	 */
	public void strafe(float delta) {
		position.addLocal(along.mul(delta));
		setChanged();
	}

	/**
	 * Fly this transform. i.e up<->down movement.
	 * @param delta Amount to move.
	 */
	public void fly(float delta) {
		position.addLocal(up.mul(delta));
		setChanged();
	}

	/**
	 * Walk this transform. i.e in<->out movement.<br>
	 * @param delta Amount to move.
	 */
	public void walk(float delta) {
		position.addLocal(look.mul(delta));
		setChanged();
	}

	/**
	 * Rotates around the look vector (facing direction).
	 * @param theta Rotation amount in radians.
	 */
	public void roll(float theta) {
		up.mulLocal(Mathf.cos(theta)).addLocal(along.mul(Mathf.sin(theta)));
		up.normalize();
		along.set(Vec3.cross(up, look));
		setChanged();
	}

	/**
	 * Rotates around the along vector (left<->right).
	 * @param theta Rotation amount in radians.
	 */
	public void pitch(float theta) {
		look.mulLocal(Mathf.cos(theta)).addLocal(up.mul(Mathf.sin(theta)));
		look.normalize();
		up.set(Vec3.cross(look, along));
		setChanged();
	}

	/**
	 * Rotates around the up vector (up<->down)
	 * @param theta Rotation amount in radians.
	 */
	public void yaw(float theta) {
		along.mulLocal(Mathf.cos(theta)).addLocal(look.mul(Mathf.sin(theta)));
		along.normalize();
		look.set(Vec3.cross(along, up));
		setChanged();
	}

	/**
	 * Translate the position of this transform by (dx, dy, dz).
	 * @param dx The x translation.
	 * @param dy The y translation.
	 * @param dz The z translation.
	 */
	public void translate(float dx, float dy, float dz) {
		position.addLocal(dx, dy, dz);
		setChanged();
	}

	/**
	 * Walks forwards along the X/Z plane.
	 * @param delta Amount of movement.
	 */
	public void walkFlat(float delta) {
		Vec3 direction = new Vec3(look.x(), 0, look.z());
		direction.normalize(); //must normalize otherwise we don't end up with a unit vector.
		position.addLocal(direction.mulLocal(delta));
		setChanged();
	}

	/**
	 * Strafes along the X/Z plane
	 * @param delta Amount of movement.
	 */
	public void strafeFlat(float delta) {
		Vec3 direction = new Vec3(along.x(), 0, along.z());
		direction.normalize(); //must normalize otherwise we don't end up with a unit vector.
		position.addLocal(direction.mulLocal(delta));
		setChanged();
	}

	/**
	 * Rotates the Transform about the world up vector.
	 * @param theta Rotation amount in radians.
	 */
	public void rotateY(float theta) {
		//derived from the standard y axis rotation matrix.
		float cos = Mathf.cos(theta);
		float sin = Mathf.sin(theta);

		//perform the matrix-vector multiplication
		along.set(cos*along.x() - sin*along.z(), along.y(), sin*along.x() + cos*along.z());
		up.set(cos*up.x() - sin*up.z(), up.y(), sin*up.x() + cos*up.z());
		look.set(cos*look.x() - sin*look.z(), look.y(),  sin*look.x() + cos*look.z());
		setChanged();
	}

	/**
	 * Makes this transform look in the direction.<br>
	 * Ignores the y component of direction.<br>
	 * The resulting transform will be looking flat on the x/z plane and up is reset to world up.
	 * @param direction The direction to look in. Cannot be (0, 0, 0)
	 */
	public void lookInDirection(Vec3 direction) {
		if(direction.equals(Vec3.ZERO) || direction.equals(Vec3.UP) || direction.equals(Vec3.DOWN)) return;
		Vec3 dir = new Vec3(direction.x(), 0, direction.z()); //ignore y component
		dir.normalize(); //unit vector
		look.set(dir);
		up.set(Vec3.UP);
		along.set(Vec3.cross(up, look));
		setChanged();
	}

	/**
	 * Rotates the transform so that it's look vector points at the targets position.
	 * @param target Transform to point towards.
	 */
	public void lookAt(Transform target) {
		lookAt(target.getPosition());
	}

	/**
	 * Rotates the transform so that it's look vector points at the target point.
	 * @param Target Point to look at.
	 */
	public void lookAt(Vec3 target) {
		lookAt(target, Vec3.UP);
	}

	/**
	 * Rotates the transform so that it's look vector points at the target point.<br>
	 * Then rotate the transform so the up vector points at worldUp.
	 * @param Target Point to look at.
	 * @param worldUp Vector specifying upwards direction.
	 */
	public void lookAt(Vec3 target, Vec3 worldUp) {
		Vec3 eye = target.sub(position);
		if(eye.equals(Vec3.ZERO)) return; //(0,0,0) so get out.
		eye.normalize(); //eye is a unit vector pointing at the target.
		yaw(Vec3.dot(Vec3.cross(up, eye), look));
		pitch(Vec3.dot(Vec3.cross(along,  eye), look));
		roll(Vec3.dot(Vec3.cross(worldUp, up), eye));
		setChanged();
	}

	/**
	 * Get a Vec3 containing the Transforms current location. <br>
	 * It's a snapshot. Modifications to this don't affect the Transform.
	 * @return An Immutable Vec3 representing the Transforms current position.
	 */
	public Vec3 getPosition() {
		return position.clone();
	}

	/**
	 * Get a Vec3 containing the Transforms current up vector. <br>
	 * It's a snapshot. Modifications to this don't affect the Transform.
	 * @return An Immutable Vec3 representing the Transforms current up direction.
	 */
	public Vec3 getUp() {
		return up.clone();
	}

	/**
	 * Get a Vec3 containing the Transforms current along vector. <br>
	 * It's a snapshot. Modifications to this don't affect the Transform.
	 * @return An Immutable Vec3 representing the Transforms current along direction.
	 */
	public Vec3 getAlong() {
		return along.clone();
	}

	/**
	 * Get a Vec3 containing the Transforms current look vector. <br>
	 * It's a snapshot. Modifications to this don't affect the Transform.
	 * @return An Immutable Vec3 representing the Transforms current look direction.
	 */
	public Vec3 getLook() {
		return look.clone();
	}

	/**
	 * Gets this Transformation represented as a 4x4 column order matrix.
	 * @return The transformation matrix that this Transform represents.
	 */
	public float[] getMatrix() {
		return new float[] {
				//Column 1
				along.x(),
				along.y(),
				along.z(),
				0,

				//Column 2
				up.x(),
				up.y(),
				up.z(),
				0,

				//Column 3
				look.x(),
				look.y(),
				look.z(),
				0,

				//Column 4
				position.x(),
				position.y(),
				position.z(),
				1
		};
	}

	/**
	 * Applies the transformation to the MODELVIEW MATRIX that this Transform specifies.
	 * @param gl The OpenGL context.
	 */
	public void applyTransform(GL2 gl) {
		//Get the modelview matrix
		FloatBuffer buffer = FloatBuffer.allocate(16);
		gl.glGetFloatv(GL_MODELVIEW_MATRIX, buffer);
		float[] modelviewMatrix = buffer.array();

		//Build the transformation matrix this Transform specifies
		float[] transformationMatrix = getMatrix();

		//Multiply modelview x transformation
		float[] m = Mathf.multiplyMatrix(modelviewMatrix, transformationMatrix);

		//Load the matrix into OpenGL
		gl.glLoadMatrixf(m, 0);
	}

	@Override
	public String toString() {
		return "Transform [position=" + position + ", up=" + up + ", along="
				+ along + ", look=" + look + "]";
	}

	@Override
	public Transform clone() {
		Transform t = new Transform();
		t.along.set(this.along);
		t.up.set(this.up);
		t.look.set(this.look);
		t.position.set(this.position);
		return t;
	}
	
	//TODO: hack..
	public void clearChanged() {
		super.clearChanged();
	}

}
