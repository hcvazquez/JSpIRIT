package spirit.priotization.rankings;

public enum RankingTypeAgglomerations {
	ARCHITECTURAL_PROBLEM_OCCURRENCES("Architectural Problem Occurrence"), 
	ARCHITECTURAL_PROBLEM_IMPORTANCE("Architectural Problem Occurrence with Importance"), 
	HISTORY("History Beta Analisis"), 
	HISTORY_LENOM("History LENOM Analisis"), 
	HISTORY_ENOM("History ENOM Analisis"), 
	RELEVANCE("Relevance of Agglomeration"),
	AGGLOMERATION_EVOLUTION("Agglomeration Evolution"),
	SCENARIOS("Modifiability Scenarios");
	
	private String name; 
	
	RankingTypeAgglomerations(String name) {
        this.name = name;
    }
 
    public String getName() {
        return name;
    }
}
