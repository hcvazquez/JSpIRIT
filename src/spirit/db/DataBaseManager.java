package spirit.db;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import spirit.core.smells.detectors.configurationByProject.BrainClassDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.BrainMethodDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.DataClassDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.DispersedCouplingDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.FeatureEnvyDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.GodClassDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.IntensiveCouplingDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.LongMethodDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.RefusedParentBequestDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.ShotgunSurgeryDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.TraditionBreakerDetectionConfiguration;
import spirit.priotization.criteria.AgglomerationEvolutionCriteria;
import spirit.priotization.criteria.AgglomerationRelevanceCriteria;
import spirit.priotization.criteria.AlphaCriteria;
import spirit.priotization.criteria.ArchitecturalConcernCriteria;
import spirit.priotization.criteria.ArchitecturalConcernCriteriaForSmellsNormalized;
import spirit.priotization.criteria.ArchitecturalConcernCriteriaWithImportance;
import spirit.priotization.criteria.BlueprintCriteria;
import spirit.priotization.criteria.HistoryBetaCriteria;
import spirit.priotization.criteria.HistoryENOMCriteria;
import spirit.priotization.criteria.HistoryLENOMCriteria;
import spirit.priotization.criteria.ModifiabilityScenariosCriteria;
import spirit.priotization.criteria.ModifiabilityScenariosForAgglomerationCriteria;
import spirit.priotization.criteria.ModifiabilityScenariosForSmellsNormalizedCriteria;
import spirit.priotization.criteria.SmellRelevanceCriteria;
import spirit.priotization.criteria.util.ArchitecturalConcern;
import spirit.priotization.criteria.util.ComponentOfBlueprint;
import spirit.priotization.criteria.util.ModifiabilityScenario;
import spirit.priotization.criteria.util.NOMHistoryProject;
import spirit.ui.views.agglomerations.AgglomerationConfigurationForProject;
import spirit.ui.views.criteriaConfiguration.util.SmellRelevance;

public class DataBaseManager {

	private static DataBaseManager instance;

	public static DataBaseManager getInstance() {
		if (instance == null)
			instance = new DataBaseManager();
		return instance;
	}

	private DataBaseManager() {
		HSQLServer.getInstance().startDatabase();

		/**
		 * Session session = HibernateUtil.getSessionFactory().openSession();
		 * session.createSQLQuery("DROP TABLE PACKAGES_ARCHCONCERN").executeUpdate();
		 * session.createSQLQuery("DROP TABLE CLASSES_ARCHCONCERN").executeUpdate();
		 * session.createSQLQuery("DROP TABLE ARCHITECTURALCONCERN").executeUpdate();
		 * 
		 * session.createSQLQuery("DROP TABLE MODIFIABILITYSCENARIO").executeUpdate();
		 * session.createSQLQuery("DROP TABLE
		 * MODIFIABILITYSCENARIOSCRITERIA").executeUpdate();
		 * 
		 * session.createSQLQuery("DROP TABLE CLASSES").executeUpdate();
		 * session.createSQLQuery("DROP TABLE PACKAGES").executeUpdate();
		 * 
		 * 
		 * session.createSQLQuery("DROP TABLE SCENARIO").executeUpdate(); Transaction
		 * transaction = null; try { transaction = session.beginTransaction();
		 * 
		 * transaction.commit(); }catch (HibernateException e) { transaction.rollback();
		 * e.printStackTrace(); }finally { session.close(); }
		 **/
	}

	public void saveEntity(Object objecToSave) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(objecToSave);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteEntity(Object objecToSave) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(objecToSave);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void saveOrUpdateEntity(Object objecToSave) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		/*
		 * session.createSQLQuery("DROP TABLE PROBLEMS").executeUpdate();
		 * session.createSQLQuery("DROP TABLE PROBLEMS_CRITERIAIMPORTANCE").
		 * executeUpdate();
		 * 
		 * session.createSQLQuery("DROP TABLE CONCERNCRITERIAIMPORTANCE").executeUpdate(
		 * ); session.createSQLQuery("DROP TABLE ARCHITECTURALCONCERNCRITERIA").
		 * executeUpdate();
		 */

		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(objecToSave);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public List<SmellRelevanceCriteria> listSmellRelevanceCriteria() {
		return (List<SmellRelevanceCriteria>) searchForTable(SmellRelevanceCriteria.class);
	}

