package engine.scratch;

import java.util.Iterator;

import javax.media.opengl.GL2;

import engine.common.Vec3;
import engine.components.Component;
import engine.components.GL2Renderer;
import engine.components.MeshFilter;
import engine.texturing.Mesh;

/**
 * 
 * @author Hamish
 *
 */
public class WireframeMeshRenderer extends Component {

	public void render(GL2 gl) {
		if(!requires(MeshFilter.class)) return;

		Mesh mesh = getOwner().getComponent(MeshFilter.class).getMesh();

		gl.glColor3f(1,1,1);
		Iterator<Vec3> iter = mesh.iterator();
		while(iter.hasNext()) {
			Vec3 a = iter.next();
			Vec3 b = iter.next();
			Vec3 c = iter.next();

			gl.glBegin(GL2.GL_LINES);
				gl.glVertex3f(a.x(), a.y(), a.z());
				gl.glVertex3f(b.x(), b.y(), b.z());

				gl.glVertex3f(b.x(), b.y(), b.z());
				gl.glVertex3f(c.x(), c.y(), c.z());

				gl.glVertex3f(c.x(), c.y(), c.z());
				gl.glVertex3f(a.x(), a.y(), a.z());
			gl.glEnd();
		}
	}

}
