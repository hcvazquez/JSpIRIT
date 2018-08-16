package spirit.priotization.rankings;

public enum RankingTypeSmells {
	SMELL_RELEVANCE("Smell Relevance"), RELEVANCE_HISTORY_AND_SCENARIOS("Relevance, History, and Scenarios"), SMELL_RELEVANCE_AND_HISTORY("Smell Relevance and history"), SCENARIOSNORMALIZED("Scenarios normalized"), CONCERNSNORMALIZED("Concerns normalized"), BLUEPRINTNORMALIZED("Blueprint normalized");
	
	private String name; 
	
	RankingTypeSmells(String name) {
        this.name = name;
    }
 
    public String getName() {
        return name;
    }
}
