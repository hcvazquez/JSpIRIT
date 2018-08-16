package bandago.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.metrics.calculate.McCabe;
import spirit.metrics.calculate.NumberOfLinesOfCode;
import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;
import bandago.filters.*;
import bandago.metrics.MetricAnalyzer;
import bandago.operators.*;
import bandago.utils.*;
import bandago.solvers.CodeSmellSolver;

public class SimulatedAnnealing extends Observable implements RefactoringAlgorithm {
	
	private class DuplaGetterOperator{
		public DuplaGetterOperator(StatementFilter getter, Operator operator){
			this.getter = getter;
			this.operator = operator;
		}
		public StatementFilter getter;
		public Operator operator;
	}
	
	private long runningTimePerSolution;
	private long startTime = 0;
	private CompilationUnitManager actualState = null;
	private ArrayList<ExtractData> solution = new ArrayList<ExtractData>(); 
	private Solution generatedSolution = null;
	private double actualTemperature = 0;
	private int actualIteration = 0;
	private volatile int actualSolutions = 0;
	private StatementFilter getter = null;
	private Operator operator = null;
	private ArrayList<StatementSet> previousExtractedStatement;
	
	private int minimumStatementToExtract;
	private double temperature;
	private int maxDeepIterations;
	private int numberOfSolutions;
	private double coolDownFactor;
	
	private String brainMethodToRefactor;
	private CompilationUnitManager initialState;	
	private ICompilationUnit originalCompilationUnit;
	
	private ArrayList<ArrayList<ExtractData>> allSolutions;
	private ArrayList<Operator> operators;
	private ArrayList<StatementFilter> getters;
	private ArrayList<DuplaGetterOperator> allCombinations;
	
	private ArrayList<String> currentBrainMethods;
	private ArrayList<Solution> generatedSolutions;
	private ArrayList<MetricAnalyzer> metrics;
	private SimpleName methodName;
	private CodeSmellSolver father;

		
	public SimulatedAnnealing(Observer o, int minimumStatementToExtract, double temperature, int numberOfSolutions, int maxDeepIterations, double coolDownFactor,long runningTimePerSolution){
		this.minimumStatementToExtract = minimumStatementToExtract;
		this.temperature = temperature;
		this.maxDeepIterations = maxDeepIterations;
		this.numberOfSolutions = numberOfSolutions;
		this.coolDownFactor = coolDownFactor;
		this.runningTimePerSolution = runningTimePerSolution;
		this.addObserver(o);
	}
	
	public SimulatedAnnealing(int minimumStatementToExtract, double temperature, int numberOfSolutions, int maxDeepIterations, double coolDownFactor,long runningTimePerSolution){
		this.minimumStatementToExtract = minimumStatementToExtract;
		this.temperature = temperature;
		this.maxDeepIterations = maxDeepIterations;
		this.numberOfSolutions = numberOfSolutions;
		this.coolDownFactor = coolDownFactor;
		this.runningTimePerSolution = runningTimePerSolution;
	}
	
	
	private void initializeAlgorithmParameters (ICompilationUnit icuOriginal){
		initialState = new CompilationUnitManager(icuOriginal, true, methodName.getFullyQualifiedName());		
		this.brainMethodToRefactor = methodName.getFullyQualifiedName();
		allSolutions = new ArrayList<ArrayList<ExtractData>>();
		currentBrainMethods = new ArrayList<String>();
		currentBrainMethods.add(methodName.getFullyQualifiedName());
		allCombinations = new ArrayList<DuplaGetterOperator>(); 
		generatedSolutions = new ArrayList<Solution>();
		metrics = new ArrayList<MetricAnalyzer>();
		previousExtractedStatement = new ArrayList<StatementSet>();
	}
	
	public ArrayList<ArrayList<ExtractData>> getAllSolutions(){
		return allSolutions;
	}
	
	public void execute(){
		initializeAlgorithmParameters(this.getCompilationUnit());
		
		setDefaultGettersAndOperators();
  		
  		run(getters, operators);
	}
	
	private void setDefaultGettersAndOperators() {
		getters = new ArrayList<StatementFilter>();
		getters.add(new AllStatementsFilter(minimumStatementToExtract));
		getters.add(new ForFilter(minimumStatementToExtract));
		getters.add(new IfFilter(minimumStatementToExtract));
		getters.add(new WhileFilter(minimumStatementToExtract));
		getters.add(new GroupStatementFilter(minimumStatementToExtract));
		getters.add(new TryFilter(minimumStatementToExtract+4));
		getters.add(new SwitchFilter(minimumStatementToExtract));
		getters.add(new DoFilter(minimumStatementToExtract));
		
		operators = new ArrayList<Operator>();
		operators.add(new LocOperator());
  		operators.add(new NestingOperator());
  		operators.add(new RandomOperator());
  		operators.add(new CyclomaticOperator());
  		operators.add(new NofOperator());
	}

