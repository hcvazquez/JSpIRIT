package spirit.core.smells.detectors.configurationByProject;

import spirit.metrics.constants.MetricThresholds;

public class ShotgunSurgeryDetectionConfiguration extends
		CodeSmellDetectionConfiguration {
	private Long id;
	private Double CC_GreaterEqual_MANY;
	private Double CM_Greater_SMemCap;
	public ShotgunSurgeryDetectionConfiguration(String projectName) {
		super(projectName);
		CC_GreaterEqual_MANY=MetricThresholds.MANY;
		CM_Greater_SMemCap=MetricThresholds.SMemCap;
	}
	public ShotgunSurgeryDetectionConfiguration() {
		super(null);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getCC_GreaterEqual_MANY() {
		return CC_GreaterEqual_MANY;
	}
	public void setCC_GreaterEqual_MANY(Double cC_GreaterEqual_MANY) {
		CC_GreaterEqual_MANY = cC_GreaterEqual_MANY;
	}
	public Double getCM_Greater_SMemCap() {
		return CM_Greater_SMemCap;
	}
	public void setCM_Greater_SMemCap(Double cM_Greater_SMemCap) {
		CM_Greater_SMemCap = cM_Greater_SMemCap;
	}
	
}
