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
import spirit.priotization.criteria.util.ArchitecturalConcern;
import spirit.ui.views.criteriaConfiguration.ArchitecturalConcernsConfiguration;
import spirit.ui.views.criteriaConfiguration.CriterionConfigurationDialog;

public class ArchitecturalConcernCriteriaWithImportance extends Criterion implements CriteriaWithConcerns{
	private Long id;
	private List<ArchitecturalConcern> architecturalProblems;
	protected Double mostAffectedConcernsByAgglomerationImportanceCache=null;
	protected Hashtable<AgglomerationModel, Double> importanceOfConcernsAffectedByAgglomeration;
	
	public ArchitecturalConcernCriteriaWithImportance(String project) {
		super("Architectural Concern Occurrence with Importance",project);
		architecturalProblems=new Vector<ArchitecturalConcern>();
		importanceOfConcernsAffectedByAgglomeration=new Hashtable<AgglomerationModel, Double>();
	}
	public ArchitecturalConcernCriteriaWithImportance() {
		super("",null);
	}
	@Override
	public CriterionConfigurationDialog getCriterionConfigurationDialog(
			Shell parentShell) {
		return new ArchitecturalConcernsConfiguration(parentShell, this, getIProject());
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<ArchitecturalConcern> getArchitecturalProblems() {
		if(architecturalProblems.size()==0)
			this.architecturalProblems = DataBaseManager.getInstance().getArchitecturalConcernsForProject(getProjectName());
		return architecturalProblems;
	}
	public void updateProblems() {
		this.architecturalProblems = DataBaseManager.getInstance().getArchitecturalConcernsForProject(getProjectName());
		mostAffectedConcernsByAgglomerationImportanceCache=null;
		importanceOfConcernsAffectedByAgglomeration=new Hashtable<AgglomerationModel, Double>();
	}
	private double importanceOfConcernsAffectingAgglomeration(AgglomerationModel agglomeration) {
		if(mostAffectedConcernsByAgglomerationImportanceCache==null)
			calculateConcernsImportanceAffectedByAgglomerations();
		//FIXME fixed: was returning null cause hash & equals methods were not implemented for @AgglomerationModel.
		//return importanceOfConcernsAffectedByAgglomeration.get(agglomeration);
		return importanceOfConcernsAffectedByAgglomeration.get(agglomeration) != null ? importanceOfConcernsAffectedByAgglomeration.get(agglomeration) : 0.0;
	}
	private void calculateConcernsImportanceAffectedByAgglomerations() {
		if(architecturalProblems.size()==0)
			this.architecturalProblems = DataBaseManager.getInstance().getArchitecturalConcernsForProject(getProjectName());
		mostAffectedConcernsByAgglomerationImportanceCache=-1.0;
		Set<AgglomerationModel> agglomerations=AgglomerationManager.getInstance().getResults(getProjectName());
		for (Iterator<AgglomerationModel> iterator = agglomerations.iterator(); iterator.hasNext();) {
			AgglomerationModel agglomerationModel = (AgglomerationModel) iterator
					.next();
			double concernsImportancesAffectingAgglomertation=getConcernsImportanceAffectingAgglomeration(agglomerationModel);
			if(concernsImportancesAffectingAgglomertation>mostAffectedConcernsByAgglomerationImportanceCache)
				mostAffectedConcernsByAgglomerationImportanceCache=concernsImportancesAffectingAgglomertation;
			importanceOfConcernsAffectedByAgglomeration.put(agglomerationModel, concernsImportancesAffectingAgglomertation);
		}
	}
	private double getConcernsImportanceAffectingAgglomeration(AgglomerationModel agglomerationModel) {
		Set<CodeSmell> codeSmells=agglomerationModel.getCodeAnomalies();
		double ret=0;
		for (Iterator<CodeSmell> iterator = codeSmells.iterator(); iterator.hasNext();) {
			CodeSmell codeSmell = (CodeSmell) iterator.next();
			String mainClass=codeSmell.getMainClassName();
			for (Iterator<ArchitecturalConcern> iterator2 = getArchitecturalProblems().iterator(); iterator2.hasNext();) {
				ArchitecturalConcern architecturalConcern = (ArchitecturalConcern) iterator2.next();
				if(architecturalConcern.affectsAClass(mainClass))
					ret=ret+architecturalConcern.getImportance();
			}
			
		}
		return ret;
	}
	
	public double getScore(AgglomerationModel agglomeration) {
		double importanceOfConcernsAffectingAgglomeration=importanceOfConcernsAffectingAgglomeration(agglomeration);
		
		if(mostAffectedConcernsByAgglomerationImportanceCache==0)
			return 0;
		return importanceOfConcernsAffectingAgglomeration/mostAffectedConcernsByAgglomerationImportanceCache;
	}
}
