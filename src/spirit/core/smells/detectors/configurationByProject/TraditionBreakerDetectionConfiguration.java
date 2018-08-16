package spirit.core.smells.detectors.configurationByProject;

import spirit.metrics.constants.MetricThresholds;

public class TraditionBreakerDetectionConfiguration extends
		CodeSmellDetectionConfiguration {
	
	private Long id;
	private Double NAS_GreaterEqual_NOMAvg;
	private Double PNAS_GreaterEqual_TWO_THIRD;
	private Double AMW_Greater_AMWAvg;
	private Double WMC_GreaterEqual_VeryHigh;
	private Double NOM_GreatherEqual_High;
	private Double AMW_Greater_AMWAvg_2;
	private Double WMC_Greater_VeryHighDiv2;
	private Double NOM_GreatherEqual_HighDiv2;
	
	public TraditionBreakerDetectionConfiguration(String projectName) {
		super(projectName);
		NAS_GreaterEqual_NOMAvg=MetricThresholds.NOMAvg;
		PNAS_GreaterEqual_TWO_THIRD=MetricThresholds.TWO_THIRD;
		AMW_Greater_AMWAvg=MetricThresholds.AMWAvg;
		WMC_GreaterEqual_VeryHigh=MetricThresholds.WMC_VERY_HIGH;
		NOM_GreatherEqual_High=MetricThresholds.NOMHigh;
		AMW_Greater_AMWAvg_2=MetricThresholds.AMWAvg;
		WMC_Greater_VeryHighDiv2=MetricThresholds.WMC_VERY_HIGH/2;
		NOM_GreatherEqual_HighDiv2=MetricThresholds.NOMHigh/2;
	}
	public TraditionBreakerDetectionConfiguration() {
		super(null);
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getNAS_GreaterEqual_NOMAvg() {
		return NAS_GreaterEqual_NOMAvg;
	}

	public void setNAS_GreaterEqual_NOMAvg(Double nAS_GreaterEqual_NOMAvg) {
		NAS_GreaterEqual_NOMAvg = nAS_GreaterEqual_NOMAvg;
	}

	public Double getPNAS_GreaterEqual_TWO_THIRD() {
		return PNAS_GreaterEqual_TWO_THIRD;
	}

	public void setPNAS_GreaterEqual_TWO_THIRD(Double pNAS_GreaterEqual_TWO_THIRD) {
		PNAS_GreaterEqual_TWO_THIRD = pNAS_GreaterEqual_TWO_THIRD;
	}

	public Double getAMW_Greater_AMWAvg() {
		return AMW_Greater_AMWAvg;
	}

	public void setAMW_Greater_AMWAvg(Double aMW_Greater_AMWAvg) {
		AMW_Greater_AMWAvg = aMW_Greater_AMWAvg;
	}

	public Double getWMC_GreaterEqual_VeryHigh() {
		return WMC_GreaterEqual_VeryHigh;
	}

	public void setWMC_GreaterEqual_VeryHigh(Double wMC_GreaterEqual_VeryHigh) {
		WMC_GreaterEqual_VeryHigh = wMC_GreaterEqual_VeryHigh;
	}

	public Double getNOM_GreatherEqual_High() {
		return NOM_GreatherEqual_High;
	}

	public void setNOM_GreatherEqual_High(Double nOM_GreatherEqual_High) {
		NOM_GreatherEqual_High = nOM_GreatherEqual_High;
	}

	public Double getAMW_Greater_AMWAvg_2() {
		return AMW_Greater_AMWAvg_2;
	}

	public void setAMW_Greater_AMWAvg_2(Double aMW_Greater_AMWAvg_2) {
		AMW_Greater_AMWAvg_2 = aMW_Greater_AMWAvg_2;
	}

	public Double getWMC_Greater_VeryHighDiv2() {
		return WMC_Greater_VeryHighDiv2;
	}

	public void setWMC_Greater_VeryHighDiv2(Double wMC_Greater_VeryHighDiv2) {
		WMC_Greater_VeryHighDiv2 = wMC_Greater_VeryHighDiv2;
	}

	public Double getNOM_GreatherEqual_HighDiv2() {
		return NOM_GreatherEqual_HighDiv2;
	}

	public void setNOM_GreatherEqual_HighDiv2(Double nOM_GreatherEqual_HighDiv2) {
		NOM_GreatherEqual_HighDiv2 = nOM_GreatherEqual_HighDiv2;
	}
	
}
