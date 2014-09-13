package wolf3d.core.components;

import java.nio.FloatBuffer;

import static javax.media.opengl.GL.*;
import static javax.media.opengl.GL2.*;

import javax.media.opengl.GL2;

import wolf3d.common.ImmutableVec3;
import wolf3d.common.Mathf;
import wolf3d.common.Vec3;

/**
 * The Transform class represents a position and orientation in 3D space.
 * @author Hamish Rae-Hodgson
 *
 */
public class Transform extends Component {
	
	private final Vec3 position = new Vec3();
	private final Vec3 up = new Vec3(Vec3.UP);
	private final Vec3 along = new Vec3(Vec3.RIGHT);
	private final Vec3 look = new Vec3(Vec3.OUT);
	
	/**
	 * Create a new Transform with position=(0,0,0) and zero rotation.
	 */
	public Transform() { }
	
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
		up.mulLocal(Mathf.cos(theta)).subLocal(along.mul(Mathf.sin(theta)));
		up.normalize();
		along.set(Vec3.cross(look, up));
		along.negateLocal();
//		log.trace("along={}", along);
	}
	
	/**
	 * Rotates around the along vector (left<->right).
	 * @param theta Rotation amount in radians.
	 */
	public void pitch(float theta) {
		look.mulLocal(Mathf.cos(theta)).addLocal(up.mul(Mathf.sin(theta)));
		look.normalize();
		up.set(Vec3.cross(look, along));
//		log.trace("up={}", up);
	}
	
	/**
	 * Rotates around the up vector (up<->down)
	 * @param theta Rotation amount in radians.
	 */
	public void yaw(float theta) {
		along.mulLocal(Mathf.cos(theta)).addLocal(look.mul(Mathf.sin(theta)));
		along.normalize();
		look.set(Vec3.cross(along, up));
//		log.trace("look={}", look);
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
	 * Applies the transformation to the MODELVIEW MATRIX that this Transform specifies.
	 * @param gl The OpenGL context.
	 */
	public void applyTransform(GL2 gl) {
		//Get the modelview matrix
		FloatBuffer buffer = FloatBuffer.allocate(16);
		gl.glGetFloatv(GL_MODELVIEW_MATRIX, buffer);
		float[] modelviewMatrix = buffer.array();
		
		//Build the transformation matrix this Transform specifies
		float[] transformationMatrix = new float[] {
				//Column 1
				along.x(),
				up.x(),
				look.x(),
				0,
				
				//Column 2
				along.y(),
				up.y(),
				look.y(),
				0,
				
				//Column 3
				along.z(),
				up.z(),
				look.z(),
				0,
				
				//Column 4
				position.x(),
				position.y(),
				position.z(),
				1
		};
		
		//Multiply modelview x transformation
		float[] m = Mathf.multiplyMatrix(modelviewMatrix, transformationMatrix);
		
		//Load the matrix into OpenGL
		gl.glLoadMatrixf(m, 0);
	}

}
