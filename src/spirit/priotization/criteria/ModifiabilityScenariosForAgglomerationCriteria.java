package spirit.priotization.criteria;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.eclipse.swt.widgets.Shell;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.core.design.AgglomerationManager;
import spirit.core.smells.CodeSmell;
import spirit.db.DataBaseManager;
import spirit.priotization.criteria.util.ModifiabilityScenario;
import spirit.ui.views.criteriaConfiguration.CriterionConfigurationDialog;
import spirit.ui.views.criteriaConfiguration.ModifiabilityScenariosConfiguration;

public class ModifiabilityScenariosForAgglomerationCriteria extends Criterion implements CriteriaWithScenarios{
	private Long id;
	private List<ModifiabilityScenario> scenarios;
	protected Double mostAffectedScenarioByAgglomerationCache=null;
	protected Hashtable<AgglomerationModel, Double> scenariosAffectedByAgglomeration;
	
	public ModifiabilityScenariosForAgglomerationCriteria(String project) {
		super("Modifiability Scenarios for agglomerations",project);
		scenarios=new Vector<ModifiabilityScenario>();
		scenariosAffectedByAgglomeration=new Hashtable<AgglomerationModel, Double>();
	}
	public ModifiabilityScenariosForAgglomerationCriteria() {
		super("",null);
	}
	
	@Override
	public CriterionConfigurationDialog getCriterionConfigurationDialog(
			Shell parentShell) {
		return new ModifiabilityScenariosConfiguration(parentShell, this, getIProject());
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public List<ModifiabilityScenario> getScenarios() {
		if(scenarios.size()==0)
			scenarios=DataBaseManager.getInstance().getModifiabilityScenariosForProject(getProjectName());
		return scenarios;
	}
	@Override
	public void updateScenarios() {
		this.scenarios = DataBaseManager.getInstance().getModifiabilityScenariosForProject(getProjectName());
		mostAffectedScenarioByAgglomerationCache=null;
		scenariosAffectedByAgglomeration=new Hashtable<AgglomerationModel, Double>();
	}
	
	private double importanceOfScenariosAffectingAgglomeration(AgglomerationModel agglomeration) {
		if(mostAffectedScenarioByAgglomerationCache==null)
			calculateScenariosImportanceAffectedByAgglomerations();
		return scenariosAffectedByAgglomeration.get(agglomeration);
	}
	private void calculateScenariosImportanceAffectedByAgglomerations() {
		if(scenarios.size()==0)
			this.scenarios = DataBaseManager.getInstance().getModifiabilityScenariosForProject(getProjectName());
		mostAffectedScenarioByAgglomerationCache=-1.0;
		Set<AgglomerationModel> agglomerations=AgglomerationManager.getInstance().getResults(getProjectName());
		for (Iterator<AgglomerationModel> iterator = agglomerations.iterator(); iterator.hasNext();) {
			AgglomerationModel agglomerationModel = (AgglomerationModel) iterator
					.next();
			double scenariosImportancesAffectingAgglomertation=getScenariosImportanceAffectingAgglomeration(agglomerationModel);
			if(scenariosImportancesAffectingAgglomertation>mostAffectedScenarioByAgglomerationCache)
				mostAffectedScenarioByAgglomerationCache=scenariosImportancesAffectingAgglomertation;
			scenariosAffectedByAgglomeration.put(agglomerationModel, scenariosImportancesAffectingAgglomertation);
		}
	}
	private double getScenariosImportanceAffectingAgglomeration(AgglomerationModel agglomerationModel) {
		Set<CodeSmell> codeSmells=agglomerationModel.getCodeAnomalies();
		double ret=0;
		for (Iterator<CodeSmell> iterator = codeSmells.iterator(); iterator.hasNext();) {
			CodeSmell codeSmell = (CodeSmell) iterator.next();
			String mainClass=codeSmell.getMainClassName();
			for (Iterator<ModifiabilityScenario> iterator2 = getScenarios().iterator(); iterator2.hasNext();) {
				ModifiabilityScenario scenario = (ModifiabilityScenario) iterator2.next();
				if(scenario.affectsAClass(mainClass))
					ret=ret+scenario.getImportance();
			}
			
		}
		return ret;
	}
	
	public double getScore(AgglomerationModel agglomeration) {
		double importanceOfScenariosAffectingAgglomeration=importanceOfScenariosAffectingAgglomeration(agglomeration);
		
		if(mostAffectedScenarioByAgglomerationCache==0)
			return 0;
		return importanceOfScenariosAffectingAgglomeration/mostAffectedScenarioByAgglomerationCache;
	}
}
