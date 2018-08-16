package spirit.priotization.criteria;

import java.util.List;

import spirit.priotization.criteria.util.ModifiabilityScenario;

public interface CriteriaWithScenarios {
	public List<ModifiabilityScenario> getScenarios();
	public void updateScenarios();
}
