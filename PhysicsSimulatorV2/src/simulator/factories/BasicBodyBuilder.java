package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body>{

	public BasicBodyBuilder() {
		super.type = "basic";
		super.desc = "Basic Body";
	}
	
	protected Body createTheInstance(JSONObject info) {
		return new Body(info.getString("id"), new Vector(jsonArrayTodoubleArray(info.getJSONArray("vel")))
				, new Vector(info.getJSONArray("pos").length()) , new Vector(jsonArrayTodoubleArray(info.getJSONArray("pos"))),
				info.getDouble("mass"));	
	}

	protected JSONObject createData() {
		JSONObject jo1 = new JSONObject();
		
		jo1.put("id", "The body's identifier");
		jo1.put("pos", "The body's position coordinates");
		jo1.put("vel", "The body's velocity");
		jo1.put("mass", "The body's mass");
		
		return jo1;
	}
}
