package engine.texturing;

import java.nio.FloatBuffer;

import com.jogamp.common.nio.Buffers;

/**
 * A Mesh is a collection of vertices and triangles that index into the vertices.
 * @author Hamish
 *
 */
public class Mesh {

	private FloatBuffer vertices;
	private FloatBuffer normals;
	private FloatBuffer texCoords;
	
	private int numVertices;
	private int mode;

	public Mesh(float[] vertices, float[] normals, float[] texCoords, int GL_MODE) {
		this.vertices = Buffers.newDirectFloatBuffer(vertices);
		this.normals = Buffers.newDirectFloatBuffer(normals);
		this.texCoords = Buffers.newDirectFloatBuffer(texCoords);
		this.mode = GL_MODE;
		this.numVertices = vertices.length/3;
	}

	public FloatBuffer getVertices() {
		return vertices;
	}

	public FloatBuffer getNormals() {
		return normals;
	}

	public FloatBuffer getTexCoords() {
		return texCoords;
	}

	public int getNumVerticies() {
		return numVertices;
	}

	public int getMode() {
		return mode;
	}

}
