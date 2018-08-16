package spirit.ui.views.agglomerations;

import java.util.Set;

import spirit.core.agglomerations.AgglomerationModel;


/**
 * Data model that aggregates agglomerations of a given type.
 * This model is exclusive for the AgglomerationsView.
 * @author Willian
 *
 */
public class AgglomerationTypeResults {

	private final String name;
	private final Set<? extends AgglomerationModel> agglomerations;
	
	public AgglomerationTypeResults(String name, Set<? extends AgglomerationModel> agglomerations) {
		this.name = name;
		this.agglomerations = agglomerations;
	}

	public String getName() {
		return name;
	}

	public Set<? extends AgglomerationModel> getAgglomerations() {
		return agglomerations;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
