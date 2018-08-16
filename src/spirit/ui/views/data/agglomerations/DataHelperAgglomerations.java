package spirit.ui.views.data.agglomerations;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.jgrapht.graph.DefaultEdge;

import spirit.core.agglomerations.AgglomerationModel;
import spirit.core.agglomerations.HierarchicalAgglomeration;
import spirit.core.agglomerations.IntraClassAgglomeration;
import spirit.core.agglomerations.IntraComponentAgglomeration;
import spirit.core.design.AgglomerationManager;
import spirit.core.smells.CodeSmell;
import spirit.dependencies.PackageVisitor;
import spirit.priotization.RankingManagerForAgglomerations;
import spirit.priotization.RankingManagerForSmells;
import spirit.priotization.rankings.RankingCalculator;

public class DataHelperAgglomerations {
	
	private static JsonFactory jFactory = new JsonFactory();
	private static final HashMap<String,Integer> smellID;
	static
	{
		smellID = new HashMap<String,Integer>();
		smellID.put("Shotgun Surgery",0);
		smellID.put("Feature Envy",1);
		smellID.put("Dispersed Coupling",2);
		smellID.put("God Class",3);
		smellID.put("Refused Parent Bequest",4);
		smellID.put("Brain Class",5);
		smellID.put("Brain Method",6);
		smellID.put("Data Class",7);
		smellID.put("Intensive Coupling",8);
		smellID.put("Tradition Breaker",9);
	}	
	/**
	 * IntraComponent Agglomeration Algorithms
	 */
	private static String getAffectedPackage(AgglomerationModel a) {
		Iterator<CodeSmell> it = ((AgglomerationModel) a).getCodeAnomalies().iterator();
		CodeSmell cs = it.next();
		return cs.getMainClassName().substring(0, cs.getMainClassName().lastIndexOf("."));
	}
	
	private static HashMap<String, List<IntraComponentAgglomeration>> groupByPackage() {
		HashMap<String, List<IntraComponentAgglomeration>> agglomerationsByPackage = new HashMap<String, List<IntraComponentAgglomeration>>();
		for(AgglomerationModel a : AgglomerationManager.getInstance().getResultsForCurrentProject())
			if(a.getTopologyName().equals(IntraComponentAgglomeration.NAME)) {
				String pack = getAffectedPackage(a);
				if(agglomerationsByPackage.get(pack) == null) {
					List<IntraComponentAgglomeration> list = new LinkedList<IntraComponentAgglomeration>();
					list.add((IntraComponentAgglomeration) a);
					agglomerationsByPackage.put(pack, list);
				} else
					agglomerationsByPackage.get(pack).add((IntraComponentAgglomeration) a);
			}
		return agglomerationsByPackage;
	}
	
	public static String getIntracomponentAgglomerationsData() {
		HashMap<String, List<IntraComponentAgglomeration>> map = groupByPackage();		
		
		//Serialization
		StringWriter writer = new StringWriter(); 		
		try {
			JsonGenerator jGenerator = jFactory.createJsonGenerator(writer);
			jGenerator.writeStartObject();
			jGenerator.writeStringField("name", "Agglomerations");
			jGenerator.writeArrayFieldStart("children");
			//<body>
			for(Entry<String, List<IntraComponentAgglomeration>> entry : map.entrySet()) {
				int count = 0;
				jGenerator.writeStartObject();
				jGenerator.writeStringField("name", entry.getKey()); //Package name
				jGenerator.writeStringField("order", String.valueOf(calculateOrder(entry.getValue())));
				jGenerator.writeArrayFieldStart("children");				
				for(IntraComponentAgglomeration value : entry.getValue()) {
					HashMap<Integer, List<CodeSmell>> groupedSmells = groupSmells(value);									
					for(Integer id : groupedSmells.keySet()) {
						jGenerator.writeStartObject();
						jGenerator.writeStringField("name", value.NAME + "*" + value.getAnomalyName() + "*" + value.getComponent().getName() + "#" + String.valueOf(RankingManagerForAgglomerations.getInstance().getRankingCalculator().getRankingPosition(value))); //Dashed boundry						
						jGenerator.writeArrayFieldStart("children");
						for(CodeSmell smell : groupedSmells.get(id)) {
							count++;
							jGenerator.writeStartObject();
							jGenerator.writeStringField("groupid", String.valueOf(id));
							jGenerator.writeStringField("name", smell.getKindOfSmellName() + "@" + smell.getElementName() + "#" + RankingManagerForSmells.getInstance().getRankingCalculator().getRankingPosition(smell));
							jGenerator.writeStringField("size", "2");
							jGenerator.writeEndObject();
						}
						jGenerator.writeEndArray();
						jGenerator.writeEndObject();
					}					
				}
				// Para agrandar los paquetes con pocos smells
				if(count <= 3)										
						addEmptySpace(count,jGenerator);
				jGenerator.writeEndArray();
				jGenerator.writeEndObject();
			}
			//</body>
			jGenerator.writeEndArray();
			jGenerator.writeEndObject();
			jGenerator.flush();
			jGenerator.close();
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}						
		return writer.toString();
		
	}

