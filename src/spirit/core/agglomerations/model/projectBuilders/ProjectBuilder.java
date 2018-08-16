package spirit.core.agglomerations.model.projectBuilders;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import spirit.core.agglomerations.model.Project;
import spirit.core.agglomerations.model.projectBuilders.impl.ProjectFromPackageStructureBuilder;

/**
 * Abstract class that defines the common attributes and methods for project builders.
 * Given an eclipse project, a project builder must recover the components implemented in the project.
 * @author Willian
 *
 */
public abstract class ProjectBuilder {

	protected final IProject eclipseProject;	
	
	public ProjectBuilder(IProject eclipseProject) {
		this.eclipseProject = eclipseProject;
	}
	
	/**
	 * Returns a Project with its respective components and classes 
	 * @return
	 */
	public abstract Project parse();

	public IProject getEclipseProject() {
		return eclipseProject;
	}
	
	/**
	 * Reads a ICompilationUnit and creates the AST DOM for manipulating the
	 * Java source file
	 * 
	 * @param unit
	 * @return
	 */
	protected CompilationUnit parseInterfaceToCompilationUnit(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null);
	}

	/**
	 * Returns the default builder
	 * @return Project
	 */
	protected Project defaultBuilder() {
		return new ProjectFromPackageStructureBuilder(eclipseProject).parse();
	}
}
