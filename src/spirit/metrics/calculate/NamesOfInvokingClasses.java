package spirit.metrics.calculate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.InvokingCache;
import spirit.metrics.storage.MethodMetrics;

public class NamesOfInvokingClasses implements IAttribute{
	
	@Override
	public String getName() {
		return MetricNames.ListOfClassInvoking;
	}

	@Override
	public void calculate(ClassMetrics node) {
		List<String> linvoking = new ArrayList<String>();
		if(InvokingCache.getInstance().loadClassesInvokingClass(node.getDeclaration())!=null){
			linvoking.addAll(InvokingCache.getInstance().loadClassesInvokingClass(node.getDeclaration()));
		}
		node.setAttribute(getName(), linvoking);
	}
	
	protected boolean verifyInvocation(MethodDeclaration methodDeclaration, MethodInvocation method){
		if(methodDeclaration.resolveBinding().getKey().equals(method.resolveMethodBinding().getKey())){
			return true;
		}
		return false;
	}

	@Override
	public void calculate(MethodMetrics node) {
		List<String> linvoking = new ArrayList<String>();
		List<String> lmethodinvoking = new ArrayList<String>();
		List<String> methods = InvokingCache.getInstance().loadMethodsInvokingMethod(node.getDeclaration());
		List<String> classes = InvokingCache.getInstance().loadClassesInvokingMethod(node.getDeclaration());
		if(methods!=null){
			for(String method:methods){
				if(!lmethodinvoking.contains(method)){
					lmethodinvoking.add(method);
				}
			}
		}
		if(classes!=null){
			for(String clazz:classes){
				if(!linvoking.contains(clazz)){
					linvoking.add(clazz);
				}
			}
		}
		node.setAttribute(getName(), linvoking);
		node.setAttribute(MetricNames.ListOfMethodInvoking,lmethodinvoking);
	}


}
