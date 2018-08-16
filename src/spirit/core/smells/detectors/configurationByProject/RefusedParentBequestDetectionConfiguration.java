package spirit.core.smells.detectors.configurationByProject;

import spirit.metrics.constants.MetricThresholds;

public class RefusedParentBequestDetectionConfiguration extends
		CodeSmellDetectionConfiguration {
	private Long id;
	private Double NProtM_Greater_Few;
	private Double BUR_Less_OneThird;
	private Double BOvR_Less_OneThird;
	private Double AMW_Greater_AMWAvg;
	private Double WMC_Greater_WMCAvg;
	private Double NOM_Greater_NOMAvg;
	public RefusedParentBequestDetectionConfiguration(String projectName) {
		super(projectName);
		NProtM_Greater_Few=MetricThresholds.FEW;
		BUR_Less_OneThird=MetricThresholds.ONE_THIRD;
		BOvR_Less_OneThird=MetricThresholds.ONE_THIRD;
		AMW_Greater_AMWAvg=MetricThresholds.AMWAvg;
		WMC_Greater_WMCAvg=MetricThresholds.WMCAvg;
		NOM_Greater_NOMAvg=MetricThresholds.NOMAvg;
	}
	public RefusedParentBequestDetectionConfiguration() {
		super(null);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getNProtM_Greater_Few() {
		return NProtM_Greater_Few;
	}
	public void setNProtM_Greater_Few(Double nProtM_Greater_Few) {
		NProtM_Greater_Few = nProtM_Greater_Few;
	}
	public Double getBUR_Less_OneThird() {
		return BUR_Less_OneThird;
	}
	public void setBUR_Less_OneThird(Double bUR_Less_OneThird) {
		BUR_Less_OneThird = bUR_Less_OneThird;
	}
	public Double getBOvR_Less_OneThird() {
		return BOvR_Less_OneThird;
	}
	public void setBOvR_Less_OneThird(Double bOvR_Less_OneThird) {
		BOvR_Less_OneThird = bOvR_Less_OneThird;
	}
	public Double getAMW_Greater_AMWAvg() {
		return AMW_Greater_AMWAvg;
	}
	public void setAMW_Greater_AMWAvg(Double aMW_Greater_AMWAvg) {
		AMW_Greater_AMWAvg = aMW_Greater_AMWAvg;
	}
	public Double getWMC_Greater_WMCAvg() {
		return WMC_Greater_WMCAvg;
	}
	public void setWMC_Greater_WMCAvg(Double wMC_Greater_WMCAvg) {
		WMC_Greater_WMCAvg = wMC_Greater_WMCAvg;
	}
	public Double getNOM_Greater_NOMAvg() {
		return NOM_Greater_NOMAvg;
	}
	public void setNOM_Greater_NOMAvg(Double nOM_Greater_NOMAvg) {
		NOM_Greater_NOMAvg = nOM_Greater_NOMAvg;
	}
	
}
