package spirit.core.smells.detectors.configurationByProject;

import spirit.metrics.constants.MetricThresholds;

public class DataClassDetectionConfiguration extends
		CodeSmellDetectionConfiguration {
	private Long id;
	private Double WOC_Less_OneThird;
	private Double NOAP_SOAP_Greater_Few;
	private Double WMC_Less_High;
	private Double NOAP_SOAP_Greater_Many;
	private Double WMC_Less_VeryHigh;
	public DataClassDetectionConfiguration(String projectName) {
		super(projectName);
		WOC_Less_OneThird=MetricThresholds.ONE_THIRD;
		NOAP_SOAP_Greater_Few=MetricThresholds.FEW;
		WMC_Less_High=MetricThresholds.WMC_HIGH;
		NOAP_SOAP_Greater_Many=MetricThresholds.MANY;
		WMC_Less_VeryHigh=MetricThresholds.WMC_VERY_HIGH;
	}
	public DataClassDetectionConfiguration() {
		super(null);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getWOC_Less_OneThird() {
		return WOC_Less_OneThird;
	}
	public void setWOC_Less_OneThird(Double wOC_Less_OneThird) {
		WOC_Less_OneThird = wOC_Less_OneThird;
	}
	public Double getNOAP_SOAP_Greater_Few() {
		return NOAP_SOAP_Greater_Few;
	}
	public void setNOAP_SOAP_Greater_Few(Double nOAP_SOAP_Greater_Few) {
		NOAP_SOAP_Greater_Few = nOAP_SOAP_Greater_Few;
	}
	public Double getWMC_Less_High() {
		return WMC_Less_High;
	}
	public void setWMC_Less_High(Double wMC_Less_High) {
		WMC_Less_High = wMC_Less_High;
	}
	public Double getNOAP_SOAP_Greater_Many() {
		return NOAP_SOAP_Greater_Many;
	}
	public void setNOAP_SOAP_Greater_Many(Double nOAP_SOAP_Greater_Many) {
		NOAP_SOAP_Greater_Many = nOAP_SOAP_Greater_Many;
	}
	public Double getWMC_Less_VeryHigh() {
		return WMC_Less_VeryHigh;
	}
	public void setWMC_Less_VeryHigh(Double wMC_Less_VeryHigh) {
		WMC_Less_VeryHigh = wMC_Less_VeryHigh;
	}
	
}
