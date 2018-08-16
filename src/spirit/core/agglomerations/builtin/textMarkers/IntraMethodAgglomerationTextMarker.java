package spirit.core.agglomerations.builtin.textMarkers;

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;

import spirit.core.agglomerations.IntraMethodAgglomeration;
import spirit.core.agglomerations.extensionPoints.AgglomerationTextMarker;
import spirit.core.smells.CodeSmell;
import spirit.ui.views.agglomerations.TextMarkerUtil;


/**
 * Class responsible for updating text markers for intra-method agglomerations.
 * @author Willian
 *
 */
public class IntraMethodAgglomerationTextMarker extends AgglomerationTextMarker<IntraMethodAgglomeration> {
	
		
	public void updateTextMarkers(IntraMethodAgglomeration agglomeration) {
		List<CodeSmell> codeAnomalies = agglomeration.getCodeAnomaliesSortedByName();
		StringBuilder message = new StringBuilder("Intra-method agglomeration with ");
		for (CodeSmell codeSmell : codeAnomalies) {
			message.append(" - " + codeSmell.getKindOfSmellName());
		}
		
		IJavaElement javaElement = agglomeration.getAffectedMethod().resolveBinding().getJavaElement();
		ICompilationUnit cu = (ICompilationUnit)javaElement.getAncestor(IJavaElement.COMPILATION_UNIT);
		int line = TextMarkerUtil.getLineNumFromOffset(cu, agglomeration.getAffectedMethod().getStartPosition());
		try {
			TextMarkerUtil.updateMarker(cu.getCorrespondingResource(), message.toString(), line);
		} catch (JavaModelException e) {
			System.err.println("Exception while updating text marker: " + e);
		}
	}
}
