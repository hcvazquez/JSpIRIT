package spirit.priotization.rankings.agglomerations;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.priotization.criteria.AgglomerationRelevanceCriteria;
import spirit.priotization.rankings.RankingCalculatorForAgglomerations;
import spirit.priotization.rankings.RankingTypeAgglomerations;

public class RankingBasedOnlyOnAgglomerationRelevance extends RankingCalculatorForAgglomerations {
	
	public RankingBasedOnlyOnAgglomerationRelevance(AgglomerationRelevanceCriteria agglomerationRelevanceCriteria) {
		super(RankingTypeAgglomerations.RELEVANCE);
		criteria.add(agglomerationRelevanceCriteria);
	}
	
	@Override
	protected Double calculateRankingValue(AgglomerationModel agglomeration) {
		AgglomerationRelevanceCriteria agglomerationRelevanceCriteria=(AgglomerationRelevanceCriteria) criteria.firstElement();
		
		return agglomerationRelevanceCriteria.getRelevance(agglomeration.getTopologyName());
	}

}
