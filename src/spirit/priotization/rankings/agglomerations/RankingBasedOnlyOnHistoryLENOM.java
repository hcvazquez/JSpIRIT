package spirit.priotization.rankings.agglomerations;

import java.util.Iterator;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.core.smells.CodeSmell;
import spirit.priotization.criteria.HistoryLENOMCriteria;
import spirit.priotization.rankings.RankingCalculatorForAgglomerations;
import spirit.priotization.rankings.RankingTypeAgglomerations;

public class RankingBasedOnlyOnHistoryLENOM extends RankingCalculatorForAgglomerations {
	
	public RankingBasedOnlyOnHistoryLENOM(HistoryLENOMCriteria historyBetaCriteria) {
		super(RankingTypeAgglomerations.HISTORY_LENOM);
		criteria.add(historyBetaCriteria);
	}

	@Override
	protected Double calculateRankingValue(AgglomerationModel agglomeration) {
		HistoryLENOMCriteria historyBetaCriteria=(HistoryLENOMCriteria) criteria.firstElement();

		double ret=0.0;
		
		for (Iterator<CodeSmell> iterator = agglomeration.getCodeAnomalies().iterator(); iterator.hasNext();) {
			CodeSmell type = (CodeSmell) iterator.next();
			ret=ret+historyBetaCriteria.getLENOMValue(type.getMainClassName());
		}
		
		
		return ret/agglomeration.getCodeAnomalies().size();
	}

}
