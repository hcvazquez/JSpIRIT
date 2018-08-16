package spirit.priotization.rankings.agglomerations;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.priotization.criteria.ArchitecturalConcernCriteriaWithImportance;
import spirit.priotization.rankings.RankingCalculatorForAgglomerations;
import spirit.priotization.rankings.RankingTypeAgglomerations;


public class RankingBasedOnlyOnArchitecturalConcernWithImportance extends
		RankingCalculatorForAgglomerations {
	public RankingBasedOnlyOnArchitecturalConcernWithImportance(ArchitecturalConcernCriteriaWithImportance architecturalProblemCriteria) {
		super(RankingTypeAgglomerations.ARCHITECTURAL_PROBLEM_IMPORTANCE);
		criteria.add(architecturalProblemCriteria);
	}

	@Override
	protected Double calculateRankingValue(AgglomerationModel agglomeration) {
		ArchitecturalConcernCriteriaWithImportance criterion=(ArchitecturalConcernCriteriaWithImportance) criteria.firstElement();
		
		return criterion.getScore(agglomeration);
	}
}
