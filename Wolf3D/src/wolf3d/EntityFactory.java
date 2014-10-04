package wolf3d;

import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT1;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPOT_DIRECTION;

import javax.media.opengl.GL2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolf3d.components.Health;
import wolf3d.components.Inventory;
import wolf3d.components.behaviours.AILookAtController;
import wolf3d.components.behaviours.CameraScrollBackController;
import wolf3d.components.behaviours.DropItem;
import wolf3d.components.behaviours.MouseLookController;
import wolf3d.components.renderers.PyramidRenderer;
import engine.common.Mathf;
import engine.common.Vec3;
import engine.components.Behaviour;
import engine.components.Camera;
import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.components.GL2Renderer;
import engine.components.Transform;
import engine.core.Entity;
import engine.core.World;
import engine.texturing.Material;
import engine.texturing.Mesh;
import engine.texturing.Texture;
import engine.util.Resources;

/**
 * 
 * @author Hamish
 *
 */
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
//		player.attachComponent(Health.class);
		player.attachComponent(Inventory.class);
		player.attachComponent(new DropItem(world));
		return player;
	}
	
	public static Entity createFirstPersonCamera(World world, final Entity target) {
		Entity camera = world.createEntity("Camera");
		camera.attachComponent(new Behaviour() {

			@Override
			public void update(float delta) {
				Transform at = target.getTransform();
				Transform cam = getOwner().getTransform();
				
				cam.setPosition(at.getPosition());
				cam.lookInDirection(at.getLook());
				cam.walk(0.25f);

//				cam.lookInDirection(at.getPosition().sub(cam.getPosition()));
//				float length = cam.getPosition().sub(at.getPosition()).length();
//				if(length > 1f) cam.walk(7*(1-(1/length))*delta);
//				Vec3 atLook = at.getLook();
//				atLook.setY(0);
//				atLook.normalize();
//				Vec3 camLook = cam.getLook();
//				camLook.setY(0);
//				camLook.normalize();
//				float dot = atLook.x()*(-camLook.z()) + atLook.z()*camLook.x();
//				
//				float sign = dot < 0 ? -1: 1;
//				
//				if(Vec3.dot(camLook, atLook) < 0.99f) cam.strafe(sign*10*Mathf.abs(dot)*delta); 
			}
			
		});
		camera.attachComponent(Camera.class);
		camera.attachComponent(PyramidRenderer.class);
		camera.attachComponent(new GL2Renderer() {

			@Override
			public void render(GL2 gl) {
				//because renderering like a scenegraph (0,0,0) is transformed to the entities position.
				Transform t = getOwner().getTransform();
				Vec3 look = t.getLook();
				gl.glLightfv(GL_LIGHT1, GL_POSITION, new float[] {0, 0, 0, 1}, 0); //1 signifies positional light
				gl.glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, new float[]{ 0,0,1}, 0); //light direction is forwards
			} 

		});
		return camera;
	}
	
	public static Entity createThirdPersonTrackingCamera(World world, final Entity target) {
		Entity camera = world.createEntity("Camera");
		camera.attachComponent(new Behaviour() {

			@Override
			public void update(float delta) {
				Transform at = target.getTransform();
				Transform cam = getOwner().getTransform();
				
				cam.lookInDirection(at.getPosition().sub(cam.getPosition()));
				float length = cam.getPosition().sub(at.getPosition()).length();
				if(length > 1f) cam.walk(7*(1-(1/length))*delta);
				else if(length < 0.8f) cam.walk(-2*delta);
				Vec3 atLook = at.getLook();
				atLook.setY(0);
				atLook.normalize();
				Vec3 camLook = cam.getLook();
				camLook.setY(0);
				camLook.normalize();
				float dot = atLook.x()*(-camLook.z()) + atLook.z()*camLook.x();
				
				float sign = dot < 0 ? -1: 1;
				
				float theta = (float) Math.acos(Vec3.dot(atLook, camLook));
				
				//if(Vec3.dot(camLook, atLook) < 0.995f) cam.strafe(sign*10*Mathf.abs(dot)*delta); 
				if(Mathf.radToDeg(theta) > 1) {
					cam.strafe(10*sign*theta*delta);
				}
			}
			
		});
		camera.attachComponent(Camera.class);
		camera.attachComponent(PyramidRenderer.class);
		camera.attachComponent(new GL2Renderer() {

			@Override
			public void render(GL2 gl) {
				//because renderering like a scenegraph (0,0,0) is transformed to the entities position.
				Transform t = getOwner().getTransform();
				Vec3 look = t.getLook();
				gl.glLightfv(GL_LIGHT1, GL_POSITION, new float[] {0, 0, 0, 1}, 0); //1 signifies positional light
				gl.glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, new float[]{ 0,0,1}, 0); //light direction is forwards
			} 

		});
		camera.getTransform().setPosition(target.getTransform().getPosition().add(target.getTransform().getLook().mul(25))); //start far away so it zooms in
		return camera;
	}
	
	public static Entity createSun(World world) {
		final Vec3 target = new Vec3(10, 0, 10);
		Entity sun = world.createEntity("Sun");
		sun.getTransform().translate(0, 0, -1);
		sun.attachComponent(new Behaviour() {
			public void update(float delta) {
				Transform t = getOwner().getTransform();
				t.lookAt(target, t.getUp());
				t.fly(1f*delta);
			}
		});
		sun.attachComponent(new GL2Renderer() {
			public void render(GL2 gl) {
				gl.glLightfv(GL_LIGHT0, GL_POSITION, new float[] {0, 0, 0, 1}, 0); //1 signifies positional light
			}
		});
		sun.attachComponent(PyramidRenderer.class);
		return sun;
	}

}
