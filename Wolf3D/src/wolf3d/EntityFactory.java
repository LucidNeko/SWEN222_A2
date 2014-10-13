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
import wolf3d.components.Strength;
import wolf3d.components.Weight;
import wolf3d.components.behaviours.AILookAtController;
import wolf3d.components.behaviours.CameraScrollBackController;
import wolf3d.components.behaviours.DropItem;
import wolf3d.components.renderers.LightlessMeshRenderer;
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
import engine.input.Mouse;
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

//	public static Entity createPlayer(World world, String name) {
//		Mesh linkMesh = Resources.getMesh("link/young_link_s.obj");
//		Texture linkTex = Resources.getTexture("link/young_link.png", true);
//
//		//Create player
//		Entity player = world.createEntity("Player");
//		player.attachComponent(Camera.class);
////		player.attachComponent(PyramidRenderer.class);
//		player.attachComponent(MeshFilter.class).setMesh(linkMesh);
//		player.attachComponent(MeshRenderer.class).setMaterial(new Material(linkTex));
////		player.attachComponent(parser.getWallCollisionComponent());
////		player.attachComponent(WASDWalking.class);
//		player.attachComponent(CameraScrollBackController.class);
//		player.attachComponent(Health.class);
//		player.attachComponent(Inventory.class);
//		player.attachComponent(new DropItem(world));
//		return player;
//	}

	/**
	 * This creates a player with supplied ID.
	 * needed for networking.
	 * Note: This attaches a camera, so use it only for the local player.
	 * @param world
	 * @param name
	 * @param id
	 * @return
	 */
	public static Entity createPlayer(World world, String name, int id){

		Mesh linkMesh = Resources.getMesh("link/young_link_s.obj");
		Texture linkTex = Resources.getTexture("link/young_link.png", true);

		//Create player
//		Entity player = world.createEntity("Player", id);
		Entity player = world.createEntity(id, "Player");
		player.attachComponent(Camera.class);
//		player.attachComponent(PyramidRenderer.class);
		player.attachComponent(MeshFilter.class).setMesh(linkMesh);
		player.attachComponent(MeshRenderer.class).setMaterial(new Material(linkTex));
//		player.attachComponent(parser.getWallCollisionComponent());
//		player.attachComponent(WASDWalking.class);
		player.attachComponent(CameraScrollBackController.class);
		player.attachComponent(Health.class);
		player.attachComponent(Strength.class);
		player.attachComponent(Weight.class);
		player.attachComponent(Inventory.class);
		player.attachComponent(new DropItem(world));
		return player;
	}

	/**
	 * Create a player without all the stuff that the local player needs,
	 * i.e. camera etc.
	 * Use this method for creating the other players.
	 * @param world
	 * @param name
	 * @param id
	 * @return
	 */
	public static Entity createOtherPlayer(World world, String name, int id){


		Mesh linkMesh = Resources.getMesh("link/young_link_s.obj");
		Texture linkTex = Resources.getTexture("link/young_link.png", true);

		//Create player
//		Entity player = world.createEntity("Other Player", id);
		Entity player = world.createEntity(id, "Other Player");
//		player.attachComponent(Camera.class);
//		player.attachComponent(PyramidRenderer.class);
		player.attachComponent(MeshFilter.class).setMesh(linkMesh);
		player.attachComponent(MeshRenderer.class).setMaterial(new Material(linkTex));
//		player.attachComponent(parser.getWallCollisionComponent());
//		player.attachComponent(WASDWalking.class);
	//	player.attachComponent(CameraScrollBackController.class);
	//	player.attachComponent(Health.class);
	//	player.attachComponent(Inventory.class);
	//	player.attachComponent(new DropItem(world));
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
//				cam.lookInDirection(at.getLook());
				cam.walk(0.25f);

				float dx = Mouse.getDX(); //calls to getDX/getDY wipe out the value.. TODO logical fix?
				float dy = Mouse.getDY();
				cam.pitch(Mathf.degToRad(dy*10*delta));
				cam.rotateY(Mathf.degToRad(dx*10*delta));

				at.lookInDirection(cam.getLook());

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
//				gl.glLightfv(GL_LIGHT0, GL_POSITION, new float[] {0, 0, 0, 1}, 0); //1 signifies positional light
//				gl.glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, new float[]{ 0,0,1}, 0); //light direction is forwards
			}

		});
		return camera;
	}

	public static Entity createThirdPersonTrackingCamera(World world, final Entity target) {
		final Entity camera = world.createEntity("Camera");
		final float near = 0.8f; //0.8f;
		final float far = 1f; //1f;
		camera.attachComponent(new Behaviour() {

			@Override
			public void update(float delta) {
				Transform at = target.getTransform();
				Transform cam = getOwner().getTransform();

//				float dy = Mouse.getDY();
//				cam.fly(dy*0.5f*delta);
//				cam.lookAt(at);

				cam.lookInDirection(at.getPosition().sub(cam.getPosition()));

				float length = cam.getPosition().sub(at.getPosition()).length();
				if(length > far) cam.walk(7*(1-(1/length))*delta);
				else if(length < near) cam.walk(-2*delta);
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
//				gl.glLightfv(GL_LIGHT0, GL_POSITION, new float[] {0, 0, 0, 1}, 0); //1 signifies positional light
//				gl.glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, new float[]{ 0,0,1}, 0); //light direction is forwards
			}

		});
		camera.getTransform().setPosition(target.getTransform().getPosition().add(target.getTransform().getLook().mul(25))); //start far away so it zooms in



		target.attachComponent(new Behaviour() {

			@Override
			public void update(float delta) {
				float dx = Mouse.getDX(); //calls to getDX/getDY wipe out the value.. TODO logical fix?
//				float dy = Mouse.getDY();
//				target.getTransform().pitch(Mathf.degToRad(dy*10*delta));
				target.getTransform().rotateY(Mathf.degToRad(dx*10*delta));
			}

		});

		return camera;
	}

	public static Entity createSkybox(World world, final Entity target) {
		Entity skybox = world.createEntity("skybox");
		skybox.attachComponent(MeshFilter.class).setMesh(Resources.getMesh("skybox.obj"));
		skybox.attachComponent(LightlessMeshRenderer.class).setMaterial(new Material(Resources.getTexture("skybox3.png", true)));
		skybox.attachComponent(new Behaviour() {
			//moves the box around with player so they can't come close to the edges.
			@Override
			public void update(float delta) {
				Vec3 pos = target.getTransform().getPosition();
				this.getOwner().getTransform().setPosition(pos);
			}
		});
		skybox.attachComponent(new Behaviour() {
			private float time = 0;
			private float duration = 10;
			private Texture[] textures = {
				Resources.getTexture("skybox.png", true),
				Resources.getTexture("skybox3.png", true),
				Resources.getTexture("skybox2.jpg", true),
				Resources.getTexture("skybox3.png", true),
			};

			public void update(float delta) {
				requires(LightlessMeshRenderer.class);

				time += delta;

				getOwner().getComponent(LightlessMeshRenderer.class).getMaterial().setTexture(textures[(int)((time/duration) % textures.length)]);
			}
		});
		return skybox;
	}

}
