package spirit.core.smells.detectors;

import spirit.core.smells.CodeSmell;
import spirit.core.smells.IntensiveCoupling;
import spirit.core.smells.detectors.configurationByProject.IntensiveCouplingDetectionConfiguration;
import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.MethodMetrics;
import spirit.metrics.storage.NodeMetrics;

public class IntensiveCouplingDetector extends CodeSmellDetector{
	private IntensiveCouplingDetectionConfiguration metricConfiguration;
	
	public IntensiveCouplingDetector(IntensiveCouplingDetectionConfiguration metricConfiguration) {
		this.metricConfiguration=metricConfiguration;
	}
	
	public boolean codeSmellVerify(NodeMetrics methodMetric){
		if(methodMetric.getMetric(MetricNames.MNL)!=null && methodMetric.getMetric(MetricNames.MNL) > metricConfiguration.getMAXNESTING_Greater_SHALLOW() &&
				
		    ((methodMetric.getMetric(MetricNames.CDISP)!=null && methodMetric.getMetric(MetricNames.CDISP) < metricConfiguration.getCDISP_Less_OneQuarter() &&
		    methodMetric.getMetric(MetricNames.CINT)!=null && methodMetric.getMetric(MetricNames.CINT) > metricConfiguration.getCINT_Greater_Few())||(
			methodMetric.getMetric(MetricNames.CDISP)!=null && methodMetric.getMetric(MetricNames.CDISP) < metricConfiguration.getCDISP_Less_Half() &&
			methodMetric.getMetric(MetricNames.CINT)!=null && methodMetric.getMetric(MetricNames.CINT) > metricConfiguration.getCINT_Greater_SMemCap()))	   
		   ){

			return true;
		}
		return false;
	}
	
	public CodeSmell codeSmellDetected(NodeMetrics methodMetric){
		return new IntensiveCoupling(((MethodMetrics) methodMetric));
	}

}
