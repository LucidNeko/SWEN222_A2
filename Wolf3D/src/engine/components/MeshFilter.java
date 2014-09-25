package engine.components;

import engine.texturing.Mesh;

/**
 * A MeshFilter is the component you use to get at a Mesh on an Entity.
 * @author Hamish
 *
 */
public class MeshFilter extends Component {

	private Mesh mesh;

	public Mesh getMesh() {
//		if(mesh == null) mesh = new Mesh();
		return mesh;
	}
	
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}
	

}