	private static void addEmptySpace(int count, JsonGenerator jGenerator) throws JsonGenerationException, IOException {
		jGenerator.writeStartObject();
		jGenerator.writeStringField("groupid", "10");
		jGenerator.writeStringField("name", "");
		jGenerator.writeStringField("size", String.valueOf(3*count));
		jGenerator.writeArrayFieldStart("children");
		jGenerator.writeEndArray();
		jGenerator.writeEndObject();
		
	}

	private static int calculateOrder(List<IntraComponentAgglomeration> value) {
		int order = 0;
		for(IntraComponentAgglomeration a : value)
			order+= a.getCodeAnomalies().size();
		return order;
	}

	private static HashMap<Integer, List<CodeSmell>> groupSmells(AgglomerationModel value) {
		HashMap<Integer, List<CodeSmell>> map = new HashMap<Integer, List<CodeSmell>>(); 
		for(CodeSmell smell : value.getCodeAnomalies()) {
			int id = smellID.get(smell.getKindOfSmellName());			
			if(map.get(id) == null) {
				List<CodeSmell> list = new LinkedList<CodeSmell>();
				list.add(smell);
				map.put(id, list);				
			} else
				map.get(id).add(smell);
		}
		return map;
	}	
	
	/**
	 * Package Dependency Algorithms
	 */
	
	public static String getPackageRelationships() {
		Set<DefaultEdge> edges = PackageVisitor.getInstance().getPackageGraph().getEdges();
		Iterator<DefaultEdge> it = edges.iterator();
		HashMap<String,List<String>> dependencies = new HashMap<String,List<String>>(); 
		while(it.hasNext()) {
			DefaultEdge edge = it.next();
			String str = edge.toString().replace("(", "").replace(")", "");
			String source = str.split(":")[0].trim();
			String target = str.split(":")[1].trim();
			if(dependencies.get(source) == null) {
				List<String> list = new LinkedList<String>();
				list.add(target);
				dependencies.put(source, list);
			} else
				dependencies.get(source).add(target);
		}				
		//Add packages with no dependencies		
		Set<String> vertexes = PackageVisitor.getInstance().getPackageGraph().vertexes();
		for(String vertex : vertexes)
			if(dependencies.get(vertex) == null)				
				dependencies.put(vertex, new LinkedList<String>());
		
		//Serialization
		StringWriter writer = new StringWriter(); 		
		try {
			JsonGenerator jGenerator = jFactory.createJsonGenerator(writer);
			jGenerator.writeStartArray();
			for(Entry<String,List<String>> entry : dependencies.entrySet()) {
				jGenerator.writeStartObject();
				jGenerator.writeStringField("name", entry.getKey());
				jGenerator.writeArrayFieldStart("dependencies");
				List<String> targets = entry.getValue();
				for(String t : targets)
					jGenerator.writeString(t);
				jGenerator.writeEndArray();
				jGenerator.writeEndObject();
			}
			jGenerator.writeEndArray();
			jGenerator.flush();
			jGenerator.close();
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}						
		return writer.toString();
	}
	
	
	/**
	 * Hierarchical Agglomerations Algorithms
	 */
	
	private static HashMap<String, List<HierarchicalAgglomeration>> groupByPackageHierarchical() {
		HashMap<String, List<HierarchicalAgglomeration>> agglomerationsByPackage = new HashMap<String, List<HierarchicalAgglomeration>>();
		for(AgglomerationModel a : AgglomerationManager.getInstance().getResultsForCurrentProject())
			if(a.getTopologyName().equals(HierarchicalAgglomeration.NAME)) {
				String pack = getAffectedPackage(a);
				if(agglomerationsByPackage.get(pack) == null) {
					List<HierarchicalAgglomeration> list = new LinkedList<HierarchicalAgglomeration>();
					list.add((HierarchicalAgglomeration) a);
					agglomerationsByPackage.put(pack, list);
				} else
					agglomerationsByPackage.get(pack).add((HierarchicalAgglomeration) a);
			}
		return agglomerationsByPackage;
	}
	
