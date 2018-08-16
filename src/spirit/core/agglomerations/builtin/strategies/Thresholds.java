package spirit.core.agglomerations.builtin.strategies;

import spirit.db.DataBaseManager;

/**
 * Class responsible for providing the thresholds for the built-in types of agglomeration.
 * The thresholds are read from the preferences of eclipse.
 * @author Willian
 *
 */
public class Thresholds {

	public static int getIntraMethod(String projectName) {
		return DataBaseManager.getInstance().getAgglomerationConfigurationForProject(projectName).getIntraMethodThreshold();
	}
	
	public static int getIntraComponent(String projectName) {
		return DataBaseManager.getInstance().getAgglomerationConfigurationForProject(projectName).getIntraComponentThreshold();
	}
	
	public static int getHierarchical(String projectName) {
		return DataBaseManager.getInstance().getAgglomerationConfigurationForProject(projectName).getHierarchicalThreshold();
	}

	public static int getIntraClass(String projectName) {
		//TODO get from project configuration
		return DataBaseManager.getInstance().getAgglomerationConfigurationForProject(projectName).getIntraClassThreshold();
	}
}
