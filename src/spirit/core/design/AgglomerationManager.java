package spirit.core.design;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.core.agglomerations.builtin.strategies.HierarchicalStrategy;
import spirit.core.agglomerations.builtin.strategies.IntraClassStrategy;
import spirit.core.agglomerations.builtin.strategies.IntraComponentStrategy;
import spirit.core.agglomerations.builtin.strategies.IntraMethodStrategy;
import spirit.core.agglomerations.extensionPoints.AgglomerationStrategy;
import spirit.core.agglomerations.model.Project;
import spirit.core.agglomerations.model.projectBuilders.ProjectBuilder;
import spirit.core.agglomerations.model.projectBuilders.ProjectBuilderFactory;
import spirit.ui.views.agglomerations.TextMarkerUtil;

public class AgglomerationManager {
	
	private static AgglomerationManager instance;
	private final Set<AgglomerationStrategy<? extends AgglomerationModel>> agglomerationStrategies;
	Map<String, Map<String, Set<? extends AgglomerationModel>>> agglomerationsPerTypePerProject = new HashMap<String, Map<String, Set<? extends AgglomerationModel>>>();
	private String currentProject = null; 
	
	
	public static AgglomerationManager getInstance(){
		if(instance==null)
			instance=new AgglomerationManager();
		return instance;
	}
	
	private AgglomerationManager() {
		agglomerationStrategies = new HashSet<AgglomerationStrategy<? extends AgglomerationModel>>();
		agglomerationStrategies.add(new IntraMethodStrategy());
		agglomerationStrategies.add(new IntraClassStrategy());
		agglomerationStrategies.add(new IntraComponentStrategy());
		agglomerationStrategies.add(new HierarchicalStrategy());
	}
	
	public void setCurrentProject(String eclipseProjectName) {
		currentProject = eclipseProjectName;
	}
	
	public void detectAglomerations(IProject eclipseProject){
		Project project = buildProject(eclipseProject);
		agglomerationsPerTypePerProject.put(eclipseProject.getFullPath().toString(), executeBultinDetectors(project));
	}
	
	private Project buildProject(IProject eclipseProject) {
		ProjectBuilder projectBuilder = ProjectBuilderFactory.get(eclipseProject);
		Project project = projectBuilder.parse();
		return project;
	}

	private Map<String, Set<? extends AgglomerationModel>> executeBultinDetectors(Project project) {
		Map<String, Set<? extends AgglomerationModel>> agglomerationsPerType = new HashMap<String, Set<? extends AgglomerationModel>>();
		
		TextMarkerUtil.deleteMarkers(project.getJavaProject().getResource());
		
		for (AgglomerationStrategy<? extends AgglomerationModel> strategy : agglomerationStrategies) {
			Set<? extends AgglomerationModel> agglomerations = strategy.findAndMarkAgglomerations(project);
			agglomerationsPerType.put(strategy.name(), agglomerations);
		}
		
		return agglomerationsPerType;
	}

	public Set<AgglomerationModel> getResults(String eclipseProjectName) {
		Set<AgglomerationModel> agglomerations = new HashSet<AgglomerationModel>();
		for (Set<? extends AgglomerationModel> subSet : getAgglomerationsOfProject(eclipseProjectName)) {
			agglomerations.addAll(subSet);
		}
		
		return agglomerations;
	}

	private Collection<Set<? extends AgglomerationModel>> getAgglomerationsOfProject(String eclipseProjectName) {
		Map<String, Set<? extends AgglomerationModel>> agglomerationsPerType = agglomerationsPerTypePerProject.get(eclipseProjectName);
		if(agglomerationsPerType!=null)
			return agglomerationsPerType.values();
		else
			return new HashSet();
	}

	public Set<AgglomerationModel> getResultsForCurrentProject() {
		if (currentProject != null && !currentProject.isEmpty()) {
			return getResults(currentProject);
		} else {
			System.err.println("Unable to get results for current project. Current project is undefined.");
			return new HashSet<AgglomerationModel>();
		}
	}
}
