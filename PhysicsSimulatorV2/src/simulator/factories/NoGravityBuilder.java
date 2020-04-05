package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws>{

	public NoGravityBuilder() {
		super.type = "ng";
		super.desc = "No Gravity";
	}
	
	protected GravityLaws createTheInstance(JSONObject info) {
		return new NoGravity();
	}
}
