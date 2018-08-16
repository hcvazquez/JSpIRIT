package spirit.priotization.rankings.agglomerations;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.priotization.criteria.ModifiabilityScenariosForAgglomerationCriteria;
import spirit.priotization.rankings.RankingCalculatorForAgglomerations;
import spirit.priotization.rankings.RankingTypeAgglomerations;


public class RankingBasedOnlyOnScenarios extends
		RankingCalculatorForAgglomerations {
	public RankingBasedOnlyOnScenarios(ModifiabilityScenariosForAgglomerationCriteria modifiabilityScenariosForAgglomerationCriteria) {
		super(RankingTypeAgglomerations.SCENARIOS);
		criteria.add(modifiabilityScenariosForAgglomerationCriteria);
	}

	@Override
	protected Double calculateRankingValue(AgglomerationModel agglomeration) {
		ModifiabilityScenariosForAgglomerationCriteria criterion=(ModifiabilityScenariosForAgglomerationCriteria) criteria.firstElement();
		
		return criterion.getScore(agglomeration);
	}
}