	public void run(ArrayList<StatementFilter> getters, ArrayList<Operator> operators){
		long startTime1 = System.nanoTime();
		long tiempototalcopiayaplica=0;
		this.getters = getters;
		this.operators = operators;
		generateAllCombinatios();
		initialConfiguration();
		
		startTime();
		
		//Caso que sea clase anonima que no se tiene que evaluar
		if(!(actualState.getMethod(currentBrainMethods.get(0)).getParent() instanceof TypeDeclaration)){
			deleteCopiesOfStates();
			return;
		} 
		
		while((actualSolutions < numberOfSolutions) && timeLimit()){  
			
			getter = null;
			operator = null;
			while((actualIteration < maxDeepIterations) && !emptyOfBrainMethods()){
				
				brainMethodToRefactor = currentBrainMethods.get(0);
				
				
				StatementSet s = getGetterAndOperator(actualState);
				if(s != null && !extractedBefore(s)){
					
					long startTime2 = System.nanoTime();
					//Creo un estado nuevo desde una copia del estado actual y aplico el refactoring
					CompilationUnitManager newState = new CompilationUnitManager(actualState.getCopyOfICompilationUnit(), true, methodName.getFullyQualifiedName());
					String methodNameExtracted = newState.applyExtractRefactoring(s);
					long endTime2 = System.nanoTime();
					tiempototalcopiayaplica=tiempototalcopiayaplica+ (endTime2 - startTime2);
					
					//Evaluo las soluciones para ver cual es mejor
					double seActualState = solutionEvaluation(actualState, brainMethodToRefactor);
					double seNewState = solutionEvaluation(newState, methodNameExtracted);
					
					if((acceptanceProbability(seActualState,seNewState,actualTemperature) > Math.random()) && goodBenefit(newState,methodNameExtracted)){
			
						//agrego el statement que saque
						previousExtractedStatement.add(s);
						
						long startTime4 = System.nanoTime();
						//Asigno al estado actual el estado refactorizado
						actualState.deleteCopy();
						actualState.setICompilationUnit(newState.getICompilationUnit());
						newState = null;
						long endTime4 = System.nanoTime();
						tiempototalcopiayaplica=tiempototalcopiayaplica+ (endTime4 - startTime4);
						
						//Agrego al arreglo si es brain method la extraccion
						if(isBrainMethod(actualState, methodNameExtracted)){
							currentBrainMethods.add(methodNameExtracted);
						}
						
						System.out.println(getter);
						System.out.println(operator);
						
						//Solucion para el archivo y solucion para los extract
						solution.add(new ExtractData(s.getStartPosition(),s.getLength()));
						
						generatedSolution.addMethod(methodNameExtracted, actualState.getMethod(methodNameExtracted).toString());
						generatedSolution.addMethod(brainMethodToRefactor, actualState.getMethod(brainMethodToRefactor).toString());
						generatedSolution.addOperatorAndGetter(operator.getName(), getter.getName());
						
					} else {
						long startTime3 = System.nanoTime();
						//Caso en el que no se aplique el estado nuevo, lo tengo que eliminar a ese estado creado
						newState.deleteCopy();
						newState = null;
						long endTime3 = System.nanoTime();
						tiempototalcopiayaplica=tiempototalcopiayaplica+ (endTime3 - startTime3);
					}
				}
				else{
					if(actualIteration == 0){
						deleteCopiesOfStates();
						return;
					}
					if(s == null){
						actualIteration = maxDeepIterations;
					}
				}
				
				//Saco todos los metodos que no son mas brain methdos
				removeAllNoMoreBrainMethods();
				
				actualTemperature = coolDownFactor * actualTemperature;
				actualIteration++;
			}
			
			//Genera la solucion
			generateSolution();
			System.out.println("Genere solucion: "+actualSolutions);
			
			//Vuelvo las variables al estado inicial			
			initialConfiguration();
			
			
		}
		deleteCopiesOfStates();
		
		String message = "¡Busqueda finalizada! \nSe encontraron " + actualSolutions + " soluciones.";
		notifyFinished(message);
		long endTime1 = System.nanoTime();
		System.out.println("Tiempo total copia y aplica: "+ tiempototalcopiayaplica);
		System.out.println("Tiempo total: " + (endTime1 - startTime1));
	}

	private void removeAllNoMoreBrainMethods() {
		ArrayList<String> toRemove = new ArrayList<String>();
		for(String s: currentBrainMethods){
			if(!isBrainMethod(actualState, s)){
				toRemove.add(s);
			}
		}
		currentBrainMethods.removeAll(toRemove);
		toRemove = null;
	}

