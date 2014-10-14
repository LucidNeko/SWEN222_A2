package engine.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceLocator {
	private static final Logger log = LogManager.getLogger();

	private static Cache cache;

	static{
		cache = new Cache();
	}

	public static <E extends Service> E getService(Class<? extends E> service) {

		return cache.getService(service);

	}
//
//	public static <E extends Service> E getService(Class<? extends E> service) {
//		try {
//			return service.newInstance();
//		} catch (InstantiationException | IllegalAccessException e) {
//			log.error("Failed instantiating an instance of {}", service.getSimpleName());
//			throw new Error(e);
//		}
//	}

	public static void main(String[] args) {
		World world = ServiceLocator.getService(World.class);
	}

}
