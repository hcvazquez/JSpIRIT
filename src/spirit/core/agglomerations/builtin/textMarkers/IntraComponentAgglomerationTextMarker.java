package spirit.core.agglomerations.builtin.textMarkers;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;

import spirit.core.agglomerations.IntraComponentAgglomeration;
import spirit.core.agglomerations.extensionPoints.AgglomerationTextMarker;
import spirit.core.smells.CodeSmell;
import spirit.ui.views.agglomerations.TextMarkerUtil;


/**
 * Class responsible for updating text markers of intra-component agglomerations
 * @author Willian
 *
 */
public class IntraComponentAgglomerationTextMarker extends AgglomerationTextMarker<IntraComponentAgglomeration> {

	public void updateTextMarkers(IntraComponentAgglomeration agglomeration) {

		for (CodeSmell codeSmell : agglomeration.getCodeAnomaliesSortedByName()) {
			int line = codeSmell.getLine();
			try {
				IResource resource = ((CompilationUnit)codeSmell.getElement().getRoot()).getJavaElement().getCorrespondingResource();
				TextMarkerUtil.updateMarker(resource, "Intra-component agglomeration: " + agglomeration.toString(), line);
			} catch (JavaModelException e) {
				System.err.println("Exception while updating text marker: " + e);
			}
		}
	}
}
