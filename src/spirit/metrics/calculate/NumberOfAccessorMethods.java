package spirit.metrics.calculate;

import java.util.List;

import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class NumberOfAccessorMethods implements IAttribute{
	private static final String GET = "get";
	private static final String SET = "set";

	@Override
	public void calculate(ClassMetrics node) {
		TypeDeclaration declaration = (TypeDeclaration) node.getDeclaration();
		MethodDeclaration[] lmethod = declaration.getMethods();
		int noam = 0;
		for(MethodDeclaration method:lmethod){
			if(isPublicAndNonStatic(method)){
				if(method.getName().getFullyQualifiedName().startsWith(GET)||
						method.getName().getFullyQualifiedName().startsWith(SET)){
					noam++;
				}
			}
		}
		node.setMetric(getName(),noam);
	}
	
	private boolean isPublicAndNonStatic(MethodDeclaration method) {
		List<IExtendedModifier> modifiers = method.modifiers();
		boolean isPublic = false;
		boolean isStatic = false;
		for(IExtendedModifier modifier:modifiers){
			if(modifier.isModifier()){
				if(((Modifier)modifier).isPublic()){
					isPublic=true;
				}
				if(((Modifier)modifier).isStatic()){
					isStatic=true;
				}
			}
		}
		if(isPublic&&!isStatic){
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return MetricNames.NOAM;
	}

	@Override
	public void calculate(MethodMetrics node) {
		// TODO Auto-generated method stub
		
	}

}