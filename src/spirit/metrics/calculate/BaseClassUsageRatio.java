package spirit.metrics.calculate;

import java.util.List;

import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class BaseClassUsageRatio implements IAttribute{

	@Override
	public void calculate(ClassMetrics node) {
		float ratio = 1;
		float uses = 0;
		if(node.getMetric(MetricNames.NProtM)!=null && node.getMetric(MetricNames.NProtM)>0){
			List<FieldAccess> ListOfLocalFieldUsed = (List<FieldAccess>) node.getAttribute(MetricNames.ListOfLocalFieldUsed);
			List<MethodInvocation> ListOfLocalMethodInvoked = (List<MethodInvocation>) node.getAttribute(MetricNames.ListOfLocalMethodInvoked);
			
			ITypeBinding declaration = (ITypeBinding) node.getDeclaration().resolveBinding().getSuperclass();
			IMethodBinding[] methods = declaration.getDeclaredMethods();
			
			if(methods!=null  && ListOfLocalMethodInvoked!=null){
				for(IMethodBinding method:methods){
					if(Modifier.isProtected(method.getModifiers())){
						for(MethodInvocation methodInv:ListOfLocalMethodInvoked){
							if(methodInv.resolveMethodBinding().getKey().equals(method.getKey())){
								uses++;
								break;
							}
						}
					}
				}
			}
			
			IVariableBinding[] fields = declaration.getDeclaredFields();
			if(fields!=null && ListOfLocalFieldUsed!=null){
				for(IVariableBinding field:fields){
					if(Modifier.isProtected(field.getModifiers())){
						for(FieldAccess fieldAcc:ListOfLocalFieldUsed){
							if(fieldAcc.resolveFieldBinding().getKey().equals(field.getKey())){
								uses++;
								break;
							}
						}
					}
				}
			}
			ratio = uses / node.getMetric(MetricNames.NProtM);
		}
		node.setMetric(getName(), ratio);
	}

	@Override
	public String getName() {
		return MetricNames.BUR;
	}

	@Override
	public void calculate(MethodMetrics node) {
		// TODO Auto-generated method stub
		
	}

}
