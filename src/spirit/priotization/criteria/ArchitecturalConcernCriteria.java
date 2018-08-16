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

public class ArchitecturalConcernCriteria extends Criterion implements CriteriaWithConcerns{
	private Long id;
	private List<ArchitecturalConcern> architecturalProblems;
	protected Double mostAffectedConcernsByAgglomerationCache=null;
	protected Hashtable<AgglomerationModel, Double> concernsAffectedByAgglomeration;
	
	public ArchitecturalConcernCriteria(String project) {
		super("Architectural Concern Occurrence",project);
		architecturalProblems=new Vector<ArchitecturalConcern>();
		concernsAffectedByAgglomeration=new Hashtable<AgglomerationModel, Double>();
	}
	public ArchitecturalConcernCriteria() {
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
		mostAffectedConcernsByAgglomerationCache=null;
		concernsAffectedByAgglomeration=new Hashtable<AgglomerationModel, Double>();
	}
	private double concernsAffectedByAgglomeration(AgglomerationModel agglomeration) {
		if(mostAffectedConcernsByAgglomerationCache==null)
			calculateConcernsAffectedByAgglomerations();
		return concernsAffectedByAgglomeration.get(agglomeration);
	}
	private void calculateConcernsAffectedByAgglomerations() {
		if(architecturalProblems.size()==0)
			this.architecturalProblems = DataBaseManager.getInstance().getArchitecturalConcernsForProject(getProjectName());
		mostAffectedConcernsByAgglomerationCache=-1.0;
		Set<AgglomerationModel> agglomerations=AgglomerationManager.getInstance().getResults(getProjectName());
		for (Iterator<AgglomerationModel> iterator = agglomerations.iterator(); iterator.hasNext();) {
			AgglomerationModel agglomerationModel = (AgglomerationModel) iterator
					.next();
			double concernsAffectingAgglomertation=getConcernsAffectingAgglomeration(agglomerationModel);
			if(concernsAffectingAgglomertation>mostAffectedConcernsByAgglomerationCache)
				mostAffectedConcernsByAgglomerationCache=concernsAffectingAgglomertation;
			concernsAffectedByAgglomeration.put(agglomerationModel, concernsAffectingAgglomertation);
		}
	}
	private double getConcernsAffectingAgglomeration(AgglomerationModel agglomerationModel) {
		Set<CodeSmell> codeSmells=agglomerationModel.getCodeAnomalies();
		int ret=0;
		for (Iterator<CodeSmell> iterator = codeSmells.iterator(); iterator.hasNext();) {
			CodeSmell codeSmell = (CodeSmell) iterator.next();
			String mainClass=codeSmell.getMainClassName();
			for (Iterator<ArchitecturalConcern> iterator2 = architecturalProblems.iterator(); iterator2.hasNext();) {
				ArchitecturalConcern architecturalConcern = (ArchitecturalConcern) iterator2.next();
				if(architecturalConcern.affectsAClass(mainClass))
					ret++;
			}
			
		}
		return ret;
	}
	
	public double getScore(AgglomerationModel agglomeration){
		double concernsAffectedByAgglomeration=concernsAffectedByAgglomeration(agglomeration);
		if(mostAffectedConcernsByAgglomerationCache==0)
			return 0;
		return concernsAffectedByAgglomeration/mostAffectedConcernsByAgglomerationCache;
	}
}