	public List<HistoryBetaCriteria> listHistoryBetaCriteria() {
		return (List<HistoryBetaCriteria>) searchForTable(HistoryBetaCriteria.class);
	}

	public TraditionBreakerDetectionConfiguration getTraditionBreakerDetectionConfiguration(String projectName) {
		TraditionBreakerDetectionConfiguration ret = new TraditionBreakerDetectionConfiguration(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<TraditionBreakerDetectionConfiguration> list = session
				.createCriteria(TraditionBreakerDetectionConfiguration.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setAMW_Greater_AMWAvg(list.get(0).getAMW_Greater_AMWAvg());
			ret.setAMW_Greater_AMWAvg_2(list.get(0).getAMW_Greater_AMWAvg_2());
			ret.setNAS_GreaterEqual_NOMAvg(list.get(0).getNAS_GreaterEqual_NOMAvg());
			ret.setNOM_GreatherEqual_High(list.get(0).getNOM_GreatherEqual_High());
			ret.setNOM_GreatherEqual_HighDiv2(list.get(0).getNOM_GreatherEqual_HighDiv2());
			ret.setPNAS_GreaterEqual_TWO_THIRD(list.get(0).getPNAS_GreaterEqual_TWO_THIRD());
			ret.setWMC_Greater_VeryHighDiv2(list.get(0).getWMC_Greater_VeryHighDiv2());
			ret.setWMC_GreaterEqual_VeryHigh(list.get(0).getWMC_GreaterEqual_VeryHigh());
		}

		session.close();

		return ret;
	}

	public ShotgunSurgeryDetectionConfiguration getShotgunSurgeryDetectionConfiguration(String projectName) {
		ShotgunSurgeryDetectionConfiguration ret = new ShotgunSurgeryDetectionConfiguration(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ShotgunSurgeryDetectionConfiguration> list = session
				.createCriteria(ShotgunSurgeryDetectionConfiguration.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setCC_GreaterEqual_MANY(list.get(0).getCC_GreaterEqual_MANY());
			ret.setCM_Greater_SMemCap(list.get(0).getCM_Greater_SMemCap());
		}

		session.close();

		return ret;
	}

	public RefusedParentBequestDetectionConfiguration getRefusedParentBequestDetectionConfiguration(
			String projectName) {
		RefusedParentBequestDetectionConfiguration ret = new RefusedParentBequestDetectionConfiguration(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<RefusedParentBequestDetectionConfiguration> list = session
				.createCriteria(RefusedParentBequestDetectionConfiguration.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setAMW_Greater_AMWAvg(list.get(0).getAMW_Greater_AMWAvg());
			ret.setBOvR_Less_OneThird(list.get(0).getBOvR_Less_OneThird());
			ret.setBUR_Less_OneThird(list.get(0).getBUR_Less_OneThird());
			ret.setNOM_Greater_NOMAvg(list.get(0).getNOM_Greater_NOMAvg());
			ret.setNProtM_Greater_Few(list.get(0).getNProtM_Greater_Few());
			ret.setWMC_Greater_WMCAvg(list.get(0).getWMC_Greater_WMCAvg());
		}

		session.close();

		return ret;
	}

	public IntensiveCouplingDetectionConfiguration getIntensiveCouplingDetectionConfiguration(String projectName) {
		IntensiveCouplingDetectionConfiguration ret = new IntensiveCouplingDetectionConfiguration(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<IntensiveCouplingDetectionConfiguration> list = session
				.createCriteria(IntensiveCouplingDetectionConfiguration.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setCDISP_Less_Half(list.get(0).getCDISP_Less_Half());
			ret.setCDISP_Less_OneQuarter(list.get(0).getCDISP_Less_OneQuarter());
			ret.setCINT_Greater_Few(list.get(0).getCINT_Greater_Few());
			ret.setCINT_Greater_SMemCap(list.get(0).getCINT_Greater_SMemCap());
			ret.setMAXNESTING_Greater_SHALLOW(list.get(0).getMAXNESTING_Greater_SHALLOW());
		}

		session.close();

		return ret;
	}

	public GodClassDetectionConfiguration getGodClassDetectionConfiguration(String projectName) {
		GodClassDetectionConfiguration ret = new GodClassDetectionConfiguration(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<GodClassDetectionConfiguration> list = session.createCriteria(GodClassDetectionConfiguration.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setATFD_Greater_FEW(list.get(0).getATFD_Greater_FEW());
			ret.setTCC_Less_OneThird(list.get(0).getTCC_Less_OneThird());
			ret.setWMC_GreaterEqual_VeryHigh(list.get(0).getWMC_GreaterEqual_VeryHigh());
		}

		session.close();

		return ret;
	}

	public FeatureEnvyDetectionConfiguration getFeatureEnvyDetectionConfiguration(String projectName) {
		FeatureEnvyDetectionConfiguration ret = new FeatureEnvyDetectionConfiguration(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<FeatureEnvyDetectionConfiguration> list = session.createCriteria(FeatureEnvyDetectionConfiguration.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setATFD_Greater_Few(list.get(0).getATFD_Greater_Few());
			ret.setFDP_LessEqual_FEW(list.get(0).getFDP_LessEqual_FEW());
			ret.setLAA_Less_OneThird(list.get(0).getLAA_Less_OneThird());
		}

		session.close();

		return ret;
	}

	public DispersedCouplingDetectionConfiguration getDispersedCouplingDetectionConfiguration(String projectName) {
		DispersedCouplingDetectionConfiguration ret = new DispersedCouplingDetectionConfiguration(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<DispersedCouplingDetectionConfiguration> list = session
				.createCriteria(DispersedCouplingDetectionConfiguration.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setCDISP_GreaterEqual_Half(list.get(0).getCDISP_GreaterEqual_Half());
			ret.setCINT_Greater_SMemCap(list.get(0).getCINT_Greater_SMemCap());
			ret.setMAXNESTING_Greater_Shallow(list.get(0).getMAXNESTING_Greater_Shallow());
		}

		session.close();

		return ret;
	}

	public DataClassDetectionConfiguration getDataClassDetectionConfiguration(String projectName) {
		DataClassDetectionConfiguration ret = new DataClassDetectionConfiguration(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<DataClassDetectionConfiguration> list = session.createCriteria(DataClassDetectionConfiguration.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setNOAP_SOAP_Greater_Few(list.get(0).getNOAP_SOAP_Greater_Few());
			ret.setNOAP_SOAP_Greater_Many(list.get(0).getNOAP_SOAP_Greater_Many());
			ret.setWMC_Less_High(list.get(0).getWMC_Less_High());
			ret.setWMC_Less_VeryHigh(list.get(0).getWMC_Less_VeryHigh());
			ret.setWOC_Less_OneThird(list.get(0).getWOC_Less_OneThird());
		}

		session.close();

		return ret;
	}

	public BrainMethodDetectionConfiguration getBrainMethodDetectionConfiguration(String projectName) {
		BrainMethodDetectionConfiguration ret = new BrainMethodDetectionConfiguration(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<BrainMethodDetectionConfiguration> list = session.createCriteria(BrainMethodDetectionConfiguration.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setLOC_Greater_VeryHigh(list.get(0).getLOC_Greater_VeryHigh());
			ret.setMAXNESTING_GreaterEqual_DEEP(list.get(0).getMAXNESTING_GreaterEqual_DEEP());
			ret.setNOF_GreaterEqual_SMemCap(list.get(0).getNOF_GreaterEqual_SMemCap());
			ret.setWMC_GreaterEqual_Many(list.get(0).getWMC_GreaterEqual_Many());
		}

		session.close();

		return ret;
	}

	public LongMethodDetectionConfiguration getLongMethodDetectionConfiguration(String projectName) {
		LongMethodDetectionConfiguration ret = new LongMethodDetectionConfiguration(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<LongMethodDetectionConfiguration> list = session.createCriteria(LongMethodDetectionConfiguration.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setMLOC_VERY_HIGH(list.get(0).getMLOC_VERY_HIGH());
		}

		session.close();

		return ret;
	}

	public BrainClassDetectionConfiguration getBrainClassDetectionConfiguration(String projectName) {
		BrainClassDetectionConfiguration ret = new BrainClassDetectionConfiguration(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<BrainClassDetectionConfiguration> list = session.createCriteria(BrainClassDetectionConfiguration.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setLOC_GreaterEqual_2xVeryHigh(list.get(0).getLOC_GreaterEqual_2xVeryHigh());
			ret.setLOC_GreaterEqual_VeryHigh(list.get(0).getLOC_GreaterEqual_VeryHigh());
			ret.setTCC_Less_Half(list.get(0).getTCC_Less_Half());
			ret.setWMC_GreaterEqual_2xVeryHigh(list.get(0).getWMC_GreaterEqual_2xVeryHigh());
			ret.setWMC_GreaterEqual_VeryHigh(list.get(0).getWMC_GreaterEqual_VeryHigh());
		}

		session.close();

		return ret;
	}

	/**
	 * Return the SmellRelevanceCriteria for the project name. If none instance is
	 * found in the db, it returns a new instance
	 * 
	 * @param projectName
	 * @return
	 */
	public AgglomerationConfigurationForProject getAgglomerationConfigurationForProject(String projectName) {
		AgglomerationConfigurationForProject ret = new AgglomerationConfigurationForProject(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<AgglomerationConfigurationForProject> list = session
				.createCriteria(AgglomerationConfigurationForProject.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setHierarchicalThreshold(list.get(0).getHierarchicalThreshold());
			ret.setIntraComponentThreshold(list.get(0).getIntraComponentThreshold());
			ret.setIntraClassThreshold(list.get(0).getIntraClassThreshold());
			ret.setIntraMethodThreshold(list.get(0).getIntraMethodThreshold());
			ret.setStructureSource(list.get(0).getStructureSource());
		}

		session.close();

		return ret;
	}

	/**
	 * Return the HistoryBetaCriteria for the project name. If none instance is
	 * found in the db, it returns a new instance
	 * 
	 * @param projectName
	 * @return
	 */
	public HistoryBetaCriteria getHistoryBetaCriteriaForProject(String projectName) {
		HistoryBetaCriteria ret = new HistoryBetaCriteria(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<HistoryBetaCriteria> list = session.createCriteria(HistoryBetaCriteria.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());

		}

		session.close();

		return ret;
	}

	/**
	 * Return the HistoryBetaCriteria for the project name. If none instance is
	 * found in the db, it returns a new instance
	 * 
	 * @param projectName
	 * @return
	 */
	public HistoryLENOMCriteria getHistoryLENOMCriteriaForProject(String projectName) {
		HistoryLENOMCriteria ret = new HistoryLENOMCriteria(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<HistoryLENOMCriteria> list = session.createCriteria(HistoryLENOMCriteria.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());

		}

		session.close();

		return ret;
	}

	/**
	 * Return the HistoryBetaCriteria for the project name. If none instance is
	 * found in the db, it returns a new instance
	 * 
	 * @param projectName
	 * @return
	 */
	public HistoryENOMCriteria getHistoryENOMCriteriaForProject(String projectName) {
		HistoryENOMCriteria ret = new HistoryENOMCriteria(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<HistoryENOMCriteria> list = session.createCriteria(HistoryENOMCriteria.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());

		}

		session.close();

		return ret;
	}

	/**
	 * Return the HistoryBetaCriteria for the project name. If none instance is
	 * found in the db, it returns a new instance
	 * 
	 * @param projectName
	 * @return
	 */
	public AlphaCriteria getAlphaCriteriaForProject(String projectName) {
		AlphaCriteria ret = new AlphaCriteria(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<AlphaCriteria> list = session.createCriteria(AlphaCriteria.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setAlpha(list.get(0).getAlpha());
		}

		session.close();

		return ret;
	}

	/**
	 * Return the ModifiabilityScenariosCriteria for the project name. If none
	 * instance is found in the db, it returns a new instance
	 * 
	 * @param projectName
	 * @return
	 */
	public ModifiabilityScenariosCriteria getModifiabilityScenariosCriteriaForProject(String projectName) {
		ModifiabilityScenariosCriteria ret = new ModifiabilityScenariosCriteria(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ModifiabilityScenariosCriteria> list = session.createCriteria(ModifiabilityScenariosCriteria.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setProjectName(projectName);
		}

		session.close();

		return ret;
	}

	public NOMHistoryProject getNOMHistoryProjectForProject(String projectName) {
		NOMHistoryProject ret = new NOMHistoryProject();
		ret.setProjectName(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<NOMHistoryProject> list = session.createCriteria(NOMHistoryProject.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setProjectName(projectName);
			ret.setNomHistory(list.get(0).getNomHistory());
		}

		session.close();

		return ret;
	}

	public ArchitecturalConcernCriteria getArchitecturalProblemCriteriaForProject(String projectName) {
		ArchitecturalConcernCriteria ret = new ArchitecturalConcernCriteria(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ArchitecturalConcernCriteria> list = session.createCriteria(ArchitecturalConcernCriteria.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setProjectName(projectName);
		}

		session.close();

		return ret;
	}

	public List<ArchitecturalConcern> getArchitecturalConcernsForProject(String projectName) {
		ArchitecturalConcern ret = new ArchitecturalConcern();
		ret.setProjectName(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ArchitecturalConcern> list = session.createCriteria(ArchitecturalConcern.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		session.close();
		return list;
	}

	public List<ComponentOfBlueprint> getComponentsOfBlueprintForProject(String projectName) {
		ComponentOfBlueprint ret = new ComponentOfBlueprint();
		ret.setProjectName(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ComponentOfBlueprint> list = session.createCriteria(ComponentOfBlueprint.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		session.close();
		return list;
	}

	public List<ComponentOfBlueprint> getComponentOfBlueprintForProject(String projectName) {
		ComponentOfBlueprint ret = new ComponentOfBlueprint();
		ret.setProjectName(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ComponentOfBlueprint> list = session.createCriteria(ComponentOfBlueprint.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		session.close();
		return list;
	}

	public List<ModifiabilityScenario> getModifiabilityScenariosForProject(String projectName) {
		ModifiabilityScenario ret = new ModifiabilityScenario();
		ret.setProjectName(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ModifiabilityScenario> list = session.createCriteria(ModifiabilityScenario.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		session.close();
		return list;
	}

	public ArchitecturalConcernCriteriaWithImportance getArchitecturalProblemCriteriaWithImportanceForProject(
			String projectName) {
		ArchitecturalConcernCriteriaWithImportance ret = new ArchitecturalConcernCriteriaWithImportance(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ArchitecturalConcernCriteriaWithImportance> list = session
				.createCriteria(ArchitecturalConcernCriteriaWithImportance.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setProjectName(projectName);
		}

		session.close();

		return ret;
	}

	public ModifiabilityScenariosForAgglomerationCriteria getModifiabilityScenariosForAgglomerationCriteriaForProject(
			String projectName) {
		ModifiabilityScenariosForAgglomerationCriteria ret = new ModifiabilityScenariosForAgglomerationCriteria(
				projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ModifiabilityScenariosForAgglomerationCriteria> list = session
				.createCriteria(ModifiabilityScenariosForAgglomerationCriteria.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setProjectName(projectName);
		}

		session.close();

		return ret;
	}

	private List<?> searchForTable(Class<?> aClass) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Criteria crit = session.createCriteria(aClass);

		List<?> results = crit.list();
		session.close();
		return results;
	}

	/*
	 * public void deleteHistoryBetaCriteria(HistoryBetaCriteria type){ Session
	 * session = HibernateUtil.getSessionFactory().openSession(); Transaction
	 * transaction = null; try { transaction = session.beginTransaction();
	 * HistoryBetaCriteria criteria = (HistoryBetaCriteria)
	 * session.get(HistoryBetaCriteria.class, type.getId()); for
	 * (Iterator<NOMHistoryOfAVersion> iterator =
	 * criteria.getNomHistory().iterator(); iterator.hasNext();) {
	 * NOMHistoryOfAVersion type2 = (NOMHistoryOfAVersion) iterator.next();
	 * 
	 * 
	 * for (Iterator<NOMOfAClass> iterator2 = type2.getNomValues().iterator();
	 * iterator2.hasNext();) { NOMOfAClass type3=(NOMOfAClass) iterator2.next();
	 * session.delete(type3); }
	 * 
	 * session.delete(type2); } session.delete(criteria); transaction.commit(); }
	 * catch (HibernateException e) { transaction.rollback(); e.printStackTrace(); }
	 * finally { session.close(); } }
	 */
	public void deleteSmellRelevanceCriteria(SmellRelevanceCriteria type) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			SmellRelevanceCriteria criteria = (SmellRelevanceCriteria) session.get(SmellRelevanceCriteria.class,
					type.getId());
			for (Iterator<SmellRelevance> iterator = criteria.getRelevances().iterator(); iterator.hasNext();) {
				SmellRelevance type2 = (SmellRelevance) iterator.next();
				session.delete(type2);
			}
			session.delete(criteria);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public SmellRelevanceCriteria getSmellRelevanceCriteriaForProject(String projectName) {
		SmellRelevanceCriteria ret = new SmellRelevanceCriteria(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<SmellRelevanceCriteria> list = session.createCriteria(SmellRelevanceCriteria.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setRelevances(list.get(0).getRelevances());
		}

		session.close();

		return ret;
	}

	public AgglomerationRelevanceCriteria getAgglomerationRelevanceCriteriaForProject(String projectName) {
		AgglomerationRelevanceCriteria ret = new AgglomerationRelevanceCriteria(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<AgglomerationRelevanceCriteria> list = session.createCriteria(AgglomerationRelevanceCriteria.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		// Chequear que haya al menos un ret y armarlo bien
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setRelevances(list.get(0).getRelevances());
		}

		session.close();

		return ret;
	}

	public AgglomerationEvolutionCriteria getAgglomerationEvolutionCriteriaForProject(String projectName) {
		AgglomerationEvolutionCriteria ret = new AgglomerationEvolutionCriteria(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<AgglomerationEvolutionCriteria> list = session.createCriteria(AgglomerationEvolutionCriteria.class)
				.add(Restrictions.eq("projectName", projectName)).list();
		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setInitialVersionName(list.get(0).getInitialVersionName());
			ret.setIntermediaryVersionName(list.get(0).getIntermediaryVersionName());
		}

		session.close();

		return ret;
	}

	public ModifiabilityScenariosForSmellsNormalizedCriteria getmodifiabilityScenariosForSmellsNormalizedCriteriaForProject(
			String projectName) {
		ModifiabilityScenariosForSmellsNormalizedCriteria ret = new ModifiabilityScenariosForSmellsNormalizedCriteria(
				projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ModifiabilityScenariosForSmellsNormalizedCriteria> list = session
				.createCriteria(ModifiabilityScenariosForSmellsNormalizedCriteria.class)
				.add(Restrictions.eq("projectName", projectName)).list();

		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setProjectName(projectName);
		}

		session.close();

		return ret;
	}

	public ArchitecturalConcernCriteriaForSmellsNormalized getArchitecturalConcernCriteriaForSmellsNormalizedForProject(
			String projectName) {
		ArchitecturalConcernCriteriaForSmellsNormalized ret = new ArchitecturalConcernCriteriaForSmellsNormalized(
				projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ArchitecturalConcernCriteriaForSmellsNormalized> list = session
				.createCriteria(ArchitecturalConcernCriteriaForSmellsNormalized.class)
				.add(Restrictions.eq("projectName", projectName)).list();

		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setProjectName(projectName);
		}

		session.close();

		return ret;
	}

	public BlueprintCriteria getBlueprintCriteriaForSmellsNormalizedForProject(String projectName) {
		BlueprintCriteria ret = new BlueprintCriteria(projectName);
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<BlueprintCriteria> list = session.createCriteria(BlueprintCriteria.class)
				.add(Restrictions.eq("projectName", projectName)).list();

		if (list.size() > 0) {
			ret.setId(list.get(0).getId());
			ret.setProjectName(projectName);
		}

		session.close();

		return ret;
	}
}
