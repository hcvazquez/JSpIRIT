package spirit.core.smells.detectors.configurationByProject;

import spirit.metrics.constants.MetricThresholds;

public class FeatureEnvyDetectionConfiguration extends
		CodeSmellDetectionConfiguration {
	private Long id;
	private Double ATFD_Greater_Few;
	private Double LAA_Less_OneThird;
	private Double FDP_LessEqual_FEW;
	public FeatureEnvyDetectionConfiguration(String projectName) {
		super(projectName);
		ATFD_Greater_Few=MetricThresholds.FEW;
		LAA_Less_OneThird=MetricThresholds.ONE_THIRD;
		FDP_LessEqual_FEW=MetricThresholds.FEW;
	}
	public FeatureEnvyDetectionConfiguration() {
		super(null);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getATFD_Greater_Few() {
		return ATFD_Greater_Few;
	}
	public void setATFD_Greater_Few(Double aTFD_Greater_Few) {
		ATFD_Greater_Few = aTFD_Greater_Few;
	}
	public Double getLAA_Less_OneThird() {
		return LAA_Less_OneThird;
	}
	public void setLAA_Less_OneThird(Double lAA_Less_OneThird) {
		LAA_Less_OneThird = lAA_Less_OneThird;
	}
	public Double getFDP_LessEqual_FEW() {
		return FDP_LessEqual_FEW;
	}
	public void setFDP_LessEqual_FEW(Double fDP_LessEqual_FEW) {
		FDP_LessEqual_FEW = fDP_LessEqual_FEW;
	}
	
}
