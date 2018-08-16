package spirit.priotization.rankings.smells;

import spirit.core.smells.CodeSmell;
import spirit.priotization.criteria.ArchitecturalConcernCriteriaForSmellsNormalized;
import spirit.priotization.criteria.ModifiabilityScenariosForSmellsNormalizedCriteria;
import spirit.priotization.rankings.RankingCalculatorForSmells;
import spirit.priotization.rankings.RankingTypeSmells;

public class RankingBasedOnlyOnConcernsForSmells extends RankingCalculatorForSmells{

	public RankingBasedOnlyOnConcernsForSmells(ArchitecturalConcernCriteriaForSmellsNormalized architecturalConcernCriteriaForSmellsNormalized) {
		super(RankingTypeSmells.CONCERNSNORMALIZED);
		criteria.add(architecturalConcernCriteriaForSmellsNormalized);
	}

	@Override
	protected Double calculateRankingValue(CodeSmell codeSmell) {
		ArchitecturalConcernCriteriaForSmellsNormalized criterion=(ArchitecturalConcernCriteriaForSmellsNormalized) criteria.firstElement();
		return criterion.getScore(codeSmell);
	}

}
