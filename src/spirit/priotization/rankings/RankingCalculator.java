package spirit.priotization.rankings;

import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Vector;

import spirit.core.design.DesignFlaw;
import spirit.priotization.criteria.Criterion;

public abstract class RankingCalculator <T extends DesignFlaw> {
	
	private Hashtable<T, Double> rankingsValueCache;
	protected Vector<Criterion> criteria;
	protected Vector<T> ranking;
	
	public RankingCalculator() {
		criteria=new Vector<Criterion>();
		rankingsValueCache=new Hashtable<T, Double>();
	}
	
	protected abstract Double calculateRankingValue(T codeSmell);
	

	public abstract String getName();
	
	public Vector<Criterion> getCriteria() {
		return criteria;
	}
	
	public void recalculateRanking(){
		rankingsValueCache=new Hashtable<T, Double>();
		sortRanking();
	}
	
	public Double getRankingValue(T codeSmell){
		Double rankingValue=rankingsValueCache.get(codeSmell);
		if(rankingValue==null){
			rankingValue=calculateRankingValue(codeSmell);
			rankingsValueCache.put(codeSmell, rankingValue);
		}
		return rankingValue;
	}
	
	public void setDesignFlaws(Vector<T> smells){
		ranking=smells;
	}
	
	//los criteria values los deberia pasar por parametro segun el subtipo
	public int getRankingPosition(T flaw){
		return ranking.indexOf(flaw)+1;
	}
	
	public Vector<T> getRanking() {
		return ranking;
	}
		
	protected void sortRanking() {
		Collections.sort(ranking, new RelevanceOrder());
	}

	public class RelevanceOrder implements Comparator<T>{
		@Override
		public int compare(T o1, T o2) {
			
			return (getRankingValue((T) o1)).compareTo(getRankingValue((T) o2))*(-1);//Multiplico por -1 para que ordene de mayor a menor
		}
	}
}
