package spirit.core.design;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;



public class CodeSmellsManagerFactory {
	private static CodeSmellsManagerFactory instance;
	private IProject currentProject = null;
	private final Map<String, CodeSmellsManager> metricsAndSmellsPerProject = new HashMap<String, CodeSmellsManager>();
	
	public static CodeSmellsManagerFactory getInstance(){
		if(instance==null)
			instance=new CodeSmellsManagerFactory();
		return instance;
	}
	
	public void setCurrentProject(IProject eclipseProject) {
		currentProject = eclipseProject;
	}
	
	public IProject getCurrentProject() {
		return currentProject;
	}

	public CodeSmellsManager getManager(IProject eclipseProject) {
		CodeSmellsManager metricsAndSmells = metricsAndSmellsPerProject.get(eclipseProject.getName());
		if (metricsAndSmells == null) {
			metricsAndSmells = new CodeSmellsManager(eclipseProject);
			metricsAndSmellsPerProject.put(eclipseProject.getName(), metricsAndSmells);
		}
		return metricsAndSmells;
	}
	
	public CodeSmellsManager getCurrentProjectManager() {
		if (currentProject != null) {
			return getManager(currentProject);
		} else {
			System.err.println("Current project was not defined.");
			return null;
		}
	}
	
	
}
