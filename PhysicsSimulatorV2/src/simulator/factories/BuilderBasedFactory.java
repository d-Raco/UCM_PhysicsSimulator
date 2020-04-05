package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {

	private List<JSONObject> josnObjs;
	private List<Builder<T>> builders;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this.builders = builders;
		josnObjs = new ArrayList<>();
		for(int i = 0; i < builders.size(); ++i)
			josnObjs.add(builders.get(i).getBuilderInfo());
	}

	public T createInstance(JSONObject info) {
		T instance = null;
		for(int i = 0; i < builders.size() && instance == null; ++i)
			instance = builders.get(i).createInstance(info);
		return instance;
	}

	public List<JSONObject> getInfo() {
		return josnObjs;
	}

}