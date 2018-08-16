package bandago.filters;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import bandago.utils.CompilationUnitManager;
import bandago.utils.StatementSet;

/**
 * 
 Esta Clase abstracta se encarga de obtener todos los statments pero
 sin filtrar por ningun criterio. Las clases hijas realizan operaciones
 para obtener statements especificos.
 *
 */

public abstract class StatementFilter {
	
	protected MethodDeclaration method;
	private int minimunStatements = 0;
	private ArrayList<StatementSet> statementsAux = new ArrayList<StatementSet>();
	protected ArrayList<StatementSet> statements = new ArrayList<StatementSet>();
	protected char[] parsedClass;
	protected CompilationUnitManager icuManager;
	protected String name = "";
	private boolean casoEspecial = false;
	
	public StatementFilter(int minimunStatements){
		this.minimunStatements = minimunStatements;
	}
	
	public String getName(){
		return name;
	}
	
	public void execute(){
		//Limpio los statements que habia antes
		statements.clear();
		statementsAux.clear();
		casoEspecial = false;
		//Si no sete un metodo devuelvo error, sino ejecuto
		if(!method.equals(null)){
			parseStatements();
			getter();
		}else
			System.out.println("Doesn't have a method seted");
	}
	
	public void setMethod(MethodDeclaration method){
		this.method = method;
	}
	
	protected abstract void getter();
	
	//Este es el metodo que va a obtener todos los statements
	protected void parseStatements(){
		addFirstStatements();
		StatementSet a = null;
		if(statementsAux.size() == 1 && !casoEspecial){ // caso especial si solo hay un statement grande que abarca todo el metodo
			a = statementsAux.get(0);			// se elimina, pero si uno o dos statements pequeños y uno grande, se agrega
			addRestStatements(statementsAux);
			statements.remove(a);
			if(statements.size() > 0){
				System.out.println();
			}
		}
		else
			addRestStatements(statementsAux);
	}
	
