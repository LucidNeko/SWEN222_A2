package engine.texturing;

import static javax.media.opengl.GL.GL_FLOAT;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_NORMAL_ARRAY;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_TEXTURE_COORD_ARRAY;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_VERTEX_ARRAY;
import static javax.media.opengl.GL2.GL_TRIANGLES;

import java.nio.FloatBuffer;

import javax.media.opengl.GL2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jogamp.common.nio.Buffers;

import engine.common.Vec2;
import engine.common.Vec3;

/**
 * A Mesh contains vertex/uv/normal information of a mesh.
 * @author Hamish
 *
 */
public class Mesh {
	private static final Logger log = LogManager.getLogger();
	
	/** All the Vertices that make up the mesh */
	private Vec3[] vertices;
	
	/** All the normals of the mesh */
	private Vec3[] normals;
	
	/** All the uvs (texture coordinates) of the mesh */
	private Vec2[] uvs;
	
	/** The triangles making up the mesh. Indicies into the vertex array. v1, v2, v3, v1, v2, v3. */
	private int[] triangles;
	
	private FloatBuffer vertexBuffer;
	private FloatBuffer normalBuffer;
	private FloatBuffer uvBuffer;
	
	/** If the mesh is dirty, need to recalculate normals */
	private boolean isDirty = true;
	
	/**
	 * Construct a new Mesh from parameters.
	 * @param vertices All the vertices that make up the mesh.
	 * @param triangles An array indexing into the vertices array specifying triangles. v1,v2,v3,v1,v2,v3
	 */
	public Mesh(Vec3[] vertices, int[] triangles) {
		this(vertices, null, triangles);
	}
	
	/**
	 * Construct a new Mesh from parameters.
	 * @param vertices All the vertices that make up the mesh.
	 * @param triangles An array indexing into the vertices array specifying triangles. v1,v2,v3,v1,v2,v3
	 * @param uvs An array of uv coordinates for each vertex.
	 */
	public Mesh(Vec3[] vertices, Vec2[] uvs, int[] triangles) {
		if(vertices == null || vertices.length < 1) 
			throw new IllegalArgumentException("verticees can't be null or empty");
		if(triangles == null || triangles.length < 2)
			throw new IllegalArgumentException("triangles can't be null or empty");
		if(uvs != null && uvs.length != triangles.length)
			throw new IllegalArgumentException("If uvs is not null, then it must provide a uv coordinate for each vertex on each triangle.");

		this.uvs = uvs;
		this.vertices = vertices;
		this.triangles = triangles;
		calculateNormals();
		createBuffers();
	}
	
	/**
	 * Calculates the normals from the faces.<br>
	 * Each vertex normal is the sum of the face normals that use the vertex (normalized).
	 */
	private void calculateNormals() {
		normals = new Vec3[vertices.length]; //vertex normals
		
		//for each triangle
		for(int i = 0; i < triangles.length; i+=3) {
			//Indices
			int idx_a = triangles[i+0];
			int idx_b = triangles[i+1];
			int idx_c = triangles[i+2];
			
			//CCW
			Vec3 a = vertices[idx_a];
			Vec3 b = vertices[idx_b];
			Vec3 c = vertices[idx_c];
			
			//Normal
			Vec3 normal = Vec3.cross(b.sub(a), c.sub(a));
			normal.normalize();
			
			if(normals[idx_a] == null) //if no normal yet
				normals[idx_a] = normal.clone(); //set as copy
			else normals[idx_a].addLocal(normal); //otherwise add
			
			if(normals[idx_b] == null) //if no normal yet
				normals[idx_b] = normal.clone(); //set as copy
			else normals[idx_b].addLocal(normal); //otherwise add
			
			if(normals[idx_c] == null) //if no normal yet
				normals[idx_c] = normal.clone(); //set as copy
			else normals[idx_c].addLocal(normal); //otherwise add
		}
		
		//Normalize all  down to a unit vector.
		for(Vec3 v : normals) {
			v.normalize(); 
		}
	}
	
	/**
	 * Create the vertex/normal/uv buffers.
	 */
	private void createBuffers() {
		vertexBuffer = Buffers.newDirectFloatBuffer(triangles.length*3);
		normalBuffer = Buffers.newDirectFloatBuffer(triangles.length*3);
		if(isUvMapped()) uvBuffer = Buffers.newDirectFloatBuffer(triangles.length*2);
		for(int i = 0; i < triangles.length; i++) {
			Vec3 vertex = vertices[triangles[i]];
			vertexBuffer.put(vertex.x());
			vertexBuffer.put(vertex.y());
			vertexBuffer.put(vertex.z());
			
			Vec3 normal = normals[triangles[i]];
			normalBuffer.put(normal.x());
			normalBuffer.put(normal.y());
			normalBuffer.put(normal.z());
			
			if(isUvMapped()) {
				Vec2 uv = uvs[i];
				uvBuffer.put(uv.x());
				uvBuffer.put(uv.y());
			}
		}
		vertexBuffer.flip();
		normalBuffer.flip();
		if(isUvMapped()) uvBuffer.flip();
	}
	
	/**
	 * Returns true if this mesh has uv coords.
	 */
	private boolean isUvMapped() {
		return uvs != null;
	}
	
	/**
	 * Bind this Mesh to the OpenGL context.
	 * @param gl The OpenGL context.
	 */
	public void bind(GL2 gl) {
		gl.glEnableClientState(GL_VERTEX_ARRAY);
			if(isUvMapped()) gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		gl.glEnableClientState(GL_NORMAL_ARRAY);
		gl.glVertexPointer(3, GL_FLOAT, 0, vertexBuffer);
			if(isUvMapped()) gl.glTexCoordPointer(2, GL_FLOAT, 0, uvBuffer);
		gl.glNormalPointer(GL_FLOAT, 0, normalBuffer);
	}
	
	/**
	 * Draw this Mesh (Must bind() first).
	 * @param gl The OpenGL context.
	 */
	public void draw(GL2 gl) {
		gl.glDrawArrays(GL_TRIANGLES, 0, triangles.length); 
	}
	
	/**
	 * Unbind this Mesh from the OpenGL context.
	 * @param gl The OpenGL context.
	 */
	public void unbind(GL2 gl) {
		gl.glDisableClientState(GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL_NORMAL_ARRAY);
	}

}
