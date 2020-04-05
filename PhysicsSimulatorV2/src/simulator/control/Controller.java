package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	
	private Factory<Body> factory;
	private Factory<GravityLaws> gravityLawsFactory;
	private PhysicsSimulator physics;
	
	public Controller(PhysicsSimulator p, Factory<Body> f, Factory<GravityLaws> g){
		factory = f;
		physics = p;
		gravityLawsFactory = g;
	}
	
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		p.print("{ \"states\": [");
		p.println(physics);
		for(int i = 1; i < n; ++i) {
			p.print(", ");
			physics.advance();
			p.println(physics);
		}
		p.print("] }");
	}
	
	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		
		JSONArray bodies = jsonInput.getJSONArray("bodies");
		
		for (int i = 0; i < bodies.length(); i++) 
			physics.addBody(factory.createInstance(bodies.getJSONObject(i)));
	}
	
	public void reset() {
		physics.reset();
	}
	
	public void setDeltaTime(double dt) {
		physics.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		physics.addObserver(o);
	}
	
	public void run(int n) {
		for(int i = 0; i < n; ++i) 
			physics.advance();
	}
	
	public Factory<GravityLaws> getGravityLawsFactory(){
		return gravityLawsFactory;
	}
	
	public void setGravityLaws(JSONObject info) {
		physics.setGravityLaws(gravityLawsFactory.createInstance(info));
	}
}
