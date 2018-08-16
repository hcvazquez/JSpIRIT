package spirit.core.smells.detectors;

import java.util.List;

import spirit.core.smells.CodeSmell;
import spirit.core.smells.TraditionBreaker;
import spirit.core.smells.detectors.configurationByProject.TraditionBreakerDetectionConfiguration;
import spirit.metrics.constants.MetricNames;

import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.NodeMetrics;

public class TraditionBreakerDetector extends CodeSmellDetector{
	
	private TraditionBreakerDetectionConfiguration metricConfiguration;
	
	public TraditionBreakerDetector(TraditionBreakerDetectionConfiguration metricConfiguration) {
		this.metricConfiguration=metricConfiguration;
	}
	
	public boolean codeSmellVerify(NodeMetrics classMetrics){
		if(isChild(classMetrics) &&
				excessiveIncreaseOfChildClassInterface((ClassMetrics) classMetrics) &&
					childClassHasSubstantialSizeAndComplexity((ClassMetrics) classMetrics) &&
						parentClassIsNeitherSmallNorDumb((ClassMetrics) classMetrics)
				){		   
			return true;
		}
		return false;	
	}

	public CodeSmell codeSmellDetected(NodeMetrics classMetrics){
		return new TraditionBreaker(((ClassMetrics) classMetrics));
	}
	

	public boolean excessiveIncreaseOfChildClassInterface(ClassMetrics classMetrics){
		if((classMetrics.getMetric(MetricNames.NAS)!=null && classMetrics.getMetric(MetricNames.NAS)>=metricConfiguration.getNAS_GreaterEqual_NOMAvg() &&
				classMetrics.getMetric(MetricNames.PNAS)!=null && classMetrics.getMetric(MetricNames.PNAS)>=metricConfiguration.getPNAS_GreaterEqual_TWO_THIRD())){
			return true;
		}
		return false;
	}
	
	public boolean childClassHasSubstantialSizeAndComplexity(ClassMetrics classMetrics){
		if(((classMetrics.getMetric(MetricNames.AMW)!=null && classMetrics.getMetric(MetricNames.AMW)>metricConfiguration.getAMW_Greater_AMWAvg() ||
				classMetrics.getMetric(MetricNames.WMC)!=null && classMetrics.getMetric(MetricNames.WMC)>=metricConfiguration.getWMC_GreaterEqual_VeryHigh()))&&
				(classMetrics.getMetric(MetricNames.NOM)!=null && classMetrics.getMetric(MetricNames.NOM)>=metricConfiguration.getNOM_GreatherEqual_High())){
			return true;
		}
		return false;
	}
	
	public boolean parentClassIsNeitherSmallNorDumb(ClassMetrics classMetrics){
		if(((classMetrics.getMetric(MetricNames.AMW)!=null && classMetrics.getMetric(MetricNames.AMW)>metricConfiguration.getAMW_Greater_AMWAvg_2() &&
				classMetrics.getMetric(MetricNames.WMC)!=null && classMetrics.getMetric(MetricNames.WMC)>metricConfiguration.getWMC_Greater_VeryHighDiv2()))&&
				(classMetrics.getMetric(MetricNames.NOM)!=null && classMetrics.getMetric(MetricNames.NOM)>=metricConfiguration.getNOM_GreatherEqual_HighDiv2())){
			return true;
		}
		return false;
	}
	
	
	private boolean isChild(NodeMetrics classMetrics){
		List<String> nameOfClasses = (List<String>) classMetrics.getAttribute(MetricNames.nameOfClasses);
		if(((ClassMetrics) classMetrics).getDeclaration().resolveBinding().getSuperclass()!=null && 
				nameOfClasses.contains(((ClassMetrics) classMetrics).getDeclaration().resolveBinding().getSuperclass().getBinaryName()) ){
			return true;
		}
		return false;
	}

}
