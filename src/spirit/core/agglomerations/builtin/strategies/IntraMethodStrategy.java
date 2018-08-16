package spirit.core.agglomerations.builtin.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import spirit.core.agglomerations.IntraMethodAgglomeration;
import spirit.core.agglomerations.builtin.textMarkers.IntraMethodAgglomerationTextMarker;
import spirit.core.agglomerations.extensionPoints.AgglomerationStrategy;
import spirit.core.agglomerations.model.Project;
import spirit.core.design.CodeSmellsManager;
import spirit.core.design.CodeSmellsManagerFactory;
import spirit.core.smells.CodeSmell;


/**
 * Strategy to detect Intra-method agglomerations.
 * @author Willian
 *
 */
public class IntraMethodStrategy extends AgglomerationStrategy<IntraMethodAgglomeration> {
	
	private final IntraMethodAgglomerationTextMarker textMarker = new IntraMethodAgglomerationTextMarker();
	private CodeSmellsManager smellsManager = null;
	
	/**
	 * finds agglomerations using an Intra-method strategy.
	 */
	public Set<IntraMethodAgglomeration> findAgglomerations(Project project) {
		smellsManager = CodeSmellsManagerFactory.getInstance().getManager(project.getJavaProject().getProject());
		int threshold = Thresholds.getIntraMethod(project.getJavaProjectName());
		Set<IntraMethodAgglomeration> agglomerations = new HashSet<IntraMethodAgglomeration>();
		for (Entry<MethodDeclaration, ArrayList<CodeSmell>> anomalies : buildAnomaliesPerMethod().entrySet()) {
			if (anomalies.getValue().size() > threshold) {
				agglomerations.add(new IntraMethodAgglomeration(anomalies.getKey(), anomalies.getValue()));
			}
		}
		
		return agglomerations;
	}

	/**
	 * Builds a dictionary of code anomalies where:
	 *  (i) the key is the anomaly name and (ii) the value is a list of instances.
	 * @return HashMap (name->instances)
	 */
	private HashMap<MethodDeclaration, ArrayList<CodeSmell>> buildAnomaliesPerMethod() {
		HashMap<MethodDeclaration, ArrayList<CodeSmell>> anomaliesPerMethod = new HashMap<MethodDeclaration, ArrayList<CodeSmell>>();
		
		for (CodeSmell codeAnomaly : smellsManager.getMethodAnomalies()) {
			MethodDeclaration method = (MethodDeclaration)codeAnomaly.getElement();
			ArrayList<CodeSmell> codeAnomalies = anomaliesPerMethod.get(method);
			if (codeAnomalies == null) {
				codeAnomalies = new ArrayList<CodeSmell>();
				anomaliesPerMethod.put(method, codeAnomalies);
			}
			codeAnomalies.add(codeAnomaly);
		}
		
		return anomaliesPerMethod;
	}

	@Override
	public String name() {
		return IntraMethodAgglomeration.NAME;
	}

	@Override
	protected void updateTextMarkers(Set<IntraMethodAgglomeration> agglomerations) {
		textMarker.updateTextMarkers(agglomerations);
	}
}
