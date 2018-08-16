package spirit.dependencies;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class DependencyVisitor extends ASTVisitor {

	private Graph g = new Graph();
	private static DependencyVisitor instance;
	
	public static DependencyVisitor getInstance() {
        if (instance == null) { // first time lock
            synchronized (DependencyVisitor.class) {
                if (instance == null) {  // second time lock
                	instance = new DependencyVisitor();
                }
            }
        }
        return instance;
    }

	private DependencyVisitor() {
		super();
	}

	@Override
	public boolean visit(CompilationUnit cunit) {		
		List imps = cunit.imports();
		g.generateNodesFromImportList(cunit.getJavaElement().getElementName(), imps);
		return super.visit(cunit);
	}

	public Graph getGraph(String name) {
		if(g != null && g.getName().equalsIgnoreCase(name))
			return g;
		return new Graph();
	}

}
