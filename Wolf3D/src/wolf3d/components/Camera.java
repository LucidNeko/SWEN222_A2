package wolf3d.components;

import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW_MATRIX;

import java.nio.FloatBuffer;

import javax.media.opengl.GL2;

import wolf3d.common.Mathf;
import wolf3d.common.Vec3;

/**
 * The camera class can transform the modelview matrix to show the view from a given entity.
 * @author Hamish Rae-Hodgson
 *
 */
public class Camera extends Component implements ICamera{
	
	/** Camera offset from entity */
	private final Transform transform = new Transform();
	
	/**
	 * Create a new Camera displaying the world as seen from this Entity.
	 */
	public Camera() { }
	
	/**
	 * Get the Transform of this camera (In relation to it's owners Transform).<br>
	 * Modify this for 3rd person/1st person etc etc..
	 * @return The transform.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	/**
	 * Build the matrix that represents the view from this entity.
	 * @return The matrix.
	 */
	private float[] buildMatrix() {
		Transform t = getOwner().getTransform();
		
		Vec3 position = t.getPosition();
		Vec3 along = t.getAlong();
		Vec3 up = t.getUp();
		Vec3 look = t.getLook();
		
		//yaw 180 - rotate to face down z
		look = look.negate();
		along = along.negate();
		
		float[] m =  new float[] {
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

				//Column 4 - virtual eye coords.
				-Vec3.dot(along, position),
				-Vec3.dot(up, position),
				Vec3.dot(look.negate(), position),
				1
		};
		
		//Multiply the cameras transformation matrix by the generated matrix.
		return Mathf.multiplyMatrix(transform.getMatrix(), m);
	}

	@Override
	public void setActive(GL2 gl) {
		//Get the modelview matrix
		FloatBuffer buffer = FloatBuffer.allocate(16);
		gl.glGetFloatv(GL_MODELVIEW_MATRIX, buffer);
		float[] modelviewMatrix = buffer.array();

		//Build the transformation matrix this Camera specifies
		float[] cameraMatrix = buildMatrix();

		//Multiply modelview x transformation
		float[] m = Mathf.multiplyMatrix(modelviewMatrix, cameraMatrix);

		//Load the matrix into OpenGL
		gl.glLoadMatrixf(m, 0);
	}

}
