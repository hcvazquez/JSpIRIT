package spirit.dependencies;

import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import spirit.core.design.CodeSmellsManagerFactory;

public class PackageGraph {
	
	private DefaultDirectedGraph<String, DefaultEdge> g;

	public PackageGraph() {
		g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
	}

	public void addVertex(String v) {
		g.addVertex(v);
	}

	public void addEdge(String v1, String v2) {
		g.addEdge(v1, v2);
	}
	
	public void removeVertex(String v) {
		g.removeVertex(v);
	}
	
	public Set<String> vertexes() {
		return g.vertexSet();
	}

	public void generatePackageRelationships(PackageDeclaration packag, List<ImportDeclaration> imports) {
		String source = packag.getName().getFullyQualifiedName();
		if (!g.containsVertex(source))
			g.addVertex(source);
		for (ImportDeclaration i : imports) {			
			String fullImport = i.getName().getFullyQualifiedName();
			if (fullImport.contains(CodeSmellsManagerFactory.getInstance().getCurrentProject().getName().toLowerCase())) {				
				String imp = fullImport.substring(0, fullImport.lastIndexOf("."));				
				if (!g.containsVertex(imp))
					g.addVertex(imp);
				g.addEdge(source, imp);
				//System.out.println(source + " ---> " + imp);
			}
		}
	}
	
	public Set<DefaultEdge> getEdges() {
		return g.edgeSet();		
	}
	
	public void clean() {
		g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
	}

}
