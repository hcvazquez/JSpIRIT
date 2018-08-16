package spirit.ui.views;

import java.util.prefs.Preferences;

import org.eclipse.swt.widgets.Shell;

import spirit.priotization.RankingManager;
import spirit.priotization.RankingManagerForAgglomerations;
import spirit.priotization.rankings.RankingCalculatorForAgglomerations;

public class RankingConfigurationForAgglomerations extends RankingConfiguration {
	private SpIRITAgglomerationsView spiritView;
	
	public RankingConfigurationForAgglomerations(Shell parentShell,
			SpIRITAgglomerationsView spiritView) {
		super(parentShell);
		this.spiritView=spiritView;
	}

	@Override
	protected RankingManager getInstanceOfRankingManager() {
		return RankingManagerForAgglomerations.getInstance();
	}

	@Override
	protected void updateView(String rankingName) {
		Preferences.userNodeForPackage(RankingCalculatorForAgglomerations.class).put("selectedRankingAgglomeration", rankingName);
		spiritView.updateView();
	}

}
