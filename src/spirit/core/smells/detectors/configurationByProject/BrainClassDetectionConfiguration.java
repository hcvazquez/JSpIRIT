package spirit.core.smells.detectors.configurationByProject;

import spirit.metrics.constants.MetricThresholds;

public class BrainClassDetectionConfiguration extends
		CodeSmellDetectionConfiguration {
	private Long id;
	private Double LOC_GreaterEqual_VeryHigh;
	private Double LOC_GreaterEqual_2xVeryHigh;
	private Double WMC_GreaterEqual_2xVeryHigh;
	private Double WMC_GreaterEqual_VeryHigh;
	private Double TCC_Less_Half;
	
	public BrainClassDetectionConfiguration(String projectName) {
		super(projectName);
		LOC_GreaterEqual_VeryHigh=MetricThresholds.LOCClassVeryHigh;
		LOC_GreaterEqual_2xVeryHigh=2*MetricThresholds.LOCClassVeryHigh;
		WMC_GreaterEqual_2xVeryHigh=2*MetricThresholds.WMC_VERY_HIGH;
		WMC_GreaterEqual_VeryHigh=MetricThresholds.WMC_VERY_HIGH;
		TCC_Less_Half=MetricThresholds.HALF;
	}
	public BrainClassDetectionConfiguration() {
		super(null);
	}
	public Double getLOC_GreaterEqual_VeryHigh() {
		return LOC_GreaterEqual_VeryHigh;
	}
	public void setLOC_GreaterEqual_VeryHigh(Double lOC_GreaterEqual_VeryHigh) {
		LOC_GreaterEqual_VeryHigh = lOC_GreaterEqual_VeryHigh;
	}
	public Double getLOC_GreaterEqual_2xVeryHigh() {
		return LOC_GreaterEqual_2xVeryHigh;
	}
	public void setLOC_GreaterEqual_2xVeryHigh(Double lOC_GreaterEqual_2xVeryHigh) {
		LOC_GreaterEqual_2xVeryHigh = lOC_GreaterEqual_2xVeryHigh;
	}
	public Double getWMC_GreaterEqual_2xVeryHigh() {
		return WMC_GreaterEqual_2xVeryHigh;
	}
	public void setWMC_GreaterEqual_2xVeryHigh(Double wMC_GreaterEqual_2xVeryHigh) {
		WMC_GreaterEqual_2xVeryHigh = wMC_GreaterEqual_2xVeryHigh;
	}
	public Double getWMC_GreaterEqual_VeryHigh() {
		return WMC_GreaterEqual_VeryHigh;
	}
	public void setWMC_GreaterEqual_VeryHigh(Double wMC_GreaterEqual_VeryHigh) {
		WMC_GreaterEqual_VeryHigh = wMC_GreaterEqual_VeryHigh;
	}
	public Double getTCC_Less_Half() {
		return TCC_Less_Half;
	}
	public void setTCC_Less_Half(Double tCC_Less_Half) {
		TCC_Less_Half = tCC_Less_Half;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
