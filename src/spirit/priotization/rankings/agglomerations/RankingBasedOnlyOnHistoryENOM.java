package spirit.priotization.rankings.agglomerations;

import java.util.Iterator;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.core.smells.CodeSmell;
import spirit.priotization.criteria.HistoryENOMCriteria;
import spirit.priotization.rankings.RankingCalculatorForAgglomerations;
import spirit.priotization.rankings.RankingTypeAgglomerations;

public class RankingBasedOnlyOnHistoryENOM extends RankingCalculatorForAgglomerations {
	
	public RankingBasedOnlyOnHistoryENOM(HistoryENOMCriteria historyBetaCriteria) {
		super(RankingTypeAgglomerations.HISTORY_ENOM);
		criteria.add(historyBetaCriteria);
	}

	@Override
	protected Double calculateRankingValue(AgglomerationModel agglomeration) {
		HistoryENOMCriteria historyBetaCriteria=(HistoryENOMCriteria) criteria.firstElement();

		double ret=0.0;
		
		for (Iterator<CodeSmell> iterator = agglomeration.getCodeAnomalies().iterator(); iterator.hasNext();) {
			CodeSmell type = (CodeSmell) iterator.next();
			ret=ret+historyBetaCriteria.getENOMValue(type.getMainClassName());
		}
		
		
		return ret/agglomeration.getCodeAnomalies().size();
	}

}
