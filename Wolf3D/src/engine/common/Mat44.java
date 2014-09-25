package engine.common;

/**
 * The Mat44 class represents a 4x4 matrix.
 * @author Hamish
 *
 */
public class Mat44 {
	
	private final Vec4 row1;
	private final Vec4 row2;
	private final Vec4 row3;
	private final Vec4 row4;
	
	/**
	 * Create a new 4x4 matrix initialized as the identity matrix.
	 */
	private Mat44() { 
		row1 = new Vec4(1, 0, 0, 0);
		row2 = new Vec4(0, 1, 0, 0);
		row3 = new Vec4(0, 0, 1, 0);
		row4 = new Vec4(0, 0, 0, 1);
	}
	
	/**
	 * Gets the first 3 components in the first column of this matrix.
	 * @return The vector containing these components.
	 */
	public Vec3 getAlong() {
		return new Vec3(row1.x, row2.x, row3.x);
	}
	
	/**
	 * Gets the first 3 components in the second column of this matrix.
	 * @return The vector containing these components.
	 */
	public Vec3 getUp() {
		return new Vec3(row1.y, row2.y, row3.y);
	}
	
	/**
	 * Gets the first 3 components in the third column of this matrix.
	 * @return The vector containing these components.
	 */
	public Vec3 getLook() {
		return new Vec3(row1.z, row2.z, row3.z);
	}
	
	/**
	 * Gets the first 3 components in the fourth column of this matrix.
	 * @return The vector containing these components.
	 */
	public Vec3 getPosition() {
		return new Vec3(row1.w, row2.w, row3.w);
	}
	
	/**
	 * Returns this matrix as a column ordered array ready to send to OpenGL.
	 * @return The matrix as a column ordered array.
	 */
	public float[] toArray() {
		return new float[] {
				row1.x, row2.x, row3.x, row4.x,
				row1.y, row2.y, row3.y, row4.y,
				row1.z, row2.z, row3.z, row4.z,
				row1.w, row2.w, row3.w, row4.w
		};
	}
	
	/**
	 * Creates a rotation matrix around the z axis by theta radians.
	 * @param theta Rotation radians.
	 * @return The new Matrix.
	 */
	public static Mat44 identity() {
		return new Mat44();
	}
	
	/**
	 * Create a new Mat44 created from the parameters.<br>
	 * The 4th row is set to (0, 0, 0, 1).
	 * @param along The first column of the Matrix
	 * @param up The second column of the Matrix
	 * @param look The third column of the Matrix
	 * @param position The fourth column of the Matrix
	 * @return The new Matrix.
	 */
	public static Mat44 create(Vec3 along, Vec3 up, Vec3 look, Vec3 position) {
		Mat44 m = identity();
		m.row1.x = along.x(); m.row1.y = up.x(); m.row1.z = look.x(); m.row1.w = position.x();
		m.row2.x = along.y(); m.row2.y = up.y(); m.row2.z = look.y(); m.row2.w = position.y(); 
		m.row3.x = along.z(); m.row3.y = up.z(); m.row3.z = look.z(); m.row3.w = position.z(); 
		return m;
	}
	
	/**
	 * Creates a translation matrix representing a translation by (x, y, z).
	 * @param x
	 * @param y
	 * @param z
	 * @return The new Matrix.
	 */
	public static Mat44 translation(float x, float y, float z) {
		Mat44 m = identity();
		m.row1.w = x;
		m.row2.w = y;
		m.row3.w = z;
		return m;
	}
	
	/**
	 * Creates a scaling matrix representing a scale by (sx, sy, sz).
	 * @param sx Scaling on x.
	 * @param sy Scaling on y.
	 * @param sz Scaling on z.
	 * @return The new Matrix.
	 */
	public static Mat44 scale(float sx, float sy, float sz) {
		Mat44 m = identity();
		m.row1.x = sx;
		m.row2.y = sy;
		m.row3.z = sz;
		return m;
	}
	
	/**
	 * Creates a rotation matrix around the x axis by theta radians.
	 * @param theta Rotation radians.
	 * @return The new Matrix.
	 */
	public static Mat44 rotationX(float theta) {
		Mat44 m = identity();
		float sin = Mathf.sin(theta);
		float cos = Mathf.sin(theta);
		m.row2.y = cos; m.row2.z = -sin;
		m.row3.y = sin; m.row3.z = cos;
		return m;
	}
	
	/**
	 * Creates a rotation matrix around the y axis by theta radians.
	 * @param theta Rotation radians.
	 * @return The new Matrix.
	 */
	public static Mat44 rotationY(float theta) {
		Mat44 m = identity();
		float sin = Mathf.sin(theta);
		float cos = Mathf.sin(theta);
		m.row1.x = cos; m.row1.z = sin;
		m.row3.x = -sin; m.row3.z = cos;
		return m;
	}
	
