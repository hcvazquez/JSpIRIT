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

import bandago.views.MessageWindow;
import bandago.views.StartupManager;
import spirit.Activator;
import spirit.core.design.CodeSmellsManagerFactory;
import spirit.core.smells.CodeSmell;
import spirit.priotization.RankingManagerForSmells;
import spirit.ui.views.ranking.RankingViewerComparator;
import spirit.ui.views.ranking.RankingViewerFactory;
import spirit.ui.views.smellDetectionConfiguration.SmellsThresholdsConfiguration;
import spirit.priotization.rankings.RankingCalculatorForSmells;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class SpIRITSmellsView extends ViewPart {
	
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "spirit.views.SpIRITSmellsView";

	private TableViewer viewer;
	private Action solver;
	private Action configureRanking;
	private Action configureDetectionThresholds;
	private Action doubleClickAction;
	private Action copyRankingToClipboard;
	
	private RankingViewerComparator comparator;

	/**
	 * The constructor.
	 */
	public SpIRITSmellsView() {
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
			  @SuppressWarnings("unused")
			@Override
			  public void selectionChanged(SelectionChangedEvent event) {
			    IStructuredSelection selection = (IStructuredSelection)
			        viewer.getSelection();
			    Object firstElement = selection.getFirstElement();
			    // do something with it
			    //TODO
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
			
			// Set the sorter for the table
		    comparator = RankingViewerFactory.getRankingComparatorViewer(((RankingCalculatorForSmells)RankingManagerForSmells.getInstance().getRankingCalculator()).getRankingType());
		    viewer.setComparator(comparator);
		    
			viewer.getTable().setRedraw( true );
			RankingViewerFactory.getRankingViewer(((RankingCalculatorForSmells)RankingManagerForSmells.getInstance().getRankingCalculator()).getRankingType())
									.createTableViewer(comparator, viewer);
		}
	}
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SpIRITSmellsView.this.fillContextMenu(manager);
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

	private void fillContextMenu(IMenuManager manager) {
		manager.add(solver);
		
		manager.add(copyRankingToClipboard);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		for (Iterator<Action> actions = SpIRITViewPopupMenuActionManager.getInstance().getActions().iterator(); actions.hasNext();) {
			Action anAction = (Action) actions.next();
			manager.add(anAction);
		}
		// Other plug-ins can contribute there actions here
		
		
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(configureRanking);
	}

	private void makeActions() {
		
		solver = new Action() {
			public void run() {
				if(viewer.getTable().getSelection().length>=1) {
					ISelection selection = viewer.getSelection();
					CodeSmell p = (CodeSmell) ((IStructuredSelection)selection).getFirstElement();
					switch(p.getKindOfSmellName()){
					case "Brain Method":
						new StartupManager(p);						
					break;
					default:
						String message = "¡Ups! Sólo Brain Methods por el momento." + "\nCode Smell: " + p.getKindOfSmellName();
						new MessageWindow(Display.getCurrent().getActiveShell(),message).open();
					break;
					}	
				}
			}
		};
		solver.setText("Solve Code Smell");
		solver.setToolTipText("Only Brain Methods by the moment.");
		ImageDescriptor solverImg = ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry("icons/bandago.gif"));
		solver.setImageDescriptor(solverImg);
		
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
		configureDetectionThresholds.setToolTipText("Configure the thresholds used to detect the smells");

		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				CodeSmell p = (CodeSmell) ((IStructuredSelection)selection).getFirstElement();
				if(p.getElement() instanceof TypeDeclaration){
					try {
						JavaUI.openInEditor( ((TypeDeclaration)(p.getElement())).resolveBinding().getJavaElement());
					} catch (PartInitException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JavaModelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }else if(p.getElement() instanceof MethodDeclaration){
			    	try {
						JavaUI.openInEditor( ((MethodDeclaration)(p.getElement())).resolveBinding().getJavaElement());
					} catch (PartInitException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JavaModelException e) {
						// TODO Auto-generated catch block
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
			new SmellsThresholdsConfiguration(viewer.getControl().getShell(),this).open();
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
		
	}
	private void openRankingConfiguration() {
		if(RankingManagerForSmells.getInstance().getCurrentProject()!=null)
			new RankingConfigurationForSmells(viewer.getControl().getShell(),this).open();
		
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
		viewer.setInput(CodeSmellsManagerFactory.getInstance().getCurrentProjectManager().getSmells());
	}
	public CodeSmell getCodeSmellSelected(){
		ISelection selection = viewer.getSelection();
		CodeSmell p = (CodeSmell) ((IStructuredSelection)selection).getFirstElement();
		return p;
	}
	
}
