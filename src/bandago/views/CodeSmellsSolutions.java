package bandago.views;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.*;
import org.eclipse.jdt.internal.corext.refactoring.code.ExtractMethodRefactoring;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import spirit.Activator;
import spirit.core.smells.CodeSmell;
import bandago.algorithms.SimulatedAnnealing;
import bandago.metrics.MetricAnalyzer;
import bandago.solvers.CodeSmellSolver;
import bandago.utils.CompilationUnitManager;


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
@SuppressWarnings("restriction")
public class CodeSmellsSolutions extends ViewPart implements Observer {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "bandago.views.CodeSmellsSolutions";

	private static Tree t;
	private Action applyRefactor;
	private Action stopRefactor;
	private Action refresh;
	private Action parameterConfiguration;
	private Composite p;
	private static TreeItem currentFather;
	private Object[] observerArgs;
	static Observable observable;
	static CodeSmellsSolutions instance;
	//va a ser despues una strategy
	private static ExtractMethodRefactoring Selectedrefactoring;
	private static Image imageSolution;
	private static Image imageMethod;
	
	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return new String[] { "One", "Two", "Three" };
		}
	}
	
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().
					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	@SuppressWarnings("static-access")
	public CodeSmellsSolutions() {
		this.instance = this;
		imageSolution = new Image(Display.getCurrent(),getClass().getClassLoader().getResourceAsStream("solution.png"));
		imageMethod = new Image(Display.getCurrent(), getClass().getClassLoader().getResourceAsStream("method.png"));
	}

	/**
	 * 
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		p = parent;
		t = new Tree(p,SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		t.setHeaderVisible(true);
	    t.setLinesVisible(true);
	    createUpdateColumns();
	    makeActions();
	    hookContextMenu();
	    contributeToActionBars();
	}
	
	public static void emptyTable(){
		t.clearAll(true);
		t.setItemCount(0);
	}

	public static void addSolutionItem(String solutionName, MetricAnalyzer metrics, CodeSmellSolver solver){
		TreeItem item = new TreeItem(t,SWT.NONE);
		addItem(item,solutionName,metrics);
		item.setData(solver);
		item.setImage(imageSolution);
		currentFather = item;
	}
	
	public static void addExtractedItem(String solutionName, MetricAnalyzer metrics){
		TreeItem item = new TreeItem(currentFather,SWT.NONE);
		addItem(item,solutionName,metrics);
		item.setImage(imageMethod);
	}
	
	private static void addItem(TreeItem item, String solutionName, MetricAnalyzer metrics){
		String[] solucion = {solutionName,
							new DecimalFormat("#.000").format(metrics.maximumNestingLevel()),
							new DecimalFormat("#.000").format(metrics.numberOfLinesOfCode()),
							new DecimalFormat("#.000").format(metrics.ciclomaticComplexity()),
							new DecimalFormat("#.000").format(metrics.numberOfFields()),
							Integer.toString(metrics.quantityOfExtractedMethods())};
		item.setText(solucion);
	}
	
	public void createColumn(String name, int size, String toolTipText){
		TreeColumn column = new TreeColumn(t, SWT.LEFT);
	    column.setText(name);
	    column.setWidth(size);
	    column.setToolTipText(toolTipText);
	    column.setMoveable(true);
	}
	
	public void createUpdateColumns() { //LE CAMBIO EL NOMBRE? PEDORRO
		
		createColumn("Solutions",150,"");
		createColumn("MNL",100,"Maximum Nesting Level");
		createColumn("LOC",100,"Lines of Code");
		createColumn("WMC",100,"Weighted Method Count");
		createColumn("NOF",100,"Number of Fields");
		createColumn("Extracted Methods",110,"Number of extracted methods");
	}
	
	protected TableViewerColumn createTableViewerColumn(TableViewer viewer,String title, int bound, final int colNumber) {
	    final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
	        SWT.NONE);
	    final TableColumn column = viewerColumn.getColumn();
	    column.setText(title);
	    column.setWidth(bound);
	    column.setResizable(true);
	    column.setMoveable(true);
	    return viewerColumn;
	 }
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				CodeSmellsSolutions.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(p);
		p.setMenu(menu);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillContextMenu(IMenuManager manager) {
		if(t.getSelection().length>=1){
			if(t.getSelection()[0].getData()!=null)
			manager.add(applyRefactor);
		}
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(applyRefactor);
		manager.add(stopRefactor);
		manager.add(refresh);
		manager.add(parameterConfiguration);
		manager.add(new Separator());
	}

	public static ExtractMethodRefactoring getSelectedStrategy(){
		return Selectedrefactoring;
	}
	
	private void makeActions() {
		
		parameterConfiguration = new Action() {
			public void run() {
				openRankingConfiguration();
			}
		};
		parameterConfiguration.setText("Parameter configuration");
		parameterConfiguration.setToolTipText("Configure the parameters of the refactoring algorithm.");
		ImageDescriptor desc = ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry("icons/configuration.png"));
		parameterConfiguration.setImageDescriptor(desc);
		
		applyRefactor = new Action() {
			public void run() {
				TreeItem[] selection = t.getSelection();
				if(selection.length>=1){
					if(selection[0].getData()!=null){
						String  s = selection[0].getText(0);
						String[] split = s.split(" ");
						int solution = Integer.valueOf(split[1]);
						CodeSmellSolver solver = (CodeSmellSolver) selection[0].getData();
						CompilationUnitManager u = solver.previewSolution(solution-1);
						openExtractMethodPreview(StartupManager.getCodeSmell(),u,solver,solution);
					}
				}
			}
		};
		applyRefactor.setText("Apply refactor");
		ImageDescriptor img = ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry("icons/apply.png"));
		applyRefactor.setImageDescriptor(img);
		
		stopRefactor = new Action() {
			@SuppressWarnings("deprecation")
			public void run() {
				Thread thread = StartupManager.getWorkingThread();
				if(thread != null){
					thread.stop();
					StartupManager.deleteRefactroingCopies();
				}
				
			}
		};
		stopRefactor.setText("Stop refactor");
		ImageDescriptor stop = ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry("icons/stop.gif"));
		stopRefactor.setImageDescriptor(stop);
		
		refresh = new Action() {
			public void run() {
				CodeSmell codeS = StartupManager.getCodeSmell();
				if(codeS != null)
					StartupManager.actionDo(codeS);
			}
		};
		refresh.setText("Refresh solutions");
		ImageDescriptor ref = ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry("icons/refresh.gif"));
		refresh.setImageDescriptor(ref);
	}
	
	private void openRankingConfiguration() {
		new ParameterConfigurator(p.getShell()).open();
		
	}
	
	private void openExtractMethodPreview(CodeSmell c, CompilationUnitManager u, CodeSmellSolver solver, int i) {
		new ExtractMethodPreview(this.getSite().getShell(),c, u, solver,i).open();				
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		p.setFocus();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof SimulatedAnnealing){
			observerArgs = (Object[]) arg;
			if((boolean)observerArgs[0]) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
				    	new MessageWindow(Display.getCurrent().getActiveShell(),(String)observerArgs[1]).open();  
				      }
				});
				
			} else {
			Display.getDefault().asyncExec(new Runnable() {
			    @SuppressWarnings("unchecked")
				public void run() {
					    addSolutionItem((String)observerArgs[1], (MetricAnalyzer)observerArgs[2], (CodeSmellSolver)observerArgs[3]);
					    ArrayList<MetricAnalyzer> metrics = (ArrayList<MetricAnalyzer>) observerArgs[4];
					    Collections.sort(metrics);
					    for(MetricAnalyzer m:metrics){
					    	addExtractedItem(m.getMethodName(), m);
					    }
			      }
			});}
		}
	}
	
	public static CodeSmellsSolutions getInstance(){
		return instance;
	}
	
}