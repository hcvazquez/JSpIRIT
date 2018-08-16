package spirit.core.agglomerations;

import java.util.Collection;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.core.smells.CodeSmell;

/**
 * Agglomeration model for Hierarchical agglomerations
 * @author Willian
 *
 */
public class HierarchicalAgglomeration extends AgglomerationModel {

	public static final String NAME = "Hierarchical";//"In a hierarchy";
	
	private final TypeDeclaration root;
	private final String typeOfAnomaly;

	/**
	 * Returns an Hierarchical agglomeration
	 * @param root of the hierarchy
	 * @param typeOfAnomaly: anomaly infecting elements in the hierarchy 
	 * @param codeAnomalies: anomalous code elements in the hierarchy  
	 */
	public HierarchicalAgglomeration(TypeDeclaration root, String typeOfAnomaly, Collection<CodeSmell> codeAnomalies) {
		super(codeAnomalies);
		this.root = root;
		this.typeOfAnomaly = typeOfAnomaly;
	}

	/**
	 * Returns the root of the hierarchy
	 * @return TypeDeclaration
	 */
	public TypeDeclaration getRoot() {
		return root;
	}
	
	@Override
	public String toString() {
		return typeOfAnomaly + " -> " + root.getName();
	}

	@Override
	public String getTopologyName() {
		return NAME;
	}

	@Override
	public String getUniqueID() {
		return NAME + typeOfAnomaly + root.getName();
	}
}
