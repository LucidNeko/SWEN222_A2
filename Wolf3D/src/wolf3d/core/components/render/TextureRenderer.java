package wolf3d.core.components.render;

//GL Constants
import static javax.media.opengl.GL.*;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_TEXTURE_COORD_ARRAY;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_VERTEX_ARRAY;

import javax.media.opengl.GL2;

import wolf3d.core.components.Component;

/**
 * TextureRenderer grabs the Sprite object attached to the same Entity and renders the texture onto it.
 * @author Hamish Rae-Hodgson
 *
 */
public class TextureRenderer extends Renderer {
	
	/** The ID of the texture in video memory */
	private int texID;

	/**
	 * Creates a new TextureRenderer using the texture at texID.
	 * @param texID The ID of the texture this Renderer will render.
	 */
	public TextureRenderer(int texID) {
		this.texID = texID;
	}

	@Override
	public void render(GL2 gl) {
		Sprite sprite = getOwner().getComponent(Sprite.class); //TODO: cache if slowdown occurs from looking up each time.
		
		if(sprite == null) {
			log.error("TextureRenderer needs a Sprite to also be attached to the Entity!");
			return;
		}
		
		gl.glColor3f(1,1,1); //texture colored as it is on disk.
		gl.glEnable(GL_TEXTURE_2D);
		gl.glBindTexture(GL_TEXTURE_2D,  texID);
		gl.glEnableClientState(GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		gl.glVertexPointer(3, GL_FLOAT, 0, sprite.getVertices()); //set vertex buffer
		gl.glTexCoordPointer(2, GL_FLOAT, 0, sprite.getTexCoords()); //set texcoord buffer
		gl.glDrawArrays(sprite.getMode(), 0, sprite.getNumVertices()); //actually draw the buffers.
		gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL_VERTEX_ARRAY);
		gl.glDisable(GL_TEXTURE_2D);
	}

}
