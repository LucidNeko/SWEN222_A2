package engine.scratch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import engine.common.Mat44;
import engine.common.Mathf;
import engine.common.Quaternion;
import engine.common.Vec3;

public class Scratch {
	private static final Logger log = LogManager.getLogger();
	
	
	public static void main(String[] args) {
		Vec3 a = Vec3.UP;
		Vec3 b = Vec3.LEFT;
		
		log.trace(Mathf.radToDeg((float)Math.acos(Vec3.dot(a, b))));
		
		log.trace(new Quaternion(Vec3.UP, Mathf.degToRad(45)));
		
		//CCW
		Quaternion q1 = new Quaternion(Vec3.RIGHT, Mathf.degToRad(0));
		Quaternion q2 = new Quaternion(Vec3.RIGHT, Mathf.degToRad(180));
		
		for(float t = 0; t <= 1; t+=0.25) {
			Quaternion lerped = Quaternion.nlerp(q1, q2, t);
			log.trace("{} t={}", lerped, t);
			log.trace(lerped.toMatrix().mul(Vec3.UP));
		}
	}

}
