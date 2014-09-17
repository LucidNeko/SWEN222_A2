package wolf3d.components.renderers;

//GL Constants
import static javax.media.opengl.GL.*;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_TEXTURE_COORD_ARRAY;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_VERTEX_ARRAY;

import java.nio.FloatBuffer;

import javax.media.opengl.GL2;

import com.jogamp.common.nio.Buffers;

/**
 * TextureRenderer grabs the Sprite object attached to the same Entity and renders the texture onto it.
 * @author Hamish Rae-Hodgson
 *
 */
public class TextureRenderer extends Renderer {
	
	/** The ID of the texture in video memory */
	private int texID;
	
	//Primative layout:
	//					3-2
	//					| |
	//					0-1
	
	private FloatBuffer vertices;
	private FloatBuffer texcoords;
	private int numVerticies = 6;

	/**
	 * Creates a new TextureRenderer using the texture at texID.
	 * @param texID The ID of the texture this Renderer will render.
	 */
	public TextureRenderer(int texID, float leftX, float bottomY, float rightX, float topY, float uvX, float uvY) {
		this.texID = texID;
		
		vertices = Buffers.newDirectFloatBuffer(3 * 3 * 2); //3components, 3verticies, 2triangles 
		{
			vertices.put(leftX); vertices.put(bottomY); vertices.put(0); //0:(x, y, z)
			vertices.put(rightX); vertices.put(bottomY); vertices.put(0); //1:(x, y, z)
			vertices.put(leftX); vertices.put(topY); vertices.put(0); //3:(x, y, z)
			
			vertices.put(leftX); vertices.put(topY); vertices.put(0); //3:(x, y, z)
			vertices.put(rightX); vertices.put(bottomY); vertices.put(0); //1:(x, y, z)
			vertices.put(rightX); vertices.put(topY); vertices.put(0); //2:(x, y, z)
			vertices.flip();
		}
		
		texcoords = Buffers.newDirectFloatBuffer(2 * 3 * 2); //2components, 3verticies, 2triangles
		{
			texcoords.put(0); texcoords.put(0); //0:(u, v)
			texcoords.put(uvX); texcoords.put(0); //1:(u, v)
			texcoords.put(0); texcoords.put(uvY); //3:(u, v)
			
			texcoords.put(0); texcoords.put(uvY); //3:(u, v)
			texcoords.put(uvX); texcoords.put(0); //1:(u, v)
			texcoords.put(uvX); texcoords.put(uvY); //2:(u, v)
			texcoords.flip();
		}
	}

	@Override
	public void render(GL2 gl) {
		gl.glPushMatrix();
			getOwner().getTransform().applyTransform(gl);
		
			gl.glColor3f(1,1,1); //texture colored as it is on disk.
			gl.glEnable(GL_TEXTURE_2D);
//			gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
//			gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			gl.glBindTexture(GL_TEXTURE_2D,  texID);
			gl.glEnableClientState(GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);
			gl.glVertexPointer(3, GL_FLOAT, 0, vertices); //set vertex buffer
			gl.glTexCoordPointer(2, GL_FLOAT, 0, texcoords); //set texcoord buffer
			gl.glDrawArrays(GL_TRIANGLES, 0, numVerticies); //actually draw the buffers.
			gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY);
			gl.glDisableClientState(GL_VERTEX_ARRAY);
			gl.glDisable(GL_TEXTURE_2D);
		gl.glPopMatrix();
	}

}