	public static String getHierarchicalAgglomerationsData() {
		HashMap<String, List<HierarchicalAgglomeration>> map = groupByPackageHierarchical();		
		
		//Serialization
		StringWriter writer = new StringWriter(); 		
		try {
			JsonGenerator jGenerator = jFactory.createJsonGenerator(writer);
			jGenerator.writeStartArray();			
			for(Entry<String, List<HierarchicalAgglomeration>> entry : map.entrySet()) {
				for(HierarchicalAgglomeration h : entry.getValue()) {
					Set<String> list = getAffectedPackages(entry.getKey(), h);
					if(!list.isEmpty()) {
						jGenerator.writeStartArray();
						for(String p : list)						
							jGenerator.writeString(p);
						jGenerator.writeEndArray();
					}
				}
			}
			jGenerator.writeEndArray();
				
			jGenerator.flush();
			jGenerator.close();
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}						
		return writer.toString();
	}	

	private static Set<String> getAffectedPackages(String origin, HierarchicalAgglomeration value) {
		Set<String> output = new HashSet<String>();
		Iterator<CodeSmell> it = value.getCodeAnomalies().iterator();
		while(it.hasNext()) {
			CodeSmell cs = it.next();
			output.add(cs.getKindOfSmellName() + "@" + cs.getElementName() + "#" + RankingManagerForSmells.getInstance().getRankingCalculator().getRankingPosition(cs));
		}
		return output;		
	}
	
	/**
	 * IntraClass Agglomerations Algorithms
	 */
	
	private static HashMap<String, List<IntraClassAgglomeration>> groupByPackageIntraClass() {
		HashMap<String, List<IntraClassAgglomeration>> agglomerationsByPackage = new HashMap<String, List<IntraClassAgglomeration>>();
		for(AgglomerationModel a : AgglomerationManager.getInstance().getResultsForCurrentProject())
			if(a.getTopologyName().equals(IntraClassAgglomeration.NAME)) {
				String pack = getAffectedPackage(a);
				if(agglomerationsByPackage.get(pack) == null) {
					List<IntraClassAgglomeration> list = new LinkedList<IntraClassAgglomeration>();
					list.add((IntraClassAgglomeration) a);
					agglomerationsByPackage.put(pack, list);
				} else
					agglomerationsByPackage.get(pack).add((IntraClassAgglomeration) a);
			}
		return agglomerationsByPackage;
	}
	
	public static String getIntraClassAgglomerationsData() {
		HashMap<String, List<IntraClassAgglomeration>> map = groupByPackageIntraClass();
		
		//Serialization
				StringWriter writer = new StringWriter(); 		
				try {
					JsonGenerator jGenerator = jFactory.createJsonGenerator(writer);
					jGenerator.writeStartObject();
					jGenerator.writeStringField("name", "root");
					jGenerator.writeNumberField("groupid", -3);
					jGenerator.writeArrayFieldStart("children");
					//<body>
					for(Entry<String, List<IntraClassAgglomeration>> entry : map.entrySet()) {
						jGenerator.writeStartObject();
						jGenerator.writeNumberField("groupid", -2);
						jGenerator.writeStringField("name", entry.getKey()); //Package name
						jGenerator.writeArrayFieldStart("children");				
						for(IntraClassAgglomeration value : entry.getValue()) {
							HashMap<Integer, List<CodeSmell>> groupedSmells = groupSmells(value);
							jGenerator.writeStartObject();									
							jGenerator.writeNumberField("groupid", -1);
							jGenerator.writeStringField("name", value.NAME + "@" + value.toString() + "#" + String.valueOf(RankingManagerForAgglomerations.getInstance().getRankingCalculator().getRankingPosition(value))); //Class name
							jGenerator.writeArrayFieldStart("children");
							for(Integer id : groupedSmells.keySet())
								for(CodeSmell smell : groupedSmells.get(id)) {
									jGenerator.writeStartObject();
									jGenerator.writeNumberField("groupid", id);
									jGenerator.writeStringField("packagename", entry.getKey());
									jGenerator.writeStringField("name", smell.getKindOfSmellName() + "@" + smell.getElementName() + "#" + RankingManagerForSmells.getInstance().getRankingCalculator().getRankingPosition(smell));
									jGenerator.writeNumberField("size", 1114);
									jGenerator.writeEndObject();
								}
							jGenerator.writeEndArray();
							jGenerator.writeEndObject();
											
						}						
						jGenerator.writeEndArray();
						jGenerator.writeEndObject();
					}
					//</body>
					jGenerator.writeEndArray();
					jGenerator.writeEndObject();
					jGenerator.flush();
					jGenerator.close();
					writer.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}						
				return writer.toString();
		
	}

	private static String getClassName(Set<CodeSmell> codeAnomalies) {
		Iterator<CodeSmell> it = codeAnomalies.iterator();
		CodeSmell cs = it.next();
		if(cs.getElementName().contains("."))
			return cs.getElementName().split("\\.")[0]; 
		return cs.getElementName();
	}
	
	
	
	
}
