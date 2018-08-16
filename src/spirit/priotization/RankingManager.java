package spirit.priotization;

import java.util.Iterator;
import java.util.Vector;
import java.util.prefs.Preferences;

import spirit.core.design.DesignFlaw;
import spirit.priotization.rankings.RankingCalculator;
import spirit.priotization.rankings.RankingCalculatorForSmells;
import spirit.priotization.rankings.agglomerations.accuracyAnalysis.AccuracyAnalyzer;

public abstract class RankingManager {
	
	private RankingCalculator currentRanking;
	protected Vector<RankingCalculator> rankingsCalculators;
	protected String currentProject;

	public RankingManager() {
		currentProject=null;
		rankingsCalculators=new Vector<RankingCalculator>();
	}
	
	public RankingCalculator getRankingCalculator(){
		return currentRanking;
	}
	public void setCurrentRanking(RankingCalculator currentRanking) {
		this.currentRanking = currentRanking;
		
	}
	public Vector<RankingCalculator> getRankingsCalculators() {
		return rankingsCalculators;
	}
	
	
	public void setDesingFlaws(Vector<DesignFlaw> smells,String project){
		currentProject=project;
		loadRankings(project);
		for (Iterator<RankingCalculator> iterator = rankingsCalculators.iterator(); iterator.hasNext();) {
			RankingCalculator calculator = (RankingCalculator) iterator.next();
			calculator.setDesignFlaws(smells);
		}
		
		RankingCalculator calculator=getRankingCalculator(getPreferedRankingCalculator());
		
		calculator.recalculateRanking();
	
		setCurrentRanking(calculator);
	}
	protected abstract String getPreferedRankingCalculator();

	protected abstract void loadRankings(String project);
	public String getCurrentProject() {
		return currentProject;
	}
	public RankingCalculator getRankingCalculator(String name) {
		for (Iterator<RankingCalculator> iterator = rankingsCalculators.iterator(); iterator.hasNext();) {
			RankingCalculator calculator = (RankingCalculator) iterator.next();
			if(calculator.getName().equals(name))
				return (RankingCalculator)calculator;
		}
		return null;
	}
	public int getPredefinedCalculatorIndex() {
		String name=getPreferedRankingCalculator();
		for (Iterator<RankingCalculator> iterator = rankingsCalculators.iterator(); iterator.hasNext();) {
			RankingCalculator calculator = (RankingCalculator) iterator.next();
			if(calculator.getName().equals(name))
				return rankingsCalculators.indexOf(calculator);
		}
		return 0;
	}
}
