package spirit.priotization.criteria;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.swt.widgets.Shell;

import spirit.core.smells.BrainClass;
import spirit.core.smells.BrainMethod;
import spirit.core.smells.DataClass;
import spirit.core.smells.DispersedCoupling;
import spirit.core.smells.FeatureEnvy;
import spirit.core.smells.GodClass;
import spirit.core.smells.IntensiveCoupling;
import spirit.core.smells.LongMethod;
import spirit.core.smells.RefusedParentBequest;
import spirit.core.smells.ShotgunSurgery;
import spirit.core.smells.TraditionBreaker;
import spirit.ui.views.criteriaConfiguration.CriterionConfigurationDialog;
import spirit.ui.views.criteriaConfiguration.SmellRelevanceConfiguration;
import spirit.ui.views.criteriaConfiguration.util.SmellRelevance;

public class SmellRelevanceCriteria extends Criterion{
	private Long id;
	private List<SmellRelevance> relevances;
	public SmellRelevanceCriteria() {
		super("",null);
	}
	public SmellRelevanceCriteria(String project) {
		super("Relevance of the kind of code smell",project);
		relevances=new Vector<SmellRelevance>();
		addRelevance(BrainClass.NAME, 1);
		addRelevance(DataClass.NAME, 1);
		addRelevance(FeatureEnvy.NAME, 1);
		addRelevance(IntensiveCoupling.NAME, 1);
		addRelevance(ShotgunSurgery.NAME, 1);
		addRelevance(BrainMethod.NAME, 1);
		addRelevance(LongMethod.NAME, 1);
		addRelevance(DispersedCoupling.NAME, 1);
		addRelevance(GodClass.NAME, 1);
		addRelevance(RefusedParentBequest.NAME, 1);
		addRelevance(TraditionBreaker.NAME, 1);
	
		
		
	}

	@Override
	public CriterionConfigurationDialog getCriterionConfigurationDialog(Shell parentShell) {
		return new SmellRelevanceConfiguration(parentShell,this,getIProject());
	}
	public void addRelevance(String codeSmellName, Integer relevance){
		SmellRelevance smellRelevance=getRelevanceFoSmell(codeSmellName);
		smellRelevance.setRelevance(relevance);
	}
	private SmellRelevance getRelevanceFoSmell(String codeSmellName) {
		for (Iterator<SmellRelevance> iterator = relevances.iterator(); iterator.hasNext();) {
			SmellRelevance type = (SmellRelevance) iterator.next();
			if(type.getSmellName().equals(codeSmellName)){
				return type;
			}
		}
		SmellRelevance newRet=new SmellRelevance(codeSmellName, 1);
		relevances.add(newRet);
		return newRet;
	}
	public Integer getRelevance(String kindOfCodeSmell){
		for (Iterator<SmellRelevance> iterator = relevances.iterator(); iterator.hasNext();) {
			SmellRelevance type = (SmellRelevance) iterator.next();
			if(type.getSmellName().equals(kindOfCodeSmell))
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
	public List<SmellRelevance> getRelevances() {
		return relevances;
	}
	public void setRelevances(List<SmellRelevance> relevances) {
		this.relevances = relevances;
	}
}
