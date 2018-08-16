package spirit.ui.views;

import org.eclipse.swt.widgets.Shell;

import java.util.prefs.Preferences;

import spirit.priotization.RankingManager;
import spirit.priotization.RankingManagerForSmells;
import spirit.priotization.rankings.RankingCalculatorForSmells;

public class RankingConfigurationForSmells extends RankingConfiguration {
	private SpIRITSmellsView spIRITView;
	
	
	public RankingConfigurationForSmells(Shell parentShell,
			SpIRITSmellsView spIRITView) {
		
		super(parentShell);
		this.spIRITView=spIRITView;
	}


	@Override
	protected RankingManager getInstanceOfRankingManager() {
		return RankingManagerForSmells.getInstance();
	}
	@Override
	protected void updateView(String rankingName) {
		Preferences.userNodeForPackage(RankingCalculatorForSmells.class).put("selectedRanking", rankingName);
		spIRITView.updateView();
	}
}