	//Agrego con el instance of analizando cada statement(si es un variable declaration, va directo...creo
	@SuppressWarnings("unchecked")
	private void addRestStatements(ArrayList<StatementSet> statement) {
		for(StatementSet s:statement){
			ArrayList<StatementSet> aux = new ArrayList<StatementSet>();
			List<Statement> auxStatements;
			if(s != null){
				if ((s.getStatement(0).getNodeType() == ASTNode.ASSERT_STATEMENT) && (minimunStatements <= 1)){
					statements.add(s);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.BLOCK) && (countLines(s.getStatement(0).toString()) - 2 >= minimunStatements)){
						auxStatements = ((Block) s.getStatement(0)).statements();
						StatementSet block = new StatementSet(StatementSet.GROUP);
						for(Statement statementAux:auxStatements){			
							block.addStatement(statementAux);
							aux.add(new StatementSet(statementAux.getNodeType(),statementAux));
						}
						statements.add(block);
						addRestStatements(aux);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.BREAK_STATEMENT) && (minimunStatements <= 1)){
					statements.add(s);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.CONSTRUCTOR_INVOCATION) && (minimunStatements <= 1)){
					statements.add(s);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.CONTINUE_STATEMENT) && (minimunStatements <= 1)){
					statements.add(s);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.DO_STATEMENT) && (countLines(s.getStatement(0).toString()) >= minimunStatements)){
					statements.add(s);
					Statement statementAux = ((DoStatement)s.getStatement(0)).getBody();
					aux.add(new StatementSet(statementAux.getNodeType(),statementAux));
					addRestStatements(aux);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.EMPTY_STATEMENT) && (minimunStatements <= 1)){
					statements.add(s);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.EXPRESSION_STATEMENT) && (minimunStatements <= 1)){
					statements.add(s);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.FOR_STATEMENT) && (countLines(s.getStatement(0).toString()) >= minimunStatements)){
					statements.add(s);
					Statement statementAux = ((ForStatement)s.getStatement(0)).getBody();
					aux.add(new StatementSet(statementAux.getNodeType(),statementAux));
					addRestStatements(aux);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.IF_STATEMENT) && (countLines(s.getStatement(0).toString()) >= minimunStatements)){
					statements.add(s);
					
					Statement statementAux = ((IfStatement)s.getStatement(0)).getThenStatement();
					if(!(statementAux == null))
						aux.add(new StatementSet(statementAux.getNodeType(),statementAux));
					
					Statement statementAux2 = ((IfStatement)s.getStatement(0)).getElseStatement();
					if(!(statementAux2 == null))
						aux.add(new StatementSet(statementAux2.getNodeType(),statementAux2));
					
					addRestStatements(aux);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.LABELED_STATEMENT) && (countLines(s.getStatement(0).toString()) >= minimunStatements)){
					statements.add(s);
					Statement statementAux = ((LabeledStatement)s.getStatement(0)).getBody();
					aux.add(new StatementSet(statementAux.getNodeType(),statementAux));
					addRestStatements(aux);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.RETURN_STATEMENT) && (minimunStatements <= 1)){
					statements.add(s);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.SUPER_CONSTRUCTOR_INVOCATION) && (minimunStatements <= 1)){
					statements.add(s);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.SWITCH_CASE) && (minimunStatements <= 1)){
					statements.add(s);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.SWITCH_STATEMENT) && (countLines(s.getStatement(0).toString()) >= minimunStatements)){
					statements.add(s);
					auxStatements = ((SwitchStatement) s.getStatement(0)).statements();
					for(Statement statementAux:auxStatements){
						StatementSet sAux = new StatementSet(statementAux.getNodeType(),statementAux);
						aux.add(sAux);
					}
					addRestStatements(aux);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.SYNCHRONIZED_STATEMENT) && (countLines(s.getStatement(0).toString()) >= minimunStatements)){
					statements.add(s);
					Statement statementAux = ((SynchronizedStatement)s.getStatement(0)).getBody();
					aux.add(new StatementSet(statementAux.getNodeType(),statementAux));
					addRestStatements(aux);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.THROW_STATEMENT) && (minimunStatements <= 1)){
					statements.add(s);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.TRY_STATEMENT) && (countLines(s.getStatement(0).toString()) >= minimunStatements)){
					statements.add(s);
					Statement statementAux = ((TryStatement)s.getStatement(0)).getBody();
					aux.add(new StatementSet(statementAux.getNodeType(),statementAux));
					//Si Hay bloque de Finally
					if(((TryStatement)s.getStatement(0)).getFinally() != null){
						statementAux = ((TryStatement)s.getStatement(0)).getFinally();
						aux.add(new StatementSet(statementAux.getNodeType(),statementAux));
					}
					addRestStatements(aux);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.TYPE_DECLARATION_STATEMENT) && (minimunStatements <= 1)){
					statements.add(s);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.VARIABLE_DECLARATION_STATEMENT) && (minimunStatements <= 1)){
					statements.add(s);
				}
				if ((s.getStatement(0).getNodeType() == ASTNode.WHILE_STATEMENT) && (countLines(s.getStatement(0).toString()) >= minimunStatements)){
					statements.add(s);//puede ser que sea mejor no agregarlo
					Statement statementAux = ((WhileStatement) s.getStatement(0)).getBody();
					aux.add(new StatementSet(statementAux.getNodeType(),statementAux));
					addRestStatements(aux);
				}
			}
		}
	}
	
	
	//Agrego los statements del metodo que puedo obtener directamente del metodo para despues recorrer cada uno
	@SuppressWarnings("unchecked")
	protected void addFirstStatements() {
		if(method.getBody() != null){
			List<Statement> aux = method.getBody().statements();
			int a = countStatementsThatAreNotNulls(aux);
			if(a >= 2)// caso especial si solo hay un statement grande que abarca todo el metodo			
				casoEspecial = true;  // se elimina, pero si uno o dos statements pequeños y uno grande, se agrega
			for(int i=0;i<aux.size();i++)
				if (countLines(aux.get(i).toString()) >= minimunStatements){
					Statement sAux = (Statement) aux.get(i);
					statementsAux.add(new StatementSet(sAux.getNodeType(), sAux));
				}
		}
	}

	private int countStatementsThatAreNotNulls(List<Statement> aux) {
		int i = 0;
		for(Statement s:aux){
			if(s!=null)
				i++;
		}
		return i;
	}

	public ArrayList<StatementSet> getStatements(){
		return statements;
	}
	
	public void printStatements() {
		for(StatementSet s:statements){
			System.out.println("----------------------------");
			System.out.println(s.print());
		}
	}
	
	public static int countLines(String code){
		String[] count = code.split("\r\n|\r|\n");
		return count.length;
	}
	
	public void setCompilationUnitManager(CompilationUnitManager icuManager) {
		this.icuManager = icuManager;
		
	}
	
}
