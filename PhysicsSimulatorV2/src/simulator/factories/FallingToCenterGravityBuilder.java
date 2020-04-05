package simulator.factories;

import org.json.JSONObject;

import simulator.model.FallingToCenterGravity;
import simulator.model.GravityLaws;

public class FallingToCenterGravityBuilder extends Builder<GravityLaws>{
	
	public FallingToCenterGravityBuilder() {
		super.type = "ftcg";
		super.desc = "Falling to Center Gravity";
	}
	
	protected GravityLaws createTheInstance(JSONObject info) {
		return new FallingToCenterGravity();
	}
}
