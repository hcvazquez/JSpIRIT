package spirit.ui.views.ranking.agglomerations;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.priotization.RankingManagerForAgglomerations;

public class RankingAgglomerationCalculatorViewer implements RankingAgglomerationViewer {
	
	public void createTableViewer(RankingAgglomerationViewerComparator comparator, TableViewer viewer){
		TableViewerColumn colTopology = createTableViewerColumn(comparator, viewer, "Topology", 120, 0);//"Pattern", 120, 0);
		colTopology.setLabelProvider(new ColumnLabelProvider() {
		  @Override
		  public String getText(Object element) {
		  AgglomerationModel p = (AgglomerationModel) element;
		    return p.getTopologyName();
		  }
		});
		
		TableViewerColumn colDescription = createTableViewerColumn(comparator, viewer, "Agglomeration Description", 450, 1);
		colDescription.setLabelProvider(new ColumnLabelProvider() {
		  @Override
		  public String getText(Object element) {
			  AgglomerationModel p = (AgglomerationModel) element;
		    return p.toString();
		  }
		});
		
		TableViewerColumn colRanking = createTableViewerColumn(comparator, viewer, "#Ranking", 100, 2);
		colRanking.setLabelProvider(new ColumnLabelProvider() {
		  @Override
		  public String getText(Object element) {
			  AgglomerationModel p = (AgglomerationModel) element;
		   
		    return (new Integer(RankingManagerForAgglomerations.getInstance().getRankingCalculator().getRankingPosition(p))).toString();
		  }
		});
		TableViewerColumn colRankingValue = createTableViewerColumn(comparator, viewer, "Ranking Value", 100, 3);
		colRankingValue.setLabelProvider(new ColumnLabelProvider() {
		  @Override
		  public String getText(Object element) {
			  AgglomerationModel p = (AgglomerationModel) element;
		   
		    return (RankingManagerForAgglomerations.getInstance().getRankingCalculator().getRankingValue(p)).toString();
		  }
		});
	}
	
	protected TableViewerColumn createTableViewerColumn(RankingAgglomerationViewerComparator comparator, TableViewer viewer,String title, int bound, final int colNumber) {
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
	
	protected SelectionAdapter getSelectionAdapter(final RankingAgglomerationViewerComparator comparator, final TableViewer viewer,final TableColumn column,
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
