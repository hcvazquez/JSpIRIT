package spirit.metrics.calculate;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class NumOverrideMethodsInAncesters implements IAttribute{
	
	List<String> nameOfClasses;
	
	public NumOverrideMethodsInAncesters(List<String> nameOfClasses) {
		this.nameOfClasses = nameOfClasses;
	}

	@Override
	public void calculate(ClassMetrics node) {
		
		float publicOverrides = 0;
		ITypeBinding ancestor = (ITypeBinding) node.getDeclaration().resolveBinding().getSuperclass();
		List<IMethodBinding> ancestorMethods = new ArrayList<IMethodBinding>();
		while(ancestor!=null && nameOfClasses.contains(ancestor.getBinaryName())){
			ancestorMethods.addAll(Arrays.asList(ancestor.getDeclaredMethods()));
			ancestor = ancestor.getSuperclass();
		}
		ITypeBinding child = (ITypeBinding) node.getDeclaration().resolveBinding();
		IMethodBinding[] childMethods = child.getDeclaredMethods();
		if(ancestorMethods!=null && childMethods!=null){
			for(IMethodBinding childMethod:childMethods){
				for(IMethodBinding ancestorMethod:ancestorMethods){
					if(!childMethod.isConstructor() && childMethod.overrides(ancestorMethod)){
						if(Modifier.isPublic(childMethod.getModifiers())){
							publicOverrides++;
						}
						break;
					}
				}
			}
		}
		node.setMetric(getName(), publicOverrides);
	}

	@Override
	public String getName() {
		return MetricNames.NPOvMAns;
	}

	@Override
	public void calculate(MethodMetrics node) {
		// TODO Auto-generated method stub
		
	}

}
