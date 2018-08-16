package spirit.core.agglomerations.model.projectBuilders.impl;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.internal.core.SourceMethod;
import org.eclipse.jdt.internal.core.SourceType;

import spirit.core.agglomerations.model.Component;
import spirit.core.agglomerations.model.Project;
import spirit.core.agglomerations.model.projectBuilders.ComponentVisitor;
import spirit.core.agglomerations.model.projectBuilders.ProjectBuilder;
//import spirit.metrics.analizer.SpiritVisitor;
import ca.mcgill.cs.serg.cm.ConcernMapper;
import ca.mcgill.cs.serg.cm.model.io.IProgressMonitor;
import ca.mcgill.cs.serg.cm.model.io.ModelIOException;
import ca.mcgill.cs.serg.cm.model.io.ModelReader;

/**
 * Concrete implementation of {@link ProjectBuilder }.
 * Parses a mapping file to a {@link Project} instance.
 * 
 * @author Willian
 *
 */
public class ProjectFromConcernMapperBuilder extends ProjectBuilder {

	public static final String NAME = "Concern Mapper File";
	public static final String FILE_NAME = "components.cm";

	public ProjectFromConcernMapperBuilder(IProject eclipseProject) {
		super(eclipseProject);
	}

	@Override
	public Project parse() {
		if (!readComponentsFromFile())
			return defaultBuilder();

		//SpiritVisitor codeSmellsVisitor = new SpiritVisitor();
		//JSpiritAdapter connector = new JSpiritAdapter(codeSmellsVisitor);
		IJavaProject javaProject = JavaCore.create(getEclipseProject());
		Project project = new Project(javaProject/*, connector*/);
		Set<SourceType> sourceTypes = new HashSet<SourceType>();
		
		for (String componentName : ConcernMapper.getDefault().getConcernModel().getConcernNames()) {
			Component component = new Component(componentName);
			ComponentVisitor componentBuilderVisitor = new ComponentVisitor(component);
			project.addComponent(component);
			
			for (Object o : ConcernMapper.getDefault().getConcernModel().getElements(componentName)) {
				if (o instanceof SourceMethod) {
					SourceType parent = (SourceType) ((SourceMethod)o).getParent();
					
					if (sourceTypes.contains(parent))
						continue;
					sourceTypes.add(parent);
					
					ICompilationUnit iCU = parent.getCompilationUnit();
					CompilationUnit classCU = parseInterfaceToCompilationUnit(iCU);
					//SmellVisitor is responsible for finding code smells in the visited class
					//classCU.accept(codeSmellsVisitor);
					//ComponentBuilderVisitor is responsible for adding the class to its enclosing component
					classCU.accept(componentBuilderVisitor);
				}
			}
		}
		
		return project;
	}

	/**
	 * Read the components defined in the mapping file.
	 * Return true if the file was read successfully and false otherwise.
	 * @return True or False
	 */
	private boolean readComponentsFromFile() {
		final IFile  mappingFile = (IFile) eclipseProject.getProject().getFile(FILE_NAME); 

		if (!mappingFile.exists())
			return false;
		
		ConcernMapper cm =  new ConcernMapper();
		
		try
		{
			ConcernMapper.getDefault().getConcernModel().startStreaming();
		    ConcernMapper.getDefault().setDefaultResource( mappingFile );
		    ConcernMapper.getDefault().getConcernModel().reset();
		    ModelReader lReader = new ModelReader( ConcernMapper.getDefault().getConcernModel() );
		        
		    final int lSkipped = lReader.read( mappingFile, new IProgressMonitor() {
				@Override public void worked(int arg0) {}
				@Override public void setTotal(int arg0) {}
			});
		}
		catch( ModelIOException lException )
		{
			ConcernMapper.getDefault().getConcernModel().reset();
			return false;
		}
		finally
		{
			ConcernMapper.getDefault().getConcernModel().stopStreaming();
			ConcernMapper.getDefault().resetDirty();
		}
		
		return true;
	}
}
