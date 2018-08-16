package spirit.core.agglomerations.model.projectBuilders;

import org.eclipse.core.resources.IProject;

import spirit.Activator;
import spirit.core.agglomerations.model.projectBuilders.impl.ProjectFromConcernMapperBuilder;
import spirit.core.agglomerations.model.projectBuilders.impl.ProjectFromPackageStructureBuilder;
import spirit.db.DataBaseManager;


/**
 * Static factory for ProjectBuilder.
 * @author Willian
 *
 */
public class ProjectBuilderFactory {
	
	public static final String SOURCE = "ComponentsSource";
	
	/**
	 * Returns a ProjectBuilder according to user preferences.	
	 * @param eclipseProject
	 * @return
	 */
	public static ProjectBuilder get(IProject eclipseProject) {
		if (eclipseProject == null)
			return null;
		
		String projectBuilderName = DataBaseManager.getInstance().getAgglomerationConfigurationForProject(eclipseProject.getFullPath().toString()).getStructureSource();//Activator.getPreferences().getString(SOURCE);
		
		if (projectBuilderName.equals(ProjectFromConcernMapperBuilder.NAME))
			return new ProjectFromConcernMapperBuilder(eclipseProject);
		
		return new ProjectFromPackageStructureBuilder(eclipseProject);
	}
}
