package engine.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This Locates and returns the service and instantiates and saves
 * the service to the cache if there is no service in the cache
 * @author Sameer Magan 300223776
 *
 */
public class ServiceLocator {
	private static final Logger log = LogManager.getLogger();

	private static Cache cache;

	static{
		cache = new Cache();
	}

	/**
	 * This gets the instantiated service provided, if there is not already an
	 * instantiated service it instantiates it and returns
	 * @param service the service that you want to get
	 * @return the instantiated service
	 */
	public static <E extends Service> E getService(Class<? extends E> service) {
		return cache.getService(service);
	}
}
