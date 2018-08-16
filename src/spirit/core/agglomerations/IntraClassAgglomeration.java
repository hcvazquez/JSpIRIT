package spirit.core.agglomerations;

import java.util.Collection;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.core.smells.CodeSmell;

public class IntraClassAgglomeration extends AgglomerationModel {

	public static final String NAME = "Intra-Class";//"Within a component";
	
	private TypeDeclaration enclosingClass;

	public IntraClassAgglomeration(TypeDeclaration enclosingClass, Collection<CodeSmell> codeAnomalies) {
		super(codeAnomalies);
		this.enclosingClass = enclosingClass;
	}
	
	@Override
	public String toString() {
		return enclosingClass.getName().getFullyQualifiedName();
	}

	@Override
	public String getTopologyName() {
		return NAME;
	}

	@Override
	public String getUniqueID() {
		return NAME + enclosingClass.getName().getFullyQualifiedName();
	}
}
