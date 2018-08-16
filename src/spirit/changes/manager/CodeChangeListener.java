package spirit.changes.manager;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

public class CodeChangeListener {

	private IResourceChangeListener listener;
	private IResourceDeltaVisitor deltaVisitor; 

	public CodeChangeListener() {
		
		deltaVisitor = new IResourceDeltaVisitor() {
			public boolean visit(IResourceDelta delta) {
				IResource res = delta.getResource();
				if (res.getName().endsWith(".java")) {
					String clazz;
					CodeChanges changes = CodeChanges.getInstance();
					switch (delta.getKind()) {					
					case IResourceDelta.ADDED:
						clazz = res.getName().split("\\.")[0];
						changes.addChange(new Change(clazz,IResourceDelta.ADDED));
						System.out.print("Resource ");
						System.out.print(clazz);
						System.out.println(" was added.");
						int addFlags = delta.getFlags();
						System.out.println(addFlags);
						break;
					case IResourceDelta.REMOVED:
						
						clazz = res.getName().split("\\.")[0];
						changes.addChange(new Change(clazz,IResourceDelta.REMOVED));
						System.out.print("Resource ");
						System.out.print(clazz);
						System.out.println(" was removed.");
						int flags = delta.getFlags();
						System.out.println(flags);
						break;
					case IResourceDelta.CHANGED:
						if (delta.getFlags() != IResourceDelta.MARKERS) { 
							clazz = res.getName().split("\\.")[0];
							changes.addChange(new Change(clazz,IResourceDelta.CHANGED));
							System.out.print("Resource ");
							System.out.print("NAME: " + clazz);
							System.out.println(" has changed.");
							int changedflags = delta.getFlags();
							System.out.println(changedflags);
							
						}
						break;
					}
				}
				return true; // visit the children
			}
		};
		

		listener = new IResourceChangeListener() {
			public void resourceChanged(IResourceChangeEvent event) {
				try {
					event.getDelta().accept(deltaVisitor);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

	}

	public IResourceChangeListener getListener() {
		return listener;
	}

}
