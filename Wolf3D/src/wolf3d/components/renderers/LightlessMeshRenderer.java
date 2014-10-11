package wolf3d.components.renderers;

import javax.media.opengl.GL2;

import engine.components.GL2Renderer;
import engine.components.MeshFilter;
import engine.texturing.Material;
import engine.texturing.Mesh;

/**
 * 
 * @author Hamish
 *
 */
public class LightlessMeshRenderer extends GL2Renderer {
	
	private Material material = new Material();
	
	/**
	 * Sets the Material that this MeshRenderer uses.
	 * @param material The Material. Cannot be null.
	 */
	public void setMaterial(Material material) {
		if(material == null) throw new IllegalArgumentException("Cannot set material to null.");
		this.material = material;
	}
	
	public Material getMaterial() {
		return material;
	}

	@Override
	public void render(GL2 gl) {
		requires(MeshFilter.class);
		
		Mesh mesh = getOwner().getComponent(MeshFilter.class).getMesh();
		if(mesh == null) return;

		gl.glDisable(GL2.GL_LIGHTING);
		
		material.bind(gl);
		mesh.bind(gl);
		mesh.draw(gl);
		mesh.unbind(gl);
		material.unbind(gl);

		//FIXME: WARNING WILL ENABLE LIGHTING IF NOT ENABLED.
		gl.glEnable(GL2.GL_LIGHTING);
		
	}

}
