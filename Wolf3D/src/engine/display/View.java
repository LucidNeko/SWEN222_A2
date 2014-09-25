package engine.display;

import engine.components.Camera;

/**
 * The View is an interface for a View that is to be displayed at a regular interval from a gameloop.
 * @author Hamish
 */
public interface View {
	
	/** Displays this view. A call to render itself. */
	public void display();
	
	/** Sets the camera this view should use to render the world */
	public void setCamera(Camera camera);

}
