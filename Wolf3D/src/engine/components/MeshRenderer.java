package engine.components;

import static javax.media.opengl.GL.GL_FLOAT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_NORMAL_ARRAY;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_TEXTURE_COORD_ARRAY;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_VERTEX_ARRAY;

import javax.media.opengl.GL2;

import engine.common.Color;
import engine.texturing.Material;
import engine.texturing.Mesh;

public class MeshRenderer extends Renderer {

	Material material = new Material(); //blank material

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

		MeshFilter meshFilter = getOwner().getComponent(MeshFilter.class);
		Mesh mesh = meshFilter.getMesh();

		if(mesh == null) return;

		gl.glPushMatrix();
			getOwner().getTransform().applyTransform(gl);
			
//			if(material.hasTexture()) {
//				gl.glEnable(GL_TEXTURE_2D);
//				material.getTexture().bind(gl);
//			}
//			
//			Color c = material.getColor();
//			gl.glColor4f(c.r(), c.g(), c.b(), c.a());
			
			material.bind(gl);
			
			gl.glEnableClientState(GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);
			gl.glEnableClientState(GL_NORMAL_ARRAY);
			gl.glVertexPointer(3, GL_FLOAT, 0, mesh.getVertices());
			gl.glTexCoordPointer(2, GL_FLOAT, 0, mesh.getTexCoords());
			gl.glNormalPointer(GL_FLOAT, 0, mesh.getNormals());
			gl.glDrawArrays(mesh.getMode(), 0, mesh.getNumVerticies());
			gl.glDisableClientState(GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY);
			gl.glDisableClientState(GL_NORMAL_ARRAY);
	
//			if(material.hasTexture()) gl.glDisable(GL_TEXTURE_2D);
			
			material.unbind(gl);
			
		gl.glPopMatrix();
	}

}
