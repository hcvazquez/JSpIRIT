package spirit.ui.views.criteriaConfiguration.util;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import spirit.priotization.criteria.util.ArchitecturalConcern;
import spirit.ui.views.criteriaConfiguration.ArchitecturalConcernsConfiguration;

public class ModifyArchitecturalConcern extends CreateArchitecturalConcern {
	
	private ArchitecturalConcern currentConcern;
	
	public ModifyArchitecturalConcern(Shell parentShell,ArchitecturalConcernsConfiguration architecturalProblemsConfiguration, IProject currentProject, ArchitecturalConcern currentConcern){
		super(parentShell, architecturalProblemsConfiguration, currentProject);
		this.currentConcern=currentConcern;
	}
	@Override
	protected Control createDialogArea(Composite parent) {
		Control container=super.createDialogArea(parent);
		//Load data
		setConcernName(currentConcern.getName());
		
		for (Iterator<String> iterator = currentConcern.getClasses().iterator(); iterator.hasNext();) {
			String type = (String) iterator.next();
			addNewClassToConcern(type);
		}
		for (Iterator<String> iterator = currentConcern.getPackages().iterator(); iterator.hasNext();) {
			String type = (String) iterator.next();
			addNewPackageToConcern(type);
		}
		loadImportanceValue(currentConcern.getImportance());
		return container;
	}
	
	@Override
	protected void okButtonClicked() {
		currentConcern.setName(getConcernName());
		currentConcern.setClasses(getSelectedClasses());
		currentConcern.setPackages(getSelectedPackages());
		currentConcern.setImportance(getImportance());
	}
}
