package engine.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cache {
	private static final Logger log = LogManager.getLogger();

	private List<Service> services;

	public Cache(){
		services = new ArrayList<Service>();
	}

	@SuppressWarnings("unchecked")
	public <E extends Service> E getService(Class<? extends E> service){
	      for (Service s : services) {
	    	  if(s.getClass() == service){
	            System.out.println("Returning cached  "+service+" object");
	            return (E)s;
	         }
	      }
	      return addService(service);
	   }

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

}
