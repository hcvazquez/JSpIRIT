package spirit.ui.views.ranking;

import org.eclipse.jface.viewers.TableViewer;

public interface RankingViewer {
	
	public void createTableViewer(RankingViewerComparator comparator, TableViewer viewer);
}
