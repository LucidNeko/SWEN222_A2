package wolf3d.window;

/**
 * The View is an interface for a View that is to be displayed at a regular interval from a gameloop.
 * @author Hamish Rae-Hodgson
 */
public interface View {
	
	/** Displays this view. A call to render itself. */
	public void display();

}
