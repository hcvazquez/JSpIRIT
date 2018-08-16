package spirit.core.smells.detectors;

import spirit.core.smells.CodeSmell;
import spirit.core.smells.DispersedCoupling;
import spirit.core.smells.detectors.configurationByProject.DispersedCouplingDetectionConfiguration;
import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.MethodMetrics;
import spirit.metrics.storage.NodeMetrics;

public class DispersedCouplingDetector extends CodeSmellDetector{
	private DispersedCouplingDetectionConfiguration metricConfiguration;
	public DispersedCouplingDetector(DispersedCouplingDetectionConfiguration metricConfiguration) {
		this.metricConfiguration=metricConfiguration;
	}
	public boolean codeSmellVerify(NodeMetrics methodMetric){
		if(methodMetric.getMetric(MetricNames.MNL)!=null && methodMetric.getMetric(MetricNames.MNL) > metricConfiguration.getMAXNESTING_Greater_Shallow()  &&
		   methodMetric.getMetric(MetricNames.CDISP)!=null && methodMetric.getMetric(MetricNames.CDISP) >= metricConfiguration.getCDISP_GreaterEqual_Half() &&
		   methodMetric.getMetric(MetricNames.CINT)!=null && methodMetric.getMetric(MetricNames.CINT) > metricConfiguration.getCINT_Greater_SMemCap()
		   ){

			return true;
		}
		return false;
	}
	
	public CodeSmell codeSmellDetected(NodeMetrics methodMetric){
		return new DispersedCoupling(((MethodMetrics) methodMetric));
	}

}
