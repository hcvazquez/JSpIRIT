package spirit.priotization.rankings.smells;

import spirit.core.smells.CodeSmell;
import spirit.priotization.criteria.ModifiabilityScenariosForSmellsNormalizedCriteria;
import spirit.priotization.rankings.RankingCalculatorForSmells;
import spirit.priotization.rankings.RankingTypeSmells;

public class RankingBasedOnlyOnScenariosForSmells extends RankingCalculatorForSmells{

	public RankingBasedOnlyOnScenariosForSmells(ModifiabilityScenariosForSmellsNormalizedCriteria modifiabilityScenariosForSmellsNormalizedCriteria) {
		super(RankingTypeSmells.SCENARIOSNORMALIZED);
		criteria.add(modifiabilityScenariosForSmellsNormalizedCriteria);
	}

	@Override
	protected Double calculateRankingValue(CodeSmell codeSmell) {
		ModifiabilityScenariosForSmellsNormalizedCriteria criterion=(ModifiabilityScenariosForSmellsNormalizedCriteria) criteria.firstElement();
		return criterion.getScore(codeSmell);
	}

}
