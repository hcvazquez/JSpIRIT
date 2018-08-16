package spirit.core.agglomerations;

import spirit.core.design.DesignFlaw;
import spirit.core.smells.CodeSmell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AgglomerationModel extends DesignFlaw {
private final Set<CodeSmell> codeAnomalies = new HashSet<CodeSmell>();
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getUniqueID() == null) ? 0 : getUniqueID().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgglomerationModel other = (AgglomerationModel) obj;
		if (getUniqueID() == null) {
			if (other.getUniqueID() != null)
				return false;
		} else if (!this.getUniqueID().equals(other.getUniqueID()))
			return false;
		return true;
	}

	/**
	 * Returns the agglomeration topology name		
	 * @return
	 */
	public abstract String getTopologyName();
	
	/**
	 * Returns the unique ID of this agglomeration
	 * @return
	 */
	public abstract String getUniqueID();

	/**
	 * Every agglomeration must have a list of code anomalies
	 * @param codeAnomalies
	 */
	public AgglomerationModel(Collection<CodeSmell> codeAnomalies) {
		this.getCodeAnomalies().addAll(codeAnomalies);
	}

	public Set<CodeSmell> getCodeAnomalies() {
		return codeAnomalies;
	}
	
	/**
	 * Returns the list of code anomalies in the agglomeration sorted by name (KindOfSmellName in JSpirit)
	 * @return List
	 */
	public List<CodeSmell> getCodeAnomaliesSortedByName() {
		List<CodeSmell> sortedCodeAnomalies = new ArrayList<CodeSmell>(codeAnomalies);
		
		Collections.sort(sortedCodeAnomalies, new Comparator<CodeSmell>() {
			@Override public int compare(CodeSmell o1, CodeSmell o2) {
				return o1.getKindOfSmellName().compareTo(o2.getKindOfSmellName());
			}});
		
		return sortedCodeAnomalies;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}	
}
