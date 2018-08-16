package spirit.priotization.criteria;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Shell;

import spirit.ui.views.criteriaConfiguration.CriterionConfigurationDialog;


public class AgglomerationEvolutionCriteria extends Criterion {

	private Long id;
	
	//TODO create configuration and remove the hardcoded value
	private String initialVersionName = "";
	private String intermediaryVersionName = "";
	
	public AgglomerationEvolutionCriteria(String project) {
		super("Relevance of the agglomeration change history", project);
		initialVersionName = "/RevistaKolbe_v1.0";
		intermediaryVersionName = "/RevistaKolbe_v2.0";
		//initialVersionName = "/MobileMedia1";
		//intermediaryVersionName = "/MobileMedia5";
		//initialVersionName = "/HealthWatcherOO_01_Base";
		//intermediaryVersionName = "/HealthWatcherOO_05_Adapter";
	}

	@Override
	public CriterionConfigurationDialog getCriterionConfigurationDialog(Shell parentShell) {
//		return new AgglomerationRelevanceConfiguration(parentShell,this,getIProject());
		//TODO
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInitialVersionName() {
		return initialVersionName;
	}

	public void setInitialVersionName(String initialVersionName) {
		this.initialVersionName = initialVersionName;
	}

	public String getIntermediaryVersionName() {
		return intermediaryVersionName;
	}

	public void setIntermediaryVersionName(String intermediaryVersionName) {
		this.intermediaryVersionName = intermediaryVersionName;
	}
	
	public IProject getInitialVersionProject() {
		return getEclipseProjectByName(initialVersionName);
	}
	
	public IProject getIntermediaryVersionProject() {
		return getEclipseProjectByName(intermediaryVersionName);
	}
}
