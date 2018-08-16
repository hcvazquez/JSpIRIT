package spirit.core.smells.detectors.configurationByProject;

public class CodeSmellDetectionConfiguration {
	private String projectName;
	public CodeSmellDetectionConfiguration(String projectName) {
		this.projectName=projectName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
