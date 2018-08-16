package spirit.metrics.calculate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class SuperClassHierarchy implements IAttribute{
	
	List<String> nameOfClasses;
	
	public SuperClassHierarchy(List<String> nameOfClasses) {
		this.nameOfClasses = nameOfClasses;
	}

	@Override
	public void calculate(ClassMetrics node) {
		TypeDeclaration clazz = node.getDeclaration();
		List<String> list = new ArrayList<String>();
		ITypeBinding superClazz = clazz.getSuperclassType()!=null?clazz.getSuperclassType().resolveBinding():null;
		while(superClazz!=null){
			if(nameOfClasses.contains(superClazz.getBinaryName())){
				list.add(superClazz.getBinaryName());
			}
			superClazz = superClazz.getSuperclass();
		}
		node.setAttribute(getName(), list);
	}

	@Override
	public String getName() {
		return MetricNames.SC;
	}

	@Override
	public void calculate(MethodMetrics node) {

	}
	
}
