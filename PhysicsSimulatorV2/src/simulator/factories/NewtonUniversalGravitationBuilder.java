package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws>{

	public NewtonUniversalGravitationBuilder() {
		super.type = "nlug";
		super.desc = "Newton's law of universal gravitation";
	}
	
	protected GravityLaws createTheInstance(JSONObject info) {
		return new NewtonUniversalGravitation();	
	}
}