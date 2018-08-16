package spirit.metrics.calculate;

import java.util.List;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class ChangingMethods implements IAttribute{
	
	@Override
	public void calculate(ClassMetrics node) {}

	@Override
	public String getName() {
		return MetricNames.CM;
	}

	@Override
	public void calculate(MethodMetrics node) {
		if(node.getAttribute(MetricNames.ListOfMethodInvoking)!=null){
			List<String> listOfMethodInvoking = (List<String>) node.getAttribute(MetricNames.ListOfMethodInvoking);
			node.setMetric(getName(), listOfMethodInvoking.size());
		}
	}

}
