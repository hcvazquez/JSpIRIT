package spirit.priotization.criteria;

import java.util.List;

import spirit.priotization.criteria.util.NOMHistoryOfAVersion;
import spirit.priotization.criteria.util.NOMHistoryProject;

public interface CriteriaWithNOMHistory {
	public List<NOMHistoryOfAVersion> getNomHistory();
	public void loadNOMHistory(List<NOMHistoryOfAVersion> nomHistory);
	public NOMHistoryProject getNomHistoryProject();
}
