package spirit.ui.views;


import java.util.Iterator;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import spirit.Activator;
import spirit.core.agglomerations.AgglomerationModel;
import spirit.core.design.AgglomerationManager;
import spirit.core.smells.CodeSmell;
import spirit.priotization.RankingManagerForAgglomerations;
import spirit.priotization.RankingManagerForSmells;
import spirit.priotization.rankings.RankingCalculatorForAgglomerations;
import spirit.ui.views.agglomerations.AgglomerationsPreferences;
import spirit.ui.views.ranking.agglomerations.RankingAgglomerationViewerComparator;
import spirit.ui.views.ranking.agglomerations.RankingAgglomerationViewerFactory;


public class SpIRITAgglomerationsView extends ViewPart {
	
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "spirit.views.SpIRITAgglomerationsView";

	private TableViewer viewer;
	private Action configureDetectionThresholds;
	private Action doubleClickAction;
	private Action configureRanking;
	private RankingAgglomerationViewerComparator comparator;
	private Action copyRankingToClipboard;
	
	/**
	 * The constructor.
	 */
	public SpIRITAgglomerationsView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		// create the columns 
		createUpdateColumns();

		// make lines and header visible
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true); 
		
		// Set the ContentProvider
		viewer.setContentProvider(ArrayContentProvider.getInstance());

		// make the selection available to other views
		getSite().setSelectionProvider(viewer);
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			  @Override
			  public void selectionChanged(SelectionChangedEvent event) {
			    IStructuredSelection selection = (IStructuredSelection)
			        viewer.getSelection();
			    Object firstElement = selection.getFirstElement();
			    //TODO Willian: open a new view to explore the agglomeration
			  }
			}); 
		
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "SpIRIT.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	public void createUpdateColumns() {
		if(RankingManagerForSmells.getInstance().getRankingCalculator()!=null){
			viewer.getTable().setRedraw( false );
			viewer.getTable().removeAll();
			for(TableColumn column:viewer.getTable().getColumns()){
				column.dispose();
			}
			
			viewer.getTable().setRedraw( true );

			comparator = RankingAgglomerationViewerFactory.getRankingComparatorViewer(((RankingCalculatorForAgglomerations)RankingManagerForAgglomerations.getInstance().getRankingCalculator()).getRankingType());
		    viewer.setComparator(comparator);
			viewer.getTable().setRedraw( true );
			RankingAgglomerationViewerFactory.getRankingViewer(((RankingCalculatorForAgglomerations)RankingManagerForAgglomerations.getInstance().getRankingCalculator()).getRankingType()).createTableViewer(comparator, viewer);			
		}
	}
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SpIRITAgglomerationsView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(configureRanking);
		manager.add(configureDetectionThresholds);
		manager.add(new Separator());
	}
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(configureRanking);
	}
	private void fillContextMenu(IMenuManager manager) {
		manager.add(copyRankingToClipboard);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		for (Iterator<Action> actions = SpIRITViewPopupMenuActionManager.getInstance().getActions().iterator(); actions.hasNext();) {
			Action anAction = (Action) actions.next();
			manager.add(anAction);
		}
	}

	private void makeActions() {
		
		configureRanking = new Action() {
			public void run() {
				openRankingConfiguration();
			}
		};
		configureRanking.setText("Ranking configuration");
		configureRanking.setToolTipText("Configure how the ranking is calculated");
		
		
		ImageDescriptor desc = ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry("icons/configuration.png"));
		configureRanking.setImageDescriptor(desc);
		
		
		configureDetectionThresholds= new Action() {
			public void run() {
				openThresholdsConfiguration();
			}
		};
		configureDetectionThresholds.setText("Detection configuration");
		configureDetectionThresholds.setToolTipText("Configure the thresholds used to detect the agglomerations");

		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				CodeSmell p = (CodeSmell) ((IStructuredSelection)selection).getFirstElement();
				if(p.getElement() instanceof TypeDeclaration){
					try {
						JavaUI.openInEditor( ((TypeDeclaration)(p.getElement())).resolveBinding().getJavaElement());
					} catch (PartInitException e) {
						e.printStackTrace();
					} catch (JavaModelException e) {
						e.printStackTrace();
					}
			    }else if(p.getElement() instanceof MethodDeclaration){
			    	try {
						JavaUI.openInEditor( ((MethodDeclaration)(p.getElement())).resolveBinding().getJavaElement());
					} catch (PartInitException e) {
						e.printStackTrace();
					} catch (JavaModelException e) {
						e.printStackTrace();
					}
			    }
			}
		};
		copyRankingToClipboard= new Action() {
			public void run() {//ACA!!!
				Table table=viewer.getTable(); // o comparator
				TableItem[] tableItems = table.getItems();
				String ret="";
				for (int i = 0; i < tableItems.length; i++) {
					
					
					for (int j = 0; j < table.getColumnCount(); j++) {
						if(j>2){//si estoy despues de la 3er columna seguramente es un float. Lo guardo con coma para Excel.
							ret=ret+tableItems[i].getText(j).replace(".", ",");
						}else
							ret=ret+tableItems[i].getText(j);
						if((j+1)<table.getColumnCount())
							ret=ret+"\t";
					}
					ret=ret+System.getProperty("line.separator");
				}
				 Clipboard cb = new Clipboard(Display.getDefault());
				 TextTransfer textTransfer = TextTransfer.getInstance();
				    cb.setContents(new Object[] { ret },
				        new Transfer[] { textTransfer });
			}
		};
		copyRankingToClipboard.setText("Copy ranking to clipboard");
	}

	private void openThresholdsConfiguration() {
		if(RankingManagerForSmells.getInstance().getCurrentProject()!=null)
			new AgglomerationsPreferences(viewer.getControl().getShell(),this).open();
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
		
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	public void updateView(){
		// Get the content for the Viewer,
		// setInput will call getElements in the ContentProvider
		createUpdateColumns();
		viewer.setInput(AgglomerationManager.getInstance().getResultsForCurrentProject());
	}
	
	public AgglomerationModel getSelectedAgglomerations(){
		ISelection selection = viewer.getSelection();
		AgglomerationModel p = (AgglomerationModel) ((IStructuredSelection)selection).getFirstElement();
		return p;
	}
		
	private void openRankingConfiguration() {
		if(RankingManagerForSmells.getInstance().getCurrentProject()!=null)
			new RankingConfigurationForAgglomerations(viewer.getControl().getShell(),this).open();
		
	}
}