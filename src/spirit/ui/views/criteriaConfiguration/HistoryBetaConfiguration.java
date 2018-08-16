package spirit.ui.views.criteriaConfiguration;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javancss.Javancss;
import javancss.ObjectMetric;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import spirit.db.DataBaseManager;
import spirit.priotization.criteria.CriteriaWithNOMHistory;
import spirit.priotization.criteria.HistoryBetaCriteria;
import spirit.priotization.criteria.util.NOMHistoryOfAVersion;
import spirit.priotization.criteria.util.NOMOfAClass;

public class HistoryBetaConfiguration extends CriterionConfigurationDialog {
	private CriteriaWithNOMHistory betaCriterion;
	private Vector<String> sourceFolders;
	private ListViewer listViewer;
	private IProject currentProject;
	private DirectoryDialog dialog;
	
	public HistoryBetaConfiguration(Shell parentShell, CriteriaWithNOMHistory betaCriterion,IProject currentProject) {
		super(parentShell);
		this.betaCriterion=betaCriterion;
		sourceFolders=new Vector<String>();
		dialog = new DirectoryDialog(parentShell);
	
		for (Iterator<NOMHistoryOfAVersion> iterator = betaCriterion.getNomHistory().iterator(); iterator.hasNext();) {
			NOMHistoryOfAVersion type = (NOMHistoryOfAVersion) iterator.next();
			sourceFolders.add(type.getPathToSource());
		}
		//Remove the last one that is the current version
		if(betaCriterion.getNomHistory().size()>0)
			sourceFolders.removeElement(sourceFolders.lastElement());
		this.currentProject=currentProject;
	}
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		listViewer = new ListViewer(container, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		List list = listViewer.getList();
		list.setBounds(20, 24, 203, 238);
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		listViewer.setInput(sourceFolders);
		
		Button btnUp = new Button(container, SWT.NONE);
		btnUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				upButtonClicked();
			}
		});
		btnUp.setBounds(227, 102, 94, 28);
		btnUp.setText("Up");
		
		Button btnDown = new Button(container, SWT.NONE);
		btnDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				downButtonClicked();
			}
		});
		btnDown.setBounds(227, 136, 94, 28);
		btnDown.setText("Down");
		
		Button btnDelete = new Button(container, SWT.NONE);
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				deleteButtonClicked();
			}
		});
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnDelete.setBounds(227, 170, 94, 28);
		btnDelete.setText("Delete");
		
		Button btnLoad = new Button(container, SWT.NONE);
		btnLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				loadButtonClicked();
			}
		});
		btnLoad.setBounds(227, 68, 94, 28);
		btnLoad.setText("Load");
		
		
		
		return container;
	}
	
	private void downButtonClicked() {
		IStructuredSelection selection = (IStructuredSelection) listViewer
			      .getSelection();
		if (selection.size() > 0){
			int index=sourceFolders.indexOf(selection.getFirstElement());
			if(index< (sourceFolders.size()-1)){
				sourceFolders.removeElementAt(index);
				sourceFolders.add((index+1), (String) selection.getFirstElement());
				listViewer.setInput(sourceFolders);
			}
		}
	}
	private void upButtonClicked() {
		IStructuredSelection selection = (IStructuredSelection) listViewer
			      .getSelection();
		if (selection.size() > 0){
			int index=sourceFolders.indexOf(selection.getFirstElement());
			if(index>0){
				sourceFolders.removeElementAt(index);
				sourceFolders.add((index-1), (String) selection.getFirstElement());
				listViewer.setInput(sourceFolders);
			}
		}
	}
	private void deleteButtonClicked() {
		IStructuredSelection selection = (IStructuredSelection) listViewer
			      .getSelection();
		if (selection.size() > 0){
			sourceFolders.remove(selection.getFirstElement());
			listViewer.setInput(sourceFolders);
		}
		
	}
	private void loadButtonClicked() {
		
		
		sourceFolders.add(dialog.open());
		listViewer.setInput(sourceFolders);
	}
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				okButtonPressed();
			}
		});
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}
	private void okButtonPressed() {
		Vector<NOMHistoryOfAVersion> nomHistory=new Vector<NOMHistoryOfAVersion>();
		for (Iterator<String> iterator = sourceFolders.iterator(); iterator.hasNext();) {
			String path = (String) iterator.next();
			nomHistory.add(analizeNOMofAVersionOfTheSystem(path));
		}
		nomHistory.add(analizeNOMofAVersionOfTheSystem(currentProject.getLocation().toString()));
		betaCriterion.loadNOMHistory(nomHistory);
		DataBaseManager.getInstance().saveOrUpdateEntity(betaCriterion.getNomHistoryProject());
		
		DataBaseManager.getInstance().saveOrUpdateEntity(betaCriterion);
	}
	private NOMHistoryOfAVersion analizeNOMofAVersionOfTheSystem(String path){
		String[] asArgs={"-object", path};
		Vector<NOMOfAClass> ret=new Vector<NOMOfAClass>();
		try {
			Javancss pJavancss = new Javancss(asArgs);
			java.util.List<ObjectMetric> listOfMetrics=pJavancss.getObjectMetrics();
			for (Iterator<ObjectMetric> iterator = listOfMetrics.iterator(); iterator
					.hasNext();) {
				ObjectMetric object = (ObjectMetric) iterator.next();
				ret.add(new NOMOfAClass(object.name, object.functions));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new NOMHistoryOfAVersion(ret,path);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(353, 367);
	}
}
