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

public class NumOverrideMethods implements IAttribute{
	
	List<String> nameOfClasses;
	
	public NumOverrideMethods(List<String> nameOfClasses) {
		this.nameOfClasses = nameOfClasses;
	}

	@Override
	public void calculate(ClassMetrics node) {
		float overrides = 0;
		float ratio = 0;
		float publicOverrides = 0;
		float publicOverridesAncestor = 0;
		
		ITypeBinding baseClass = (ITypeBinding) node.getDeclaration().resolveBinding().getSuperclass();
		
		if(baseClass!=null &&
				nameOfClasses.contains(baseClass.getBinaryName())){
			
			IMethodBinding[] baseClassMethods = baseClass.getDeclaredMethods();
			ITypeBinding child = (ITypeBinding) node.getDeclaration().resolveBinding();
			IMethodBinding[] childMethods = child.getDeclaredMethods();
			
			if(baseClassMethods!=null && childMethods!=null){
				
				ITypeBinding ancestor = baseClass.getSuperclass();
				List<IMethodBinding> ancestorMethods = new ArrayList<IMethodBinding>();
				while(ancestor!=null && nameOfClasses.contains(ancestor.getBinaryName())){
					ancestorMethods.addAll(Arrays.asList(ancestor.getDeclaredMethods()));
					ancestor = ancestor.getSuperclass();
				}
				
				for(IMethodBinding childMethod:childMethods){
					for(IMethodBinding baseClassMethod:baseClassMethods){
						if(!childMethod.isConstructor() && childMethod.overrides(baseClassMethod)){
							overrides++;
							if(Modifier.isPublic(childMethod.getModifiers())){
								publicOverridesAncestor++;
								publicOverrides++;
							}
							break;
						}
					}
					if(ancestorMethods!=null){
						for(IMethodBinding ancestorMethod:ancestorMethods){
							if(!childMethod.isConstructor() && childMethod.overrides(ancestorMethod)){
								if(Modifier.isPublic(childMethod.getModifiers())){
									publicOverridesAncestor++;
								}
								break;
							}
						}
					}
				}
			}
			ratio = overrides/(float)childMethods.length;
		}
		node.setMetric(getName(), ratio);
		node.setMetric(MetricNames.NOvM, overrides);
		node.setMetric(MetricNames.NPOvM, publicOverrides);
		node.setMetric(MetricNames.NPOvMAns, publicOverridesAncestor);
	}

	@Override
	public String getName() {
		return MetricNames.BOvR;
	}

	@Override
	public void calculate(MethodMetrics node) {
		// TODO Auto-generated method stub
		
	}

}