	private boolean extractedBefore(StatementSet s) {
		for(StatementSet statements: previousExtractedStatement){
			if(statements.equals(s)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Elimino las copias creadas de la icompilationunit para que no queden en el proyecto
	 */
	private void deleteCopiesOfStates(){
		if(actualState != null)
			actualState.deleteCopy();
		if(initialState != null)
			initialState.deleteCopy();
	}
	
	private void generateSolution() {
		if((generatedSolution.getMethodsExtracted().size() != 0) &&  !alreadySolution(generatedSolution) && emptyOfBrainMethods() && !isBrainMethod(actualState, brainMethodToRefactor)){
			//Guardo la solucion en el archvio de log
			if(operator != null && getter != null)
				//log.addSolution("Strategy "+ operator.getName()+", "+getter.getName(), generatedSolution, actualIteration);
			actualSolutions++;
	
			//Guardo la solucion final
			allSolutions.add(solution);
			generatedSolutions.add(generatedSolution);
			
			//Calculo la metrica promedio
			MetricAnalyzer m = getAverageMetrics(generatedSolution, actualState);
			
			//Calculo las metricas individuales de cada método
			ArrayList<MetricAnalyzer> metricsOfExtracted = getMetricsOfExtracted(generatedSolution, actualState);
			
		    System.out.println("Solution "+ actualSolutions);
		    
		    //Notify Change to view
			notifyAddedSolutionChange("Solution "+ actualSolutions, m, father, metricsOfExtracted);
			
		}
	}

	private boolean timeLimit() {
		long duration = System.nanoTime() - startTime;
		long limit = runningTimePerSolution * numberOfSolutions;
		if(duration < limit)
			return true;
		return false;
	}


	private boolean alreadySolution(Solution generatedSolution2) {
		ArrayList<MethodDeclaration> auxMethods = new ArrayList<MethodDeclaration>();
		for(String methodName :generatedSolution2.getMethodsExtracted()){
			auxMethods.add(actualState.getMethod(methodName));
		}
		MetricAnalyzer me = new MetricAnalyzer(auxMethods);
		
		for(MetricAnalyzer m: metrics){
			if(m.equals(me))
				return true;
		}
		return false;
	}

	private void initialConfiguration(){
		brainMethodToRefactor = methodName.getFullyQualifiedName();
		previousExtractedStatement.clear();
		currentBrainMethods.clear();
		currentBrainMethods.add(brainMethodToRefactor);
		solution = new ArrayList<ExtractData>();
		generatedSolution = new Solution();
		if(actualState != null)
			actualState.deleteCopy();
		
		actualState = new CompilationUnitManager(initialState.getCopyOfICompilationUnit(), true, methodName.getFullyQualifiedName());
		CompilationUnitManager.restartCounter();
		actualTemperature = temperature;
		actualIteration = 0;
	}
	
	private boolean isBrainMethod(CompilationUnitManager actualState, String method) {
		ArrayList<MethodDeclaration> aux = new ArrayList<MethodDeclaration>();
		aux.add(actualState.getMethod(method));
		MetricAnalyzer m = new MetricAnalyzer(aux);
		return m.stillBrainMethod();
	}
	
	private double solutionEvaluation(CompilationUnitManager state, String name){
		TypeDeclaration td = (TypeDeclaration)(state.getMethod(name).getParent());
		ClassMetrics cm = new ClassMetrics(td);
		MethodDeclaration[] methods = td.getMethods();
		McCabe cabe = new McCabe();
		for (MethodDeclaration method : methods) {
			MethodMetrics methodMetrics = new MethodMetrics(method,cm);
			cabe.calculate(methodMetrics);
			cm.getMethodsMetrics().add(methodMetrics);
		}
		cabe.calculate(cm);
		NumberOfLinesOfCode nloc = new NumberOfLinesOfCode();
		nloc.calculate(cm);	
		double cyc = cm.getMetric(MetricNames.WMC);
		double loc = cm.getMetric(MetricNames.LOC);
		return cyc/loc;
	}
	
	private boolean goodBenefit(CompilationUnitManager state,String name) {
		MethodDeclaration md = state.getMethod(name);
		MethodMetrics mm = new MethodMetrics(md, null);
		NumberOfLinesOfCode benefit = new NumberOfLinesOfCode();
		benefit.calculate(mm);
		int coupling = md.parameters().size();
		if(md.getReturnType2()!=null)
			coupling += 1;
		double ratio = 0;
		if (coupling != 0)
			ratio = mm.getMetric(MetricNames.LOC)/coupling;
		if (ratio>=Parameters.getBCR())
			return true;
		return false;
	}

	private double acceptanceProbability(double seActualState, double seNewState, double actualTemperature){
		if (seNewState < seActualState) 
            return 1.0;
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((seActualState - seNewState) / actualTemperature);
	}
	
	private void generateAllCombinatios() {
		for(Operator o : operators)
			for(StatementFilter g: getters)
				allCombinations.add(new DuplaGetterOperator(g,o));
	}

	@SuppressWarnings("unchecked")
	private StatementSet getGetterAndOperator(CompilationUnitManager actualState){
		ArrayList<DuplaGetterOperator> allCombinationsAux = (ArrayList<DuplaGetterOperator>) allCombinations.clone();
		StatementFilter randomGetter = null;
		Operator randomOperator = null;
		
		while(!allCombinationsAux.isEmpty()){
			long seed = System.nanoTime();
			Collections.shuffle(allCombinationsAux, new Random(seed));
			
			randomGetter = allCombinationsAux.get(0).getter;
			randomOperator = allCombinationsAux.get(0).operator;
			allCombinationsAux.remove(0);
			
			ArrayList<StatementSet> setOfStatements = new ArrayList<StatementSet>();
			
			//Obtengo los Statements Candidatos
			randomGetter.setMethod(actualState.getMethod(brainMethodToRefactor));
			randomGetter.setCompilationUnitManager(actualState);
			randomGetter.execute();
			setOfStatements = randomGetter.getStatements();
			
			if(anyStatements(setOfStatements)){
				//Opero sobre los statement candidatos
				randomOperator.setICompilationUnit(actualState.getICompilationUnit());
				StatementSet s = randomOperator.getSelectedStatement(setOfStatements);
				if(foundStatement(s)){
					getter = randomGetter;
					operator = randomOperator;
					return s;
				}
			}
		}
		return null;
	}
			
	private boolean foundStatement(StatementSet s) {
		if (s == null)
			return false;
		else
			return true;
	}

	private boolean anyStatements(ArrayList<StatementSet> setOfStatements) {
		if(setOfStatements.isEmpty())
			return false;
		else
			return true;
	}
	
	private boolean emptyOfBrainMethods(){
		return currentBrainMethods.isEmpty();
	}
	
	private MetricAnalyzer getAverageMetrics(Solution s, CompilationUnitManager icuManager){
		ArrayList<MethodDeclaration> auxMethods = new ArrayList<MethodDeclaration>();
		for(String methodName :s.getMethodsExtracted()){
			auxMethods.add(icuManager.getMethod(methodName));
		}
		MetricAnalyzer m = new MetricAnalyzer(auxMethods);
		metrics.add(m);
		return m;
	}
	
	private ArrayList<MetricAnalyzer> getMetricsOfExtracted(Solution s, CompilationUnitManager icuManager) {
		ArrayList<MetricAnalyzer> metricsOfExtracted = new ArrayList<MetricAnalyzer>();
		
		for(String methodName :s.getMethodsExtracted()){
			//Add a single method to the array
			ArrayList<MethodDeclaration> aux = new ArrayList<MethodDeclaration>();
			aux.add(icuManager.getMethod(methodName));
			
			MetricAnalyzer m = new MetricAnalyzer(aux);
			metricsOfExtracted.add(m);
		}
		return metricsOfExtracted;
	}
	
	public ArrayList<MetricAnalyzer> getMetrics() {
		return metrics;
	}

	@Override
	public ArrayList<ArrayList<ExtractData>> getSolutions() {
		if(allSolutions != null)
			return allSolutions;
		return null;
	}

	@Override
	public void setMethodName(SimpleName simpleName) {
		this.methodName = simpleName;
		
	}

	public SimpleName getMethodName() {
		return methodName;
	}
	
	@Override
	public void setCompilationUnit(ICompilationUnit iCompilationUnit) {
		this.originalCompilationUnit = iCompilationUnit;
	}

	@Override
	public ICompilationUnit getCompilationUnit() {
		return originalCompilationUnit;
	}

	public void setFather(CodeSmellSolver codeSmellSolver) {
		this.father = codeSmellSolver;
		
	}
	
	public void notifyAddedSolutionChange(String solutionName, MetricAnalyzer metrics, CodeSmellSolver solver, ArrayList<MetricAnalyzer> metricsOfExtracted){
		this.setChanged();
		Object[] o = {false,solutionName, metrics, solver, metricsOfExtracted};
		this.notifyObservers(o);
	}

	public void notifyFinished(String message){
		this.setChanged();
		Object[] o = {true,message};
		this.notifyObservers(o);
	}
	
	private void startTime() {
		startTime = System.nanoTime();	
	}
}
