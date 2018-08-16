package spirit.metrics.calculate;

import java.util.List;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class CouplingDispersion implements IAttribute{
	
	@Override
	public void calculate(ClassMetrics node) {}

	@Override
	public String getName() {
		return MetricNames.CDISP;
	}

	@Override
	public void calculate(MethodMetrics node) {
		List<String> ListOfClassInvoked = (List<String>) node.getAttribute(MetricNames.ListOfClassInvoked);
		List<String> superClasses = (List<String>) node.getClassMetrics().getAttribute(MetricNames.SC);
		int numberOfclasses = 0;
		for(String clazz:ListOfClassInvoked){
			if(!superClasses.contains(clazz)){
				numberOfclasses++;
			}
		}
		if(node.getMetric(MetricNames.CINT)!=0){
			float cdisp = (float)numberOfclasses/(float)node.getMetric(MetricNames.CINT);
			node.setMetric(getName(), cdisp);
		}else{
			node.setMetric(getName(), 1);
		}
	}

}
