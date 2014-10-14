package engine.core;

public class InitialContex {
	public Object lookup(String jndiName){
	      if(jndiName.equalsIgnoreCase("WORLD")){
	         System.out.println("Looking up and creating a new World object");
	         return new World();
	      }
	      return null;
	}
}
