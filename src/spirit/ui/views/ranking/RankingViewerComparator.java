package spirit.ui.views.ranking;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import spirit.core.smells.CodeSmell;
import spirit.priotization.RankingManagerForSmells;


public class RankingViewerComparator extends ViewerComparator {
  protected int propertyIndex;
  protected static final int DESCENDING = 1;
  protected int direction = DESCENDING;

  public RankingViewerComparator() {
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
    CodeSmell p1 = (CodeSmell) e1;
    CodeSmell p2 = (CodeSmell) e2;
    int rc = 0;
 //   ((TableViewer)viewer).getTable().getColumn(propertyIndex);
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
