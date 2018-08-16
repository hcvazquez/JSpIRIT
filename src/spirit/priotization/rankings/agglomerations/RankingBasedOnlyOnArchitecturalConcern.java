package spirit.priotization.rankings.agglomerations;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.priotization.criteria.ArchitecturalConcernCriteria;
import spirit.priotization.rankings.RankingCalculatorForAgglomerations;
import spirit.priotization.rankings.RankingTypeAgglomerations;


public class RankingBasedOnlyOnArchitecturalConcern extends
		RankingCalculatorForAgglomerations {
	public RankingBasedOnlyOnArchitecturalConcern(ArchitecturalConcernCriteria architecturalProblemCriteria) {
		super(RankingTypeAgglomerations.ARCHITECTURAL_PROBLEM_OCCURRENCES);
		criteria.add(architecturalProblemCriteria);
	}

	@Override
	protected Double calculateRankingValue(AgglomerationModel agglomeration) {
		ArchitecturalConcernCriteria criterion=(ArchitecturalConcernCriteria) criteria.firstElement();
		
		return criterion.getScore(agglomeration);
	}
}
