package spirit.priotization.criteria;

import java.util.List;

import org.eclipse.swt.widgets.Shell;

import spirit.db.DataBaseManager;
import spirit.priotization.criteria.util.NOMHistoryOfAVersion;
import spirit.priotization.criteria.util.NOMHistoryProject;
import spirit.ui.views.criteriaConfiguration.CriterionConfigurationDialog;
import spirit.ui.views.criteriaConfiguration.HistoryBetaConfiguration;

public class HistoryLENOMCriteria extends Criterion implements CriteriaWithNOMHistory{
	
	private NOMHistoryProject nomHistoryProject=null;

	private Long id;
	public HistoryLENOMCriteria(String project) {
		super("LENOM Analysis",project);
		nomHistoryProject=DataBaseManager.getInstance().getNOMHistoryProjectForProject(project);
	}
	public HistoryLENOMCriteria() {
		super("",null);
	}
	@Override
	public CriterionConfigurationDialog getCriterionConfigurationDialog(
			Shell parentShell) {
		return new HistoryBetaConfiguration(parentShell, this,getIProject());
	}

	public void loadNOMHistory(List<NOMHistoryOfAVersion> nomHistory){
		nomHistoryProject.loadNOMHistory(nomHistory);
	}
	/**
	 * 
	 * @param qualifiedNameOfAClass it is the path to the class separated by dots.
	 * @return
	 */
	public Double getLENOMValue(String qualifiedNameOfAClass){
		if(nomHistoryProject==null)
			nomHistoryProject=DataBaseManager.getInstance().getNOMHistoryProjectForProject(getProjectName());
		
		return nomHistoryProject.calculateLENOMForAClass(qualifiedNameOfAClass);
	}
	
	public List<NOMHistoryOfAVersion> getNomHistory() {
		if(nomHistoryProject==null)
			nomHistoryProject=DataBaseManager.getInstance().getNOMHistoryProjectForProject(getProjectName());
		return nomHistoryProject.getNomHistory();
	}
	public NOMHistoryProject getNomHistoryProject() {
		return nomHistoryProject;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
