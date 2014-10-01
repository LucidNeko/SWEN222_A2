package engine.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import engine.common.Vec2;
import engine.common.Vec3;
import engine.texturing.Mesh;

/**
 * OBJBuilder is a class to read in and interpert Wavefont.obj files.
 * @author Hamish
 *
 */
public class OBJBuilder {
	private static final Logger log = LogManager.getLogger();

	//Parsing
	private final static String OBJ_VERTEX_TEXTURE = "vt";
    private final static String OBJ_VERTEX_NORMAL = "vn";
    private final static String OBJ_VERTEX = "v";
    private final static String OBJ_FACE = "f";

    private final static int GL_TRIANGLES = GL2.GL_TRIANGLES;
    private final static int GL_QUADS = GL2.GL_QUADS;

    private boolean built = false;
	private String objFile;

	private List<Vec3> vertices = new ArrayList<Vec3>();
	private List<Vec3> normals = new ArrayList<Vec3>();
	private List<Vec2> texCoords = new ArrayList<Vec2>();
	private List<Face> faces = new ArrayList<Face>();
	
	/**
	 * Build from the InputStream
	 * @param in An InputStream coming from an .obj file.
	 */
	public OBJBuilder(InputStream in) {
		if(in == null)
			throw new NullPointerException("InputStream cannot be null");

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while((line = reader.readLine()) != null)
				buffer.append(line).append("\n");
			reader.close();
			objFile = buffer.toString();
		} catch(IOException e) {
			e.printStackTrace();
			throw new Error("Failed creating OBJ: +", e);
		}
	}

	/**
	 * Builds from a String that is an obj file with each line seperated by /n
	 * @param obj The obj specification of the object.
	 */
	public OBJBuilder(String obj) {
		this.objFile = obj;
	}
	
	/**
	 * Gets the Mesh that the obj file represents.
	 * @return The Mesh.
	 */
	public Mesh getMesh() {
		if(!built) build();
		
		Vec3[] mesh_vertices = vertices.toArray(new Vec3[vertices.size()]);
		Vec2[] mesh_uvs = new Vec2[faces.size()*3];
		int uv_idx = 0;
		int[] triangles = new int[faces.size()*3];
		int tri_idx = 0;
		for(Face face : faces) {
			triangles[tri_idx++] = face.vertices.get(0)-1; //-1 because f lines start at 1 not 0
			triangles[tri_idx++] = face.vertices.get(1)-1; //-1 because f lines start at 1 not 0
			triangles[tri_idx++] = face.vertices.get(2)-1; //-1 because f lines start at 1 not 0
			
			//TODO:
			mesh_uvs[uv_idx++] = texCoords.get(face.texCoords.get(0)-1).clone();
			mesh_uvs[uv_idx++] = texCoords.get(face.texCoords.get(1)-1).clone();
			mesh_uvs[uv_idx++] = texCoords.get(face.texCoords.get(2)-1).clone();
		}
		return new Mesh(mesh_vertices, mesh_uvs, triangles);
	}
	
	
	/** Build from the obj file */
	private void build() {
		if(built) return;

		for(String line : objFile.split("\n"))
			parseLine(line);

		built = true;
	}

	private void parseLine(String line) {
		if(line.startsWith(OBJ_VERTEX_NORMAL)) {
			parseVertexNormal(line);
		} else if(line.startsWith(OBJ_VERTEX_TEXTURE)) {
			parseVertexTexture(line);
		} else if(line.startsWith(OBJ_VERTEX)) {
			parseVertex(line);
		} else if(line.startsWith(OBJ_FACE)) {
			parseFace(line);
		}
	}

	private void parseVertex(String line) {
		String[] tokens = line.split("[ \t]+"); //splits at spaces and tabs

		if(!tokens[0].equals(OBJ_VERTEX))
			throw new IllegalStateException("Expected:" + OBJ_VERTEX +" Had:" + tokens[0]);

		Vec3 vertex = new Vec3(Float.parseFloat(tokens[1]),
							   Float.parseFloat(tokens[2]),
							   Float.parseFloat(tokens[3]));
		vertices.add(vertex);
	}

	private void parseVertexTexture(String line) {
		String[] tokens = line.split("[ \t]+"); //splits at spaces and tabs

		if(!tokens[0].equals(OBJ_VERTEX_TEXTURE))
			throw new IllegalStateException("Expected:" + OBJ_VERTEX_TEXTURE +" Had:" + tokens[0]);

		Vec2 vertex = new Vec2(Float.parseFloat(tokens[1]),
							   Float.parseFloat(tokens[2]));
		texCoords.add(vertex);
	}

	private void parseVertexNormal(String line) {
		String[] tokens = line.split("[ \t]+"); //splits at spaces and tabs

		if(!tokens[0].equals(OBJ_VERTEX_NORMAL))
			throw new IllegalStateException("Expected:" + OBJ_VERTEX_NORMAL +" Had:" + tokens[0]);

		Vec3 vertex = new Vec3(Float.parseFloat(tokens[1]),
							   Float.parseFloat(tokens[2]),
							   Float.parseFloat(tokens[3]));
		normals.add(vertex);
	}

	private void parseFace(String line) {
		String[] tokens = line.split("[ \t]+"); //splits at spaces and tabs

		if(!tokens[0].equals(OBJ_FACE))
			throw new IllegalStateException("Expected:" + OBJ_FACE +" Had:" + tokens[0]);

		// v/vt/vn or v//vn
		Face face = new Face();
		for(int i = 1; i < tokens.length; i++) {
			String[] parts = tokens[i].split("/");
			face.vertices.add(Integer.parseInt(parts[0]));
			if(parts.length > 1 && parts[1].length() != 0) face.texCoords.add(Integer.parseInt(parts[1]));
			if(parts.length > 2) face.normals.add(Integer.parseInt(parts[2]));
		}
		faces.add(face);
	}

	/**
	 * The Face class represents the three sets of 3-Tuples that represent a face.<br>
	 * v/vt/vn v/vt/vn v/vt/vn
	 * @author Hamish
	 *
	 */
	private class Face {
		List<Integer> vertices = new ArrayList<Integer>(3);
		List<Integer> texCoords = new ArrayList<Integer>(3);
		List<Integer> normals = new ArrayList<Integer>(3);
	}

}
