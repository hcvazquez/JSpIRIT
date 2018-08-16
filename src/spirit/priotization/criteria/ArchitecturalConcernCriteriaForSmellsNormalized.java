package spirit.priotization.criteria;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.swt.widgets.Shell;

import spirit.core.design.CodeSmellsManagerFactory;
import spirit.core.smells.CodeSmell;
import spirit.db.DataBaseManager;
import spirit.priotization.criteria.util.ArchitecturalConcern;
import spirit.ui.views.criteriaConfiguration.ArchitecturalConcernsConfiguration;
import spirit.ui.views.criteriaConfiguration.CriterionConfigurationDialog;

public class ArchitecturalConcernCriteriaForSmellsNormalized extends Criterion implements CriteriaWithConcerns{
	private Long id;
	private List<ArchitecturalConcern> concerns;
	protected Double mostAffectedConcernsBySmellsCache=null;
	protected Hashtable<CodeSmell, Double> concernsAffectedBySmell;
	
	public ArchitecturalConcernCriteriaForSmellsNormalized(String project) {
		super("Concerns Normalized",project);
		concerns=new Vector<ArchitecturalConcern>();
		concernsAffectedBySmell=new Hashtable<CodeSmell, Double>();
	}
	public ArchitecturalConcernCriteriaForSmellsNormalized() {
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
		if(concerns.size()==0)
			this.concerns = DataBaseManager.getInstance().getArchitecturalConcernsForProject(getProjectName());
		return concerns;
	}
	public void updateProblems() {
		this.concerns = DataBaseManager.getInstance().getArchitecturalConcernsForProject(getProjectName());
		mostAffectedConcernsBySmellsCache=null;
		concernsAffectedBySmell=new Hashtable<CodeSmell, Double>();
	}
	private double concernsAffectedBySmell(CodeSmell codeSmell) {
		if(mostAffectedConcernsBySmellsCache==null)
			calculateConcernsAffectedByAgglomerations();
		return concernsAffectedBySmell.get(codeSmell);
	}
	private void calculateConcernsAffectedByAgglomerations() {
		if(concerns.size()==0)
			this.concerns = DataBaseManager.getInstance().getArchitecturalConcernsForProject(getProjectName());
		mostAffectedConcernsBySmellsCache=-1.0;
		Vector<CodeSmell> smells=CodeSmellsManagerFactory.getInstance().getCurrentProjectManager().getSmells();
		for (Iterator<CodeSmell> iterator = smells.iterator(); iterator.hasNext();) {
			CodeSmell smell = (CodeSmell) iterator
					.next();
			double concernsAffectingAgglomertation=getConcernsAffectingAgglomeration(smell);
			if(concernsAffectingAgglomertation>mostAffectedConcernsBySmellsCache)
				mostAffectedConcernsBySmellsCache=concernsAffectingAgglomertation;
			concernsAffectedBySmell.put(smell, concernsAffectingAgglomertation);
		}
	}
	private double getConcernsAffectingAgglomeration(CodeSmell codeSmell) {
		
		int ret=0;
		
			
			String mainClass=codeSmell.getMainClassName();
			for (Iterator<ArchitecturalConcern> iterator2 = concerns.iterator(); iterator2.hasNext();) {
				ArchitecturalConcern architecturalConcern = (ArchitecturalConcern) iterator2.next();
				if(architecturalConcern.affectsAClass(mainClass))
					ret++;
			}
			
		
		return ret;
	}
	
	public double getScore(CodeSmell codeSmell){
		double concernsAffectedByAgglomeration=concernsAffectedBySmell(codeSmell);
		if(mostAffectedConcernsBySmellsCache==0)
			return 0;
		return concernsAffectedByAgglomeration/mostAffectedConcernsBySmellsCache;
	}
}
