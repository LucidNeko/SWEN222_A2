package wolf3d.components;

import java.nio.FloatBuffer;

import static javax.media.opengl.GL2.GL_TRIANGLES;

import com.jogamp.common.nio.Buffers;

/**
 * A Class defining sets of verticies and uv coords to render a texture to screen
 * @author Hamish Rae-Hodgson
 *
 */
public class Sprite extends Component {

	//Primative layout:
	//					3-2
	//					| |
	//					0-1
	
	private FloatBuffer vertices;
	private FloatBuffer texcoords;
	
	/**
	 * Creates a new sprite with width and height centered at (0,0) local space.
	 * @param width The width of the Sprite.
	 * @param height The height of the Sprite.
	 */
	public Sprite(float width, float height) {
		float hw = width/2f;  //half width
		float hh = height/2f; //half height
		
		vertices = Buffers.newDirectFloatBuffer(3 * 3 * 2); //3components, 3verticies, 2triangles 
		{
			vertices.put(-hw); vertices.put(-hh); vertices.put(0); //0:(x, y, z)
			vertices.put( hw); vertices.put(-hh); vertices.put(0); //1:(x, y, z)
			vertices.put(-hw); vertices.put( hh); vertices.put(0); //3:(x, y, z)
			
			vertices.put(-hw); vertices.put( hh); vertices.put(0); //3:(x, y, z)
			vertices.put( hw); vertices.put(-hh); vertices.put(0); //1:(x, y, z)
			vertices.put( hw); vertices.put( hh); vertices.put(0); //2:(x, y, z)
			vertices.flip();
		}
		
		texcoords = Buffers.newDirectFloatBuffer(2 * 3 * 2); //2components, 3verticies, 2triangles
		{
			texcoords.put(0); texcoords.put(0); //0:(u, v)
			texcoords.put(1); texcoords.put(0); //1:(u, v)
			texcoords.put(0); texcoords.put(1); //3:(u, v)
			
			texcoords.put(0); texcoords.put(1); //3:(u, v)
			texcoords.put(1); texcoords.put(0); //1:(u, v)
			texcoords.put(1); texcoords.put(1); //2:(u, v)
			texcoords.flip();
		}
	}
	
	/**
	 * Get the (x,y,z) vertex buffer that contains the vertex information.
	 * @return The Vertex Buffer.
	 */
	public FloatBuffer getVertices() {
		return vertices;
	}
	
	/**
	 * Get the (u,v) texture coordinate buffer.
	 * @return The texture coordinate buffer.
	 */
	public FloatBuffer getTexCoords() {
		return texcoords;
	}
	
	/**
	 * Get the rendermode that should be used to render this sprite.<br>
	 * GL_TRIANGLES or GL_QUADS ..etc
	 * @return Returns the mode.
	 */
	public int getMode() {
		return GL_TRIANGLES;
	}
	
	/**
	 * Returns the number of vertices 
	 * @return
	 */
	public int getNumVertices() {
		return 6;
	}
	
}
