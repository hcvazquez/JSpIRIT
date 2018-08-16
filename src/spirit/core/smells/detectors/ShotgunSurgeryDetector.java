package spirit.core.smells.detectors;

import java.util.List;

import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;

import spirit.core.smells.CodeSmell;
import spirit.core.smells.ShotgunSurgery;
import spirit.core.smells.detectors.configurationByProject.ShotgunSurgeryDetectionConfiguration;
import spirit.metrics.constants.MetricNames;

import spirit.metrics.storage.MethodMetrics;
import spirit.metrics.storage.NodeMetrics;

public class ShotgunSurgeryDetector extends CodeSmellDetector {
	private ShotgunSurgeryDetectionConfiguration metricConfiguration;
	public ShotgunSurgeryDetector(ShotgunSurgeryDetectionConfiguration metricConfiguration) {
		this.metricConfiguration=metricConfiguration;
	}
	@Override
	public boolean codeSmellVerify(NodeMetrics methodMetric) {
		if(isNonConstructorAndNonStatic(((MethodMetrics) methodMetric).getDeclaration()) &&
				methodMetric.getMetric(MetricNames.CC)!=null && methodMetric.getMetric(MetricNames.CC) >=metricConfiguration.getCC_GreaterEqual_MANY()  &&
		   methodMetric.getMetric(MetricNames.CM)!=null && methodMetric.getMetric(MetricNames.CM) > metricConfiguration.getCM_Greater_SMemCap()
		   ){
			return true;
		}
		return false;
	}

	@Override
	public CodeSmell codeSmellDetected(NodeMetrics methodMetric) {
		return new ShotgunSurgery(((MethodMetrics) methodMetric));
	}
	
	private boolean isNonConstructorAndNonStatic(MethodDeclaration method) {
		List<IExtendedModifier> modifiers = method.modifiers();
		boolean isStatic = false;
		for(IExtendedModifier modifier:modifiers){
			if(modifier.isModifier()){
				if(((Modifier)modifier).isStatic()){
						isStatic=true;
				}
			}
		}
		if(!method.isConstructor()&&!isStatic){
			return true;
		}
		return false;
	}

}
