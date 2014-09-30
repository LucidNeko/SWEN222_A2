package engine.components;

import javax.media.opengl.GL2;

import engine.texturing.Material;
import engine.texturing.Mesh;

public class MeshRenderer extends Renderer {
	
	private Material material = new Material();
	
	/**
	 * Sets the Material that this MeshRenderer uses.
	 * @param material The Material. Cannot be null.
	 */
	public void setMaterial(Material material) {
		if(material == null) throw new IllegalArgumentException("Cannot set material to null.");
		this.material = material;
	}

	@Override
	public void render(GL2 gl) {
		requires(MeshFilter.class);
		
		Mesh mesh = getOwner().getComponent(MeshFilter.class).getMesh();
		if(mesh == null) return;
		
		material.bind(gl);
		mesh.bind(gl);
		mesh.draw(gl);
		mesh.unbind(gl);
		material.unbind(gl);
	}

}
