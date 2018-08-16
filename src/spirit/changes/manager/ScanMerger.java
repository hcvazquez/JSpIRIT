package spirit.changes.manager;

import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import spirit.core.smells.CodeSmell;

public class ScanMerger {
	
	
	public static Vector<CodeSmell> merge(Vector<CodeSmell> actual) {
		
		//System.out.println("############  L O    Q U E   S E    V O L V I O   A    E S C A N E A R  ############");
		//mostrar(actual);
		
		//System.out.println("############  F I N A L  #############");
		PreviousScanInfo.getInstance().getPreviousSmells().addAll(actual);
		//mostrar(PreviousScanInfo.getInstance().getPreviousSmells());
		return PreviousScanInfo.getInstance().getPreviousSmells();
	}
	

	public static void removeModified(Set<String> modified) {
		Vector<CodeSmell> smells = PreviousScanInfo.getInstance().getPreviousSmells();
		
		//System.out.println("############  L I S T A      P R E V I A  #############");
		//mostrar(smells);
		
		//System.out.println("C L A S E S   M O D I F I C A D A S!!!!");
		//System.out.println(modified);
		
		Iterator<CodeSmell> it = smells.iterator();
		while(it.hasNext()) {
			CodeSmell cs = it.next();
			String name = cs.getMainClass().getName().getFullyQualifiedName();			
			for(String mod : modified){
				if (name.contentEquals(mod)){
					//System.out.println("SE BORRRRRROOOO ------>>>>> " + name);
					it.remove();					
				}
			}
		}						
		PreviousScanInfo.getInstance().setPreviousSmells(smells);//TODO ver si se puede eliminar la linea.
		//System.out.println("@@@@@@@@@@  L I S T A      P O S T    R E M O V E  @@@@@@@@@@@@@@");
		//mostrar(smells);
	}
	
	private static void mostrar(Vector<CodeSmell> smells) {
		for(CodeSmell smell : smells)
			System.out.println(smell.getKindOfSmellName() + " : " + smell.getElementName());
	}

}
