package spirit.priotization.criteria;

import java.util.List;

import spirit.priotization.criteria.util.ArchitecturalConcern;

public interface CriteriaWithConcerns {
	public void updateProblems() ;
	public List<ArchitecturalConcern> getArchitecturalProblems();
}
