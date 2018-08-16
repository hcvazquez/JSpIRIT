package spirit.priotization.rankings.agglomerations.accuracyAnalysis;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.core.design.AgglomerationManager;
import spirit.core.design.DesignFlaw;
import spirit.core.smells.CodeSmell;
import spirit.priotization.RankingManagerForAgglomerations;
import spirit.priotization.rankings.RankingCalculator;

public class AccuracyAnalyzer {
	private Vector<ArchitecturalProblem> architecturalProblems;
	private Vector<AgglomerationModel> agglomerations;
	public AccuracyAnalyzer() {
		loadArchitecturalProblems();
		
		agglomerations=new Vector<>();
		 
		for (Iterator<AgglomerationModel> iterator = AgglomerationManager.getInstance().getResultsForCurrentProject().iterator(); iterator.hasNext();) {
			AgglomerationModel agglomeration = (AgglomerationModel) iterator.next();
			agglomerations.add(agglomeration);
		}
	}
	private void loadArchitecturalProblems() {
		architecturalProblems=new Vector<ArchitecturalProblem>();

		//loadSubscribersDB24();
		//loadMobileMedia8();
		//loadHealthWatcher10();
		//loadMobileMedia8Refined();
		loadHealthWatcher10Refined();
		//loadSubscribersDB24Refined();
		//loadSubscribersDB24New();
	}
	
