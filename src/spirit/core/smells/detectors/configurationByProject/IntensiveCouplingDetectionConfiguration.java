package spirit.core.smells.detectors.configurationByProject;

import spirit.metrics.constants.MetricThresholds;

public class IntensiveCouplingDetectionConfiguration extends
CodeSmellDetectionConfiguration {
	private Long id;
	private Double MAXNESTING_Greater_SHALLOW;
	private Double CDISP_Less_OneQuarter;
	private Double CINT_Greater_Few;
	private Double CDISP_Less_Half;
	private Double CINT_Greater_SMemCap;
	
	public IntensiveCouplingDetectionConfiguration(String projectName) {
		super(projectName);
		MAXNESTING_Greater_SHALLOW=MetricThresholds.SHALLOW;
		CDISP_Less_OneQuarter=MetricThresholds.ONE_QUARTER;
		CINT_Greater_Few=MetricThresholds.FEW;
		CDISP_Less_Half=MetricThresholds.HALF;
		CINT_Greater_SMemCap=MetricThresholds.SMemCap;
	}
	public IntensiveCouplingDetectionConfiguration() {
		super(null);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getMAXNESTING_Greater_SHALLOW() {
		return MAXNESTING_Greater_SHALLOW;
	}

	public void setMAXNESTING_Greater_SHALLOW(Double mAXNESTING_Greater_SHALLOW) {
		MAXNESTING_Greater_SHALLOW = mAXNESTING_Greater_SHALLOW;
	}

	public Double getCDISP_Less_OneQuarter() {
		return CDISP_Less_OneQuarter;
	}

	public void setCDISP_Less_OneQuarter(Double cDISP_Less_OneQuarter) {
		CDISP_Less_OneQuarter = cDISP_Less_OneQuarter;
	}

	public Double getCINT_Greater_Few() {
		return CINT_Greater_Few;
	}

	public void setCINT_Greater_Few(Double cINT_Greater_Few) {
		CINT_Greater_Few = cINT_Greater_Few;
	}

	public Double getCDISP_Less_Half() {
		return CDISP_Less_Half;
	}

	public void setCDISP_Less_Half(Double cDISP_Less_Half) {
		CDISP_Less_Half = cDISP_Less_Half;
	}

	public Double getCINT_Greater_SMemCap() {
		return CINT_Greater_SMemCap;
	}

	public void setCINT_Greater_SMemCap(Double cINT_Greater_SMemCap) {
		CINT_Greater_SMemCap = cINT_Greater_SMemCap;
	}
	
}
