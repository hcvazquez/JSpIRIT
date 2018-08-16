package spirit.ui.views.ranking;

import org.eclipse.jface.viewers.Viewer;

import spirit.core.smells.CodeSmell;
import spirit.priotization.RankingManagerForSmells;
import spirit.priotization.rankings.smells.RankingBasedOnRelevanceHistoryAndScenarios;


public class RankingHistoryAndScenariosComparator extends RankingViewerComparator {

  @Override
  public int compare(Viewer viewer, Object e1, Object e2) {
    CodeSmell p1 = (CodeSmell) e1;
    CodeSmell p2 = (CodeSmell) e2;
    int rc = 0;
    switch (propertyIndex) {
    case 0:
      rc = p1.getKindOfSmellName().compareTo(p2.getKindOfSmellName());
      break;
    case 1:
    	rc = p1.getElementName().compareTo(p2.getElementName());
      break;
    case 2:
    	  rc = (new Integer( RankingManagerForSmells.getInstance().getRankingCalculator().getRankingPosition(p1))).compareTo((new Integer( RankingManagerForSmells.getInstance().getRankingCalculator().getRankingPosition(p2))));
          break;
    case 3:
  	  rc = RankingManagerForSmells.getInstance().getRankingCalculator().getRankingValue(p1).compareTo(RankingManagerForSmells.getInstance().getRankingCalculator().getRankingValue(p2));
        break;
    case 4:
    	  rc = ((RankingBasedOnRelevanceHistoryAndScenarios)RankingManagerForSmells.getInstance().getRankingCalculator()).getHistoryValue(p1).compareTo(
    			  ((RankingBasedOnRelevanceHistoryAndScenarios)RankingManagerForSmells.getInstance().getRankingCalculator()).getHistoryValue(p2));
          break;
    case 5:
    		Double historyValue1 = ((RankingBasedOnRelevanceHistoryAndScenarios)RankingManagerForSmells.getInstance().getRankingCalculator()).getHistoryValue(p1);
    		Double historyValue2 = ((RankingBasedOnRelevanceHistoryAndScenarios)RankingManagerForSmells.getInstance().getRankingCalculator()).getHistoryValue(p2);
    		Double scenarioValue1 = RankingManagerForSmells.getInstance().getRankingCalculator().getRankingValue(p1)-historyValue1;
    		Double scenarioValue2 = RankingManagerForSmells.getInstance().getRankingCalculator().getRankingValue(p2)-historyValue2;
    	  rc = scenarioValue1.compareTo(scenarioValue2);
          break;
    default:
      rc = 0;
    }
    // If descending order, flip the direction
    if (direction == DESCENDING) {
      rc = -rc;
    }
    return rc;
  }

}
