package spirit.priotization.rankings.smells;

import spirit.core.smells.CodeSmell;
import spirit.priotization.criteria.HistoryBetaCriteria;
import spirit.priotization.criteria.SmellRelevanceCriteria;
import spirit.priotization.rankings.RankingCalculatorForSmells;
import spirit.priotization.rankings.RankingTypeSmells;

public class RankingBasedOnSmellRelevanceAndHistory extends RankingCalculatorForSmells{
	protected SmellRelevanceCriteria aaa;
	public RankingBasedOnSmellRelevanceAndHistory(SmellRelevanceCriteria smellRelevanceCriteria, HistoryBetaCriteria historyBetaCriteria) {
		super(RankingTypeSmells.SMELL_RELEVANCE_AND_HISTORY);
		criteria.add(smellRelevanceCriteria);
		criteria.add(historyBetaCriteria);
	}

	@Override
	public Double calculateRankingValue(CodeSmell codeSmell) {
		SmellRelevanceCriteria criterion1=(SmellRelevanceCriteria) criteria.firstElement();
		HistoryBetaCriteria criterion2=(HistoryBetaCriteria) criteria.lastElement();
		Double rankValue1=criterion1.getRelevance(codeSmell.getKindOfSmellName())*criterion2.getBetaValue(codeSmell.getMainClassName());
		return rankValue1;
	}
	
}
