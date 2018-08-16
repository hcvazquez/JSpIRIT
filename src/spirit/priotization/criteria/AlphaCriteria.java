package spirit.priotization.criteria;

import org.eclipse.swt.widgets.Shell;

import spirit.ui.views.criteriaConfiguration.AlphaConfiguration;
import spirit.ui.views.criteriaConfiguration.CriterionConfigurationDialog;

public class AlphaCriteria extends Criterion {
	private Double alpha;
	private Long id;
	public AlphaCriteria(String projectName) {
		super("Alpha", projectName);
		alpha=0.0;
	}
	public AlphaCriteria() {
		super("",null);
	}
	@Override
	public CriterionConfigurationDialog getCriterionConfigurationDialog(
			Shell parentShell) {
		return new AlphaConfiguration(parentShell, this);
	}
	public Double getAlpha() {
		return alpha;
	}
	public void setAlpha(Double alpha) {
		this.alpha = alpha;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
