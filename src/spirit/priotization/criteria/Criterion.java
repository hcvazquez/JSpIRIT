package spirit.priotization.criteria;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.widgets.Shell;

import spirit.ui.views.criteriaConfiguration.CriterionConfigurationDialog;

public abstract class Criterion {
	private String name;
	private String projectName;
	
	public Criterion(String name, String projectName) {
		this.name=name;
		this.projectName=projectName;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract CriterionConfigurationDialog getCriterionConfigurationDialog(Shell parentShell);
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	protected IProject getIProject(){
		return getEclipseProjectByName(projectName);
	}
	
	protected IProject getEclipseProjectByName(String projectName) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	}
}
