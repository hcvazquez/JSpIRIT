package spirit.core.agglomerations.model.projectBuilders.impl;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;

import spirit.core.agglomerations.model.Component;
import spirit.core.agglomerations.model.Project;
import spirit.core.agglomerations.model.projectBuilders.ComponentVisitor;
import spirit.core.agglomerations.model.projectBuilders.ProjectBuilder;
//import spirit.metrics.analizer.SpiritVisitor;

/**
 * Concrete implementation of {@link ProjectBuilder }.
 * Parses the package structure to a {@link Project} instance.
 * @author Willian
 *
 */
public class ProjectFromPackageStructureBuilder extends ProjectBuilder {

	public static final String NAME = "Package Structure";
	
	public ProjectFromPackageStructureBuilder(IProject eclipseProject) {
		super(eclipseProject);
	}
	
	@Override
	public Project parse() {
		//SpiritVisitor codeSmellsVisitor = new SpiritVisitor();
		//JSpiritAdapter connector = new JSpiritAdapter(codeSmellsVisitor);
		IJavaProject javaProject = JavaCore.create(getEclipseProject());
		Project project = new Project(javaProject/*, connector*/);
		
		try {
			for(IPackageFragment package_ : javaProject.getPackageFragments()) {
				if (package_.getKind() == IPackageFragmentRoot.K_SOURCE) {
					ICompilationUnit[] compilationUnits = package_.getCompilationUnits();
					if (compilationUnits.length > 0) {
						Component component = new Component(package_.getElementName());
						ComponentVisitor componentBuilderVisitor = new ComponentVisitor(component);
						project.addComponent(component);
						
						for (ICompilationUnit i : compilationUnits) {
							System.out.println("mapping(\'" + package_.getElementName() + "\', class(\'" + package_.getElementName() + "\',\'"+ i.getElementName() + "\')).");
							CompilationUnit compilationUnit =  parseInterfaceToCompilationUnit(i);
							//compilationUnit.accept(codeSmellsVisitor);
							compilationUnit.accept(componentBuilderVisitor);
						}
					}
				}
			}
		} catch (JavaModelException e) {
			System.err.println("Exception when parsing package structure to project: " + e);
			return null;
		}
				
		return project;
	}
}