	/**
	 * Creates a rotation matrix around the z axis by theta radians.
	 * @param theta Rotation radians.
	 * @return The new Matrix.
	 */
	public static Mat44 rotationZ(float theta) {
		Mat44 m = identity();
		float sin = Mathf.sin(theta);
		float cos = Mathf.sin(theta);
		m.row1.x = cos; m.row1.y = -sin;
		m.row2.x = sin; m.row2.y = cos;
		return m;
	}
	
	/**
	 * Multiplies matrix a by matrix b.<br>
	 * DOES NOT MODIFY EITHER MATRIX.
	 * @param a Matrix a.
	 * @param b Matrix b.
	 * @return The resulting matrix.
	 */
	public static Mat44 multiply(Mat44 a, Mat44 b) {
		Mat44 m = new Mat44();
		m.row1.x = a.row1.x*b.row1.x + a.row1.y*b.row2.x + a.row1.z*b.row3.x + a.row1.w*b.row4.x;
		m.row1.y = a.row1.x*b.row1.y + a.row1.y*b.row2.y + a.row1.z*b.row3.y + a.row1.w*b.row4.y;
		m.row1.z = a.row1.x*b.row1.z + a.row1.y*b.row2.z + a.row1.z*b.row3.z + a.row1.w*b.row4.z;
		m.row1.w = a.row1.x*b.row1.w + a.row1.y*b.row2.w + a.row1.z*b.row3.w + a.row1.w*b.row4.w;
		
		m.row2.x = a.row2.x*b.row1.x + a.row2.y*b.row2.x + a.row2.z*b.row3.x + a.row2.w*b.row4.x;
		m.row2.y = a.row2.x*b.row1.y + a.row2.y*b.row2.y + a.row2.z*b.row3.y + a.row2.w*b.row4.y;
		m.row2.z = a.row2.x*b.row1.z + a.row2.y*b.row2.z + a.row2.z*b.row3.z + a.row2.w*b.row4.z;
		m.row2.w = a.row2.x*b.row1.w + a.row2.y*b.row2.w + a.row2.z*b.row3.w + a.row2.w*b.row4.w;
		
		m.row3.x = a.row3.x*b.row1.x + a.row3.y*b.row2.x + a.row3.z*b.row3.x + a.row3.w*b.row4.x;
		m.row3.y = a.row3.x*b.row1.y + a.row3.y*b.row2.y + a.row3.z*b.row3.y + a.row3.w*b.row4.y;
		m.row3.z = a.row3.x*b.row1.z + a.row3.y*b.row2.z + a.row3.z*b.row3.z + a.row3.w*b.row4.z;
		m.row3.w = a.row3.x*b.row1.w + a.row3.y*b.row2.w + a.row3.z*b.row3.w + a.row3.w*b.row4.w;
		
		m.row4.x = a.row4.x*b.row1.x + a.row4.y*b.row2.x + a.row4.z*b.row3.x + a.row4.w*b.row4.x;
		m.row4.y = a.row4.x*b.row1.y + a.row4.y*b.row2.y + a.row4.z*b.row3.y + a.row4.w*b.row4.y;
		m.row4.z = a.row4.x*b.row1.z + a.row4.y*b.row2.z + a.row4.z*b.row3.z + a.row4.w*b.row4.z;
		m.row4.w = a.row4.x*b.row1.w + a.row4.y*b.row2.w + a.row4.z*b.row3.w + a.row4.w*b.row4.w;
		return m;
	}
	
	/**
	 * Multiplies vector v by matrix m.<br>
	 * DOES NOT MODIFY EITHER PARAMETER.
	 * @param m The matrix.
	 * @param v The vector.
	 * @return The resulting vector.
	 */
	public static Vec3 multiply(Mat44 m, Vec3 v) {
		return new Vec3(m.row1.x*v.x() + m.row1.y*v.y() + m.row1.z*v.z() + m.row1.w, 
						m.row2.x*v.x() + m.row2.y*v.y() + m.row2.z*v.z() + m.row2.w, 
						m.row3.x*v.x() + m.row3.y*v.y() + m.row3.z*v.z() + m.row3.w);
	}
	
	/**
	 * A class representing a 4 component vector.
	 * @author Hamish
	 *
	 */
	private static class Vec4 {
		public float x;
		public float y;
		public float z;
		public float w;
		
		public Vec4(float x, float y, float z, float w) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.w = w;
		}
	}

}
