package spirit.core.agglomerations.builtin.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.core.agglomerations.IntraComponentAgglomeration;
import spirit.core.agglomerations.builtin.textMarkers.IntraComponentAgglomerationTextMarker;
import spirit.core.agglomerations.extensionPoints.AgglomerationStrategy;
import spirit.core.agglomerations.model.Component;
import spirit.core.agglomerations.model.Project;
import spirit.core.design.CodeSmellsManager;
import spirit.core.design.CodeSmellsManagerFactory;
import spirit.core.smells.CodeSmell;


/**
 * Strategy to detect Intra-component agglomerations
 * @author Willian
 *
 */
public class IntraComponentStrategy extends AgglomerationStrategy<IntraComponentAgglomeration> {
	
	private final IntraComponentAgglomerationTextMarker textMarker = new IntraComponentAgglomerationTextMarker();
	private CodeSmellsManager smellsManager = null;
	
	/**
	 * finds agglomerations using an Intra-component strategy.
	 */
	protected Set<IntraComponentAgglomeration> findAgglomerations(Project project) {
		smellsManager = CodeSmellsManagerFactory.getInstance().getManager(project.getJavaProject().getProject());
		int threshold = Thresholds.getIntraComponent(project.getJavaProjectName());
		Set<IntraComponentAgglomeration> agglomerations = new HashSet<IntraComponentAgglomeration>();
		
		for (Component component : project.getComponents()) {
			HashMap<String, ArrayList<CodeSmell>> typesToAnomalies = computeAnomaliesInComponent(component);
			
			for (Entry<String, ArrayList<CodeSmell>> typeAnomalies : typesToAnomalies.entrySet()) {
				if (typeAnomalies.getValue().size() > threshold)
					agglomerations.add(new IntraComponentAgglomeration(component, typeAnomalies.getKey(), typeAnomalies.getValue()));
			}
		}
		return agglomerations;
	}


	/**
	 * Builds a dictionary of code anomalies where:
	 *  (i) the key is the anomaly name and (ii) the value is a list of instances.
	 * @param component
	 * @return HashMap (name->instances) of code anomalies
	 */
	private HashMap<String, ArrayList<CodeSmell>> computeAnomaliesInComponent(Component component) {
		HashMap<String, ArrayList<CodeSmell>> typesToAnomalies = new HashMap<String, ArrayList<CodeSmell>>();
		for (TypeDeclaration class_ : component.getClasses()) {
			for (CodeSmell codeSmell : smellsManager.getAllSmellsOfClass(class_)) {
				ArrayList<CodeSmell> codeSmells = typesToAnomalies.get(codeSmell.getKindOfSmellName());
				if (codeSmells == null) {
					codeSmells = new ArrayList<CodeSmell>();
					typesToAnomalies.put(codeSmell.getKindOfSmellName(), codeSmells);
				}
				codeSmells.add(codeSmell);
			}
		}
		return typesToAnomalies;
	}


	@Override
	public String name() {
		return IntraComponentAgglomeration.NAME;
	}


	@Override
	protected void updateTextMarkers(Set<IntraComponentAgglomeration> agglomerations) {
		textMarker.updateTextMarkers(agglomerations);
	}
}
