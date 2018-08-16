package spirit.ui.views.ranking;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;

import spirit.core.smells.CodeSmell;
import spirit.priotization.RankingManagerForSmells;

public class RankingCalculatorViewer implements RankingViewer{
	
	public void createTableViewer(RankingViewerComparator comparator, TableViewer viewer){
		TableViewerColumn colKindOfSmell = createTableViewerColumn(comparator, viewer, "Kind of Design Flaw", 120, 0);
		colKindOfSmell.setLabelProvider(new ColumnLabelProvider() {
		  @Override
		  public String getText(Object element) {
		    CodeSmell p = (CodeSmell) element;
		    return p.getKindOfSmellName();
		  }
		});
		
		TableViewerColumn colJavaElement = createTableViewerColumn(comparator, viewer, "Java Element", 300, 1);
		colJavaElement.setLabelProvider(new ColumnLabelProvider() {
		  @Override
		  public String getText(Object element) {
		    CodeSmell p = (CodeSmell) element;
		    return p.getElementName();
		  }
		});
		
		TableViewerColumn colRanking = createTableViewerColumn(comparator, viewer, "#Ranking", 100, 2);
		colRanking.setLabelProvider(new ColumnLabelProvider() {
		  @Override
		  public String getText(Object element) {
		    CodeSmell p = (CodeSmell) element;
		   
		    return (new Integer(RankingManagerForSmells.getInstance().getRankingCalculator().getRankingPosition(p))).toString();
		  }
		});
		TableViewerColumn colRankingValue = createTableViewerColumn(comparator, viewer, "Ranking Value", 100, 3);
		colRankingValue.setLabelProvider(new ColumnLabelProvider() {
		  @Override
		  public String getText(Object element) {
		    CodeSmell p = (CodeSmell) element;
		   
		    return (RankingManagerForSmells.getInstance().getRankingCalculator().getRankingValue(p)).toString();
		  }
		});
	}
	
	protected TableViewerColumn createTableViewerColumn(RankingViewerComparator comparator, TableViewer viewer,String title, int bound, final int colNumber) {
	    final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
	        SWT.NONE);
	    final TableColumn column = viewerColumn.getColumn();
	    column.setText(title);
	    column.setWidth(bound);
	    column.setResizable(true);
	    column.setMoveable(true);
	    column.addSelectionListener(getSelectionAdapter(comparator, viewer,column, colNumber));
	    return viewerColumn;
	 }
	
	protected SelectionAdapter getSelectionAdapter(final RankingViewerComparator comparator, final TableViewer viewer,final TableColumn column,
		      final int index) {
	    SelectionAdapter selectionAdapter = new SelectionAdapter() {
	      @Override
	      public void widgetSelected(SelectionEvent e) {
	        comparator.setColumn(index);
	        int dir = comparator.getDirection();
	        viewer.getTable().setSortDirection(dir);
	        viewer.getTable().setSortColumn(column);
	        viewer.refresh();
	      }
	    };
	    return selectionAdapter;
	}

}
