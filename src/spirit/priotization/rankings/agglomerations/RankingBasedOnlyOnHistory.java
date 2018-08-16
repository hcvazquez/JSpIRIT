package spirit.priotization.rankings.agglomerations;

import java.util.Iterator;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.core.smells.CodeSmell;
import spirit.priotization.criteria.HistoryBetaCriteria;
import spirit.priotization.rankings.RankingCalculatorForAgglomerations;
import spirit.priotization.rankings.RankingTypeAgglomerations;

public class RankingBasedOnlyOnHistory extends RankingCalculatorForAgglomerations {
	
	public RankingBasedOnlyOnHistory(HistoryBetaCriteria historyBetaCriteria) {
		super(RankingTypeAgglomerations.HISTORY);
		criteria.add(historyBetaCriteria);
	}

	@Override
	protected Double calculateRankingValue(AgglomerationModel agglomeration) {
		HistoryBetaCriteria historyBetaCriteria=(HistoryBetaCriteria) criteria.firstElement();

		double ret=0.0;
		
		for (Iterator<CodeSmell> iterator = agglomeration.getCodeAnomalies().iterator(); iterator.hasNext();) {
			CodeSmell type = (CodeSmell) iterator.next();
			ret=ret+historyBetaCriteria.getBetaValue(type.getMainClassName());
		}
		
		
		return ret/agglomeration.getCodeAnomalies().size();
	}

}
