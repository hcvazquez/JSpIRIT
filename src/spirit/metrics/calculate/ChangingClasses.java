package spirit.metrics.calculate;

import java.util.List;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class ChangingClasses implements IAttribute{
	
	@Override
	public void calculate(ClassMetrics node) {}

	@Override
	public String getName() {
		return MetricNames.CC;
	}

	@Override
	public void calculate(MethodMetrics node) {
		if(node.getAttribute(MetricNames.ListOfClassInvoking)!=null){
			List<String> listOfClassInvoking = (List<String>) node.getAttribute(MetricNames.ListOfClassInvoking);
			node.setMetric(getName(), listOfClassInvoking.size());
		}
	}

}
