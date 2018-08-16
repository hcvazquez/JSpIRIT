package spirit.priotization.rankings;

import spirit.core.smells.CodeSmell;

public abstract class RankingCalculatorForSmells extends RankingCalculator<CodeSmell>{
	
	
	
	protected RankingTypeSmells rankingType;
	
	
	public RankingCalculatorForSmells(RankingTypeSmells rankingType) {
		this.rankingType = rankingType;
	}
	
	
	
	public String getName() {
		return rankingType.getName();
	}
	
	public RankingTypeSmells getRankingType() {
		return rankingType;
	}
	
	
	

}
