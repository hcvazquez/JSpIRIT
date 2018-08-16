package spirit.metrics.calculate;

import java.util.List;

import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class WeightOfClass implements IAttribute{
	
	private static final String GET = "get";
	private static final String SET = "set";

	@Override
	public void calculate(ClassMetrics node) {
		TypeDeclaration declaration = (TypeDeclaration) node.getDeclaration();
		
		MethodDeclaration[] methods = declaration.getMethods();
		int nom = 0;
		int den = 0;
		if(methods!=null){
			for(MethodDeclaration method:methods){
				if(isPublicAndNonStatic(method)){
					if(!method.isConstructor()){
						nom++;
						String nameOfMethod = method.getName().getFullyQualifiedName();
						if ((nameOfMethod.startsWith(GET)||nameOfMethod.startsWith(SET))){
							den++;
						}
					}
				}
			}
		}
		nom = nom + node.getMetric(MetricNames.NOPA).intValue();
		float woc = 1;
		if(nom>0){
			woc = (float)den / (float)nom;
		}else if(den==0){
			woc = 0;
		}
		node.setMetric(getName(), woc);
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
		return MetricNames.WOC;
	}

	@Override
	public void calculate(MethodMetrics node) {

	}

}
