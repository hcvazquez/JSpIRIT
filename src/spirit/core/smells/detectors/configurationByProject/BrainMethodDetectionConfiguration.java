package spirit.core.smells.detectors.configurationByProject;

import spirit.metrics.constants.MetricThresholds;

public class BrainMethodDetectionConfiguration extends CodeSmellDetectionConfiguration{
	private Long id;
	private Double LOC_Greater_VeryHigh;
	private Double WMC_GreaterEqual_Many;
	private Double MAXNESTING_GreaterEqual_DEEP;
	private Double NOF_GreaterEqual_SMemCap;
	public BrainMethodDetectionConfiguration(String projectName) {
		super(projectName);
		LOC_Greater_VeryHigh=MetricThresholds.LOCVeryHigh;
		WMC_GreaterEqual_Many=MetricThresholds.MANY;
		MAXNESTING_GreaterEqual_DEEP=MetricThresholds.DEEP;
		NOF_GreaterEqual_SMemCap=MetricThresholds.SMemCap;
	}
	public BrainMethodDetectionConfiguration() {
		super(null);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getLOC_Greater_VeryHigh() {
		return LOC_Greater_VeryHigh;
	}
	public void setLOC_Greater_VeryHigh(Double lOC_Greater_VeryHigh) {
		LOC_Greater_VeryHigh = lOC_Greater_VeryHigh;
	}
	public Double getWMC_GreaterEqual_Many() {
		return WMC_GreaterEqual_Many;
	}
	public void setWMC_GreaterEqual_Many(Double wMC_GreaterEqual_Many) {
		WMC_GreaterEqual_Many = wMC_GreaterEqual_Many;
	}
	public Double getMAXNESTING_GreaterEqual_DEEP() {
		return MAXNESTING_GreaterEqual_DEEP;
	}
	public void setMAXNESTING_GreaterEqual_DEEP(Double mAXNESTING_GreaterEqual_DEEP) {
		MAXNESTING_GreaterEqual_DEEP = mAXNESTING_GreaterEqual_DEEP;
	}
	public Double getNOF_GreaterEqual_SMemCap() {
		return NOF_GreaterEqual_SMemCap;
	}
	public void setNOF_GreaterEqual_SMemCap(Double nOF_GreaterEqual_SMemCap) {
		NOF_GreaterEqual_SMemCap = nOF_GreaterEqual_SMemCap;
	}

}
