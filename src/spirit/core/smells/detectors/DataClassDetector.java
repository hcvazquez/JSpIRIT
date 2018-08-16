package spirit.core.smells.detectors;

import spirit.core.smells.CodeSmell;
import spirit.core.smells.DataClass;
import spirit.core.smells.detectors.configurationByProject.DataClassDetectionConfiguration;
import spirit.metrics.constants.MetricNames;

import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.NodeMetrics;

public class DataClassDetector extends CodeSmellDetector{
	private DataClassDetectionConfiguration metricConfiguration;
	public DataClassDetector(DataClassDetectionConfiguration metricConfiguration) {
		this.metricConfiguration=metricConfiguration;
	}
	public boolean codeSmellVerify(NodeMetrics classMetrics){
		if(classMetrics.getMetric(MetricNames.NOAM)!=null && classMetrics.getMetric(MetricNames.NOPA)!=null){
			int sum = classMetrics.getMetric(MetricNames.NOAM).intValue() + (int)classMetrics.getMetric(MetricNames.NOPA).intValue(); 
			if(  classMetrics.getMetric(MetricNames.WOC)!=null && classMetrics.getMetric(MetricNames.WOC) < metricConfiguration.getWOC_Less_OneThird() &&
			   ((sum > metricConfiguration.getNOAP_SOAP_Greater_Few() &&
			   classMetrics.getMetric(MetricNames.WMC)!=null && classMetrics.getMetric(MetricNames.WMC) >= metricConfiguration.getWMC_Less_High())||
			   (sum > metricConfiguration.getNOAP_SOAP_Greater_Many() &&
					   classMetrics.getMetric(MetricNames.WMC)!=null && classMetrics.getMetric(MetricNames.WMC) < metricConfiguration.getWMC_Less_VeryHigh()))){		   
			return true;
			}
		}
		return false;	
	}
	
	public CodeSmell codeSmellDetected(NodeMetrics classMetrics){
		return new DataClass(((ClassMetrics) classMetrics));
	}

}
