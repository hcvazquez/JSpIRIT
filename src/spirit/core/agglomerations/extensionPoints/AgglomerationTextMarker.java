package spirit.core.agglomerations.extensionPoints;

import java.util.Set;

import spirit.core.agglomerations.AgglomerationModel;

/**
 * For each type of {@link AgglomerationModel} there must be a specific TextMarker (that inherit from this class)
 * @author Willian 
 */
public abstract class AgglomerationTextMarker<T extends AgglomerationModel> {

	/**
	 * Method responsible for updating text markers for a list of agglomerations
	 * @param agglomerations
	 */
	public void updateTextMarkers(Set<T> agglomerations) {
		for (T agglomeration : agglomerations) {
			updateTextMarkers(agglomeration);
		}
	}
	
	/**
	 * Method responsible for updating the text markers for a specific agglomeration
	 * @param agglomeration
	 */
	protected abstract void updateTextMarkers(T agglomeration);
}
