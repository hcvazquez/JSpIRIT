package spirit.ui.views.agglomerations;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

/**
 * Class that modularize functions related to text markers.
 * @author Willian
 *
 */
public class TextMarkerUtil {
	
	private static final String AGGLOMERATION = "br.pucrio.inf.organic.agglomerationMarker";

	/**
	 * Delete all text markers in a given resource.
	 * @param resource
	 */
	public static void deleteMarkers(IResource resource) {
		   try {
			IMarker[] markers = resource.findMarkers(AGGLOMERATION, true, IResource.DEPTH_INFINITE);
			for (int i = 0; i < markers.length; i++) {
				markers[i].delete();
			}
		} catch (CoreException e) {
			System.err.println("Exception while deleting markers: " + e);
		}
	}

	/**
	 * Create a text marker in a given resource with a given message
	 * @param resource
	 * @param message
	 * @param line
	 */
	public static void updateMarker(IResource resource, String message, int line) {
		try {
			IMarker marker = resource.createMarker(AGGLOMERATION);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.LINE_NUMBER, line);
			marker.setAttribute(IMarker.TRANSIENT, false);
		} catch (CoreException e) {
			System.err.println("Exception while updating text marker: " + e);
		}
	}
	
	/**
	 * Returns the line number in the file, based on the offset in the compilation unit
	 * @param cUnit
	 * @param offSet
	 * @return
	 */
	public static int getLineNumFromOffset(ICompilationUnit cUnit, int offSet) {
		try {
			String source = cUnit.getSource();
			IType type = cUnit.findPrimaryType();
			if (type != null) {
				String sourcetodeclaration = source.substring(0, offSet);
				int lines = 0;
				char[] chars = new char[sourcetodeclaration.length()];
				sourcetodeclaration.getChars(0, sourcetodeclaration.length(),
						chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] == '\n') {
						lines++;
					}
				}
				return lines + 1;
			}
		} catch (JavaModelException jme) {
			System.out.println("Exception while getting line number: " + jme);
		}
		return 0;
	}	
}
