package spirit.priotization.rankings.agglomerations.accuracyAnalysis;

import java.util.Iterator;
import java.util.Vector;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.core.design.DesignFlaw;

public class SpearmansCorrelation {
	/**
	 * 
	 * @param referenceRanking 
	 * @param agglomerationRanking 
	 * @param agglomerationRanking position values with ties resolved
	 * @param referenceRanking position values with ties resolved
	 */
	public static double calculateCorrelation(Vector<Double> agglomerationRankingPositions,Vector<Double> referenceRankingPositions, Vector<DesignFlaw> agglomerationRanking, Vector<AgglomerationModel> referenceRanking) {
		double mediaAgglomerationRanking=calculateMedia(agglomerationRankingPositions);
		double mediaReferenceRanking=calculateMedia(referenceRankingPositions);
		
		double sumOfDifferences=0;
		for (int i = 0; i < agglomerationRanking.size(); i++) {
			sumOfDifferences=sumOfDifferences+(agglomerationRankingPositions.get(i)-mediaAgglomerationRanking)*(referenceRankingPositions.get(referenceRanking.indexOf(agglomerationRanking.get(i))/*getIndexOf(agglomerationRanking.get(i),referenceRanking)*/)-mediaReferenceRanking);
		}
		
		
		double squaresOfDifferencesAgglomerationRanking=0;
		double squaresOfDifferencesReferenceRanking=0;
		
		
		for (int i = 0; i < agglomerationRanking.size(); i++) {
			squaresOfDifferencesAgglomerationRanking=squaresOfDifferencesAgglomerationRanking+Math.pow((agglomerationRankingPositions.get(i)-mediaAgglomerationRanking),2);
			squaresOfDifferencesReferenceRanking=squaresOfDifferencesReferenceRanking+Math.pow((referenceRankingPositions.get(referenceRanking.indexOf(agglomerationRanking.get(i))/*getIndexOf(agglomerationRanking.get(i),referenceRanking)*/)-mediaReferenceRanking),2);
		}
		double spearmansValue=sumOfDifferences/(Math.sqrt(squaresOfDifferencesAgglomerationRanking*squaresOfDifferencesReferenceRanking));
		return spearmansValue; 
	}
	/*private static int getIndexOf(DesignFlaw agglomeration, Vector<AgglomerationModel> referenceRanking){
		for (int i = 0; i < referenceRanking.size(); i++) {
			if(agglomeration.toString().equals(referenceRanking.get(i).toString()))
				return i;
		}
		return -1;
	}*/
	private static double calculateMedia(Vector<Double> rankingPositions) {
		double ret=0;
		
		for (Iterator<Double> iterator = rankingPositions.iterator(); iterator
				.hasNext();) {
			Double double1 = (Double) iterator.next();
			ret=ret+double1;
		}
		
		return ret/rankingPositions.size();
	} 
}
