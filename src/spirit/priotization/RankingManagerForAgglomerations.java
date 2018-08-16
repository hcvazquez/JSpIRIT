package spirit.priotization;

import java.util.Vector;
import java.util.prefs.Preferences;

import spirit.db.DataBaseManager;
import spirit.priotization.criteria.AgglomerationEvolutionCriteria;
import spirit.priotization.criteria.AgglomerationRelevanceCriteria;
import spirit.priotization.criteria.ArchitecturalConcernCriteria;
import spirit.priotization.criteria.ArchitecturalConcernCriteriaWithImportance;
import spirit.priotization.criteria.HistoryBetaCriteria;
import spirit.priotization.criteria.HistoryENOMCriteria;
import spirit.priotization.criteria.HistoryLENOMCriteria;
import spirit.priotization.criteria.ModifiabilityScenariosForAgglomerationCriteria;
import spirit.priotization.rankings.RankingCalculator;
import spirit.priotization.rankings.RankingCalculatorForAgglomerations;
import spirit.priotization.rankings.agglomerations.RankingBasedOnlyOnAgglomerationEvolution;
import spirit.priotization.rankings.agglomerations.RankingBasedOnlyOnAgglomerationRelevance;
import spirit.priotization.rankings.agglomerations.RankingBasedOnlyOnArchitecturalConcern;
import spirit.priotization.rankings.agglomerations.RankingBasedOnlyOnArchitecturalConcernWithImportance;
import spirit.priotization.rankings.agglomerations.RankingBasedOnlyOnHistory;
import spirit.priotization.rankings.agglomerations.RankingBasedOnlyOnHistoryENOM;
import spirit.priotization.rankings.agglomerations.RankingBasedOnlyOnHistoryLENOM;
import spirit.priotization.rankings.agglomerations.RankingBasedOnlyOnScenarios;
import spirit.priotization.rankings.agglomerations.accuracyAnalysis.AccuracyAnalyzer;
import spirit.priotization.rankings.smells.accuracyAnalysis.PrecisionAndRecall;



public class RankingManagerForAgglomerations extends RankingManager{
	private static RankingManagerForAgglomerations instance;
	
	public static RankingManagerForAgglomerations getInstance(){
		if(instance==null)
			instance=new RankingManagerForAgglomerations();
		return instance;
	}

	@Override
	protected String getPreferedRankingCalculator() {
		return Preferences.userNodeForPackage(RankingCalculatorForAgglomerations.class).get("selectedRankingAgglomeration", rankingsCalculators.get(0).getName());
	}

	@Override
	protected void loadRankings(String project) {
		ArchitecturalConcernCriteria architecturalProblemCriteria=DataBaseManager.getInstance().getArchitecturalProblemCriteriaForProject(project);
		ArchitecturalConcernCriteriaWithImportance architecturalConcernCriteriaWithImportance=DataBaseManager.getInstance().getArchitecturalProblemCriteriaWithImportanceForProject(project);
		HistoryBetaCriteria historyBetaCriteria=DataBaseManager.getInstance().getHistoryBetaCriteriaForProject(project);
		AgglomerationRelevanceCriteria agglomerationRelevanceCriteria=DataBaseManager.getInstance().getAgglomerationRelevanceCriteriaForProject(project);
		AgglomerationEvolutionCriteria agglomerationEvolutionCriteria = DataBaseManager.getInstance().getAgglomerationEvolutionCriteriaForProject(project);
		ModifiabilityScenariosForAgglomerationCriteria modifiabilityScenariosForAgglomerationCriteria = DataBaseManager.getInstance().getModifiabilityScenariosForAgglomerationCriteriaForProject(project);
		HistoryLENOMCriteria historyLENOMCriteria=DataBaseManager.getInstance().getHistoryLENOMCriteriaForProject(project);
		HistoryENOMCriteria historyENOMCriteria=DataBaseManager.getInstance().getHistoryENOMCriteriaForProject(project);
		
		rankingsCalculators=new Vector<RankingCalculator>();
		rankingsCalculators.add(new RankingBasedOnlyOnArchitecturalConcern(architecturalProblemCriteria));
		rankingsCalculators.add(new RankingBasedOnlyOnArchitecturalConcernWithImportance(architecturalConcernCriteriaWithImportance));
		rankingsCalculators.add(new RankingBasedOnlyOnHistory(historyBetaCriteria));
		rankingsCalculators.add(new RankingBasedOnlyOnAgglomerationRelevance(agglomerationRelevanceCriteria));
		rankingsCalculators.add(new RankingBasedOnlyOnAgglomerationEvolution(agglomerationEvolutionCriteria));
		rankingsCalculators.add(new RankingBasedOnlyOnScenarios(modifiabilityScenariosForAgglomerationCriteria));
		rankingsCalculators.add(new RankingBasedOnlyOnHistoryLENOM(historyLENOMCriteria));
		rankingsCalculators.add(new RankingBasedOnlyOnHistoryENOM(historyENOMCriteria));
	}
	
	@Override
	public void setCurrentRanking(RankingCalculator currentRanking) {
		
		super.setCurrentRanking(currentRanking);
		new AccuracyAnalyzer().analyzeAccuracy();
		//new PrecisionAndRecall();
	}
	

}
