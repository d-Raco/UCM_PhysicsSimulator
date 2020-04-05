package simulator.model;

import java.util.List;

import simulator.misc.Vector;

public class NewtonUniversalGravitation implements GravityLaws{
	
	private final static double G = 6.67E-11;
	
	public NewtonUniversalGravitation() {
		
	}
	
	public void apply(List<Body> list) {
		Vector ForceVector;
		
		if (!list.isEmpty()) {
			for	(int i = 0; i < list.size(); i++) {
				if(list.get(i).getMass() != 0) {
					ForceVector = new Vector(list.get(0).getPosition().dim());
					for(int j = 0; j < list.size(); j++) {
						if(j != i) {
							Vector directionVector = new Vector(list.get(j).getPosition()).minus(new Vector(list.get(i).getPosition())).direction();
							double force = G * (list.get(i).getMass() * list.get(j).getMass()) / Math.pow(new Vector(list.get(i).getPosition()).distanceTo(new Vector(list.get(j).getPosition())), 2);
							ForceVector = ForceVector.plus(directionVector.scale(force));
						}
					}
					list.get(i).setAcceleration(ForceVector.scale(1/list.get(i).getMass()));
				}
				else {
					Vector v = new Vector(list.get(0).getPosition().dim());
					list.get(i).setAcceleration(v);
					list.get(i).setVelocity(v);
				}
			}
		}	
	}
	
	public String toString() {
		return "Newton's law of universal gravitation";
	}

}
