package spirit.core.smells.detectors;

import spirit.core.smells.CodeSmell;
import spirit.core.smells.LongMethod;
import spirit.core.smells.detectors.configurationByProject.LongMethodDetectionConfiguration;
import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.MethodMetrics;
import spirit.metrics.storage.NodeMetrics;

public class LongMethodDetector extends CodeSmellDetector {

	private LongMethodDetectionConfiguration metricsConfiguration;

	public LongMethodDetector(LongMethodDetectionConfiguration metricsConfiguration) {
		this.metricsConfiguration = metricsConfiguration;
	}

	@Override
	public boolean codeSmellVerify(NodeMetrics methodMetric) {
		if (methodMetric.getMetric(MetricNames.LOC) != null
				&& methodMetric.getMetric(MetricNames.LOC) > metricsConfiguration.getMLOC_VERY_HIGH()) {
			return true;
		}
		return false;
	}

	@Override
	public CodeSmell codeSmellDetected(NodeMetrics nodeMetrics) {
		return new LongMethod(((MethodMetrics) nodeMetrics));
	}

}
