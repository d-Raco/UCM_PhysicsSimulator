package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Builder<T> {
	
	protected String type;
	protected String desc;
	
	public Builder() {
		
	}
	
	protected double[] jsonArrayTodoubleArray(JSONArray array) {
		double[] arr = new double[array.length()];
		for(int j = 0; j < array.length(); j++){
            arr[j] = array.getDouble(j);
        }
		return arr;
	}
	
	public T createInstance(JSONObject info) {
		
		if(info.getString("type").equals(type)) {
			return createTheInstance(info.getJSONObject("data"));
		}
		else
			return null;
	}
	
	public JSONObject getBuilderInfo() {
		JSONObject jo = new JSONObject();
		jo.put("type", type);
		jo.put("data", createData());
		jo.put("desc", desc);
		return jo;
		
	}
	
	protected JSONObject createData() {
		return new JSONObject();
	}
	
	abstract protected T createTheInstance(JSONObject info);
}
