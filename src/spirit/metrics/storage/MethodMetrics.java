package spirit.metrics.storage;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class MethodMetrics extends NodeMetrics{
	
	private MethodDeclaration declaration;
	private ClassMetrics classMetrics;

	public MethodMetrics(MethodDeclaration declaration, ClassMetrics classMetrics) {
		this.declaration = declaration;
		this.classMetrics = classMetrics;
	}

	public MethodDeclaration getDeclaration() {
		return declaration;
	}

	public void setDeclaration(MethodDeclaration declaration) {
		this.declaration = declaration;
	}

	public TypeDeclaration getClassParent() {
		return classMetrics.getDeclaration();
	}
	
	public ClassMetrics getClassMetrics() {
		return classMetrics;
	}

}
