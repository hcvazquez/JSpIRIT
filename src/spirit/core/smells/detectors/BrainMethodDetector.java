package spirit.core.smells.detectors;

import spirit.core.smells.BrainMethod;
import spirit.core.smells.CodeSmell;
import spirit.core.smells.detectors.configurationByProject.BrainMethodDetectionConfiguration;
import spirit.metrics.constants.MetricNames;
import spirit.metrics.constants.MetricThresholds;
import spirit.metrics.storage.MethodMetrics;
import spirit.metrics.storage.NodeMetrics;

public class BrainMethodDetector extends CodeSmellDetector{
	private BrainMethodDetectionConfiguration metricsConfiguration;
	public BrainMethodDetector(BrainMethodDetectionConfiguration metricsConfiguration) {
		this.metricsConfiguration=metricsConfiguration;
	}
	public boolean codeSmellVerify(NodeMetrics methodMetric){
		//if(((MethodMetrics)methodMetric).getClassParent().getName().toString().equals("OverviewDialog"))
			//System.out.println(((MethodMetrics)methodMetric).getDeclaration().getName()+"\tab"+methodMetric.getMetric(MetricNames.LOC)+"\t"+methodMetric.getMetric(MetricNames.WMC)+"\t"+methodMetric.getMetric(MetricNames.MNL)+"\t"+methodMetric.getMetric(MetricNames.NOF));
		if(methodMetric.getMetric(MetricNames.LOC)!=null && methodMetric.getMetric(MetricNames.LOC) > metricsConfiguration.getLOC_Greater_VeryHigh() &&
				   methodMetric.getMetric(MetricNames.MNL)!=null && methodMetric.getMetric(MetricNames.MNL) >= metricsConfiguration.getMAXNESTING_GreaterEqual_DEEP() &&
				   //methodMetric.getMetric(MetricNames.WMC)!=null && methodMetric.getMetric(MetricNames.WMC)/2 >= MetricThresholds.DEEP &&
				   methodMetric.getMetric(MetricNames.WMC)!=null && methodMetric.getMetric(MetricNames.WMC) >= metricsConfiguration.getWMC_GreaterEqual_Many() &&
				   methodMetric.getMetric(MetricNames.NOF)!=null && methodMetric.getMetric(MetricNames.NOF) >= metricsConfiguration.getNOF_GreaterEqual_SMemCap()
				   ){
				   
			methodMetric.setAttribute(MetricNames.BM, Boolean.valueOf(true));
			return true;
		}
		return false;
	}
	
	public CodeSmell codeSmellDetected(NodeMetrics methodMetric){
		return new BrainMethod(((MethodMetrics) methodMetric));
	}

}
