package spirit.core.smells.detectors;

import spirit.core.smells.CodeSmell;
import spirit.core.smells.GodClass;
import spirit.core.smells.detectors.configurationByProject.GodClassDetectionConfiguration;
import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.NodeMetrics;

public class GodClassDetector extends CodeSmellDetector{
	private GodClassDetectionConfiguration metricConfiguration;
	public GodClassDetector(GodClassDetectionConfiguration metricConfiguration) {
		this.metricConfiguration=metricConfiguration;
	}
	public boolean codeSmellVerify(NodeMetrics classMetrics){
		if(classMetrics.getMetric(MetricNames.ATFD)!=null && classMetrics.getMetric(MetricNames.ATFD) > metricConfiguration.getATFD_Greater_FEW()  &&
		   classMetrics.getMetric(MetricNames.TCC)!=null && classMetrics.getMetric(MetricNames.TCC) < metricConfiguration.getTCC_Less_OneThird() &&
		   classMetrics.getMetric(MetricNames.WMC)!=null && classMetrics.getMetric(MetricNames.WMC) >= metricConfiguration.getWMC_GreaterEqual_VeryHigh()){
		   
			classMetrics.setAttribute(MetricNames.GC, Boolean.valueOf(true));
			return true;
		}
		return false;	
	}
	
	public CodeSmell codeSmellDetected(NodeMetrics classMetrics){
		return new GodClass(((ClassMetrics) classMetrics));
	}

}
