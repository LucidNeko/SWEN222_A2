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
	
	private float verticalOffset;
	private float distanceBehind;
	
	/**
	 * Create a new Camera displaying the world as seen exaclty from this entities Transform.
	 */
	public Camera() {
		this(0, 0);
	}

	/**
	 * Create a new camera with the given parameters.
	 * @param verticalOffset How for along the World up to translate the camera.
	 * @param distanceBehind How far behind the entity should the camera be.
	 */
	public Camera(float verticalOffset, float distanceBehind) {
		this.verticalOffset = verticalOffset;
		this.distanceBehind = distanceBehind;
	}
	
	/** 
	 * Sets the distance behind the entity this camera should be.
	 * @param distanceBehind The distance.
	 */
	public void setDistanceBehind(float distanceBehind) {
		this.distanceBehind = distanceBehind;
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
		
		position = position.add(Vec3.UP.mul(verticalOffset));
		position = position.add(look.mul(distanceBehind));
		
		return new float[] {
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
	}

	@Override
	public void setActive(GL2 gl) {
		//Get the modelview matrix
		FloatBuffer buffer = FloatBuffer.allocate(16);
		gl.glGetFloatv(GL_MODELVIEW_MATRIX, buffer);
		float[] modelviewMatrix = buffer.array();

		//Build the transformation matrix this Transform specifies
		float[] transformationMatrix = buildMatrix();

		//Multiply modelview x transformation
		float[] m = Mathf.multiplyMatrix(modelviewMatrix, transformationMatrix);

		//Load the matrix into OpenGL
		gl.glLoadMatrixf(m, 0);
	}

}
