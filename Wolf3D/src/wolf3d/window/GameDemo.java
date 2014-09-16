package wolf3d.window;

import java.awt.event.KeyEvent;

import wolf3d.core.Entity;
import wolf3d.core.GameLoop;
import wolf3d.core.Keyboard;
import wolf3d.world.World;

public class GameDemo extends GameLoop {

	private static final int FPS = 60; //frames per second/regular updates per second.
	private static final int FUPS = 50; //fixed updates per second.
	
	private World world;
	private View view;
	
	public GameDemo(World world) {
		super(FPS, FUPS);
		this.world = world;
	}
	
	public void setView(View view) {
		this.view = view;
	}

	@Override
	protected void tick(float delta) {
		if(Keyboard.isKeyDown(KeyEvent.VK_ESCAPE))
			System.exit(0);
		
		Entity tri = world.getEntity(0);
		if(tri == null) return;
		
		if(Keyboard.isKeyDown(KeyEvent.VK_A))
			tri.getTransform().strafe(-0.1f);
		if(Keyboard.isKeyDown(KeyEvent.VK_D))
			tri.getTransform().strafe(0.1f);
		
		if(Keyboard.isKeyDown(KeyEvent.VK_W))
			tri.getTransform().walk(0.1f);
		if(Keyboard.isKeyDown(KeyEvent.VK_S))
			tri.getTransform().walk(-0.1f);
		
		if(Keyboard.isKeyDown(KeyEvent.VK_R))
			tri.getTransform().fly(0.1f);
		if(Keyboard.isKeyDown(KeyEvent.VK_F))
			tri.getTransform().fly(-0.1f);
		
		
		if(Keyboard.isKeyDown(KeyEvent.VK_I))
			tri.getTransform().pitch(-0.1f);
		if(Keyboard.isKeyDown(KeyEvent.VK_K))
			tri.getTransform().pitch(0.1f);
		
		if(Keyboard.isKeyDown(KeyEvent.VK_J))
			tri.getTransform().yaw(0.1f);
		if(Keyboard.isKeyDown(KeyEvent.VK_L))
			tri.getTransform().yaw(-0.1f);
		
		if(Keyboard.isKeyDown(KeyEvent.VK_U))
			tri.getTransform().roll(0.1f);
		if(Keyboard.isKeyDown(KeyEvent.VK_H))
			tri.getTransform().roll(-0.1f);
		
		
		if(Keyboard.isKeyDown(KeyEvent.VK_LEFT))
			tri.getTransform().strafeFlat(-0.1f);
		if(Keyboard.isKeyDown(KeyEvent.VK_RIGHT))
			tri.getTransform().strafeFlat(0.1f);
		
		if(Keyboard.isKeyDown(KeyEvent.VK_UP) && !Keyboard.isKeyDown(KeyEvent.VK_CONTROL))
			tri.getTransform().walkFlat(0.1f);
		if(Keyboard.isKeyDown(KeyEvent.VK_DOWN) && !Keyboard.isKeyDown(KeyEvent.VK_CONTROL))
			tri.getTransform().walkFlat(-0.1f);
		
		if(Keyboard.isKeyDown(KeyEvent.VK_UP) && Keyboard.isKeyDown(KeyEvent.VK_CONTROL))
			tri.getTransform().flyVertical(0.1f);
		if(Keyboard.isKeyDown(KeyEvent.VK_DOWN) && Keyboard.isKeyDown(KeyEvent.VK_CONTROL))
			tri.getTransform().flyVertical(-0.1f);
	}

	@Override
	protected void fixedTick(float delta) { }

	@Override
	protected void render() {
		if(view != null) view.display();
	}

}
