package spirit.priotization.rankings.agglomerations;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.eclipse.core.resources.IProject;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.core.design.AgglomerationManager;
import spirit.core.design.CodeSmellsManager;
import spirit.core.design.CodeSmellsManagerFactory;
import spirit.priotization.criteria.AgglomerationEvolutionCriteria;
import spirit.priotization.rankings.RankingCalculatorForAgglomerations;
import spirit.priotization.rankings.RankingTypeAgglomerations;

public class RankingBasedOnlyOnAgglomerationEvolution extends RankingCalculatorForAgglomerations {
	
	private HashMap<String, AgglomerationModel> initialVersionAgglomerations = null;
	private HashMap<String, AgglomerationModel> intermediaryVersionAgglomerations = null;
	private HashMap<String, Double> averagePerAgglomeration = null;
	private Double minAverageValue = null;
	private Double maxAverageValue = null;
	
	private AgglomerationEvolutionCriteria agglomerationEvolutionCriteria;

	public RankingBasedOnlyOnAgglomerationEvolution(AgglomerationEvolutionCriteria agglomerationEvolutionCriteria) {
		super(RankingTypeAgglomerations.AGGLOMERATION_EVOLUTION);
		this.agglomerationEvolutionCriteria = agglomerationEvolutionCriteria;
				
	}

	private HashMap<String, AgglomerationModel> getIdToAgglomerationsMapping(IProject project) {
		HashMap<String, AgglomerationModel> idToAgglomeration = new HashMap<String, AgglomerationModel>();
		
		try {
			CodeSmellsManager smellsManager = CodeSmellsManagerFactory.getInstance().getManager(project);
			smellsManager.calculateMetricsCode();
			smellsManager.calculateAditionalMetrics();
			smellsManager.detectCodeSmells();
			AgglomerationManager.getInstance().detectAglomerations(project);
			
			for (AgglomerationModel agg : AgglomerationManager.getInstance().getResults(project.getFullPath().toString())) {
				idToAgglomeration.put(agg.getUniqueID(), agg);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			System.err.println(ex);
			return new HashMap<String, AgglomerationModel>();
		}
		
		return idToAgglomeration;
	}

	@Override
	protected Double calculateRankingValue(AgglomerationModel agglomeration) {
				
		if (initialVersionAgglomerations == null)
			initialVersionAgglomerations = getIdToAgglomerationsMapping(agglomerationEvolutionCriteria.getInitialVersionProject());
		if (intermediaryVersionAgglomerations == null)
			intermediaryVersionAgglomerations = getIdToAgglomerationsMapping(agglomerationEvolutionCriteria.getIntermediaryVersionProject());
		if (averagePerAgglomeration == null) {
			calculateAverageValues();
		}
		
		String uniqueID = agglomeration.getUniqueID();
		
		Double averageValue = averagePerAgglomeration.get(uniqueID);
		
		return ((averageValue - minAverageValue) / (maxAverageValue - minAverageValue));
	}

	private void calculateAverageValues() {
		averagePerAgglomeration = new HashMap<String, Double>();
		Set<AgglomerationModel> currentAgglomerations = AgglomerationManager.getInstance().getResultsForCurrentProject();
		for (AgglomerationModel ag : currentAgglomerations) {
			
			String uniqueID = ag.getUniqueID();
			
			AgglomerationModel initialAgglomeration = initialVersionAgglomerations.get(uniqueID);
			AgglomerationModel intermediaryAgglomeration = intermediaryVersionAgglomerations.get(uniqueID);
			
			double v1v2 = compareAgglomerations(initialAgglomeration, intermediaryAgglomeration);
			double v2v3 = compareAgglomerations(intermediaryAgglomeration, ag);
			
			double averageValue = (v1v2 + v2v3) / 2;
			averagePerAgglomeration.put(uniqueID, averageValue);
			
			if (minAverageValue == null || averageValue < minAverageValue)
				minAverageValue = averageValue;
			if (maxAverageValue == null || averageValue > maxAverageValue)
				maxAverageValue = averageValue;
		}
	}

	private double compareAgglomerations(AgglomerationModel agglomeration1, AgglomerationModel agglomeration2) {
		if (agglomeration1 != null && agglomeration2 != null) {
			int numberOfAnomalies1 = agglomeration1.getCodeAnomalies().size();
			int numberOfAnomalies2 = agglomeration2.getCodeAnomalies().size();
			return ((numberOfAnomalies2 * 100) / numberOfAnomalies1) - 100;
		} else if (agglomeration2 != null) {
			//TODO check if it is right
			return 100.0;
		}
		return 0.0;
	}

}
