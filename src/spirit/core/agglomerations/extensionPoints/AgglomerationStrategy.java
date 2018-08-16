package spirit.core.agglomerations.extensionPoints;

import java.util.Set;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.core.agglomerations.model.Project;



/**
 * Abstract class that must be extended by all agglomeration strategies.
 * 
 * @author Willian
 *  
 */
public abstract class AgglomerationStrategy<T extends AgglomerationModel>  {

	/**
	 * Abstract method responsible for find and return a set with all agglomeration using a given strategy.
	 * 
	 * @return set of agglomerations
	 */
	public Set<T> findAndMarkAgglomerations(Project project) {
		Set<T> agglomerations = findAgglomerations(project);
		
		updateTextMarkers(agglomerations);
		
		return agglomerations;
	}
	
	protected abstract void updateTextMarkers(Set<T> agglomerations);

	protected abstract Set<T> findAgglomerations(Project project);

	public abstract String name();
	
}
