package spirit.ui.views.ranking;

import spirit.priotization.rankings.RankingTypeSmells;

public class RankingViewerFactory {
	 public static RankingViewer getRankingViewer(RankingTypeSmells rankingType) {
		 	RankingViewer viewer = null;
	        switch (rankingType) {
	        case SMELL_RELEVANCE:
	        	viewer = new RankingCalculatorViewer();
	            break;
	 
	        case SMELL_RELEVANCE_AND_HISTORY:
	        	viewer = new RankingCalculatorViewer();
	            break;
	 
	        case RELEVANCE_HISTORY_AND_SCENARIOS:
	        	viewer = new RankingHistoryAndScenariosViewer();
	            break;
	        
	        case SCENARIOSNORMALIZED:
	        	viewer = new RankingCalculatorViewer();
	        	break;
	        	
	        case CONCERNSNORMALIZED:
	        	viewer = new RankingCalculatorViewer();
	        	break;
	        	
	        case BLUEPRINTNORMALIZED:
	        	viewer = new RankingCalculatorViewer();
	        	break;	
	        	
	        default:
	        	//viewer = new RankingCalculatorViewer();
	            break;
	        }
	        return viewer;
	    }
	 
	 public static RankingViewerComparator getRankingComparatorViewer(RankingTypeSmells rankingType) {
		 	RankingViewerComparator comparator = null;
	        switch (rankingType) {
	        case SMELL_RELEVANCE:
	        	comparator = new RankingViewerComparator();
	            break;
	 
	        case SMELL_RELEVANCE_AND_HISTORY:
	        	comparator = new RankingViewerComparator();
	            break;
	 
	        case RELEVANCE_HISTORY_AND_SCENARIOS:
	        	comparator = new RankingHistoryAndScenariosComparator();
	            break;
	 
	        case SCENARIOSNORMALIZED:
	        	comparator = new RankingViewerComparator();
	        	break;
	        	
	        case CONCERNSNORMALIZED:
	        	comparator = new RankingViewerComparator();
	        	break; 	
	       
	        case BLUEPRINTNORMALIZED:
	        	comparator = new RankingViewerComparator();
	        	break;	
	        	
	        default:
	        	//viewer = new RankingCalculatorViewer();
	            break;
	        }
	        return comparator;
	    }
}
