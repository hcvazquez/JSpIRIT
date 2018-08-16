package spirit.priotization.criteria;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.swt.widgets.Shell;

import spirit.core.agglomerations.HierarchicalAgglomeration;
import spirit.core.agglomerations.IntraClassAgglomeration;
import spirit.core.agglomerations.IntraComponentAgglomeration;
import spirit.core.agglomerations.IntraMethodAgglomeration;
import spirit.ui.views.criteriaConfiguration.AgglomerationRelevanceConfiguration;
import spirit.ui.views.criteriaConfiguration.CriterionConfigurationDialog;
import spirit.ui.views.criteriaConfiguration.util.AgglomerationRelevance;


public class AgglomerationRelevanceCriteria extends Criterion{
	private Long id;
	private List<AgglomerationRelevance> relevances;
	public AgglomerationRelevanceCriteria() {
		super("",null);
	}
	public AgglomerationRelevanceCriteria(String project) {
		super("Relevance of the kind of agglomeration",project);
		relevances=new Vector<AgglomerationRelevance>();
		addRelevance(HierarchicalAgglomeration.NAME, 0.5);
		addRelevance(IntraClassAgglomeration.NAME, 0.5);
		addRelevance(IntraComponentAgglomeration.NAME, 0.5);
		addRelevance(IntraMethodAgglomeration.NAME, 0.5);
	}

	@Override
	public CriterionConfigurationDialog getCriterionConfigurationDialog(Shell parentShell) {
		return new AgglomerationRelevanceConfiguration(parentShell,this,getIProject());
	}
	public void addRelevance(String codeSmellName, Double relevance){
		AgglomerationRelevance smellRelevance=getRelevanceFoAgglomeration(codeSmellName);
		smellRelevance.setRelevance(relevance);
	}
	private AgglomerationRelevance getRelevanceFoAgglomeration(String agglomerationName) {
		for (Iterator<AgglomerationRelevance> iterator = relevances.iterator(); iterator.hasNext();) {
			AgglomerationRelevance type = (AgglomerationRelevance) iterator.next();
			if(type.getAgglomerationName().equals(agglomerationName)){
				return type;
			}
		}
		AgglomerationRelevance newRet=new AgglomerationRelevance(agglomerationName, 0.5);
		relevances.add(newRet);
		return newRet;
	}
	public Double getRelevance(String kindOfAgglomeration){
		for (Iterator<AgglomerationRelevance> iterator = relevances.iterator(); iterator.hasNext();) {
			AgglomerationRelevance type = (AgglomerationRelevance) iterator.next();
			if(type.getAgglomerationName().equals(kindOfAgglomeration))
				return type.getRelevance();
		}
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public List<AgglomerationRelevance> getRelevances() {
		return relevances;
	}
	public void setRelevances(List<AgglomerationRelevance> relevances) {
		this.relevances = relevances;
	}
}
