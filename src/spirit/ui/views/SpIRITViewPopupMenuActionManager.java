package spirit.ui.views;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import org.eclipse.jface.action.Action;

public class SpIRITViewPopupMenuActionManager extends Observable{
	private static SpIRITViewPopupMenuActionManager instance;
	private List<Action> actions;
	public static SpIRITViewPopupMenuActionManager getInstance(){
		if(instance==null)
			instance=new SpIRITViewPopupMenuActionManager();
		return instance;
	}
	public SpIRITViewPopupMenuActionManager() {
		actions=new Vector<Action>();
	}
	public List<Action> getActions() {
		return actions;
	}
	public void addAction(Action anAction){
		//Antes de agregar chequeo que no se haya agregado antes
		for (Iterator<Action> iterator = actions.iterator(); iterator.hasNext();) {
			Action action = (Action) iterator.next();
			if(action.getText().equals(anAction.getText()))
				return; 
		}
		actions.add(anAction);
	}
}
