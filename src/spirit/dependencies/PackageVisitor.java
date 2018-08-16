package spirit.dependencies;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class PackageVisitor extends ASTVisitor {
	
	private PackageGraph packages = new PackageGraph();
	private static PackageVisitor instance;
	
	public static PackageVisitor getInstance() {
        if (instance == null) { // first time lock
            synchronized (PackageVisitor.class) {
                if (instance == null) {  // second time lock
                	instance = new PackageVisitor();
                }
            }
        }
        return instance;
    }

	private PackageVisitor() {
		super();
	}

	@Override
	public boolean visit(CompilationUnit cunit) {
		List imps = cunit.imports();
		packages.generatePackageRelationships(cunit.getPackage(), imps);
		return super.visit(cunit);
	}
	
	public PackageGraph getPackageGraph() {
		return packages;
	}

}
