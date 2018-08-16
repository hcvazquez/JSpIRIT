package spirit.dependencies;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import spirit.changes.manager.Change;
import spirit.changes.manager.CodeChanges;
import spirit.core.design.CodeSmellsManagerFactory;

public class Graph {	
	
	private DefaultDirectedGraph<String, DefaultEdge> g;
	private String name;

	public Graph() {
		g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		name = CodeSmellsManagerFactory.getInstance().getCurrentProject().getName();
	}
	
	public String getName() {
		return name;
	}
	
	public int countVertexes() {
		return g.vertexSet().size();
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

	public void generateNodesFromImportList(String source, List<ImportDeclaration> imports) {
		source = source.split("\\.")[0];
		for (ImportDeclaration i : imports) {			
			String fullImport = i.getName().getFullyQualifiedName();
			if (fullImport.contains(name.toLowerCase())) {				
				String imp = fullImport.substring(fullImport.lastIndexOf(".") + 1);
				if (!g.containsVertex(source))
					g.addVertex(source);
				if (!g.containsVertex(imp))
					g.addVertex(imp);
				g.addEdge(source, imp);
				//System.out.println(imp + " ---> " + source);
			}
		}
	}
	
	public Set<String> getTouchedClasses(CodeChanges modResources) {

		Set<String> classes = new HashSet<String>();
		Iterator<Change> it = modResources.getIterator();
		while (it.hasNext()) {
			Change ch = it.next();
			if (ch.getType().equals(Change.Type.CHANGED)) {
				if(!classes.contains(ch.getClassChanged())) {
					GraphIterator<String, DefaultEdge> iterator = new BreadthFirstIterator<String, DefaultEdge>(g,ch.getClassChanged());
					while (iterator.hasNext()) {
						String actual = iterator.next();
						if(!classes.contains(actual))
							classes.add(actual);
					}
				}
			}	
		}			
		return classes;
	}

}
