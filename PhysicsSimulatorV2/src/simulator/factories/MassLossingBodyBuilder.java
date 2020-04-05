package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLossingBodyBuilder extends Builder<Body>{
	
	public MassLossingBodyBuilder() {
		super.type = "mlb";
		super.desc = "Mass Losing Body";
	}
	
	protected Body createTheInstance(JSONObject info) {
		return new MassLossingBody(info.getString("id"), new Vector(jsonArrayTodoubleArray(info.getJSONArray("vel")))
				, new Vector(info.getJSONArray("pos").length()) , new Vector(jsonArrayTodoubleArray(info.getJSONArray("pos"))),
				info.getDouble("mass"), info.getDouble("factor"), info.getDouble("freq"));
	}
	
	protected JSONObject createData() {
		JSONObject jo1 = new JSONObject();
		
		jo1.put("id", "The body's identifier");
		jo1.put("pos", "The body's position coordinates");
		jo1.put("vel", "The body's velocity");
		jo1.put("mass", "The body's mass");
		jo1.put("freq", "The frequency with which a body loses its mass ");
		jo1.put("factor", "The mass factor it loses each time");
		
		return jo1;
	}
}
