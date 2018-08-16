package spirit.core.smells.detectors.configurationByProject;

import spirit.metrics.constants.MetricThresholds;

public class GodClassDetectionConfiguration extends
		CodeSmellDetectionConfiguration {
	private Long id;
	private Double ATFD_Greater_FEW;
	private Double TCC_Less_OneThird;
	private Double WMC_GreaterEqual_VeryHigh;
	public GodClassDetectionConfiguration(String projectName) {
		super(projectName);
		ATFD_Greater_FEW=MetricThresholds.FEW;
		TCC_Less_OneThird=MetricThresholds.ONE_THIRD;
		WMC_GreaterEqual_VeryHigh=MetricThresholds.WMC_VERY_HIGH;
	}
	public GodClassDetectionConfiguration() {
		super(null);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getATFD_Greater_FEW() {
		return ATFD_Greater_FEW;
	}
	public void setATFD_Greater_FEW(Double aTFD_Greater_FEW) {
		ATFD_Greater_FEW = aTFD_Greater_FEW;
	}
	public Double getTCC_Less_OneThird() {
		return TCC_Less_OneThird;
	}
	public void setTCC_Less_OneThird(Double tCC_Less_OneThird) {
		TCC_Less_OneThird = tCC_Less_OneThird;
	}
	public Double getWMC_GreaterEqual_VeryHigh() {
		return WMC_GreaterEqual_VeryHigh;
	}
	public void setWMC_GreaterEqual_VeryHigh(Double wMC_GreaterEqual_VeryHigh) {
		WMC_GreaterEqual_VeryHigh = wMC_GreaterEqual_VeryHigh;
	}
	
}
