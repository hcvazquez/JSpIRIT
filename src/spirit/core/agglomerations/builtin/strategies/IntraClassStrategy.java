package spirit.core.agglomerations.builtin.strategies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.core.agglomerations.IntraClassAgglomeration;
import spirit.core.agglomerations.extensionPoints.AgglomerationStrategy;
import spirit.core.agglomerations.model.Project;
import spirit.core.design.CodeSmellsManager;
import spirit.core.design.CodeSmellsManagerFactory;
import spirit.core.smells.CodeSmell;


public class IntraClassStrategy extends AgglomerationStrategy<IntraClassAgglomeration> {

	protected Set<IntraClassAgglomeration> findAgglomerations(Project project) {
		CodeSmellsManager smellsManager = CodeSmellsManagerFactory.getInstance().getManager(project.getJavaProject().getProject());
		HashSet<IntraClassAgglomeration> agglomerations = new HashSet<IntraClassAgglomeration>();
		int threshold =Thresholds.getIntraClass(project.getJavaProjectName());
				
		for (TypeDeclaration class_ : project.getClasses().values()) {
			ArrayList<CodeSmell> allSmellsOfClass = smellsManager.getAllSmellsOfClass(class_);			
			if (allSmellsOfClass.size() > threshold) {
				agglomerations.add(new IntraClassAgglomeration(class_, allSmellsOfClass));
			}
		}
		
		return agglomerations;
	}

	@Override
	public String name() {
		return IntraClassAgglomeration.NAME;
	}

	@Override
	protected void updateTextMarkers(Set<IntraClassAgglomeration> agglomerations) {
		//TODO
	}
}
