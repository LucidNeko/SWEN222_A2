package engine.core;

public interface Service {
	/**
	 *
	 * @return returns the name of the service
	 */
	public String getName();

	/**
	 * Notifying that the service has been executed
	 */
	public void execute();
}
