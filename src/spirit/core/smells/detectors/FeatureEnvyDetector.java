package spirit.core.smells.detectors;

import spirit.core.smells.CodeSmell;
import spirit.core.smells.FeatureEnvy;
import spirit.core.smells.detectors.configurationByProject.FeatureEnvyDetectionConfiguration;
import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.MethodMetrics;
import spirit.metrics.storage.NodeMetrics;

public class FeatureEnvyDetector extends CodeSmellDetector{
	private FeatureEnvyDetectionConfiguration metricConfiguration;
	public FeatureEnvyDetector(FeatureEnvyDetectionConfiguration metricConfiguration) {
		this.metricConfiguration=metricConfiguration;
	}
	
	public boolean codeSmellVerify(NodeMetrics methodMetric){
		if(methodMetric.getMetric(MetricNames.ATFD)!=null && methodMetric.getMetric(MetricNames.ATFD) > metricConfiguration.getATFD_Greater_Few() &&
		   methodMetric.getMetric(MetricNames.LAA)!=null && methodMetric.getMetric(MetricNames.LAA) < metricConfiguration.getLAA_Less_OneThird() &&
		   methodMetric.getMetric(MetricNames.FDP)!=null && methodMetric.getMetric(MetricNames.FDP) <= metricConfiguration.getFDP_LessEqual_FEW()){
		   
			return true;
		}
		return false;	
	}
		
	public CodeSmell codeSmellDetected(NodeMetrics methodMetric){
		return new FeatureEnvy(((MethodMetrics) methodMetric));
	}
	
}
