package wolf3d;

import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;

import javax.media.opengl.GL2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.components.Health;
import wolf3d.components.Inventory;
import wolf3d.components.behaviours.AILookAtController;
import wolf3d.components.behaviours.CameraScrollBackController;
import wolf3d.components.behaviours.MouseLookController;
import wolf3d.components.renderers.PyramidRenderer;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Camera;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.components.Renderer;
import engine.components.Transform;
import engine.core.Entity;
import engine.core.World;
import engine.texturing.Material;
import engine.texturing.Mesh;
import engine.texturing.Texture;
import engine.util.Resources;

public class EntityFactory {
	private static final Logger log = LogManager.getLogger();
	
	public static final int PLAYER = 1;
	
	public static Entity create(int type, World world, String name) {
		if(type == PLAYER) {
			return createPlayer(world, name);
		} else {
			log.error("UnKnowen entity type {}", type);
			log.error("Returning blank Entity");
			return world.createEntity(name);
		}
	}
	
	public static Entity createPlayer(World world, String name) {
		Mesh linkMesh = Resources.getMesh("link/young_link_s.obj");
		Texture linkTex = Resources.getTexture("link/young_link.png", true);

		//Create player
		Entity player = world.createEntity("Player");
		player.attachComponent(Camera.class);
//		player.attachComponent(PyramidRenderer.class);
		player.attachComponent(MeshFilter.class).setMesh(linkMesh);
		player.attachComponent(MeshRenderer.class).setMaterial(new Material(linkTex));
//		player.attachComponent(parser.getWallCollisionComponent());
//		player.attachComponent(WASDWalking.class);
		player.attachComponent(MouseLookController.class);
		player.attachComponent(CameraScrollBackController.class);
		player.attachComponent(Health.class);
		//Testing pickup behavior
		player.attachComponent(Inventory.class);
		player.attachComponent(new Renderer() {

			@Override
			public void render(GL2 gl) {
				//because renderering like a scenegraph (0,0,0) is transformed to the entities position.
				gl.glLightfv(GL_LIGHT0, GL_POSITION, new float[] {0, 0, 0, 1}, 0); //1 signifies positional light
			}

		});
		return player;
	}
	
	public static Entity createCamera(World world, final Entity target) {
		Entity camera = world.createEntity("Camera");
		camera.attachComponent(new Behaviour() {

			@Override
			public void update(float delta) {
				Transform at = target.getTransform();
				Transform cam = getOwner().getTransform();
				
				cam.lookAt(at);
				
				float distance = at.getPosition().sub(cam.getPosition()).length();
				
				if(distance < 1) {
					cam.walk(-2*delta);
				} else if(distance > 1.1f) {
					cam.walk(2*delta);
				}
				
				float radians = Vec3.dot(at.getLook(), cam.getLook());
				
//				log.trace("{} {} {}", at.getLook(), cam.getLook(), radians);
				
				if(radians < 0.98f) {
					float sign = cam.getLook().x() < 0 ? -1 : 1;
					cam.strafe(sign*1*delta);
				}
					
			}
			
		});
		camera.attachComponent(Camera.class);
		camera.attachComponent(PyramidRenderer.class);
		return camera;
	}

}
