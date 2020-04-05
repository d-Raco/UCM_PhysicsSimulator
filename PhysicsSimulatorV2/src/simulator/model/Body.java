package simulator.model;

import simulator.misc.Vector;

public class Body {
	protected double mass;
	private String id;
	private Vector velocity, acceleration, position;
	public Body(String str, Vector v, Vector a, Vector p, double mass) {
		this.mass = mass;
		id = str;
		velocity = v;
		acceleration = a;
		position = p;
	}
	
	public String getId() {
		// returns the body’s identifier.
		return id;
	}
	
	public Vector getVelocity() {
		//returns a copy of the velocity vector.
		return velocity;
	}
	
	public Vector getAcceleration() {
		//returns a copy of the acceleration vector.
		return acceleration;
	}
	
	public Vector getPosition() {
		//returns a copy of the position vector.
		return position;
	}
	
	public double getMass() {
		return mass;
	}
	
	void setVelocity(Vector v) {
		//sets the velocity vector to a copy of parameter v.
		velocity = v;
	}
	
	void setAcceleration(Vector a) {
		//sets the acceleration vector to a copy of parameter a.
		acceleration = a;
	}
	
	void setPosition(Vector p) {
		//sets the position vector to a copy of parameter p.
		position = p;
	}
	
	void move(double t) {
		//moves the body for t seconds using the current attributes,
		//v = v + a*t  && pos = pos + v*t + 1/2*a*t^2
		position = position.plus(velocity.scale(t).plus(acceleration.scale(1/2 * Math.pow(t, 2))));
		velocity = velocity.plus(acceleration.scale(t));
	}
	
	public String toString() {
		String str = "{ \"id\": \""+ id + "\", \"mass\": " + mass + ", \"pos\": " + position + ", \"vel\": " + velocity + 
				", \"acc\": " + acceleration + " }";
		return str;
	}
}
