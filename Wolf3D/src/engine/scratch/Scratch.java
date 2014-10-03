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
		
		//CCW
		Quaternion q1 = new Quaternion();
		Quaternion q2 = Quaternion.createRotation(Mathf.degToRad(180), 1, 0, 0);
		
		for(float t = 0; t <= 1; t+=0.25) {
			Quaternion lerped = Quaternion.nlerp(q1, q2, t);
			log.trace("{} t={}", lerped, t);
			log.trace(lerped.toMatrix().mul(Vec3.UP));
			Vec3 axis = Vec3.UP.add(Vec3.RIGHT).add(Vec3.FORWARD);
			axis.normalize();
			log.trace(Quaternion.mul(lerped, Vec3.UP));
			log.trace(Quaternion.mul2(lerped, Vec3.UP));
		}
		
		log.trace("");


		benchMul();
		benchMul2();
		benchMul();
		benchMul2();
		benchMul();
		benchMul2();

	}
	
	private static void benchMul() {
		Vec3 v = new Vec3(1, 1, 1);
		v.mulLocal(Mathf.random());
		v.normalize();
		long start = System.nanoTime();
		for(int i = 0; i < 10000000; i++) {
			Quaternion q = Quaternion.createRotation(Mathf.degToRad(6), v.x(), v.y(), v.z());
			v = Quaternion.mul(q, v);
			v.normalize(); //have to normalize because of drift!
		}
		log.trace("benchMul -> v={} : {}s", v, (System.nanoTime()-start)*0.000000001f);
	}
	
	private static void benchMul2() {
		Vec3 v = new Vec3(1, 1, 1);
		v.mulLocal(Mathf.random());
		v.normalize();
		long start = System.nanoTime();
		for(int i = 0; i < 10000000; i++) {
			Quaternion q = Quaternion.createRotation(Mathf.degToRad(6), v.x(), v.y(), v.z());
			v = Quaternion.mul2(q, v);
//			v.normalize();
		}
		log.trace("benchMul2-> v={} : {}s", v, (System.nanoTime()-start)*0.000000001f);
	}

}
