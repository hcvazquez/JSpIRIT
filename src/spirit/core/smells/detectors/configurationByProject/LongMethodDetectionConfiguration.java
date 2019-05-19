package spirit.core.smells.detectors.configurationByProject;

import spirit.metrics.constants.MetricThresholds;

public class LongMethodDetectionConfiguration extends CodeSmellDetectionConfiguration {

	private Long id;
	private Double MLOC_VERY_HIGH;
	
	public LongMethodDetectionConfiguration(String projectName) {
		super(projectName);
		MLOC_VERY_HIGH = MetricThresholds.MLOC_VERY_HIGH;
	}

	public LongMethodDetectionConfiguration() {
		super(null);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getMLOC_VERY_HIGH() {
		return MLOC_VERY_HIGH;
	}

	public void setMLOC_VERY_HIGH(Double mLOC_VERY_HIGH) {
		MLOC_VERY_HIGH = mLOC_VERY_HIGH;
	}

}
