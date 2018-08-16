package spirit.metrics.calculate;

import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public interface IAttribute {
	
	public void calculate(ClassMetrics node);
	
	public void calculate(MethodMetrics node);
	
	public String getName();

}
