package spirit.metrics.calculate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodInvocation;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class CouplingIntensity implements IAttribute{
	
	@Override
	public void calculate(ClassMetrics node) {}

	@Override
	public String getName() {
		return MetricNames.CINT;
	}

	@Override
	public void calculate(MethodMetrics node) {
		List<MethodInvocation> ListOfMethodInvoked = (List<MethodInvocation>) node.getAttribute(MetricNames.ListOfMethodInvoked);
		List<String> distinctMethods = new ArrayList<String>();
		for(MethodInvocation method:ListOfMethodInvoked){
			if(!method.resolveMethodBinding().isConstructor() && !definedInSuperClass(node, method)
					&& !distinctMethods.contains(method.resolveMethodBinding().getKey())){
				distinctMethods.add(method.resolveMethodBinding().getKey());
			}
		}
		node.setMetric(getName(), distinctMethods.size());
	}

	private boolean definedInSuperClass(MethodMetrics node, MethodInvocation method) {
		List<String> superClasses = (List<String>) node.getClassMetrics().getAttribute(MetricNames.SC);
		if(method.resolveMethodBinding().getDeclaringClass()!=null && 
				superClasses.contains(method.resolveMethodBinding().getDeclaringClass().getBinaryName())){
			return true;
		}
		return false;
	}

}
