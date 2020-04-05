package simulator.model;

import java.util.List;

import simulator.misc.Vector;

public class FallingToCenterGravity implements GravityLaws{
	
	private final static double g = 9.81;
	
	public FallingToCenterGravity() {
		
	}
	
	public void apply(List<Body> list) {		
		if (!list.isEmpty()) {
			for	(int i = 0; i < list.size(); i++) {
				Vector directionVector = list.get(i).getPosition().direction();
				list.get(i).setAcceleration(directionVector.scale(-g));
			}
		}
	}
	
	public String toString() {
		return "Falling to Center Gravity";
	}
}
