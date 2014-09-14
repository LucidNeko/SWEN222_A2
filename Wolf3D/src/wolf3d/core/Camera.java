package wolf3d.core;

import static javax.media.opengl.GL2.GL_MODELVIEW_MATRIX;

import java.nio.FloatBuffer;

import javax.media.opengl.GL2;

import wolf3d.common.ImmutableVec3;
import wolf3d.common.Mathf;
import wolf3d.common.Vec3;
import wolf3d.components.Component;
import wolf3d.components.Transform;

/**
 * The Camera is a camera in 3D space used as the inital transformation when rendering a scene.
 * @author Hamish Rae-Hodgson
 *
 */
public class Camera extends Component {
	
	private final Vec3 position;
	private final Vec3 up;
	private final Vec3 along;
	private final Vec3 look;
	
	/**
	 * Create a new Transform with position=(0,0,0) and zero rotation.
	 */
	public Camera() { 
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
		look.set(Vec3.BACK); //changed from forward
	}
	
	/**
	 * Strafe this transform. i.e left<->right movement.
	 * @param delta Amount to move.
	 */
	public void strafe(float delta) {
		position.addLocal(along.mul(delta));
	}
	
	/**
	 * Fly this transform. i.e up<->down movement.
	 * @param delta Amount to move.
	 */
	public void fly(float delta) {
		position.addLocal(up.mul(delta));
	}
	
	/**
	 * Walk this transform. i.e in<->out movement.<br>
	 * @param delta Amount to move.
	 */
	public void walk(float delta) {
		position.addLocal(look.mul(delta));
	}
	
	/**
	 * Rotates around the look vector (facing direction).
	 * @param theta Rotation amount in radians.
	 */
	public void roll(float theta) {
		up.mulLocal(Mathf.cos(theta)).addLocal(along.mul(Mathf.sin(theta)));
		up.normalize();
		along.set(Vec3.cross(up, look));
		along.negateLocal();
	}
	
	/**
	 * Rotates around the along vector (left<->right).
	 * @param theta Rotation amount in radians.
	 */
	public void pitch(float theta) {
		look.mulLocal(Mathf.cos(theta)).addLocal(up.mul(Mathf.sin(theta)));
		look.normalize();
		up.set(Vec3.cross(look, along));
		up.negateLocal();
	}
	
	/**
	 * Rotates around the up vector (up<->down)
	 * @param theta Rotation amount in radians.
	 */
	public void yaw(float theta) {
		along.mulLocal(Mathf.cos(theta)).addLocal(look.mul(Mathf.sin(theta)));
		along.normalize();		
		look.set(Vec3.cross(along, up));
		look.negateLocal();
	}
	
	/**
	 * Translate the position of this transform by (dx, dy, dz).
	 * @param dx The x translation.
	 * @param dy The y translation.
	 * @param dz The z translation.
	 */
	public void translate(float dx, float dy, float dz) {
		position.addLocal(dx, dy, dz);
	}
	
	/**
	 * Get a Vec3 containing the Transforms current location. It's just a snapshot - so it's Immutable.
	 * @return An Immutable Vec3 representing the Transforms current position.
	 */
	public ImmutableVec3 getPosition() {
		return new ImmutableVec3(position);
	}
	
	/** 
	 * Get a Vec3 containing the Transforms current up vector. It's just a snapshot - so it's Immutable.
	 * @return An Immutable Vec3 representing the Transforms current up direction.
	 */
	public ImmutableVec3 getUp() {
		return new ImmutableVec3(up);
	}
	
	/**
	 * Get a Vec3 containing the Transforms current along vector. It's just a snapshot - so it's Immutable.
	 * @return An Immutable Vec3 representing the Transforms current along direction.
	 */
	public ImmutableVec3 getAlong() {
		return new ImmutableVec3(along);
	}
	
	/**
	 * Get a Vec3 containing the Transforms current look vector. It's just a snapshot - so it's Immutable.
	 * @return An Immutable Vec3 representing the Transforms current look direction.
	 */
	public ImmutableVec3 getLook() {
		return new ImmutableVec3(look);
	}
	
	/**
	 * Gets this Transformation represented as a 4x4 column order matrix.
	 * @return The transformation matrix that this Transform represents.
	 */
	public float[] getMatrix() {
		return new float[] {
				along.x(),
				up.x(),
				-look.x(),
				0,
				
				//Column 2
				along.y(),
				up.y(),
				-look.y(),
				0,
				
				//Column 3
				along.z(),
				up.z(),
				-look.z(),
				0,
				
				//Column 4
				-Vec3.dot(along, position),
				-Vec3.dot(up, position),
				Vec3.dot(look, position),
				1
		};
	}

	/**
	 * Applies the transformation to the MODELVIEW MATRIX that this Transform specifies.
	 * @param gl The OpenGL context.
	 */
	public void setActive(GL2 gl) {	
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

}