	private void loadSubscribersDB24Refined2() {
		Vector<String> classes=new Vector<>();
		Vector<String> packages=new Vector<>();
		
		classes.add("ui.search.personUtils.searchCriteria.SearchCriteria");
		classes.add("ui.search.SearchPerson");
		architecturalProblems.add(new ArchitecturalProblem("Ambiguous Interface", classes, packages));
		loadProblemWith1Class("Concern Overload", "bd.DataBaseManager");
		
		loadViolationWith2Classes("Architectural Violation","ui.add.AddDonation", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddEvent", "bd.DataBaseManager");
		//loadViolationWith2Classes("Architectural Violation","ui.add.AddLabel", "bd.DataBaseManager");
		//loadViolationWith2Classes("Architectural Violation","ui.add.AddPersonFrame", "bd.DataBaseManager");
		//loadViolationWith2Classes("Architectural Violation","ui.add.AddPersonToEvent", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.add.EditLabelsForPerson", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditDonation", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditEvents", "bd.DataBaseManager");
		//loadViolationWith2Classes("Architectural Violation","ui.edit.EditLabels", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditPassword", "bd.DataBaseManager");
		//loadViolationWith2Classes("Architectural Violation","ui.edit.EditPerson", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.ModifyEventInstanceDate", "bd.DataBaseManager");
		//loadViolationWith2Classes("Architectural Violation","ui.edit.ModifyEventName", "bd.DataBaseManager");
		//loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.events.CriteriaCreationPanelForEvent", "bd.DataBaseManager");
		//loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchDonationsResults", "bd.DataBaseManager");
		//loadViolationWith2Classes("Architectural Violation","ui.search.SearchDonations", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.add.AddDonation");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddPersonFrame", "ui.search.Searchresults");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.add.AddPersonToEvent");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditDonation", "ui.search.SearchDonationsResults");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditPerson", "ui.search.Searchresults");
		loadViolationWith2Classes("Architectural Violation","ui.add.label.modifyAssociationField.SimpleDatePanelForLabel", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","bd.pojos.AssociatedField.AssociatedFieldValueForSearch", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","bd.pojos.AssociatedField.AssociatedFieldValueDateForSearch", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","bd.pojos.AssociatedField.AssociatedFieldValueStringForSearch", "bd.pojos.AssociatedField");
		//loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.labels.internalPanels.SimpleFieldPanelForLabel", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.labels.CriteriaCreationPanelForLabel", "bd.pojos.AssociatedField");
		//loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.pojos.Donation");
		//loadViolationWith2Classes("Architectural Violation","ui.search.SearchDonations", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchDonationsResults", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.pojos.Event");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.events.CriteriaCreationPanelForEvent", "bd.pojos.Event");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.events.CriteriaCreationPanelForEvent", "bd.pojos.EventInstance");
		//loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.labels.CriteriaCreationPanelForLabel", "bd.pojos.Label");
		//loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.pojos.Label");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "bd.pojos.Label");
		//loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.pojos.Person");
		//loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.mailing.CreateLetterDialog", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.mailing.CreateLetterDialog", "bd.pojos.Person");
		//loadViolationWith2Classes("Architectural Violation","ui.edit.EditDonation", "ui.add.AddDonation");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddPersonFrame", "ui.add.EditLabelsForPerson");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditPerson", "ui.add.AddPersonFrame");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.mailing.CreateLetterDialog");
		//loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForDonation", "bd.pojos.Person");
		//loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForDonation", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForEvent", "bd.pojos.EventInstance");
		//loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForEvent", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForEvent", "bd.pojos.Event");
		//loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaToFilter", "bd.pojos.Person");
		//loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForLabel", "bd.pojos.AssociatedField");
		//loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForLabel", "bd.pojos.Person");
		//loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForLabel", "bd.pojos.AssociatedFieldValueForLabel");
		//loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForLabel", "bd.pojos.Label");
	}
	private void loadSubscribersDB24New() {
		Vector<String> classes=new Vector<>();
		Vector<String> packages=new Vector<>();
		
		
		classes.add("ui.search.SearchPerson");
		architecturalProblems.add(new ArchitecturalProblem("Ambiguous Interface", classes, packages));
		loadProblemWith1Class("Concern Overload", "bd.DataBaseManager");
		
		loadViolationWith2Classes("Cyclic Dependency","ui.MainWindowKolbe", "ui.PasswordDialog");
		
		loadViolationWith2Classes("Architectural Violation","ui.add.AddPersonFrame", "ui.search.Searchresults");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.add.AddDonation");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.add.AddPersonToEvent");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.mailing.MailLabel");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.mailing.CreateLetterDialog");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.mailing.JasperReportBuilder");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditDonation", "ui.search.SearchDonationsResults");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.edit.EditPerson");
		
	
	}
	
	private void loadSubscribersDB24Refined() {
		Vector<String> classes=new Vector<>();
		Vector<String> packages=new Vector<>();
		
		classes.add("ui.search.personUtils.searchCriteria.SearchCriteria");
		classes.add("ui.search.SearchPerson");
		architecturalProblems.add(new ArchitecturalProblem("Ambiguous Interface", classes, packages));
		//loadProblemWith1Class("Concern Overload", "bd.DataBaseManager");
		
		/*loadViolationWith2Classes("Architectural Violation","ui.add.AddDonation", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddEvent", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddLabel", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddPersonFrame", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddPersonToEvent", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.add.EditLabelsForPerson", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditDonation", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditEvents", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditLabels", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditPassword", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditPerson", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.ModifyEventInstanceDate", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.ModifyEventName", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.events.CriteriaCreationPanelForEvent", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchDonationsResults", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchDonations", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.add.AddDonation");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddPersonFrame", "ui.search.Searchresults");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.add.AddPersonToEvent");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditDonation", "ui.search.SearchDonationsResults");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditPerson", "ui.search.Searchresults");
		loadViolationWith2Classes("Architectural Violation","ui.add.label.modifyAssociationField.SimpleDatePanelForLabel", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","bd.pojos.AssociatedField.AssociatedFieldValueForSearch", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","bd.pojos.AssociatedField.AssociatedFieldValueDateForSearch", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","bd.pojos.AssociatedField.AssociatedFieldValueStringForSearch", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.labels.internalPanels.SimpleFieldPanelForLabel", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.labels.CriteriaCreationPanelForLabel", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchDonations", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchDonationsResults", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.pojos.Event");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.events.CriteriaCreationPanelForEvent", "bd.pojos.Event");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.events.CriteriaCreationPanelForEvent", "bd.pojos.EventInstance");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.labels.CriteriaCreationPanelForLabel", "bd.pojos.Label");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.pojos.Label");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "bd.pojos.Label");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.mailing.CreateLetterDialog", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.mailing.CreateLetterDialog", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditDonation", "ui.add.AddDonation");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddPersonFrame", "ui.add.EditLabelsForPerson");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditPerson", "ui.add.AddPersonFrame");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.mailing.CreateLetterDialog");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForDonation", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForDonation", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForEvent", "bd.pojos.EventInstance");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForEvent", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForEvent", "bd.pojos.Event");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaToFilter", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForLabel", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForLabel", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForLabel", "bd.pojos.AssociatedFieldValueForLabel");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForLabel", "bd.pojos.Label");
		*/
	}
	private void loadHealthWatcher10Refined() {
		
		loadViolationWith2Classes("Conector Envy", "healthwatcher.business.RMIFacadeAdapter","healthwatcher.view.IFacade");	
		loadViolationWith2Classes("Conector Envy", "healthwatcher.business.HealthWatcherFacade","lib.persistence.IPersistenceMechanism");
		loadViolationWith2Classes("Conector Envy", "healthwatcher.business.HealthWatcherFacade","lib.persistence.PersistenceMechanism");
		
		loadViolationWith2Classes("Ambiguous Interface","healthwatcher.business.complaint.ComplaintRecord","healthwatcher.model.complaint.Complaint");
		loadViolationWith2Classes("Ambiguous Interface","healthwatcher.business.complaint.ComplaintRecord","healthwatcher.model.complaint.FoodComplaint");
		loadViolationWith2Classes("Ambiguous Interface","healthwatcher.business.complaint.ComplaintRecord","healthwatcher.model.complaint.AnimalComplaint");
		
		loadViolationWith2Classes("Ambiguous Interface","healthwatcher.data.mem.ComplaintRepositoryArray","healthwatcher.model.complaint.FoodComplaint");
		loadViolationWith2Classes("Ambiguous Interface","healthwatcher.data.mem.ComplaintRepositoryArray","healthwatcher.model.complaint.AnimalComplaint");
		loadViolationWith2Classes("Ambiguous Interface","healthwatcher.data.mem.ComplaintRepositoryArray","healthwatcher.model.complaint.SpecialComplaint");
	
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.HealthWatcherFacade", "lib.persistence.IPersistenceMechanism");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.HealthWatcherFacade", "lib.persistence.PersistenceMechanism");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.HealthWatcherFacade", "healthwatcher.model.healthguide.HealthUnit");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.HealthWatcherFacade", "healthwatcher.model.complaint.Complaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.HealthWatcherFacade", "healthwatcher.model.complaint.DiseaseType");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.HealthWatcherFacade", "healthwatcher.model.employee.Employee");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.HealthWatcherFacade", "healthwatcher.model.complaint.Symptom");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.HealthWatcherFacade", "healthwatcher.model.healthguide.MedicalSpeciality");
		
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.IFacadeRMITargetAdapter", "healthwatcher.model.complaint.Complaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.IFacadeRMITargetAdapter", "healthwatcher.model.complaint.DiseaseType");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.IFacadeRMITargetAdapter", "healthwatcher.model.healthguide.HealthUnit");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.IFacadeRMITargetAdapter", "healthwatcher.model.employee.Employee");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.IFacadeRMITargetAdapter", "healthwatcher.model.healthguide.MedicalSpeciality");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.IFacadeRMITargetAdapter", "healthwatcher.model.complaint.Symptom");
		
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.RMIFacadeAdapter", "healthwatcher.model.complaint.Complaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.RMIFacadeAdapter", "healthwatcher.model.complaint.DiseaseType");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.RMIFacadeAdapter", "healthwatcher.model.healthguide.HealthUnit");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.RMIFacadeAdapter", "healthwatcher.model.employee.Employee");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.RMIFacadeAdapter", "healthwatcher.model.healthguide.MedicalSpeciality");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.RMIFacadeAdapter", "healthwatcher.model.complaint.Symptom");
		
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.GetDataForSearchByDiseaseType", "healthwatcher.model.complaint.DiseaseType");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.GetDataForSearchByHealthUnit", "healthwatcher.model.healthguide.HealthUnit");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.GetDataForSearchBySpeciality", "healthwatcher.model.healthguide.MedicalSpeciality");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertAnimalComplaint", "healthwatcher.model.address.Address");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertAnimalComplaint", "healthwatcher.model.complaint.AnimalComplaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertAnimalComplaint", "healthwatcher.model.complaint.Complaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertDiseaseType", "healthwatcher.model.complaint.DiseaseType");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertEmployee",  "healthwatcher.model.employee.Employee");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertFoodComplaint", "healthwatcher.model.address.Address");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertFoodComplaint", "healthwatcher.model.complaint.Complaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertFoodComplaint", "healthwatcher.model.complaint.FoodComplaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertHealthUnit", "healthwatcher.model.healthguide.HealthUnit");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertMedicalSpeciality", "healthwatcher.model.healthguide.MedicalSpeciality");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertSpecialComplaint", "healthwatcher.model.address.Address");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertSpecialComplaint", "healthwatcher.model.complaint.Complaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertSpecialComplaint", "healthwatcher.model.complaint.SpecialComplaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertSymptom", "healthwatcher.model.complaint.Symptom");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.SearchComplaintData", "healthwatcher.model.address.Address");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.SearchComplaintData", "healthwatcher.model.complaint.AnimalComplaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.SearchComplaintData", "healthwatcher.model.complaint.Complaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.SearchComplaintData", "healthwatcher.model.complaint.FoodComplaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.SearchComplaintData", "healthwatcher.model.complaint.Situation");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.SearchComplaintData", "healthwatcher.model.complaint.SpecialComplaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.SearchDiseaseData", "healthwatcher.model.complaint.DiseaseType");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.SearchDiseaseData", "healthwatcher.model.complaint.Symptom");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.SearchHealthUnitsBySpecialty", "healthwatcher.model.healthguide.HealthUnit");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.SearchSpecialtiesByHealthUnit", "healthwatcher.model.healthguide.MedicalSpeciality");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateComplaintList", "healthwatcher.model.complaint.Complaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateComplaintSearch", "healthwatcher.model.complaint.AnimalComplaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateComplaintSearch", "healthwatcher.model.complaint.Complaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateComplaintSearch", "healthwatcher.model.complaint.FoodComplaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateComplaintSearch", "healthwatcher.model.complaint.Situation");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateComplaintSearch", "healthwatcher.model.complaint.SpecialComplaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateHealthUnitSearch", "healthwatcher.model.healthguide.HealthUnit");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateMedicalSpecialityList", "healthwatcher.model.healthguide.MedicalSpeciality");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateMedicalSpecialitySearch", "healthwatcher.model.healthguide.MedicalSpeciality");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateSymptomData", "healthwatcher.model.complaint.Symptom");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateSymptomList", "healthwatcher.model.complaint.Symptom");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateSymptomSearch", "healthwatcher.model.complaint.Symptom");
	}
	private void loadMobileMedia8Refined() {
	
		loadProblemWith1Class("Concern Overload", "ubc.midp.mobilephoto.core.ui.datamodel.ImageAlbumData");
		loadProblemWith1Class("Concern Overload", "ubc.midp.mobilephoto.core.ui.datamodel.MusicAlbumData");
		loadProblemWith1Class("Concern Overload", "ubc.midp.mobilephoto.core.ui.datamodel.MusicMediaAccessor");
		loadProblemWith1Class("Concern Overload", "ubc.midp.mobilephoto.core.ui.datamodel.VideoAlbumData");
		loadProblemWith1Class("Concern Overload", "ubc.midp.mobilephoto.core.util.MusicMediaUtil");
		
		Vector<String> classes=new Vector<>();
		Vector<String> packages=new Vector<>();
		classes.add("ubc.midp.mobilephoto.core.ui.controller.AlbumController");
		classes.add("ubc.midp.mobilephoto.core.ui.controller.BaseController");
		classes.add("ubc.midp.mobilephoto.core.ui.controller.MediaController");
		classes.add("ubc.midp.mobilephoto.core.ui.controller.PhotoViewController");
		classes.add("ubc.midp.mobilephoto.core.ui.controller.PlayVideoController");
		classes.add("ubc.midp.mobilephoto.core.ui.controller.SelectMediaController");
		classes.add("ubc.midp.mobilephoto.core.ui.controller.VideoCaptureController");
		classes.add("ubc.midp.mobilephoto.sms.SmsReceiverController");
		classes.add("ubc.midp.mobilephoto.sms.SmsSenderController");
		architecturalProblems.add(new ArchitecturalProblem("Ambiguous Interface", classes, packages));
		
		loadProblemWith1Class("Ambiguous Interface","ubc.midp.mobilephoto.core.ui.screens.PlayMediaScreen");
		
		classes=new Vector<>();
		packages=new Vector<>();
		classes.add("ubc.midp.mobilephoto.core.ui.datamodel.ImageAlbumData");
		classes.add("ubc.midp.mobilephoto.core.ui.datamodel.MusicAlbumData");
		architecturalProblems.add(new ArchitecturalProblem("Redundant Interface", classes, packages));
		
		loadViolationWith2Classes("Cyclic Dependency","ubc.midp.mobilephoto.core.ui.controller.MediaController", "ubc.midp.mobilephoto.core.ui.controller.MediaListController");
		loadViolationWith2Classes("Cyclic Dependency","ubc.midp.mobilephoto.core.ui.controller.MediaListController","ubc.midp.mobilephoto.core.ui.controller.MediaController");
		loadViolationWith2Classes("Cyclic Dependency","ubc.midp.mobilephoto.core.ui.controller.MediaController","ubc.midp.mobilephoto.core.ui.controller.MusicPlayController");
		loadViolationWith2Classes("Cyclic Dependency","ubc.midp.mobilephoto.core.ui.controller.MediaController","ubc.midp.mobilephoto.core.ui.controller.PhotoViewController");
		loadViolationWith2Classes("Cyclic Dependency","ubc.midp.mobilephoto.core.ui.controller.MediaController","ubc.midp.mobilephoto.core.ui.controller.PlayVideoController");
		loadViolationWith2Classes("Cyclic Dependency","ubc.midp.mobilephoto.core.ui.controller.MediaController","ubc.midp.mobilephoto.core.ui.controller.VideoCaptureController");
		loadViolationWith2Classes("Cyclic Dependency","ubc.midp.mobilephoto.core.ui.controller.SelectMediaController","ubc.midp.mobilephoto.core.ui.controller.BaseController");
		
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.MusicPlayController", "ubc.midp.mobilephoto.core.ui.screens.PlayMediaScreen");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.PhotoViewController", "ubc.midp.mobilephoto.core.ui.screens.CaptureVideoScreen");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.SelectMediaController","ubc.midp.mobilephoto.core.ui.controller.BaseController");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.datamodel.AlbumData", "ubc.midp.mobilephoto.core.ui.datamodel.MediaAccessor");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.datamodel.ImageAlbumData", "ubc.midp.mobilephoto.core.ui.datamodel.ImageMediaAccessor");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.MediaController", "ubc.midp.mobilephoto.core.ui.screens.PlayMediaScreen");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.MediaController", "ubc.midp.mobilephoto.core.ui.screens.PlayVideoScreen");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.MediaController", "ubc.midp.mobilephoto.sms.SmsSenderController");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.PhotoViewController", "ubc.midp.mobilephoto.core.ui.screens.AlbumListScreen");
		
	}
	private void loadHealthWatcher10() {
		loadProblemWith1Class("Conector Envy", "healthwatcher.business.RMIFacadeAdapter");		
		loadProblemWith1Class("Ambiguous Interface","healthwatcher.business.complaint.ComplaintRecord");
		loadProblemWith1Class("Ambiguous Interface","healthwatcher.data.mem.ComplaintRepositoryArray");
		
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.IFacadeRMITargetAdapter", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.RMIFacadeAdapter", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.RMIServletAdapter", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.GetDataForSearchByDiseaseType", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.GetDataForSearchByHealthUnit", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.GetDataForSearchBySpeciality", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertAnimalComplaint", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertDiseaseType", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertEmployee", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertFoodComplaint", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertHealthUnit", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertMedicalSpeciality", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertSpecialComplaint", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.InsertSymptom", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.Login", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.LoginMenu", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.SearchComplaintData", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.SearchDiseaseData", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.SearchHealthUnitsBySpecialty", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.SearchSpecialtiesByHealthUnit", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateComplaintData", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateComplaintList", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateComplaintSearch", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateEmployeeData", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateEmployeeSearch", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateHealthUnitData", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateHealthUnitList", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateHealthUnitSearch", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateMedicalSpecialityData", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateMedicalSpecialityList", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateMedicalSpecialitySearch", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateSymptomData", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateSymptomList", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.view.command.UpdateSymptomSearch", "healthwatcher.view.IFacade");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.HealthWatcherFacade", "healthwatcher.model.complaint.Complaint");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.HealthWatcherFacade", "healthwatcher.model.complaint.DiseaseType");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.HealthWatcherFacade", "healthwatcher.model.healthguide.HealthUnit");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.HealthWatcherFacade", "healthwatcher.model.employee.Employee");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.HealthWatcherFacade", "healthwatcher.model.complaint.Symptom");
		loadViolationWith2Classes("Architectural Violation","healthwatcher.business.HealthWatcherFacade", "healthwatcher.model.healthguide.MedicalSpeciality");
	}
	private void loadMobileMedia8() {
		Vector<String> classes=new Vector<>();
		Vector<String> packages=new Vector<>();
		classes.add("ubc.midp.mobilephoto.core.ui.controller.ControllerInterface");
		classes.add("ubc.midp.mobilephoto.core.ui.controller.AlbumController");
		classes.add("ubc.midp.mobilephoto.core.ui.controller.BaseController");
		classes.add("ubc.midp.mobilephoto.core.ui.controller.MediaController");
		classes.add("ubc.midp.mobilephoto.core.ui.controller.MediaListController");
		classes.add("ubc.midp.mobilephoto.core.ui.controller.MusicPlayController");
		classes.add("ubc.midp.mobilephoto.core.ui.controller.PhotoViewController");
		classes.add("ubc.midp.mobilephoto.core.ui.controller.PlayVideoController");
		classes.add("ubc.midp.mobilephoto.core.ui.controller.SelectMediaController");
		classes.add("ubc.midp.mobilephoto.sms.SmsReceiverController");
		classes.add("ubc.midp.mobilephoto.sms.SmsSenderController");
		architecturalProblems.add(new ArchitecturalProblem("Ambiguous Interface", classes, packages));
		
		
		loadProblemWith1Class("Ambiguous Interface","ubc.midp.mobilephoto.core.ui.screens.PlayMediaScreen");
		
		loadProblemWith1Class("Concern Overload", "lancs.midp.mobilephoto.lib.exceptions.ImageNotFoundException");
		loadProblemWith1Class("Concern Overload", "lancs.midp.mobilephoto.lib.exceptions.InvalidImageDataException");
		loadProblemWith1Class("Concern Overload", "lancs.midp.mobilephoto.lib.exceptions.PersistenceMechanismException");
		loadProblemWith1Class("Concern Overload", "lancs.midp.mobilephoto.lib.exceptions.UnavailablePhotoAlbumException");
		loadProblemWith1Class("Concern Overload", "ubc.midp.mobilephoto.core.ui.datamodel.ImageAlbumData");
		loadProblemWith1Class("Concern Overload", "ubc.midp.mobilephoto.core.ui.datamodel.MusicAlbumData");
		loadProblemWith1Class("Concern Overload", "ubc.midp.mobilephoto.core.ui.datamodel.MusicMediaAccessor");
		loadProblemWith1Class("Concern Overload", "ubc.midp.mobilephoto.core.ui.datamodel.VideoAlbumData");
		loadProblemWith1Class("Concern Overload", "ubc.midp.mobilephoto.core.util.MediaUtil");
		loadProblemWith1Class("Concern Overload", "ubc.midp.mobilephoto.core.util.MusicMediaUtil");
	
		loadViolationWith2Classes("Cyclic Dependency","ubc.midp.mobilephoto.core.ui.controller.MediaController", "ubc.midp.mobilephoto.core.ui.controller.MediaListController");
		loadViolationWith2Classes("Cyclic Dependency","ubc.midp.mobilephoto.core.ui.controller.AbstractController", "ubc.midp.mobilephoto.core.ui.controller.AlbumController");
		loadViolationWith2Classes("Cyclic Dependency","ubc.midp.mobilephoto.core.ui.controller.BaseController", "ubc.midp.mobilephoto.core.ui.controller.PhotoViewController");
		loadViolationWith2Classes("Cyclic Dependency","ubc.midp.mobilephoto.core.ui.controller.MusicPlayController", "ubc.midp.mobilephoto.core.ui.screens.PlayMediaScreen");
		loadViolationWith2Classes("Cyclic Dependency","ubc.midp.mobilephoto.core.ui.controller.PlayVideoController", "ubc.midp.mobilephoto.core.ui.screens.PlayVideoScreen");
		
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.MediaController", "ubc.midp.mobilephoto.core.ui.screens.CaptureVideoScreen");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.MediaController", "ubc.midp.mobilephoto.core.ui.screens.PlayMediaScreen");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.MediaController", "ubc.midp.mobilephoto.core.ui.screens.PlayVideoScreen");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.MediaController", "lancs.midp.mobilephoto.lib.exceptions.PersistenceMechanismException");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.MediaController", "ubc.midp.mobilephoto.sms.SmsSenderController");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.MusicPlayController", "lancs.midp.mobilephoto.lib.exceptions.PersistenceMechanismException");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.PhotoViewController", "ubc.midp.mobilephoto.core.ui.screens.AlbumListScreen");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.PhotoViewController", "ubc.midp.mobilephoto.core.ui.screens.CaptureVideoScreen");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.PhotoViewController", "ubc.midp.mobilephoto.core.ui.screens.PhotoViewScreen");
		loadViolationWith2Classes("Architectural Violation","ubc.midp.mobilephoto.core.ui.controller.PhotoViewController", "lancs.midp.mobilephoto.lib.exceptions.PersistenceMechanismException");
	}
	private void loadSubscribersDB24() {
		Vector<String> classes=new Vector<>();
		Vector<String> packages=new Vector<>();
		classes.add("bd.pojos.Person");
		architecturalProblems.add(new ArchitecturalProblem("Scattered functionality", classes, packages));
		
		classes=new Vector<>();
		packages=new Vector<>();
		classes.add("ui.search.personUtils.searchCriteria.SearchCriteria");
		classes.add("ui.search.SearchPerson");
		architecturalProblems.add(new ArchitecturalProblem("Ambiguous Interface", classes, packages));
		
		loadViolationWith2Classes("Architectural Violation","ui.add.AddDonation", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddEvent", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddLabel", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddPersonFrame", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddPersonToEvent", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.add.EditLabelsForPerson", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditDonation", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditEvents", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditLabels", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditPassword", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditPerson", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.ModifyEventInstanceDate", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.edit.ModifyEventName", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.events.CriteriaCreationPanelForEvent", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchDonationsResults", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchDonations", "bd.DataBaseManager");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.add.AddDonation");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddPersonFrame", "ui.search.Searchresults");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.add.AddPersonToEvent");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditDonation", "ui.search.SearchDonationsResults");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditPerson", "ui.search.Searchresults");
		loadViolationWith2Classes("Architectural Violation","ui.add.label.modifyAssociationField.SimpleDatePanelForLabel", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","bd.pojos.AssociatedField.AssociatedFieldValueForSearch", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","bd.pojos.AssociatedField.AssociatedFieldValueDateForSearch", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","bd.pojos.AssociatedField.AssociatedFieldValueStringForSearch", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.labels.internalPanels.SimpleFieldPanelForLabel", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.labels.CriteriaCreationPanelForLabel", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchDonations", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchDonationsResults", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.pojos.Event");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.events.CriteriaCreationPanelForEvent", "bd.pojos.Event");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.events.CriteriaCreationPanelForEvent", "bd.pojos.EventInstance");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.panels.labels.CriteriaCreationPanelForLabel", "bd.pojos.Label");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.pojos.Label");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "bd.pojos.Label");
		loadViolationWith2Classes("Architectural Violation","ui.search.SearchPerson", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.mailing.CreateLetterDialog", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.mailing.CreateLetterDialog", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditDonation", "ui.add.AddDonation");
		loadViolationWith2Classes("Architectural Violation","ui.add.AddPersonFrame", "ui.add.EditLabelsForPerson");
		loadViolationWith2Classes("Architectural Violation","ui.edit.EditPerson", "ui.add.AddPersonFrame");
		loadViolationWith2Classes("Architectural Violation","ui.search.Searchresults", "ui.mailing.CreateLetterDialog");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForDonation", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForDonation", "bd.pojos.Donation");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForEvent", "bd.pojos.EventInstance");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForEvent", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForEvent", "bd.pojos.Event");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaToFilter", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForLabel", "bd.pojos.AssociatedField");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForLabel", "bd.pojos.Person");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForLabel", "bd.pojos.AssociatedFieldValueForLabel");
		loadViolationWith2Classes("Architectural Violation","ui.search.personUtils.searchCriteria.SearchCriteriaForLabel", "bd.pojos.Label");
		
		
		//There are a lot more to add. Look at ArchitecturalSmellsSubscriberDB
	}
	private void loadProblemWith1Class(String problemName,String class1){
		Vector<String> classes=new Vector<>();
		Vector<String> packages=new Vector<>();
		classes.add(class1);
		
		architecturalProblems.add(new ArchitecturalProblem(problemName, classes, packages));
		
	}
	private void loadViolationWith2Classes(String problemName, String class1, String class2){
		Vector<String> classes=new Vector<>();
		Vector<String> packages=new Vector<>();
		classes.add(class1);
		classes.add(class2);
		architecturalProblems.add(new ArchitecturalProblem(problemName, classes, packages));
		
	}
	public void analyzeAccuracy(){
		Vector<Double> referenceRankingPosiionsWithTies=calculateReferenceRankingOnlyByRelationWithProblem();
		System.out.println(referenceRankingPosiionsWithTies);
		
		/*for (int i = 0; i < agglomerations.size(); i++) {
			System.out.println(agglomerations.get(i)+"\t"+referenceRankingPosiionsWithTies.get(i));
		}*/
		
		
		Vector<Double> currentRankingPositionWithTies=calculateCurrentRankingPositions();
		
		
		
		System.out.print(SpearmansCorrelation.calculateCorrelation(currentRankingPositionWithTies, referenceRankingPosiionsWithTies,RankingManagerForAgglomerations.getInstance().getRankingCalculator().getRanking(),agglomerations));
		referenceRankingPosiionsWithTies=calculateReferenceRankingByRelationAndOccurrenceWithProblem();
		
		/*for (int i = 0; i < agglomerations.size(); i++) {
			System.out.println(agglomerations.get(i)+"\t"+referenceRankingPosiionsWithTies.get(i));
		}*/
		
		System.out.print("\t"+SpearmansCorrelation.calculateCorrelation(currentRankingPositionWithTies, referenceRankingPosiionsWithTies,RankingManagerForAgglomerations.getInstance().getRankingCalculator().getRanking(),agglomerations));
		
		//calculatePrecisionAndRecall();
		
		
		//referenceRankingPosiionsWithTies=calculateReferenceRankingByRelationAndRelevanceAndOccurrenceWithProblem();
		//SpearmansCorrelation.calculateCorrelation(currentRankingPositionWithTies, referenceRankingPosiionsWithTies,RankingManagerForAgglomerations.getInstance().getRankingCalculator().getRanking(),agglomerations);
		System.out.println();
	}
	
	
	private void calculatePrecisionAndRecall() {
		
		
		Vector<DesignFlaw> ranking=RankingManagerForAgglomerations.getInstance().getRankingCalculator().getRanking();
		int[] k_movil={1,2,3,4,5,10,15,/*20,30,40,50*/};
		
	//Esta primera parte calcula el PrecisionAndRecall analizando con cuantos architectural problems estan relacionados los primeros k agglomerations 
		//Puede que no tenga sentido
	/**	for (int i = 0; i < k_movil.length; i++) {
			int numberOfArchitecturalProblemsForFirstKAgglomerations=getNumberOfArchitecturalProblemsForFirstKAgglomerations(k_movil[i],ranking);
		}*/
		
	//Esta segunda parte calcula el PrecisionAndRecall analizando si cada uno de los primeros k agglomerations esta relacionado con al menos un architectural problem
		int numberOfAgglomerationsWithAtLeatOneRelatedProblem=getNumberOfAgglomerationsProblemsWithAtLeatOneRelatedProblem(ranking);
		for (int i = 0; i < k_movil.length; i++) {
			int numberOfArchitecturalProblemsForFirstKAgglomerationsWithAtLeatOneRelatedProblem=getNumberOfArchitecturalProblemsForFirstKAgglomerationsWithAtLeatOneRelatedProblem(k_movil[i],ranking);
			
			double precision=(numberOfArchitecturalProblemsForFirstKAgglomerationsWithAtLeatOneRelatedProblem*1.0)/k_movil[i];
			double recall=(numberOfArchitecturalProblemsForFirstKAgglomerationsWithAtLeatOneRelatedProblem*1.0)/numberOfAgglomerationsWithAtLeatOneRelatedProblem;
			
			//System.out.println(k_movil[i]);
			System.out.print("\t"+precision);
			System.out.print("\t"+recall);
		}
	}
	private int getNumberOfAgglomerationsProblemsWithAtLeatOneRelatedProblem(
			Vector<DesignFlaw> ranking) {
		int ret=0;
		for (Iterator iterator = ranking.iterator(); iterator.hasNext();) {
			AgglomerationModel designFlaw = (AgglomerationModel) iterator.next();
			if(getArchitecturalProblemsRelatedWithAgglomeration(designFlaw).size()>0)
				ret=ret+1;
		}
		return ret;
	}
	private int getNumberOfArchitecturalProblemsForFirstKAgglomerationsWithAtLeatOneRelatedProblem(int k,
			Vector<DesignFlaw> ranking) {
		int ret=0;
		for (int i = 0; i < k; i++) {
			AgglomerationModel designFlaw = (AgglomerationModel) ranking.get(i);
			if(getArchitecturalProblemsRelatedWithAgglomeration(designFlaw).size()>0)
				ret=ret+1;
		}
		
		return ret;
	}
	/**private int getNumberOfArchitecturalProblemsForFirstKAgglomerations(int k,
			Vector<DesignFlaw> ranking) {
		Set<ArchitecturalProblem> ret= new HashSet<>();
		for (int i = 0; i < k; i++) {
			AgglomerationModel designFlaw = (AgglomerationModel) ranking.get(i);
			ret.addAll(getArchitecturalProblemsRelatedWithAgglomeration(designFlaw));
		}
		
		return ret.size();
	}*/
	private Vector<Double> calculateCurrentRankingPositions() {
		//Puedo hacer un metodo que me devuelva todas las posciones de un vector que tienen el mismo ranking. Devuelve un vector de vectores.
		Vector<Vector<Integer>> positionsWithEqualRanking=getAgglomerationsWithTheSameRanking(RankingManagerForAgglomerations.getInstance().getRankingCalculator());
		return generateRankingPositionsWithTies(positionsWithEqualRanking);
	}
	private Vector<Double> generateRankingPositionsWithTies(Vector<Vector<Integer>> positionsWithEqualRanking){
		Vector<Double> ret=new Vector<>();
		for (Iterator<Vector<Integer>> iterator = positionsWithEqualRanking.iterator(); iterator.hasNext();) {
			Vector<Integer> vector = (Vector<Integer>) iterator.next();
			double rankingSum=0;
			for (Iterator<Integer> iterator2 = vector.iterator(); iterator2.hasNext();) {
				Integer integer = (Integer) iterator2.next();
				rankingSum=rankingSum+integer;
			}
			for (int i = 0; i < vector.size(); i++) {
				ret.add(rankingSum/vector.size());
			}
		}
		
		
		
		return ret;
	}
	/** devuelva todas las posciones de un vector que tienen el mismo ranking. Devuelve un vector de vectores.
	 * 
	 * @param calculator
	 * @return
	 */
	private Vector<Vector<Integer>> getAgglomerationsWithTheSameRanking(RankingCalculator calculator){
		Vector<DesignFlaw> ranking=calculator.getRanking();
		Vector<Vector<Integer>> ret=new Vector<Vector<Integer>>();
		double previousValue=-1;
		for (int i = 0; i < ranking.size(); i++) {
			double currentValue=calculator.getRankingValue(ranking.get(i));
			if(currentValue==previousValue)
				ret.lastElement().add(i+1);
			else{
				previousValue=currentValue;
				Vector<Integer> positions=new Vector<>();
				positions.add(i+1);
				ret.add(positions);
			}
				
		}
		return ret;
	}
	
	
	private Vector<Double> calculateReferenceRankingOnlyByRelationWithProblem() {
		//Calculate the ranking
		Collections.sort(agglomerations, new ReferenceRankingOnlyByRelationWithProblem());
		
		
		//Generation of the values positions taking into account the ties
		boolean relatedToAgglomeration=true;
		int index=0;
		double positionSumRelated=0;
		if(agglomerations.size()>0)
			while (relatedToAgglomeration) {
				if(getArchitecturalProblemsRelatedWithAgglomeration(agglomerations.get(index)).size()>0){
					index++;
					positionSumRelated=positionSumRelated+index;
				}else
					relatedToAgglomeration=false;
			}		
		Vector<Double> ret=new Vector<>();
		for (int i = 0; i < index; i++) {//All the agglomerations related with an architectural problem are tied. 
			ret.add(positionSumRelated/(index));
		}
		double positionSumNotRelated=0;
		for (int i = index; i < agglomerations.size(); i++) {
			positionSumNotRelated=positionSumNotRelated+i+1;
		}
		for (int i = index; i < agglomerations.size(); i++) {
			ret.add(positionSumNotRelated/(agglomerations.size()-index));
		}
				
		return ret;
	}
	public class ReferenceRankingOnlyByRelationWithProblem implements Comparator<AgglomerationModel>{

		@Override
		public int compare(AgglomerationModel o1, AgglomerationModel o2) {
			//Si agglomeration o1 esta relacionada a un problema y o2 no devuelve -1
			//Si agglomeration o1 esta relacionada a un problema y o2 tambien devuelve 0
			//Si agglomeration o1 no esta relacionada a un problema y o2 si devuelve 1
			Set<ArchitecturalProblem> relatedProblemsO1=getArchitecturalProblemsRelatedWithAgglomeration(o1);
			Set<ArchitecturalProblem> relatedProblemsO2=getArchitecturalProblemsRelatedWithAgglomeration(o2);
			if(relatedProblemsO1.size()==relatedProblemsO2.size()||(relatedProblemsO1.size()>0 && relatedProblemsO2.size()>0))
				return 0;
			else if(relatedProblemsO1.size()>0 && relatedProblemsO2.size()==0)
				return -1;
			else if(relatedProblemsO1.size()==0 && relatedProblemsO2.size()>0)
				return 1;
			else 
				return 0;
		}
		
	}
	private Set<ArchitecturalProblem> getArchitecturalProblemsRelatedWithAgglomeration(AgglomerationModel agglomeration){
		
		Set<ArchitecturalProblem> ret= new HashSet<>();
		for (Iterator<CodeSmell> iterator = agglomeration.getCodeAnomalies().iterator(); iterator.hasNext();) {
			CodeSmell type = (CodeSmell) iterator.next();
			for (Iterator<ArchitecturalProblem> iterator2 = architecturalProblems.iterator(); iterator2.hasNext();) {
				ArchitecturalProblem problem = (ArchitecturalProblem) iterator2.next();
				if(problem.affectsAClass(type.getMainClassName()))
					ret.add(problem);
			}
		}
		return ret;
	}
	private Vector<Double> calculateReferenceRankingByRelationAndOccurrenceWithProblem() {
		//Calculate the ranking
		Collections.sort(agglomerations, new ReferenceRankingByRelationAndOccurrenceWithProblem());
		
		for (Iterator<AgglomerationModel> iterator = agglomerations.iterator(); iterator.hasNext();) {
			AgglomerationModel type = (AgglomerationModel) iterator.next();
			System.out.println(type+" "+getArchitecturalProblemsRelatedWithAgglomeration(type).size());
			//printSet(getArchitecturalProblemsRelatedWithAgglomeration(type));
		}
		
		//Generation of the values positions taking into account the ties
		Vector<Vector<Integer>> ret=new Vector<Vector<Integer>>();
		double previousValue=-1;
		for (int i = 0; i < agglomerations.size(); i++) {
			double currentValue=getArchitecturalProblemsRelatedWithAgglomeration(agglomerations.get(i)).size();
			if(currentValue==previousValue)
				ret.lastElement().add(i+1);
			else{
				previousValue=currentValue;
				Vector<Integer> positions=new Vector<>();
				positions.add(i+1);
				ret.add(positions);
			}
				
		}
		return generateRankingPositionsWithTies(ret);
		
	}
	public class ReferenceRankingByRelationAndOccurrenceWithProblem implements Comparator<AgglomerationModel>{

		@Override
		public int compare(AgglomerationModel o1, AgglomerationModel o2) {
			//Si agglomeration o1 esta relacionada a mas problemas que o2 devuelve -1
			//Si agglomeration o1 esta relacionada a la misma cantidad de problemas que o2 devuelve 0
			//Si agglomeration o1 esta relacionada a menos problemas que o2 devuelve 1
			Set<ArchitecturalProblem> relatedProblemsO1=getArchitecturalProblemsRelatedWithAgglomeration(o1);
			//System.out.println(o1);
			//printSet(relatedProblemsO1);
			Set<ArchitecturalProblem> relatedProblemsO2=getArchitecturalProblemsRelatedWithAgglomeration(o2);
			//System.out.println(o2);
			//printSet(relatedProblemsO2);
			//System.out.println(relatedProblemsO1.size()+" "+relatedProblemsO2.size());
			if(relatedProblemsO1.size()>relatedProblemsO2.size())
				return -1;
			else if(relatedProblemsO1.size()==relatedProblemsO2.size())
				return 0;
			else if(relatedProblemsO1.size()<relatedProblemsO2.size())
				return 1;
			else 
				return 0;
		}
		
	}
	private void printSet(Set<ArchitecturalProblem> problems){
		for (Iterator iterator = problems.iterator(); iterator.hasNext();) {
			ArchitecturalProblem architecturalProblem = (ArchitecturalProblem) iterator
					.next();
			System.out.println(architecturalProblem.getName()+" "+architecturalProblem.getClasses()+" "+architecturalProblem.getPackages());
		}
	}
}
