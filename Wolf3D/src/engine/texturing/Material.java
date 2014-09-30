package engine.texturing;

import static javax.media.opengl.GL2.GL_TEXTURE_2D;

import javax.media.opengl.GL2;

import engine.common.Color;

/**
 * A Material is the Combination of a texture and a color.<br>
 * A Material with a white color will render the texture as is. <br>
 * Changing the color you can add a hue to objects.
 * @author Hamish
 *
 */
public class Material {
	
	private Color color;
	private Texture texture;
	
	/**
	 * Create a Material with no Texture and the Color.WHITE
	 */
	public Material() {
		setTexture(null);
		setColor(Color.WHITE);
	}
	
	/**
	 * Create a Material with no texture and the color color.
	 * @param color The color.
	 */
	public Material(Color color) {
		setTexture(null);
		setColor(color);
	}
	
	/**
	 * Create a Material with the texture and Color.WHITE
	 * @param texture
	 */
	public Material(Texture texture) {
		setTexture(texture);
		setColor(Color.WHITE);
	}	
	
	/**
	 * Create a Material with the given Texture and Color.
	 * @param texture The texture.
	 * @param color The color.
	 */
	public Material(Texture texture, Color color) {
		setTexture(texture);
		setColor(color);
	}
	
	/**
	 * Returns true if this Material has a Texture assigned to it.
	 * @return True if this Material has a texture.
	 */
	public boolean hasTexture() {
		return texture != null;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public void setColor(Color color) {
		if(color == null) throw new IllegalArgumentException("Cannot set color to null.");
		this.color = color;
	}
	
	public void bind(GL2 gl) {
		if(texture != null) {
			gl.glEnable(GL_TEXTURE_2D);
			texture.bind(gl);
		}
		gl.glColor4f(color.r(), color.g(), color.b(), color.a());
	}
	
	public void unbind(GL2 gl) {
		if(texture != null) {
			gl.glDisable(GL_TEXTURE_2D);
		}
	}
	

}
