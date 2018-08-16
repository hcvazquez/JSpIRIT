package spirit.priotization.criteria;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.swt.widgets.Shell;

import spirit.core.design.CodeSmellsManagerFactory;
import spirit.core.smells.CodeSmell;
import spirit.db.DataBaseManager;
import spirit.priotization.criteria.util.ModifiabilityScenario;
import spirit.ui.views.criteriaConfiguration.CriterionConfigurationDialog;
import spirit.ui.views.criteriaConfiguration.ModifiabilityScenariosConfiguration;

public class ModifiabilityScenariosForSmellsNormalizedCriteria extends Criterion implements CriteriaWithScenarios{
	private Long id;
	private List<ModifiabilityScenario> scenarios;
	protected Double mostAffectedScenarioBySmellCache=null;
	protected Hashtable<CodeSmell, Double> scenariosAffectedBySmell;
	
	public ModifiabilityScenariosForSmellsNormalizedCriteria(String project) {
		super("Modifiability Scenarios for smells (normalized)",project);
		scenarios=new Vector<ModifiabilityScenario>();
		scenariosAffectedBySmell=new Hashtable<CodeSmell, Double>();
	}
	public ModifiabilityScenariosForSmellsNormalizedCriteria() {
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
		mostAffectedScenarioBySmellCache=null;
		scenariosAffectedBySmell=new Hashtable<CodeSmell, Double>();
	}
	
	private double importanceOfScenariosAffectingAgglomeration(CodeSmell codeSmell) {
		if(mostAffectedScenarioBySmellCache==null)
			calculateScenariosImportanceAffectedBySmells();
		return scenariosAffectedBySmell.get(codeSmell);
	}
	private void calculateScenariosImportanceAffectedBySmells() {
		if(scenarios.size()==0)
			this.scenarios = DataBaseManager.getInstance().getModifiabilityScenariosForProject(getProjectName());
		mostAffectedScenarioBySmellCache=-1.0;
		Vector<CodeSmell> smells=CodeSmellsManagerFactory.getInstance().getCurrentProjectManager().getSmells();
		for (Iterator<CodeSmell> iterator = smells.iterator(); iterator.hasNext();) {
			CodeSmell smell = (CodeSmell) iterator
					.next();
			double scenariosImportancesAffectingAgglomertation=getScenariosImportanceAffectingAgglomeration(smell);
			if(scenariosImportancesAffectingAgglomertation>mostAffectedScenarioBySmellCache)
				mostAffectedScenarioBySmellCache=scenariosImportancesAffectingAgglomertation;
			scenariosAffectedBySmell.put(smell, scenariosImportancesAffectingAgglomertation);
		}
	}
	private double getScenariosImportanceAffectingAgglomeration(CodeSmell codeSmell) {
		double ret=0.0;
		
			String mainClass=codeSmell.getMainClassName();
			for (Iterator<ModifiabilityScenario> iterator = scenarios.iterator(); iterator.hasNext();) {
				ModifiabilityScenario scenario = (ModifiabilityScenario) iterator.next();
				if(scenario.affectsAClass(mainClass)){
					
						ret=ret+scenario.getImportance();
				}
			}
			
			return ret;
	}
	
	public double getScore(CodeSmell codeSmell) {
		double importanceOfScenariosAffectingSmell=importanceOfScenariosAffectingAgglomeration(codeSmell);
		
		if(mostAffectedScenarioBySmellCache==0)
			return 0;
		return importanceOfScenariosAffectingSmell/mostAffectedScenarioBySmellCache;
	}
}
