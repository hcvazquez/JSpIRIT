package spirit.metrics.calculate;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class LocalityOfAttributesAccesses implements IAttribute{
	
	@Override
	public void calculate(ClassMetrics node) {
	}

	@Override
	public String getName() {
		return MetricNames.LAA;
	}

	@Override
	public void calculate(MethodMetrics node) {
		float laa = 1;
		if(node.getMetric(MetricNames.ATLD)!=null && node.getMetric(MetricNames.ATFD)!=null){
			if(node.getMetric(MetricNames.ATLD)+node.getMetric(MetricNames.ATFD)!=0){
				laa = node.getMetric(MetricNames.ATLD)/(node.getMetric(MetricNames.ATLD)+node.getMetric(MetricNames.ATFD));
			}
		}
		node.setMetric(getName(),laa);
	}
	
}
