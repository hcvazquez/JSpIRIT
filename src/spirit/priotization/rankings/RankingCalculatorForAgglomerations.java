package spirit.priotization.rankings;

import spirit.core.agglomerations.AgglomerationModel;


public abstract class RankingCalculatorForAgglomerations extends RankingCalculator<AgglomerationModel> {
	
	protected RankingTypeAgglomerations rankingType;
	
	public RankingCalculatorForAgglomerations(RankingTypeAgglomerations rankingType) {
		this.rankingType = rankingType;
	}

	@Override
	public String getName() {
		return rankingType.getName();
	}

	public RankingTypeAgglomerations getRankingType() {
		return rankingType;
	}
}
