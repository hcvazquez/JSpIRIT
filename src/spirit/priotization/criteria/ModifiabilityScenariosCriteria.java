package spirit.priotization.criteria;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.eclipse.swt.widgets.Shell;

import spirit.db.DataBaseManager;
import spirit.priotization.criteria.util.ModifiabilityScenario;
import spirit.ui.views.criteriaConfiguration.CriterionConfigurationDialog;
import spirit.ui.views.criteriaConfiguration.ModifiabilityScenariosConfiguration;

public class ModifiabilityScenariosCriteria extends Criterion implements CriteriaWithScenarios{
	private List<ModifiabilityScenario> scenarios;
	private Long id;
	private Integer affectedClassesByScenariosCache=null;
	public ModifiabilityScenariosCriteria(String project) {
		super("Related Modifiability Scenarios",project);
		scenarios=new Vector<ModifiabilityScenario>();
	}
	public ModifiabilityScenariosCriteria() {
		super("",null);
	}
	@Override
	public CriterionConfigurationDialog getCriterionConfigurationDialog(
			Shell parentShell) {
		
		return new ModifiabilityScenariosConfiguration(parentShell, this, getIProject());
	}
	
	
	
	public double getMostImportantScenarioValueAffectingAClass(String aClassPath){
		double mostImportantScenario=0.0;
		
		if(scenarios.size()==0)
			scenarios=DataBaseManager.getInstance().getModifiabilityScenariosForProject(getProjectName());
		
		for (Iterator<ModifiabilityScenario> iterator = scenarios.iterator(); iterator.hasNext();) {
			ModifiabilityScenario scenario = (ModifiabilityScenario) iterator.next();
			if(scenario.affectsAClass(aClassPath)){
				if(scenario.getImportance()>mostImportantScenario)
					mostImportantScenario=scenario.getImportance();
			}
		}
		
		return mostImportantScenario;
	}
	public int getNumberOfClassesAffectedByTheScenarios(){
		if(affectedClassesByScenariosCache==null){
			Set<String> ret = new TreeSet<String>();
			for (Iterator<ModifiabilityScenario> iterator = scenarios.iterator(); iterator.hasNext();) {
				ModifiabilityScenario scenario = (ModifiabilityScenario) iterator.next();
				ret.addAll(scenario.getAllAffectedClasses());
			}	
			affectedClassesByScenariosCache=ret.size();
		}
		
		return affectedClassesByScenariosCache;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<ModifiabilityScenario> getScenarios() {
		if(scenarios.size()==0)
			scenarios=DataBaseManager.getInstance().getModifiabilityScenariosForProject(getProjectName());
		return scenarios;
	}
	public void updateScenarios() {
		if(scenarios.size()==0)
			scenarios=DataBaseManager.getInstance().getModifiabilityScenariosForProject(getProjectName());
		
		affectedClassesByScenariosCache=null;
	}
}
