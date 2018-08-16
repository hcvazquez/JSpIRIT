package spirit.metrics.calculate;

import java.util.List;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class AverageMethodWeight implements IAttribute{
	
	@Override
	public void calculate(ClassMetrics node) {
		float avgMethodWeight = 1;
		List<MethodMetrics> methods = node.getMethodsMetrics();
		for(MethodMetrics method:methods){
			if(method.getMetric(MetricNames.WMC)!=null){
				avgMethodWeight = avgMethodWeight + method.getMetric(MetricNames.WMC);
			}
		}
		if(methods.size()!=0){
			avgMethodWeight = avgMethodWeight / (float)methods.size();
		}
		node.setMetric(getName(), avgMethodWeight);
	}

	@Override
	public String getName() {
		return MetricNames.AMW;
	}

	@Override
	public void calculate(MethodMetrics node) {

	}

}
