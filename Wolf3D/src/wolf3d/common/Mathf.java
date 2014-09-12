package wolf3d.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A collection of Math functions that take and return floats. javas Math class which returns doubles.
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
}
