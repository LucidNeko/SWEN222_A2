package engine.core;

public class ServiceLocator {
	private static Cache cache;

	static{
		cache = new Cache();
	}

	public static Service getService(String jndiName){

		Service service = cache.getService(jndiName);

		if(service != null){
			return service;
		}

		InitialContex contex = new InitialContex();
		Service service1 = (Service) contex.lookup(jndiName);
		cache.addService(service1);
		return service1;
	}

}
