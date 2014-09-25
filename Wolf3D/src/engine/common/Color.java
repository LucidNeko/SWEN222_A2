package engine.common;

/**
 * The Color class represents a color (r, g, b, a) -> (0..1, 0..1, 0..1, 0..1)
 * @author Hamish
 *
 */
public class Color {
	
	public static final Color RED =   new Color(1, 0, 0);
	public static final Color GREEN = new Color(0, 1, 0);
	public static final Color BLUE =  new Color(0, 0, 1);
	public static final Color WHITE = new Color(1, 1, 1);
	public static final Color BLACK = new Color(0, 0, 0);
	
	private float red;
	private float green;
	private float blue;
	private float alpha;
	
	/**
	 * Create a new Color. Alpha is set to 1.
	 * @param red The red component (0..1f)
	 * @param green The green component (0..1f)
	 * @param blue The blue component (0..1f)
	 */
	public Color(float red, float green, float blue) {
		this(red, green, blue, 1);
	}

	/**
	 * Create a new Color.
	 * @param red The red component (0..1f)
	 * @param green The green component (0..1f)
	 * @param blue The blue component (0..1f)
	 * @param alpha The alpha component (0..1f)
	 */
	public Color(float red, float green, float blue, float alpha) {
		if(red   < 0 || red   > 1) throw new IllegalArgumentException("red was "   + red   + " must be (0 <= red <= 1)");
		if(green < 0 || green > 1) throw new IllegalArgumentException("green was " + green + " must be (0 <= green <= 1)");
		if(blue  < 0 || blue  > 1) throw new IllegalArgumentException("blue was "  + blue + " must be (0 <= blue <= 1)");
		if(alpha < 0 || alpha > 1) throw new IllegalArgumentException("alpha was " + alpha + " must be (0 <= alpha <= 1)");
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public float r() { return red; }
	public float g() { return green; }
	public float b() { return blue; }
	public float a() { return alpha; }
	
	public float red() { return red; }
	public float green() { return green; }
	public float blue() { return blue; }
	public float alpha() { return alpha; }

	
	
}
