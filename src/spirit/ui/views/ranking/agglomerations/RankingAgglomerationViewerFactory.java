package spirit.ui.views.ranking.agglomerations;

import spirit.priotization.rankings.RankingTypeAgglomerations;

public class RankingAgglomerationViewerFactory {
	
	 public static RankingAgglomerationViewer getRankingViewer(RankingTypeAgglomerations rankingType) {
		 RankingAgglomerationViewer viewer = null;
	        switch (rankingType) {
	        case ARCHITECTURAL_PROBLEM_OCCURRENCES:
	        	viewer = new RankingAgglomerationCalculatorViewer();
	            break;
	            
	        case ARCHITECTURAL_PROBLEM_IMPORTANCE:
	        	viewer = new RankingAgglomerationCalculatorViewer();
	            break;
	            
	        case HISTORY:
	        	viewer = new RankingAgglomerationCalculatorViewer();
	            break;
	            
	        case HISTORY_LENOM:
	        	viewer = new RankingAgglomerationCalculatorViewer();
	            break;
	            
	        case HISTORY_ENOM:
	        	viewer = new RankingAgglomerationCalculatorViewer();
	            break;
	            
	        case RELEVANCE:
	        	viewer = new RankingAgglomerationCalculatorViewer();
	            break;
	        
	        case SCENARIOS:
	        	viewer = new RankingAgglomerationCalculatorViewer();
	            break;
	            
	        case AGGLOMERATION_EVOLUTION:
	        	viewer = new RankingAgglomerationCalculatorViewer();
	            break;	            
	        }
	        return viewer;
	    }
	 
	 public static RankingAgglomerationViewerComparator getRankingComparatorViewer(RankingTypeAgglomerations rankingType) {
		 	RankingAgglomerationViewerComparator comparator = null;
	        switch (rankingType) {
	        case ARCHITECTURAL_PROBLEM_OCCURRENCES:
	        	comparator = new RankingAgglomerationViewerComparator();
	            break;
	            
	        case ARCHITECTURAL_PROBLEM_IMPORTANCE:
	        	comparator = new RankingAgglomerationViewerComparator();
	            break;
	            
	        case HISTORY:
	        	comparator = new RankingAgglomerationViewerComparator();
	            break;
	            
	        case HISTORY_LENOM:
	        	comparator = new RankingAgglomerationViewerComparator();
	            break;
	            
	        case HISTORY_ENOM:
	        	comparator = new RankingAgglomerationViewerComparator();
	            break;
	        
	        case RELEVANCE:
	        	comparator = new RankingAgglomerationViewerComparator();
	            break;
	            
	        case SCENARIOS:
	        	comparator = new RankingAgglomerationViewerComparator();
	            break;

	        case AGGLOMERATION_EVOLUTION:
	        	comparator = new RankingAgglomerationViewerComparator();
	        	break;
	        }
	        return comparator;
	    }
}
