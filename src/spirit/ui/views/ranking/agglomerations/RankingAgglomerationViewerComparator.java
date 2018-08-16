package spirit.ui.views.ranking.agglomerations;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.priotization.RankingManagerForAgglomerations;


public class RankingAgglomerationViewerComparator extends ViewerComparator {
  protected int propertyIndex;
  protected static final int DESCENDING = 1;
  protected int direction = DESCENDING;

  public RankingAgglomerationViewerComparator() {
    this.propertyIndex = 3;
    direction = DESCENDING;
  }

  public int getDirection() {
    return direction == 1 ? SWT.DOWN : SWT.UP;
  }

  public void setColumn(int column) {
    if (column == this.propertyIndex) {
      // Same column as last sort; toggle the direction
      direction = 1 - direction;
    } else {
      // New column; do an ascending sort
      this.propertyIndex = column;
      direction = DESCENDING;
    }
  }

  @Override
  public int compare(Viewer viewer, Object e1, Object e2) {
	  AgglomerationModel p1 = (AgglomerationModel) e1;
	  AgglomerationModel p2 = (AgglomerationModel) e2;
    int rc = 0;

    switch (propertyIndex) {
    case 0:
      rc = p1.getTopologyName().compareTo(p2.getTopologyName());
      break;
    case 1:
    	rc = p1.toString().compareTo(p2.toString());
      break;
    case 2:
    	  rc = (new Integer( RankingManagerForAgglomerations.getInstance().getRankingCalculator().getRankingPosition(p1))).compareTo((new Integer( RankingManagerForAgglomerations.getInstance().getRankingCalculator().getRankingPosition(p2))));
          break;
    case 3:
    	//FIXME NullPointer when changing between projects
    	//rc = RankingManagerForAgglomerations.getInstance().getRankingCalculator().getRankingValue(p1).compareTo(RankingManagerForAgglomerations.getInstance().getRankingCalculator().getRankingValue(p2));
        rc = 0;
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
