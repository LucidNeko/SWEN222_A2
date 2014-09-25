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
 * The OBJBuilder takes an InputStream to the wavefont.obj file, or the data
 * as a single string separated by \n characters at the end of where each line would be.
 * You can get a new Mesh object containing the data by calling the createMesh() method.
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

	private int mode = GL_TRIANGLES;

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

	public OBJBuilder(String obj) {
		this.objFile = obj;
	}

	public Mesh createMesh() {
		if(built == false) build();

		float[] verts = new float[faces.size()*3*(mode==GL_TRIANGLES?3:4)]; //numFaces*3components*3vertices
		float[] norms = new float[faces.size()*3*(mode==GL_TRIANGLES?3:4)]; //numFaces*3components*3vertices
		float[] uvs =   new float[faces.size()*2*(mode==GL_TRIANGLES?3:4)]; //numFaces*2components*3vertices

		int vertIndex = 0;
		int normIndex = 0;
		int uvIndex = 0;

		for(Face face : faces) {
			for(int i = 0; i < face.vertices.size(); i++) {
				Vec3 vert = vertices.get(face.vertices.get(i)-1);
				verts[vertIndex++] = vert.x();
				verts[vertIndex++] = vert.y();
				verts[vertIndex++] = vert.z();
			}

			for(int i = 0; i < face.normals.size(); i++) {
				Vec3 norm = normals.get(face.normals.get(i)-1);
				norms[normIndex++] = norm.x();
				norms[normIndex++] = norm.y();
				norms[normIndex++] = norm.z();
			}

			for(int i = 0; i < face.texCoords.size(); i++) {
				Vec2 uv = texCoords.get(face.texCoords.get(i)-1);
				uvs[uvIndex++] = uv.x();
				uvs[uvIndex++] = uv.y();
			}
		}

		return new Mesh(verts, norms, uvs, mode);
	}

	public void build() {
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

		if(mode == GL_TRIANGLES && tokens.length > 4) {
			mode = GL_QUADS;
		}

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

	private class Face {
		List<Integer> vertices = new ArrayList<Integer>(4);
		List<Integer> texCoords = new ArrayList<Integer>(4);
		List<Integer> normals = new ArrayList<Integer>(4);
	}

}
