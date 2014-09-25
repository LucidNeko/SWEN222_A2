package engine.components;

import static javax.media.opengl.GL.GL_FLOAT;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_VERTEX_ARRAY;

import javax.media.opengl.GL2;

import engine.texturing.Mesh;
import engine.util.OBJBuilder;

/**
 * TransformRenderer renders the Entities Transform component.
 * @author Hamish
 *
 */
public class TransformRenderer extends Renderer {

	//obj file representing a cylinder.
	private static final String cylinderObj = "v 0.000000 0.000000 0.000000\nv 0.000000 1.000000 0.000000\nv 0.000000 0.000000 -0.025000\nv 0.000000 1.000000 -0.025000\nv 0.004877 0.000000 -0.024520\nv 0.004877 1.000000 -0.024520\nv 0.009567 0.000000 -0.023097\nv 0.009567 1.000000 -0.023097\nv 0.013889 0.000000 -0.020787\nv 0.013889 1.000000 -0.020787\nv 0.017678 0.000000 -0.017678\nv 0.017678 1.000000 -0.017678\nv 0.020787 0.000000 -0.013889\nv 0.020787 1.000000 -0.013889\nv 0.023097 0.000000 -0.009567\nv 0.023097 1.000000 -0.009567\nv 0.024520 0.000000 -0.004877\nv 0.024520 1.000000 -0.004877\nv 0.025000 0.000000 -0.000000\nv 0.025000 1.000000 -0.000000\nv 0.024520 0.000000 0.004877\nv 0.024520 1.000000 0.004877\nv 0.023097 0.000000 0.009567\nv 0.023097 1.000000 0.009567\nv 0.020787 0.000000 0.013889\nv 0.020787 1.000000 0.013889\nv 0.017678 0.000000 0.017678\nv 0.017678 1.000000 0.017678\nv 0.013889 0.000000 0.020787\nv 0.013889 1.000000 0.020787\nv 0.009567 0.000000 0.023097\nv 0.009567 1.000000 0.023097\nv 0.004877 0.000000 0.024520\nv 0.004877 1.000000 0.024520\nv -0.000000 0.000000 0.025000\nv -0.000000 1.000000 0.025000\nv -0.004877 0.000000 0.024520\nv -0.004877 1.000000 0.024520\nv -0.009567 0.000000 0.023097\nv -0.009567 1.000000 0.023097\nv -0.013889 0.000000 0.020787\nv -0.013889 1.000000 0.020787\nv -0.017678 0.000000 0.017678\nv -0.017678 1.000000 0.017678\nv -0.020787 0.000000 0.013889\nv -0.020787 1.000000 0.013889\nv -0.023097 0.000000 0.009567\nv -0.023097 1.000000 0.009567\nv -0.024520 0.000000 0.004877\nv -0.024520 1.000000 0.004877\nv -0.025000 0.000000 -0.000000\nv -0.025000 1.000000 -0.000000\nv -0.024520 0.000000 -0.004877\nv -0.024520 1.000000 -0.004877\nv -0.023097 0.000000 -0.009567\nv -0.023097 1.000000 -0.009567\nv -0.020787 0.000000 -0.013889\nv -0.020787 1.000000 -0.013889\nv -0.017678 0.000000 -0.017678\nv -0.017678 1.000000 -0.017678\nv -0.013889 0.000000 -0.020787\nv -0.013889 1.000000 -0.020787\nv -0.009567 0.000000 -0.023097\nv -0.009567 1.000000 -0.023097\nv -0.004877 0.000000 -0.024520\nv -0.004877 1.000000 -0.024520\nf 1 3 5\nf 2 6 4\nf 4 6 5\nf 1 5 7\nf 2 8 6\nf 6 8 7\nf 1 7 9\nf 2 10 8\nf 8 10 9\nf 1 9 11\nf 2 12 10\nf 10 12 11\nf 1 11 13\nf 2 14 12\nf 11 12 14\nf 1 13 15\nf 2 16 14\nf 14 16 15\nf 1 15 17\nf 2 18 16\nf 16 18 17\nf 1 17 19\nf 2 20 18\nf 17 18 20\nf 1 19 21\nf 2 22 20\nf 19 20 22\nf 1 21 23\nf 2 24 22\nf 22 24 23\nf 1 23 25\nf 2 26 24\nf 24 26 25\nf 1 25 27\nf 2 28 26\nf 25 26 28\nf 1 27 29\nf 2 30 28\nf 27 28 30\nf 1 29 31\nf 2 32 30\nf 30 32 31\nf 1 31 33\nf 2 34 32\nf 31 32 34\nf 1 33 35\nf 2 36 34\nf 33 34 36\nf 1 35 37\nf 2 38 36\nf 35 36 38\nf 1 37 39\nf 2 40 38\nf 37 38 40\nf 1 39 41\nf 2 42 40\nf 39 40 42\nf 1 41 43\nf 2 44 42\nf 41 42 44\nf 1 43 45\nf 2 46 44\nf 44 46 45\nf 1 45 47\nf 2 48 46\nf 46 48 47\nf 1 47 49\nf 2 50 48\nf 48 50 49\nf 1 49 51\nf 2 52 50\nf 50 52 51\nf 1 51 53\nf 2 54 52\nf 52 54 53\nf 1 53 55\nf 2 56 54\nf 53 54 56\nf 1 55 57\nf 2 58 56\nf 55 56 58\nf 1 57 59\nf 2 60 58\nf 58 60 59\nf 1 59 61\nf 2 62 60\nf 59 60 62\nf 1 61 63\nf 2 64 62\nf 62 64 63\nf 1 63 65\nf 2 66 64\nf 63 64 66\nf 1 65 3\nf 2 4 66\nf 65 66 4\nf 3 4 5\nf 5 6 7\nf 7 8 9\nf 9 10 11\nf 13 11 14\nf 13 14 15\nf 15 16 17\nf 19 17 20\nf 21 19 22\nf 21 22 23\nf 23 24 25\nf 27 25 28\nf 29 27 30\nf 29 30 31\nf 33 31 34\nf 35 33 36\nf 37 35 38\nf 39 37 40\nf 41 39 42\nf 43 41 44\nf 43 44 45\nf 45 46 47\nf 47 48 49\nf 49 50 51\nf 51 52 53\nf 55 53 56\nf 57 55 58\nf 57 58 59\nf 61 59 62\nf 61 62 63\nf 65 63 66\nf 3 65 4";

	/** Mesh of the cylinder */
	private static final Mesh cylinder;
	static {
		cylinder = new OBJBuilder(cylinderObj).createMesh();
	}
	
	@Override
	public void render(GL2 gl) {
		Transform t = getOwner().getTransform();
		gl.glPushMatrix();
			t.applyTransform(gl);

			renderCylinder(gl, 0, 1, 0);
			gl.glRotatef(90, 1, 0, 0);
			renderCylinder(gl, 0, 0, 1);
			gl.glRotatef(-90, 0, 0, 1);
			renderCylinder(gl, 1, 0, 0);
		gl.glPopMatrix();
	}

	/**
	 * Render the cylinder.
	 * @param gl
	 * @param r color red
	 * @param g color green
	 * @param b color blue
	 */
	private void renderCylinder(GL2 gl, float r, float g, float b) {
		gl.glColor3f(r, g, b);
		gl.glEnableClientState(GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL_FLOAT, 0, cylinder.getVertices());
		gl.glDrawArrays(cylinder.getMode(), 0, cylinder.getNumVerticies());
		gl.glDisableClientState(GL_VERTEX_ARRAY);
	}



}
