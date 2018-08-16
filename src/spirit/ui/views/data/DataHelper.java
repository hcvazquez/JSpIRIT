package spirit.ui.views.data;

import java.awt.Color;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.jgrapht.graph.DefaultEdge;

import spirit.core.design.CodeSmellsManagerFactory;
import spirit.core.smells.CodeSmell;
import spirit.dependencies.PackageVisitor;
import spirit.priotization.RankingManagerForSmells;

public class DataHelper {
	
	private static JsonFactory jFactory = new JsonFactory();		
	
	private static LinkedHashMap<String, List<String>> generateSmellsByPackageData() {		
		LinkedHashMap<String, List<String>> smellsByPackage = new LinkedHashMap<String, List<String>>();
		for(String pack : PackageVisitor.getInstance().getPackageGraph().vertexes())
			smellsByPackage.put(pack, new LinkedList<String>());
		for(CodeSmell cs : CodeSmellsManagerFactory.getInstance().getCurrentProjectManager().getSmells()) {
			String packag = cs.getMainClassName().substring(0, cs.getMainClassName().lastIndexOf('.'));
			List<String> smells = smellsByPackage.get(packag);
			if(smells == null) {
				smells = new LinkedList<String>();
				smellsByPackage.put(packag, smells);
			}
			smells.add(cs.getKindOfSmellName() + "@" + cs.getElementName() + "#" + RankingManagerForSmells.getInstance().getRankingCalculator().getRankingPosition(cs));											
		}
		return smellsByPackage;
					
	}
	
	public static String getPackageRelationships() {
		Set<DefaultEdge> edges = PackageVisitor.getInstance().getPackageGraph().getEdges();
		Iterator<DefaultEdge> it = edges.iterator();
		
		//Serialization
		StringWriter writer = new StringWriter(); 		
		try {
			JsonGenerator jGenerator = jFactory.createJsonGenerator(writer);
			jGenerator.writeStartArray();
			while(it.hasNext()) {
				DefaultEdge edge = it.next();
				String str = edge.toString().replace("(", "").replace(")", "");
				String source = str.split(":")[0].trim();
				String target = str.split(":")[1].trim();
				jGenerator.writeStartObject();
				jGenerator.writeStringField("source", source);
				jGenerator.writeStringField("target", target);
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
	
	
	 //Un mapa con paquetes como clave y un HashSet (sin repetidos) de sus clases afectadas. 	 
	private static HashMap<String,HashSet<String>> buildAffectedClassesMap(LinkedHashMap<String, List<String>> smellsByPackage) {
		HashMap<String,HashSet<String>> map = new HashMap<String,HashSet<String>>();
		for (String mypackage : PackageVisitor.getInstance().getPackageGraph().vertexes())
			map.put(mypackage, new HashSet<String>());
		for(Entry<String,List<String>> smell : smellsByPackage.entrySet()) {
			for(String s : smell.getValue()) {
				HashSet<String> aux = map.get(smell.getKey());
				if(aux == null)
					aux = new HashSet<String>();
				aux.add(s.split("@")[1].split("#")[0].split("\\.")[0]);
			}
		}
		return map;		
	}
	
	private static String reorderByColor(List<ColoredPackage> list, LinkedHashMap<String, List<String>> smellsByPackage) {
		Collections.sort(list);
		LinkedHashMap<String, List<String>> orderedMap = new LinkedHashMap<String, List<String>>();		
		for(ColoredPackage obj : list)
			orderedMap.put(obj.getName(),smellsByPackage.get(obj.getName()));
		
		//Serialization	
		StringWriter writer = new StringWriter(); 
		
		try {
			JsonGenerator jGenerator = jFactory.createJsonGenerator(writer);
			jGenerator.writeStartArray();
			for (Entry<String,List<String>> entry : orderedMap.entrySet()) {				
				jGenerator.writeStartObject();
				jGenerator.writeStringField("name", entry.getKey());
				jGenerator.writeArrayFieldStart("smells");
				for(String smell : entry.getValue())
					jGenerator.writeString(smell);
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
	
	public static String[] calculatePackageData() {
		LinkedHashMap<String, List<String>> smellsByPackage = generateSmellsByPackageData();
		System.out.println(smellsByPackage);
		String[] result = new String[4];
		HashMap<String, String> colorMap = new HashMap<String, String>();
		HashMap<String, String> colorMap2 = new HashMap<String, String>();
		List<ColoredPackage> list = new LinkedList<ColoredPackage>();
		List<ColoredPackage> list2 = new LinkedList<ColoredPackage>();
		HashMap<String,HashSet<String>> affectedMap = buildAffectedClassesMap(smellsByPackage);								
		for (String mypackage : PackageVisitor.getInstance().getPackageGraph().vertexes()) {				
			if(CodeSmellsManagerFactory.getInstance().getCurrentProjectManager().getClassesByPackage().containsKey(mypackage)){
				int totalClasses = CodeSmellsManagerFactory.getInstance().getCurrentProjectManager().getClassesByPackage().get(mypackage).intValue();
				int affectedClasses = affectedMap.get(mypackage).size();
				String color = calculateColorOriginalFormula(mypackage, affectedClasses, totalClasses);
				String color2 = calculateColorByTotal(mypackage, affectedClasses);
				colorMap.put(mypackage, color);
				colorMap2.put(mypackage, color2);
				list.add(new ColoredPackage(mypackage,color));
				list2.add(new ColoredPackage(mypackage,color2));
				result[0] = reorderByColor(list, smellsByPackage);
				result[1] = reorderByColor(list2, smellsByPackage);
			}	
		}								
		
		result[2] = serializeColors(colorMap);					
		result[3] = serializeColors(colorMap2);
		
		return result;
		
	}
	
	private static String serializeColors(HashMap<String, String> colorMap) {
		//Serialization
		StringWriter writer = new StringWriter(); 
		
		try {
			JsonGenerator jGenerator = jFactory.createJsonGenerator(writer);
			jGenerator.writeStartArray();
			for (Entry<String,String> entry : colorMap.entrySet()) {				
				jGenerator.writeStartObject();
				jGenerator.writeStringField("name", entry.getKey());
				String c = entry.getValue();				
				jGenerator.writeStringField("color", c);
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

	private static String calculateColorOriginalFormula(String pack, float affected, float total) {
		if(total != 0)
			if(affected == 0)
				return "#FFFFFF";
			else {
				float gAndB = affected/total;
				Color c = new Color(1.0f ,Math.min(Math.abs(1.0f - gAndB), 1.0f), Math.min(Math.abs(1.0f - gAndB), 1.0f)); // 0.0 < gAndB < 1.0
				String colorHex = String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
				return colorHex;							
			}
		return "#FFFFFF";
 	}	
	
	private static String calculateColorByTotal(String pack, float affected) {
		int totalSystemSmells = CodeSmellsManagerFactory.getInstance().getCurrentProjectManager().getSmells().size();
		if(totalSystemSmells != 0)
			if(affected == 0)
				return "#FFFFFF";
			else {
				float gAndB = affected/totalSystemSmells;
				Color c = new Color(1.0f ,Math.abs(1.0f - gAndB), Math.abs(1.0f - gAndB)); // 0.0 < gAndB < 1.0
				String colorHex = String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
				return colorHex;							
			}
		return "#FFFFFF";
 	}	
	

}
