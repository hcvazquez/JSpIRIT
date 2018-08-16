package spirit.priotization.rankings.smells;

import spirit.core.smells.CodeSmell;
import spirit.priotization.criteria.SmellRelevanceCriteria;
import spirit.priotization.rankings.RankingCalculatorForSmells;
import spirit.priotization.rankings.RankingTypeSmells;

public class RankingBasedOnlyOnSmellRelevance extends RankingCalculatorForSmells{
	
	public RankingBasedOnlyOnSmellRelevance(SmellRelevanceCriteria smellRelevanceCriteria) {
		super(RankingTypeSmells.SMELL_RELEVANCE);
		criteria.add(smellRelevanceCriteria);
	}

	@Override
	public Double calculateRankingValue(CodeSmell codeSmell) {
		SmellRelevanceCriteria criterion=(SmellRelevanceCriteria) criteria.firstElement();
		return new Double(criterion.getRelevance(codeSmell.getKindOfSmellName()));
	}
	
}
