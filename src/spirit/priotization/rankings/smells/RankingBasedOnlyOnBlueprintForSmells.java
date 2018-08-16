package spirit.priotization.rankings.smells;

import spirit.core.smells.CodeSmell;
import spirit.priotization.criteria.BlueprintCriteria;
import spirit.priotization.criteria.ModifiabilityScenariosForSmellsNormalizedCriteria;
import spirit.priotization.rankings.RankingCalculatorForSmells;
import spirit.priotization.rankings.RankingTypeSmells;

public class RankingBasedOnlyOnBlueprintForSmells extends RankingCalculatorForSmells{

	public RankingBasedOnlyOnBlueprintForSmells(BlueprintCriteria blueprintCriteria) {
		super(RankingTypeSmells.BLUEPRINTNORMALIZED);
		criteria.add(blueprintCriteria);
	}

	@Override
	protected Double calculateRankingValue(CodeSmell codeSmell) {
		BlueprintCriteria criterion=(BlueprintCriteria) criteria.firstElement();
		return criterion.getScore(codeSmell);
	}

}
