package spirit.core.smells.detectors.configurationByProject;

import spirit.metrics.constants.MetricThresholds;

public class DispersedCouplingDetectionConfiguration extends
		CodeSmellDetectionConfiguration {
	private Long id;
	private Double MAXNESTING_Greater_Shallow;
	private Double CINT_Greater_SMemCap;
	private Double CDISP_GreaterEqual_Half;
	
	public DispersedCouplingDetectionConfiguration(String projectName) {
		super(projectName);
		MAXNESTING_Greater_Shallow=MetricThresholds.SHALLOW;
		CINT_Greater_SMemCap=MetricThresholds.SMemCap;
		CDISP_GreaterEqual_Half=MetricThresholds.HALF;
	}
	public DispersedCouplingDetectionConfiguration() {
		super(null);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getMAXNESTING_Greater_Shallow() {
		return MAXNESTING_Greater_Shallow;
	}

	public void setMAXNESTING_Greater_Shallow(Double mAXNESTING_Greater_Shallow) {
		MAXNESTING_Greater_Shallow = mAXNESTING_Greater_Shallow;
	}

	public Double getCINT_Greater_SMemCap() {
		return CINT_Greater_SMemCap;
	}

	public void setCINT_Greater_SMemCap(Double cINT_Greater_SMemCap) {
		CINT_Greater_SMemCap = cINT_Greater_SMemCap;
	}

	public Double getCDISP_GreaterEqual_Half() {
		return CDISP_GreaterEqual_Half;
	}

	public void setCDISP_GreaterEqual_Half(Double cDISP_GreaterEqual_Half) {
		CDISP_GreaterEqual_Half = cDISP_GreaterEqual_Half;
	}
	
}
