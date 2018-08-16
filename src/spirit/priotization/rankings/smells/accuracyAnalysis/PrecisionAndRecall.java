package spirit.priotization.rankings.smells.accuracyAnalysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import spirit.core.design.CodeSmellsManagerFactory;
import spirit.core.smells.CodeSmell;
import spirit.priotization.RankingManagerForSmells;
import spirit.priotization.rankings.RankingCalculator;

public class PrecisionAndRecall {
	private Vector<String> criticalExpertClasses;
	private Vector<CodeSmell> smells;
	public PrecisionAndRecall() {
		criticalExpertClasses=new Vector<>();
		//loadMM8();
		//loadSubscribersDB();
		loadHW();
		smells=CodeSmellsManagerFactory.getInstance().getCurrentProjectManager().getSmells();
		
		analyzeMetrics();
	}
	private void loadHW() {
		criticalExpertClasses.add("healthwatcher.view.IFacade");
		criticalExpertClasses.add("healthwatcher.view.RMIServletAdapter");
		criticalExpertClasses.add("healthwatcher.business.HealthWatcherFacade");
		criticalExpertClasses.add("healthwatcher.business.IFacadeRMITargetAdapter");
		criticalExpertClasses.add("healthwatcher.business.RMIFacadeAdapter");
		criticalExpertClasses.add("healthwatcher.view.command.GetDataForSearchByDiseaseType");
		criticalExpertClasses.add("healthwatcher.view.command.GetDataForSearchByHealthUnit");
		criticalExpertClasses.add("healthwatcher.view.command.GetDataForSearchBySpeciality");
		criticalExpertClasses.add("healthwatcher.view.command.InsertAnimalComplaint");
		criticalExpertClasses.add("healthwatcher.view.command.InsertDiseaseType");
		criticalExpertClasses.add("healthwatcher.view.command.InsertEmployee");
		criticalExpertClasses.add("healthwatcher.view.command.InsertFoodComplaint");
		criticalExpertClasses.add("healthwatcher.view.command.InsertHealthUnit");
		criticalExpertClasses.add("healthwatcher.view.command.InsertMedicalSpeciality");
		criticalExpertClasses.add("healthwatcher.view.command.InsertSpecialComplaint");
		criticalExpertClasses.add("healthwatcher.view.command.InsertSymptom");
		criticalExpertClasses.add("healthwatcher.view.command.SearchComplaintData");
		criticalExpertClasses.add("healthwatcher.view.command.SearchDiseaseData");
		criticalExpertClasses.add("healthwatcher.view.command.SearchHealthUnitsBySpecialty");
		criticalExpertClasses.add("healthwatcher.view.command.SearchSpecialtiesByHealthUnit");
		criticalExpertClasses.add("healthwatcher.view.command.UpdateComplaintData");
		criticalExpertClasses.add("healthwatcher.view.command.UpdateComplaintList");
		criticalExpertClasses.add("healthwatcher.view.command.UpdateEmployeeData");
		criticalExpertClasses.add("healthwatcher.view.command.UpdateEmployeeSearch");
		criticalExpertClasses.add("healthwatcher.view.command.UpdateHealthUnitData");
		criticalExpertClasses.add("healthwatcher.view.command.UpdateHealthUnitList");
		criticalExpertClasses.add("healthwatcher.view.command.UpdateHealthUnitSearch");
		criticalExpertClasses.add("healthwatcher.view.command.UpdateMedicalSpecialityData");
		criticalExpertClasses.add("healthwatcher.view.command.UpdateMedicalSpecialityList");
		
	}
	private void loadSubscribersDB() {
		criticalExpertClasses.add("ui.search.Searchresults");
		criticalExpertClasses.add("ui.search.personUtils.searchCriteria.SearchCriteriaForLabel");
		criticalExpertClasses.add("bd.pojos.Person");
		criticalExpertClasses.add("bd.pojos.Label");
		criticalExpertClasses.add("bd.DataBaseManager");
		criticalExpertClasses.add("ui.add.AddPersonFrame");
		criticalExpertClasses.add("ui.search.personUtils.searchCriteria.SearchCriteriaForDonation");
		criticalExpertClasses.add("ui.add.AddLabel");
		criticalExpertClasses.add("ui.add.AddEvent");
		criticalExpertClasses.add("ui.add.AddPersonToEvent");
		criticalExpertClasses.add("ui.add.AddDonation");
		criticalExpertClasses.add("ui.add.EditLabelsForPerson");
		
	}
	private void loadMM8() {
		criticalExpertClasses.add("ubc.midp.mobilephoto.core.ui.controller.AbstractController");
		criticalExpertClasses.add("ubc.midp.mobilephoto.core.ui.controller.AlbumController");
		criticalExpertClasses.add("ubc.midp.mobilephoto.core.ui.controller.ControllerInterface");
		criticalExpertClasses.add("ubc.midp.mobilephoto.core.ui.controller.MediaController");
		criticalExpertClasses.add("ubc.midp.mobilephoto.core.ui.controller.PhotoViewController");
		criticalExpertClasses.add("ubc.midp.mobilephoto.core.ui.controller.MediaListController");
		criticalExpertClasses.add("ubc.midp.mobilephoto.core.ui.controller.MusicPlayController");
		criticalExpertClasses.add("ubc.midp.mobilephoto.sms.SmsReceiverController");
		criticalExpertClasses.add("ubc.midp.mobilephoto.core.ui.controller.PlayVideoController");
		criticalExpertClasses.add("ubc.midp.mobilephoto.core.ui.controller.VideoCaptureController");
		
		/*criticalExpertClasses.add("lancs.midp.mobilephoto.lib.exceptions.PersistenceMechanismException");
		 criticalExpertClasses.add("ubc.midp.mobilephoto.core.ui.controller.BaseController");
		 criticalExpertClasses.add("ubc.midp.mobilephoto.core.ui.datamodel.ImageMediaAccessor");
		 criticalExpertClasses.add("ubc.midp.mobilephoto.core.ui.MainUIMidlet");
		 criticalExpertClasses.add("ubc.midp.mobilephoto.sms.SmsMessaging");*/
	}
	private void analyzeMetrics(){
		//Calculo para el total de smells
		calculateMetrcis(getMainClassesOfSmellsAboveAThreashold(-1, RankingManagerForSmells.getInstance().getRankingCalculator()));
		System.out.println("");
		
		//Calculo los criterios
		calculateMetricForACriterion(getRankingCalculator("Scenarios normalized"));
		calculateMetricForACriterion(getRankingCalculator("Concerns normalized"));
		calculateMetricForACriterion(getRankingCalculator("Blueprint normalized"));
		
		//Calculo el voting
		calculateMetricsForVoting();
	}
	
	
	private RankingCalculator getRankingCalculator(String name){
		RankingCalculator calculator=RankingManagerForSmells.getInstance().getRankingCalculator(name);
		calculator.recalculateRanking();
		RankingManagerForSmells.getInstance().setCurrentRanking(calculator);
		return calculator;
	}
	private Set<String> getMainClassesOfSmellsAboveAThreashold(double k, RankingCalculator calculator){
		Set<String> ret=new HashSet<String>();
		for (Iterator<CodeSmell> iterator = smells.iterator(); iterator.hasNext();) {
			CodeSmell smell = (CodeSmell) iterator.next();
			if(calculator.getRankingValue(smell)>k)
				ret.add(smell.getMainClassName());
		}
		return ret;
	}
	private void calculateMetricForACriterion(RankingCalculator calculator) {
		double[] k_movil={0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
		
		for (int i = 0; i < k_movil.length; i++) {
			calculateMetrcis(getMainClassesOfSmellsAboveAThreashold(k_movil[i],calculator));
		} 
		System.out.println("");
	}
	private void calculateMetricsForVoting() {
		double[] k_movil={0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
		for (int i_scenarios = 0; i_scenarios < k_movil.length; i_scenarios++) {
			Set<String> classes_scenarios=getMainClassesOfSmellsAboveAThreashold(k_movil[i_scenarios],getRankingCalculator("Scenarios normalized"));
			
			for (int i_concerns = 0; i_concerns < k_movil.length; i_concerns++) {
				Set<String> classes_concerns=getMainClassesOfSmellsAboveAThreashold(k_movil[i_concerns],getRankingCalculator("Concerns normalized"));
				
				for (int i_blueprint = 0; i_blueprint < k_movil.length; i_blueprint++) {
					Set<String> classes_blueprint=getMainClassesOfSmellsAboveAThreashold(k_movil[i_blueprint],getRankingCalculator("Blueprint normalized"));
					
					System.out.print(k_movil[i_scenarios]+"\t"+k_movil[i_concerns]+"\t"+k_movil[i_blueprint]+"\t");
					calculateMetrcis(voteInAtLeast2(classes_scenarios,classes_concerns,classes_blueprint));
					System.out.println("");
				}
			}
		}
		
	}
	private Set<String> voteInAtLeast2(Set<String> classes_scenarios,
			Set<String> classes_concerns, Set<String> classes_blueprint) {
		Set<String> ret=new HashSet<String>();
		for (Iterator<String> iterator = classes_scenarios.iterator(); iterator
				.hasNext();) {
			String string = (String) iterator.next();
			if(classes_concerns.contains(string)||classes_blueprint.contains(string))
				ret.add(string);
		}
		for (Iterator<String> iterator = classes_concerns.iterator(); iterator
				.hasNext();) {
			String string = (String) iterator.next();
			if(classes_scenarios.contains(string)||classes_blueprint.contains(string))
				ret.add(string);
		}
		for (Iterator<String> iterator = classes_blueprint.iterator(); iterator
				.hasNext();) {
			String string = (String) iterator.next();
			if(classes_concerns.contains(string)||classes_scenarios.contains(string))
				ret.add(string);
		}
		return ret;
	}
	private void calculateMetrcis(Set<String> retrievedClasses){
		double tp=0;
		double fp=0;
		double fn=0;
		
		for (Iterator<String> iterator = retrievedClasses.iterator(); iterator
				.hasNext();) {
			String className = (String) iterator.next();
			if(criticalExpertClasses.contains(className))
				tp++;
			else
				fp++;
		}
		for (Iterator<String> iterator = criticalExpertClasses.iterator(); iterator
				.hasNext();) {
			String className = (String) iterator.next();
			if(!retrievedClasses.contains(className))
				fn++;
		}
		double precision=tp/(tp+fp);
		double recall=tp/(tp+fn);
		double fmeasured=(2*precision*recall)/(precision+recall);
		System.out.print(precision+"\t"+recall+"\t"+fmeasured+"\t");
	}
}
