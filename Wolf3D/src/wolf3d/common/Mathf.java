package wolf3d.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A collection of Math functions that that deal with floats. As opposed to javas Math class which deals in doubles.
 * @author Hamish Rae-Hodgson
 *
 */
public class Mathf {
	static final Logger logger = LogManager.getLogger();
	
	public static final float PI = (float)Math.PI;
	public static final float E = (float)Math.E;
	
	public static float sqrt(float x) {
		return (float)Math.sqrt(x);
	}
	
	public static float sin(float a) {
		return (float)Math.sin(a);
	}
	
	public static float cos(float a) {
		return (float)Math.cos(a);
	}
	
	public static float tan(float a) {
		return (float)Math.tan(a);
	}
	
	public static float degToRad(float degrees) {
		return (PI/180f)*degrees;
	}
	
	public static float radToDeg(float radians) {
		return (180f/PI)*radians;
	}

	public static float abs(float x) {
		return x < 0 ? -x : x;
	}
	
	public static float min(float x, float y) {
		return x < y ? x : y;
	}
	
	public static float max(float x, float y) {
		return x > y ? x : y;
	}
	
	public static float clamp(float x, float min, float max) {
		float v = (max < x ? max : x);
		return min > v ? min : v;
	}
	
	public static float clamp(float x, float min, float max, float threshold) {
		float v = (max-threshold < x ? max : x);
		return min+threshold > v ? min : v;
	}
	
	/**
	 * Lerp from a to b. When t is 0 it's 100% a. When t is 1 it is 100% b.
	 * @param a Starting value.
	 * @param b Destination value.
	 * @param t A percentage specified as 0..1 inclusive.
	 * @throws IllegalArgumentException When t is not 0..1 inclusive.
	 * @return Returns the lerped value.
	 */
	public static float lerp(float a, float b, float t) {
		if(t < 0 || t > 1) throw new IllegalArgumentException("t must be (0 >= t <= 1)");
		return (1-t)*a + t*b;
	}

	public static float random() {
		return (float)Math.random();
	}
	
	public static float ceil(float x) {
		return (float) Math.ceil(x);
	}
	
	public static float floor(float x) {
		return (float) Math.floor(x);
	}
	
	/**
	 * Multiplies matrix a by matrix b. 
	 * @param a A 4x4 Column ordered matrix.
	 * @param b A 4x4 Column ordered matrix.
	 * @throws IllegalArgumentException When either of the matrices is not a 4x4 matrix.
	 * @return The resulting matrix.
	 */
	public static float[] multiplyMatrix(float[] a, float[] b) {
		if(a.length != 16 || b.length != 16)
			throw new IllegalArgumentException("Provided matricies must have exactly 16 elements");
		
		return new float[] {
				//col1
				a[0]*b[0] + a[4]*b[1] + a[8] *b[2] + a[12]*b[3],
				a[1]*b[0] + a[5]*b[1] + a[9] *b[2] + a[13]*b[3],
				a[2]*b[0] + a[6]*b[1] + a[10]*b[2] + a[14]*b[3],
				a[3]*b[0] + a[7]*b[1] + a[11]*b[2] + a[15]*b[3],
				
				//col2
				a[0]*b[4] + a[4]*b[5] + a[8] *b[6] + a[12]*b[7],
				a[1]*b[4] + a[5]*b[5] + a[9] *b[6] + a[13]*b[7],
				a[2]*b[4] + a[6]*b[5] + a[10]*b[6] + a[14]*b[7],
				a[3]*b[4] + a[7]*b[5] + a[11]*b[6] + a[15]*b[7],
				
				//col3
				a[0]*b[8] + a[4]*b[9] + a[8] *b[10] + a[12]*b[11],
				a[1]*b[8] + a[5]*b[9] + a[9] *b[10] + a[13]*b[11],
				a[2]*b[8] + a[6]*b[9] + a[10]*b[10] + a[14]*b[11],
				a[3]*b[8] + a[7]*b[9] + a[11]*b[10] + a[15]*b[11],
				
				//col4
				a[0]*b[12] + a[4]*b[13] + a[8] *b[14] + a[12]*b[15],
				a[1]*b[12] + a[5]*b[13] + a[9] *b[14] + a[13]*b[15],
				a[2]*b[12] + a[6]*b[13] + a[10]*b[14] + a[14]*b[15],
				a[3]*b[12] + a[7]*b[13] + a[11]*b[14] + a[15]*b[15]
		};
	}
}
