package spirit.metrics.storage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassMetrics extends NodeMetrics{
	
	private TypeDeclaration declaration;
	private List<MethodMetrics> methodsMetrics;

	public ClassMetrics(TypeDeclaration declaration) {
		this.declaration = declaration;
		methodsMetrics = new ArrayList<MethodMetrics>();
	}

	public TypeDeclaration getDeclaration() {
		return declaration;
	}

	public void setDeclaration(TypeDeclaration declaration) {
		this.declaration = declaration;
	}

	public List<MethodMetrics> getMethodsMetrics() {
		return methodsMetrics;
	}

	public void setMethodsMetrics(List<MethodMetrics> methodsMetrics) {
		this.methodsMetrics = methodsMetrics;
	}

}
