package engine.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is responsible for storing
 * the current service that has been instantiated
 * @author Sameer Magan 300223776
 *
 */
public class Cache {
	private static final Logger log = LogManager.getLogger();

	private List<Service> services;

	public Cache(){
		services = new ArrayList<Service>();
	}

	/**
	 *
	 * @param service the service to be looked up in cache
	 * @return returns the instance of the service provided
	 */
	@SuppressWarnings("unchecked")
	public <E extends Service> E getService(Class<? extends E> service){
	      for (Service s : services) {
	    	  if(s.getClass() == service){
	            return (E)s;
	         }
	      }
	      return addService(service);
	   }

	/**
	 * Instantiates the given service and adds it to the services array
	 * @param newService the service to be instantiated
	 * @return returns the newly Instantiated service
	 */
	@SuppressWarnings("unchecked")
	private <E extends Service> E addService(Class<? extends Service> newService){
	      boolean exists = false;
	      for (Service s : services) {
	    	  if(s.getClass() == newService){
	            exists = true;
	         }
	      }
	      if(!exists){
	    	 try {

				E service = (E) newService.newInstance();
				services.add(service);
				return service;
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("Failed instantiating an instance of {}", newService.getSimpleName());
				throw new Error(e);
			}
	      }
	      throw new Error("This broke in adding a new Service");
	   }

	/**
	 * Instantiates the given service and adds it to the services array
	 * @param newService the service to be instantiated
	 * @return returns the newly Instantiated service
	 */
	public void registerService(Service newService){
		for(Service s: services){
			if(newService.getClass() == s.getClass()){
				services.remove(s);
			}
		}
		services.add(newService);
	}


}
