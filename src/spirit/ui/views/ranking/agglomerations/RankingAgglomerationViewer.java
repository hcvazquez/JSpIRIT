package spirit.ui.views.ranking.agglomerations;

import org.eclipse.jface.viewers.TableViewer;

public interface RankingAgglomerationViewer {
	
	public void createTableViewer(RankingAgglomerationViewerComparator comparator, TableViewer viewer);
}
