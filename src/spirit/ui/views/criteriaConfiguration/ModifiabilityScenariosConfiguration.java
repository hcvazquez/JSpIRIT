package spirit.ui.views.criteriaConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import spirit.db.DataBaseManager;
import spirit.priotization.criteria.CriteriaWithScenarios;
import spirit.priotization.criteria.ModifiabilityScenariosCriteria;
import spirit.priotization.criteria.util.ModifiabilityScenario;
import spirit.ui.views.criteriaConfiguration.util.CreateModifiabilityScenario;
import spirit.ui.views.criteriaConfiguration.util.ModifyModifiabilityScenario;

public class ModifiabilityScenariosConfiguration extends
		CriterionConfigurationDialog {
	private CriteriaWithScenarios selectedCriterion;
	private ListViewer listViewer;
	private java.util.List<ModifiabilityScenario> scenarios;
	private java.util.List<ModifiabilityScenario> scenariosToDelete;
	private IProject currentProject;
	public ModifiabilityScenariosConfiguration(Shell parentShell, CriteriaWithScenarios selectedCriterion, IProject currentProject) {
		super(parentShell);
		this.selectedCriterion=selectedCriterion;
		
		scenarios=selectedCriterion.getScenarios();//ACA HAY QUE CARGAR LO QUE TENGO EN LA BD
		this.currentProject=currentProject;
		scenariosToDelete=new Vector<ModifiabilityScenario>();
	}
	
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		Label lblScenarios = new Label(container, SWT.NONE);
		lblScenarios.setBounds(10, 10, 59, 14);
		lblScenarios.setText("Scenarios");
		
		listViewer = new ListViewer(container, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		List list = listViewer.getList();
		list.setBounds(10, 30, 219, 241);
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		listViewer.setInput(scenarios);
		Button btnAdd = new Button(container, SWT.NONE);
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				addButtonClicked();
			}
		});
		btnAdd.setBounds(249, 46, 94, 28);
		btnAdd.setText("Add");
		
		Button btnRemove = new Button(container, SWT.NONE);
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				removeButtonClicked();
			}
		});
		btnRemove.setBounds(249, 80, 94, 28);
		btnRemove.setText("Remove");
		
		Button btnLoadFromXml = new Button(container, SWT.NONE);
		btnLoadFromXml.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				loadFromXMLClicked();
			}
		});
		btnLoadFromXml.setBounds(249, 162, 108, 28);
		btnLoadFromXml.setText("Load from XML");
		
		Button btnModify = new Button(container, SWT.NONE);
		btnModify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				modifyButtonClicked();
			}
		});
		btnModify.setBounds(249, 114, 94, 28);
		btnModify.setText("Modify");
		
		
		return container;
	}
	
	private void loadFromXMLClicked() {
		FileDialog dialog = new FileDialog(this.getShell());
		java.util.List<ModifiabilityScenario> scenariosFromXML=parseXML(dialog.open());
		//Cargarlos en la interface
		scenarios.addAll(scenariosFromXML);
		listViewer.setInput(scenarios);
	}

	private java.util.List<ModifiabilityScenario> parseXML(String path) {
		
		

         // Load the input XML document, parse it and return an instance of the
         // Document class.
         java.util.List<ModifiabilityScenario> ret = new Vector<ModifiabilityScenario>();
		try {
			 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(path));
			
	        NodeList nodeList = document.getDocumentElement().getChildNodes();
	        for (int i = 0; i < nodeList.getLength(); i++) {
	             Node node = nodeList.item(i);

	             if (node.getNodeType() == Node.ELEMENT_NODE) {
	                  Element elem = (Element) node;

	                 
	                  // Get the value of all sub-elements.
	                  String name = elem.getElementsByTagName("name")
	                                      .item(0).getChildNodes().item(0).getNodeValue();

	                  String description = elem.getElementsByTagName("description").item(0)
	                                      .getChildNodes().item(0).getNodeValue();


	                  Double importance = Double.parseDouble(elem.getElementsByTagName("importance")
	                                      .item(0).getChildNodes().item(0).getNodeValue());
	                  Vector<String> classes=parseXMLClasses(elem.getElementsByTagName("classes").item(0),"class");
	                  Vector<String> packages=parseXMLClasses(elem.getElementsByTagName("packages").item(0),"package");;

	                  ret.add(new ModifiabilityScenario(name, description, classes, packages, importance,currentProject.getFullPath().toString()));
	             }
	        }
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
	private Vector<String> parseXMLClasses(Node node, String elementName){
		Vector<String> ret=new Vector<>();
		if(node!=null)
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                 Element elem = (Element) node;
                 NodeList nodeList=elem.getElementsByTagName(elementName);
                 for (int i = 0; i < nodeList.getLength(); i++) {
                	 Element elem2 = (Element) nodeList.item(i);
                	 String path =elem2.getChildNodes().item(0).getNodeValue();
                	 
    	             ret.add(path);
                     
                 }
                 
                
            }
		
		return ret;
	}

	private void removeButtonClicked() {
		IStructuredSelection selection = (IStructuredSelection) listViewer
			      .getSelection();
		if (selection.size() > 0){
			scenariosToDelete.add((ModifiabilityScenario) selection.getFirstElement());
			scenarios.remove(selection.getFirstElement());
			listViewer.setInput(scenarios);
		}
	}

	private void addButtonClicked() {
		new CreateModifiabilityScenario(getShell(),this,currentProject).open();
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
		for (Iterator<ModifiabilityScenario> iterator = scenariosToDelete.iterator(); iterator.hasNext();) {
			ModifiabilityScenario type = (ModifiabilityScenario) iterator.next();
			DataBaseManager.getInstance().deleteEntity(type);
		}
		for (Iterator<ModifiabilityScenario> iterator = scenarios.iterator(); iterator.hasNext();) {
			ModifiabilityScenario type = (ModifiabilityScenario) iterator.next();
			DataBaseManager.getInstance().saveOrUpdateEntity(type);
		}
		
		
		selectedCriterion.updateScenarios();
		
	}
	
	
	
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(385, 367);
	}
	public void addScenario(ModifiabilityScenario scenario){
		scenarios.add(scenario);
		listViewer.setInput(scenarios);
	}
	
	private void modifyButtonClicked() {
		IStructuredSelection selection = (IStructuredSelection) listViewer
			      .getSelection();
		if (selection.size() > 0){
			new ModifyModifiabilityScenario(getShell(),this,currentProject, (ModifiabilityScenario) selection.getFirstElement()).open();
		}
	}
}
