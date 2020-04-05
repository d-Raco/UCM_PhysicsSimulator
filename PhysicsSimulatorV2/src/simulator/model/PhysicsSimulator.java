package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {
	
	private List<Body> bodies;
	private List<SimulatorObserver> observers;
	private double time, dt;
	private GravityLaws gravity;
	
	public PhysicsSimulator(GravityLaws g, double time) {
		this.time = 0;
		gravity = g;
		dt = time;
		bodies = new ArrayList<>();	
		observers = new ArrayList<>();	
		
		if(gravity == null) {
			throw new IllegalArgumentException();
		}
	}
	
	public void addBody(Body body) {
		boolean equals = false;
		for(int i = 0; i < bodies.size() && !equals; ++i) {
			if(bodies.get(i).getId().equals(body.getId())) {
				equals = true;
			}
		}
		if(equals)
			throw new IllegalArgumentException();
		else
			bodies.add(body);
		for(int i = 0; i < observers.size(); ++i)
			observers.get(i).onBodyAdded(bodies, body);
	}
	
	public void advance() {
		gravity.apply(bodies);
		for(Body b : bodies)
			b.move(dt);
		time += dt;
		for(int i = 0; i < observers.size(); ++i)
			observers.get(i).onAdvance(bodies, dt);
	}
	
	public String toString() {
		String str = "{ \"time\": " + time + ", \"bodies\": [" + bodies.get(0);
		for(int i = 1; i < bodies.size(); ++i) {
			str += ", " + bodies.get(i);
		}
		str += "] }";
		return str;
	}
	
	public void reset() {
		bodies.clear();
		dt = 0.0;
		for(int i = 0; i < observers.size(); ++i)
			observers.get(i).onReset(bodies, time, dt, gravity.toString());
	}
	
	public void setDeltaTime(double dt) {
		if(dt < 0)
			throw new IllegalArgumentException();
		else
			this.dt = dt;
		for(int i = 0; i < observers.size(); ++i)
			observers.get(i).onDeltaTimeChanged(dt);
	}
	
	public void setGravityLaws(GravityLaws gravityLaws) {
		if(gravityLaws == null)
			throw new IllegalArgumentException();
		else
			gravity = gravityLaws;
		for(int i = 0; i < observers.size(); ++i)
			observers.get(i).onGravityLawChanged(gravity.toString());
	}
	
	public void addObserver(SimulatorObserver o) {
		if(!observers.contains(o)) {
			observers.add(o);
			o.onRegister(bodies, time, dt, gravity.toString());
		}
	}
 }
