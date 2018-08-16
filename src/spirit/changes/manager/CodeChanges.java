package spirit.changes.manager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import spirit.core.design.CodeSmellsManagerFactory;
import spirit.dependencies.DependencyVisitor;
public class CodeChanges {
	
	private Set<Change> changes;
	private boolean needReScan;
	private static CodeChanges instance;
	
	public static CodeChanges getInstance() {
        if (instance == null) { // first time lock
            synchronized (CodeChanges.class) {
                if (instance == null) {  // second time lock
                	instance = new CodeChanges();
                }
            }
        }
        return instance;
    }
	
	private CodeChanges() {
		changes = new HashSet<Change>();
		setNeedReScan(false);
	}
	
	
	public void addChange(Change c) {		
		if (c.getType() == Change.Type.ADDED) {
			DependencyVisitor.getInstance().getGraph(CodeSmellsManagerFactory.getInstance().getCurrentProject().getName()).addVertex(c.getClassChanged());
			setNeedReScan(true);
		}
		else if (c.getType() == Change.Type.REMOVED) {
			DependencyVisitor.getInstance().getGraph(CodeSmellsManagerFactory.getInstance().getCurrentProject().getName()).removeVertex(c.getClassChanged());
			setNeedReScan(true);
		}
		else if(!changes.contains(c)) {
			changes.add(c);
			DependencyVisitor.getInstance().getGraph(CodeSmellsManagerFactory.getInstance().getCurrentProject().getName()).addVertex(c.getClassChanged());
		}
	}
	
	public Set<Change> getChanges() {
		return changes;
	}
	public void setChanges(Set<Change> changes) {
		 this.changes = changes;
	}
	
	public Iterator<Change> getIterator() {
		return changes.iterator();
	}
	
	public boolean anyChanges() {
		return (changes.size() > 0);
	}
	
	public void clear() {
		changes.clear();
	}

	public boolean isNeedReScan() {
		return needReScan;
	}

	public void setNeedReScan(boolean needReScan) {
		this.needReScan = needReScan;
	}

}
