package spirit.core.agglomerations.builtin.textMarkers;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.core.agglomerations.HierarchicalAgglomeration;
import spirit.core.agglomerations.extensionPoints.AgglomerationTextMarker;
import spirit.core.smells.CodeSmell;
import spirit.ui.views.agglomerations.TextMarkerUtil;


/**
 * Class responsible for updating text markers of hierarchical agglomerations
 * @author Willian
 *
 */
public class HierarchicalAgglomerationTextMarker extends AgglomerationTextMarker<HierarchicalAgglomeration> {

	@Override
	protected void updateTextMarkers(HierarchicalAgglomeration agglomeration) {
		TypeDeclaration agglomerationRoot = agglomeration.getRoot();
		try {
			IJavaElement element = ((CompilationUnit)agglomerationRoot.getRoot()).getJavaElement();
			IResource rootResource = element.getCorrespondingResource();
			int line = TextMarkerUtil.getLineNumFromOffset((ICompilationUnit) element, agglomerationRoot.getStartPosition());
			TextMarkerUtil.updateMarker(rootResource, "Root of a Hierarchical agglomeration: " + agglomeration.toString(), line);
			
		} catch (JavaModelException e1) {
			System.err.println("Exception while updating text marker: " + e1);
		}
		
		for (CodeSmell codeSmell : agglomeration.getCodeAnomaliesSortedByName()) {
			int line = codeSmell.getLine();
			try {
				IResource resource = ((CompilationUnit)codeSmell.getElement().getRoot()).getJavaElement().getCorrespondingResource();
				TextMarkerUtil.updateMarker(resource, "Hierarchical agglomeration: " + codeSmell.toString(), line);
			} catch (JavaModelException e) {
				System.err.println("Exception while updating text marker: " + e);
			}
		}
	}

}
