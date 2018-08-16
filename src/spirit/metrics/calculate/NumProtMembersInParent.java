package spirit.metrics.calculate;

import java.util.List;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Modifier;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class NumProtMembersInParent implements IAttribute{
	
	List<String> nameOfClasses;
	
	public NumProtMembersInParent(List<String> nameOfClasses) {
		this.nameOfClasses = nameOfClasses;
	}


	@Override
	public void calculate(ClassMetrics node) {
		int protMembers = 0;
		if(node.getDeclaration().resolveBinding().getSuperclass()!=null && 
				nameOfClasses.contains(node.getDeclaration().resolveBinding().getSuperclass().getBinaryName())){
			ITypeBinding declaration = (ITypeBinding) node.getDeclaration().resolveBinding().getSuperclass();
			IMethodBinding[] methods = declaration.getDeclaredMethods();
			if(methods!=null){
				for(IMethodBinding method:methods){
					if(Modifier.isProtected(method.getModifiers())){
						protMembers++;
					}
				}
			}
			IVariableBinding[] fields = declaration.getDeclaredFields();
			if(fields!=null){
				for(IVariableBinding field:fields){
					if(Modifier.isProtected(field.getModifiers())){
						protMembers++;
					}
				}
			}
		}
		node.setMetric(getName(), protMembers);
	}

	@Override
	public String getName() {
		return MetricNames.NProtM;
	}

	@Override
	public void calculate(MethodMetrics node) {
		// TODO Auto-generated method stub
		
	}

}
