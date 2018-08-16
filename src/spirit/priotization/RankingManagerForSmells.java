package spirit.priotization;

import java.util.Vector;
import java.util.prefs.Preferences;

import spirit.db.DataBaseManager;
import spirit.priotization.criteria.AlphaCriteria;
import spirit.priotization.criteria.ArchitecturalConcernCriteriaForSmellsNormalized;
import spirit.priotization.criteria.BlueprintCriteria;
import spirit.priotization.criteria.HistoryBetaCriteria;
import spirit.priotization.criteria.ModifiabilityScenariosCriteria;
import spirit.priotization.criteria.ModifiabilityScenariosForSmellsNormalizedCriteria;
import spirit.priotization.criteria.SmellRelevanceCriteria;
import spirit.priotization.rankings.RankingCalculator;
import spirit.priotization.rankings.RankingCalculatorForSmells;
import spirit.priotization.rankings.agglomerations.accuracyAnalysis.AccuracyAnalyzer;
import spirit.priotization.rankings.smells.RankingBasedOnRelevanceHistoryAndScenarios;
import spirit.priotization.rankings.smells.RankingBasedOnSmellRelevanceAndHistory;
import spirit.priotization.rankings.smells.RankingBasedOnlyOnBlueprintForSmells;
import spirit.priotization.rankings.smells.RankingBasedOnlyOnConcernsForSmells;
import spirit.priotization.rankings.smells.RankingBasedOnlyOnScenariosForSmells;
import spirit.priotization.rankings.smells.RankingBasedOnlyOnSmellRelevance;

public class RankingManagerForSmells extends RankingManager{
	private static RankingManagerForSmells instance;
	
	public static RankingManagerForSmells getInstance(){
		if(instance==null)
			instance=new RankingManagerForSmells();
		return instance;
	}
	
	
	protected void loadRankings(String project){
		/**for (Iterator<SmellRelevanceCriteria> iterator = DataBaseManager.getInstance().listSmellRelevanceCriteria().iterator(); iterator.hasNext();) {
			SmellRelevanceCriteria type = (SmellRelevanceCriteria) iterator.next();
			DataBaseManager.getInstance().deleteSmellRelevanceCriteria(type);
		}*/
		/**for (Iterator<HistoryBetaCriteria> iterator = DataBaseManager.getInstance().listHistoryBetaCriteria().iterator(); iterator.hasNext();) {
			HistoryBetaCriteria type = (HistoryBetaCriteria) iterator.next();
			DataBaseManager.getInstance().deleteHistoryBetaCriteria(type);
		}*/
		SmellRelevanceCriteria smellRelevanceCriteria=DataBaseManager.getInstance().getSmellRelevanceCriteriaForProject(project);
		HistoryBetaCriteria historyBetaCriteria=DataBaseManager.getInstance().getHistoryBetaCriteriaForProject(project);
		ModifiabilityScenariosCriteria modifiabilityScenariosCriteria=DataBaseManager.getInstance().getModifiabilityScenariosCriteriaForProject(project);
		AlphaCriteria alphaCriteria=DataBaseManager.getInstance().getAlphaCriteriaForProject(project);
		ModifiabilityScenariosForSmellsNormalizedCriteria modifiabilityScenariosForSmellsNormalizedCriteria=DataBaseManager.getInstance().getmodifiabilityScenariosForSmellsNormalizedCriteriaForProject(project);
		ArchitecturalConcernCriteriaForSmellsNormalized architecturalConcernCriteriaForSmellsNormalized=DataBaseManager.getInstance().getArchitecturalConcernCriteriaForSmellsNormalizedForProject(project);
		BlueprintCriteria blueprintCriteria=DataBaseManager.getInstance().getBlueprintCriteriaForSmellsNormalizedForProject(project);
		
		rankingsCalculators=new Vector<RankingCalculator>();
		rankingsCalculators.add(new RankingBasedOnlyOnSmellRelevance(smellRelevanceCriteria));
		rankingsCalculators.add(new RankingBasedOnSmellRelevanceAndHistory(smellRelevanceCriteria, historyBetaCriteria));
		rankingsCalculators.add(new RankingBasedOnRelevanceHistoryAndScenarios(smellRelevanceCriteria, historyBetaCriteria, modifiabilityScenariosCriteria, alphaCriteria));
		rankingsCalculators.add(new RankingBasedOnlyOnScenariosForSmells(modifiabilityScenariosForSmellsNormalizedCriteria));
		rankingsCalculators.add(new RankingBasedOnlyOnConcernsForSmells(architecturalConcernCriteriaForSmellsNormalized));
		rankingsCalculators.add(new RankingBasedOnlyOnBlueprintForSmells(blueprintCriteria));
	}
	
	
	protected String getPreferedRankingCalculator(){
		return Preferences.userNodeForPackage(RankingCalculatorForSmells.class).get("selectedRanking", rankingsCalculators.get(0).getName());
	}
	
	
}
