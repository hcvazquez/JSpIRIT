package spirit.ui.views.data.agglomerations;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

import spirit.core.design.CodeSmellsManagerFactory;

public class UIEventMethods {
	
//	public static void openClassInEditor(String packageName, String className, String kind) {
//		try {
//			String path = null;
//			for(IResource r : CodeSmellsManagerFactory.getInstance().getCurrentProject().members())				
//				if(r.getFullPath().toString().contains("src"))
//					path = r.getFullPath().toString();
//			if(path.startsWith("/"))
//				path = path.substring(1);
//			path = path + "/" + normalize(packageName) + "/" + className + ".java";
//			
//			IPath fullPath=new Path(path);
//			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
//			IMarker[] markers = ResourcesPlugin.getWorkspace().getRoot().getFile(fullPath).findMarkers(IMarker.PROBLEM, true,
//					IResource.DEPTH_INFINITE);
//			IMarker marker = null;
//			for(IMarker m : markers) {
//				String msg = (String) m.getAttribute(IMarker.MESSAGE);
//				if(msg.contains(kind))
//					marker = m;
//			}
//			IDE.openEditor(page, marker);
//		} catch (Exception e) {		
//			e.printStackTrace();
//		}
//	}
	
	public static void openClassInEditor(String packageName, String className, String methodName) {
		try {
			String path = null;
			for(IResource r : CodeSmellsManagerFactory.getInstance().getCurrentProject().members())				
				if(r.getFullPath().toString().contains("src"))
					path = r.getFullPath().toString();
			if(path.startsWith("/"))
				path = path.substring(1);
			path = path + "/" + normalize(packageName) + "/" + className + ".java";
			
			IPath fullPath=new Path(path);
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			ICompilationUnit je = getCompilationUnit(ResourcesPlugin.getWorkspace().getRoot().getFile(fullPath));
			IMethod method = null;
			IType classDeclaration = null;
			
			for(IType type : je.getAllTypes()) {
				if(type.getElementName().contentEquals(className))
					classDeclaration = type;
				for(IMethod m : type.getMethods())
					if(m.getElementName().contentEquals(methodName))
						method = m;
			}
							
			ITextEditor editor = (ITextEditor) IDE.openEditor(page, ResourcesPlugin.getWorkspace().getRoot().getFile(fullPath));
			if(method == null)
				editor.selectAndReveal(classDeclaration.getSourceRange().getOffset(), 0);
			else
				editor.selectAndReveal(method.getSourceRange().getOffset(), 0);
			
		} catch (Exception e) {		
			e.printStackTrace();
		}
	}

	private static String normalize(String packageName) {
		return packageName.replace(".", "/");
	}
	
	private static ICompilationUnit getCompilationUnit(final IResource resource) {
        IJavaElement element = JavaCore.create(resource);
        if (element instanceof ICompilationUnit) {
            return (ICompilationUnit) element;
        }
        return null;
    }

}
