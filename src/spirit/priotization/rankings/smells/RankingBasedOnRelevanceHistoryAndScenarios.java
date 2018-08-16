package spirit.priotization.rankings.smells;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import spirit.core.smells.CodeSmell;
import spirit.priotization.criteria.AlphaCriteria;
import spirit.priotization.criteria.HistoryBetaCriteria;
import spirit.priotization.criteria.ModifiabilityScenariosCriteria;
import spirit.priotization.criteria.SmellRelevanceCriteria;
import spirit.priotization.rankings.RankingCalculatorForSmells;
import spirit.priotization.rankings.RankingTypeSmells;

public class RankingBasedOnRelevanceHistoryAndScenarios extends RankingCalculatorForSmells{
	
	private Hashtable<CodeSmell,Double> historyValueCache;
	
	public RankingBasedOnRelevanceHistoryAndScenarios(SmellRelevanceCriteria smellRelevanceCriteria, HistoryBetaCriteria historyBetaCriteria, ModifiabilityScenariosCriteria modifiabilityScenariosCriteria, AlphaCriteria alphaCriteria) {
		super(RankingTypeSmells.RELEVANCE_HISTORY_AND_SCENARIOS);
		criteria.add(smellRelevanceCriteria);
		criteria.add(historyBetaCriteria);
		criteria.add(modifiabilityScenariosCriteria);
		criteria.add(alphaCriteria);
		historyValueCache = new Hashtable<CodeSmell,Double>();
	}

	@Override
	public Double calculateRankingValue(CodeSmell codeSmell) {
		SmellRelevanceCriteria criterion1=(SmellRelevanceCriteria) criteria.firstElement();
		HistoryBetaCriteria criterion2=(HistoryBetaCriteria) criteria.get(1);
		ModifiabilityScenariosCriteria criterion3=(ModifiabilityScenariosCriteria) criteria.get(2);
		double alpha=((AlphaCriteria)criteria.lastElement()).getAlpha();
		Integer relevanceOfTheSmell=criterion1.getRelevance(codeSmell.getKindOfSmellName());
		Double rankValue1=alpha*(relevanceOfTheSmell*criterion2.getBetaValue(codeSmell.getMainClassName()));
		historyValueCache.put(codeSmell, rankValue1);

		//The RMS value for a code smell is computed on the basis of the number of components that belong to the scenarios that are affected by the smell.
		//First, we sum up the RCS value multiplied by the importance of the scenario that includes the affected class if the class in which the code smell is implemented is mapped to at least one scenario.
		
		Double rankValue2=relevanceOfTheSmell*criterion3.getMostImportantScenarioValueAffectingAClass(codeSmell.getMainClassName());
		
		//Then, for each class affected by the code smell that is mapped to at least one scenario, we sum the RCS value multiplied by the importance of the scenario and this value is divided by the number of classes that are mapped by at least one scenario.
		//In those cases in which more than one scenario maps to a class, the scenario with the highest importance is used.
		
		Set<String> pathAffectedClasses = codeSmell.getAffectedClasses();
		
		double sum=0.0;
		
		for (Iterator<String> iterator = pathAffectedClasses.iterator(); iterator
				.hasNext();) {
			String classPath = (String) iterator.next();
			sum=sum+ (criterion3.getMostImportantScenarioValueAffectingAClass(classPath))*relevanceOfTheSmell;
		}
		
		sum=sum/criterion3.getNumberOfClassesAffectedByTheScenarios();
		rankValue2=rankValue2+sum;
		rankValue2=rankValue2*(1-alpha);
		return rankValue1+rankValue2;
	}
	
	public Double getHistoryValue(CodeSmell p){
		return historyValueCache.get(p);
	}
	
}
