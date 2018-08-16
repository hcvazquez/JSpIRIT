package spirit.ui.views.criteriaConfiguration.util;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;


import spirit.priotization.criteria.util.ModifiabilityScenario;

import spirit.ui.views.criteriaConfiguration.ModifiabilityScenariosConfiguration;

public class ModifyModifiabilityScenario extends CreateModifiabilityScenario {
	private ModifiabilityScenario currentScenario;
	
	public ModifyModifiabilityScenario(Shell parentShell,ModifiabilityScenariosConfiguration architecturalProblemsConfiguration, IProject currentProject, ModifiabilityScenario currentScenario){
		super(parentShell, architecturalProblemsConfiguration, currentProject);
		this.currentScenario=currentScenario;
	}
	@Override
	protected Control createDialogArea(Composite parent) {
		Control container=super.createDialogArea(parent);
		//Load data
		setScenarioName(currentScenario.getName());
		setScenarioDescription(currentScenario.getDescription());
		
		for (Iterator<String> iterator = currentScenario.getClasses().iterator(); iterator.hasNext();) {
			String type = (String) iterator.next();
			addNewClassToScenario(type);
		}
		for (Iterator<String> iterator = currentScenario.getPackages().iterator(); iterator.hasNext();) {
			String type = (String) iterator.next();
			addNewPackageToScenario(type);
		}
		loadImportanceValue(currentScenario.getImportance());
		return container;
	}
	
	@Override
	protected void okButtonClicked() {
		currentScenario.setName(getScenarioName());
		currentScenario.setDescription(getScenarioDescription());
		currentScenario.setClasses(getSelectedClasses());
		currentScenario.setPackages(getSelectedPackages());
		currentScenario.setImportance(getImportance());
	}
}
