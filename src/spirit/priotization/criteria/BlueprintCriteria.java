package spirit.priotization.criteria;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.eclipse.swt.widgets.Shell;

import spirit.core.design.CodeSmellsManagerFactory;
import spirit.core.smells.CodeSmell;
import spirit.db.DataBaseManager;
import spirit.priotization.criteria.util.ArchitecturalConcern;
import spirit.priotization.criteria.util.ComponentOfBlueprint;
import spirit.ui.views.criteriaConfiguration.ArchitecturalConcernsConfiguration;
import spirit.ui.views.criteriaConfiguration.ComponentOfBlueprintConfiguration;
import spirit.ui.views.criteriaConfiguration.CriterionConfigurationDialog;

public class BlueprintCriteria extends Criterion{
	private Long id;
	private List<ComponentOfBlueprint> components;
	protected Double mostAffectedConcernsBySmellsCache=null;
	protected Hashtable<CodeSmell, Double> concernsAffectedBySmell;
	
	public BlueprintCriteria(String project) {
		super("Blueprint Normalized",project);
		components=new Vector<ComponentOfBlueprint>();
		concernsAffectedBySmell=new Hashtable<CodeSmell, Double>();
	}
	public BlueprintCriteria() {
		super("",null);
	}
	@Override
	public CriterionConfigurationDialog getCriterionConfigurationDialog(
			Shell parentShell) {
		return new ComponentOfBlueprintConfiguration(parentShell, this, getIProject());
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<ComponentOfBlueprint> getComponentOfBlueprints() {
		if(components.size()==0)
			this.components = DataBaseManager.getInstance().getComponentOfBlueprintForProject(getProjectName());
		return components;
	}
	public void updateProblems() {
		this.components = DataBaseManager.getInstance().getComponentOfBlueprintForProject(getProjectName());
		mostAffectedConcernsBySmellsCache=null;
		concernsAffectedBySmell=new Hashtable<CodeSmell, Double>();
	}
	
	public double getScore(CodeSmell codeSmell){
		double componentsAffectedBySmell=componentsAffectedBySmell(codeSmell);
		if(mostAffectedConcernsBySmellsCache==0)
			return 0;
		return componentsAffectedBySmell/mostAffectedConcernsBySmellsCache;
	}
	private double componentsAffectedBySmell(CodeSmell codeSmell) {
		if(mostAffectedConcernsBySmellsCache==null)
			calculateComponentsAffectedBySmells();
		return concernsAffectedBySmell.get(codeSmell);
	}
	private void calculateComponentsAffectedBySmells() {
		if(components.size()==0)
			this.components = DataBaseManager.getInstance().getComponentsOfBlueprintForProject(getProjectName());
		mostAffectedConcernsBySmellsCache=-1.0;
		Vector<CodeSmell> smells=CodeSmellsManagerFactory.getInstance().getCurrentProjectManager().getSmells();
		for (Iterator<CodeSmell> iterator = smells.iterator(); iterator.hasNext();) {
			CodeSmell smell = (CodeSmell) iterator
					.next();
			double concernsAffectingAgglomertation=getComponentsAffectingSmell(smell);
			if(concernsAffectingAgglomertation>mostAffectedConcernsBySmellsCache)
				mostAffectedConcernsBySmellsCache=concernsAffectingAgglomertation;
			concernsAffectedBySmell.put(smell, concernsAffectingAgglomertation);
		}
	}
	
	private double getComponentsAffectingSmell(CodeSmell codeSmell) {
		
		Set<ComponentOfBlueprint> ret=new HashSet<ComponentOfBlueprint>();
			Set<String> classes=codeSmell.getAffectedClasses();
			classes.add(codeSmell.getMainClassName());
			System.out.println(codeSmell+" "+classes.size());
			
			for (Iterator<String> iterator = classes.iterator(); iterator.hasNext();) {
				String className = (String) iterator.next();
				for (Iterator<ComponentOfBlueprint> iterator2 = components.iterator(); iterator2.hasNext();) {
					ComponentOfBlueprint architecturalConcern = (ComponentOfBlueprint) iterator2.next();
					if(architecturalConcern.affectsAClass(className))
						ret.add(architecturalConcern);
				}
			}
			
			
		
		return ret.size();
	}

}
