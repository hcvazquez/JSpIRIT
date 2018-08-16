package spirit.metrics.storage;

import java.util.HashMap;
import java.util.Set;

public abstract class NodeMetrics {
	
	private HashMap<String,Float> metrics = new HashMap<String,Float>();
	private HashMap<String,Object> attributes = new HashMap<String,Object>();
	
	public HashMap<String, Float> getMetrics() {
		return metrics;
	}

	public void setMetrics(HashMap<String, Float> metrics) {
		this.metrics = metrics;
	}
	
	public Float getMetric(String metric) {
		return metrics.get(metric);
	}

	public void setMetric(String metric, float valor) {
		metrics.put(metric, Float.valueOf(valor));
	}
	
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	public void setAttribute(String name, Object attribute) {
		attributes.put(name, attribute);
	}
	
	//DEBUG Console Method
	public void printDebug(){
		Set<String> keys = metrics.keySet();
		System.out.println("----- NodeMetrics -----");
		for(String key:keys){
			System.out.println(key+" : "+metrics.get(key));	
		}
		System.out.println("----- ----------- -----");
	}
	
	//DEBUG Console Method
	public void printDebugAttr(){
		Set<String> keys = metrics.keySet();
		System.out.println("----- NodeAttributes -----");
		for(String key:keys){
			System.out.println(key+" : "+attributes.get(key));	
		}
		System.out.println("----- ----------- -----");
	}

}
