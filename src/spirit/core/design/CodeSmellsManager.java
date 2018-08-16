package spirit.core.design;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.core.smells.CodeSmell;
import spirit.core.smells.detectors.BrainClassDetector;
import spirit.core.smells.detectors.BrainMethodDetector;
import spirit.core.smells.detectors.CodeSmellDetector;
import spirit.core.smells.detectors.DataClassDetector;
import spirit.core.smells.detectors.DispersedCouplingDetector;
import spirit.core.smells.detectors.FeatureEnvyDetector;
import spirit.core.smells.detectors.GodClassDetector;
import spirit.core.smells.detectors.IntensiveCouplingDetector;
import spirit.core.smells.detectors.RefusedParentBequestDetector;
import spirit.core.smells.detectors.ShotgunSurgeryDetector;
import spirit.core.smells.detectors.TraditionBreakerDetector;
import spirit.db.DataBaseManager;
import spirit.dependencies.DependencyVisitor;
import spirit.dependencies.PackageVisitor;
import spirit.metrics.analizer.SpiritVisitor;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class CodeSmellsManager {

	private IProject eclipseProject;
	private Vector<CodeSmell> smells;
	private Vector<CodeSmellDetector> classDetectors;
	private Vector<CodeSmellDetector> methodDetectors;
	private Vector<String> classesList;
	private SpiritVisitor visitor;
	private boolean visitDependencies; // True if the dependency graph needs to
										// be re-constructed.
	private boolean visitOnlyModified; // True if the AST Visitor only needs to
										// visit the classes that were modified
										// recently.
	private HashSet<String> modifiedClasses;
	private HashMap<String, Integer> classesByPackage;

	public CodeSmellsManager(IProject eclipseProject) {
		this.eclipseProject = eclipseProject;
		initialize();
	}

	public void initialize() {
		visitor = new SpiritVisitor();
		smells = new Vector<CodeSmell>();
		visitor = new SpiritVisitor();
		resetDetectors();
		setVisitDependencies(false);
		setVisitOnlyModified(false);
		modifiedClasses = new HashSet<String>();
		setClassesByPackage(new HashMap<String, Integer>());
	}

	public void resetDetectors() {
		classDetectors = new Vector<CodeSmellDetector>();
		methodDetectors = new Vector<CodeSmellDetector>();
		classesList = new Vector<String>();
		
		String eclipseProjectPath = eclipseProject.getFullPath().toString();
		methodDetectors.add(new BrainMethodDetector(DataBaseManager
				.getInstance().getBrainMethodDetectionConfiguration(
						eclipseProjectPath)));
		methodDetectors.add(new FeatureEnvyDetector(DataBaseManager
				.getInstance().getFeatureEnvyDetectionConfiguration(
						eclipseProjectPath)));
		methodDetectors.add(new DispersedCouplingDetector(DataBaseManager
				.getInstance().getDispersedCouplingDetectionConfiguration(
						eclipseProjectPath)));
		methodDetectors.add(new IntensiveCouplingDetector(DataBaseManager
				.getInstance().getIntensiveCouplingDetectionConfiguration(
						eclipseProjectPath)));
		methodDetectors.add(new ShotgunSurgeryDetector(DataBaseManager
				.getInstance().getShotgunSurgeryDetectionConfiguration(
						eclipseProjectPath)));
		classDetectors.add(new GodClassDetector(DataBaseManager.getInstance()
				.getGodClassDetectionConfiguration(eclipseProjectPath)));
		classDetectors.add(new BrainClassDetector(DataBaseManager.getInstance()
				.getBrainClassDetectionConfiguration(eclipseProjectPath)));
		classDetectors.add(new DataClassDetector(DataBaseManager.getInstance()
				.getDataClassDetectionConfiguration(eclipseProjectPath)));
		classDetectors.add(new RefusedParentBequestDetector(DataBaseManager
				.getInstance().getRefusedParentBequestDetectionConfiguration(
						eclipseProjectPath)));
		classDetectors.add(new TraditionBreakerDetector(DataBaseManager
				.getInstance().getTraditionBreakerDetectionConfiguration(
						eclipseProjectPath)));
	}

	public Vector<CodeSmell> getSmells() {
		return smells;// TODO
	}

	public void countCodeSmellsDebug() {
		HashMap<String, Integer> codeSmellsCount = new HashMap<String, Integer>();
		for (CodeSmell smell : smells) {
			if (codeSmellsCount.get(smell.getKindOfSmellName()) == null) {
				codeSmellsCount.put(smell.getKindOfSmellName(), 1);
			} else {
				codeSmellsCount.put(smell.getKindOfSmellName(),
						codeSmellsCount.get(smell.getKindOfSmellName()) + 1);
			}
		}
		for (String codeSmell : codeSmellsCount.keySet()) {
			System.out.println(codeSmell + ": "
					+ codeSmellsCount.get(codeSmell));
		}
	}

	public void calculateMetricsCode() throws IOException {
		try {
			analyseProject();
			visitor.executeMetrics();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

	public void calculateAditionalMetrics() {
		visitor.calculateAditionalMetrics();
	}

	public void detectCodeSmells() {
		smells = new Vector<CodeSmell>();
		resetDetectors();
		List<ClassMetrics> lclassMetrics = visitor.getLClassesMetrics();
		for (ClassMetrics classMetrics : lclassMetrics) {
			for (MethodMetrics methodMetrics : classMetrics.getMethodsMetrics()) {
				for (CodeSmellDetector methodDetector : methodDetectors) {
					if (methodDetector.codeSmellVerify(methodMetrics)) {
						smells.add(methodDetector
								.codeSmellDetected(methodMetrics));
					}
				}
			}
			for (CodeSmellDetector classDetector : classDetectors) {
				if (classDetector.codeSmellVerify(classMetrics)) {
					smells.add(classDetector.codeSmellDetected(classMetrics));
				}
			}
		}
		loadTextMarkers();
	}

	private void loadTextMarkers() {
		// deleteTextMarkers(selectedProject);

		for (Iterator<CodeSmell> iterator = smells.iterator(); iterator
				.hasNext();) {
			CodeSmell type = (CodeSmell) iterator.next();

			IJavaElement javaElement = type.getMainClass().resolveBinding()
					.getJavaElement();
			ICompilationUnit cu = (ICompilationUnit) javaElement
					.getAncestor(IJavaElement.COMPILATION_UNIT);

			IMarker marker;
			try {
				deleteSmellMarker(cu.getCorrespondingResource(),
						type.getKindOfSmellName(), type.getLine());
				marker = cu.getCorrespondingResource().createMarker(
						IMarker.PROBLEM);// "codeSmellMarker");
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
				marker.setAttribute(IMarker.MESSAGE, type.getKindOfSmellName());
				marker.setAttribute(IMarker.LINE_NUMBER, type.getLine());// 1);
				marker.setAttribute(IMarker.TRANSIENT, false);

				// marker.setAttribute(IMarker.CHAR_START,type.getElement().getStartPosition());
				// marker.setAttribute(IMarker.CHAR_END,type.getElement().getStartPosition()+type.getElement().getLength());
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void deleteSmellMarker(IResource target, String kind, int line) {

		try {
			IMarker[] markers = target.findMarkers(IMarker.PROBLEM, true,
					IResource.DEPTH_INFINITE);
			for (int i = 0; i < markers.length; i++) {
				String markerMessage = (String) markers[i]
						.getAttribute(IMarker.MESSAGE);
				Integer lineNumber = (Integer) markers[i]
						.getAttribute(IMarker.LINE_NUMBER);
				if (markerMessage != null && lineNumber != null) {
					if ((markerMessage.equals(kind)) && (lineNumber == line)) {
						markers[i].delete();
					}
				}

			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * get the ICompilationUnit for the source
	 * 
	 * @return ICompilationUnit
	 */
	public ASTNode getCompilationUnit(ASTNode node) {
		if (node.getNodeType() == ASTNode.COMPILATION_UNIT)
			return node;
		else {
			return getAncestorCU(node);
		}
	}

	/**
	 * @see IJavaElement
	 */
	public ASTNode getAncestorCU(ASTNode element) {
		while (element != null) {
			if (element.getNodeType() == ASTNode.COMPILATION_UNIT)
				return element;
			element = element.getParent();
		}
		return null;
	}

	private void analyseProject() throws JavaModelException {
		IPackageFragment[] packages = JavaCore.create(eclipseProject)
				.getPackageFragments();
		PackageVisitor.getInstance().getPackageGraph().clean();
		for (IPackageFragment mypackage : packages) {
			if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
				classesByPackage.put(mypackage.getElementName(), mypackage.getCompilationUnits().length);
				createAST(mypackage);
			}
		}
	}

	private void createAST(IPackageFragment mypackage)
			throws JavaModelException {
		for (ICompilationUnit unit : mypackage.getCompilationUnits()){
			
			createProjectClassesList(unit);
			
			if (modifiedClasses.contains(unit.getElementName().split("\\.")[0]))
				for (ICompilationUnit clazz : mypackage.getCompilationUnits())
					modifiedClasses.add(clazz.getElementName().split("\\.")[0]);
		}
		
		for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
			if (isVisitOnlyModified()) {
				if (modifiedClasses
						.contains(unit.getElementName().split("\\.")[0])) {
					CompilationUnit parse = parse(unit);
					parse.accept(visitor);
					parse.accept(PackageVisitor.getInstance());
					if (isVisitDependencies())
						parse.accept(DependencyVisitor.getInstance());
				}
			} else {
				CompilationUnit parse = parse(unit);
				parse.accept(visitor);
				parse.accept(PackageVisitor.getInstance());
				if (isVisitDependencies())
					parse.accept(DependencyVisitor.getInstance());
			}
		}
	}

	private void createProjectClassesList(ICompilationUnit unit) throws JavaModelException {
		for (IType className : unit.getTypes()){
			classesList.add(className.getFullyQualifiedName());
		}
	}

	/**
	 * Reads a ICompilationUnit and creates the AST DOM for manipulating the
	 * Java source file
	 * 
	 * @param unit
	 * @return
	 */

	@SuppressWarnings("deprecation")
	private static CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null);
	}

	public ArrayList<CodeSmell> getAllSmellsOfClass(TypeDeclaration class_) {
		ArrayList<CodeSmell> ret = new ArrayList<CodeSmell>();
		for (Iterator<CodeSmell> iterator = smells.iterator(); iterator
				.hasNext();) {
			CodeSmell type = (CodeSmell) iterator.next();

			// TODO William should look why this was not working
			if (class_.getName().toString()
					.equals(type.getMainClass().getName().toString())
					&& class_.getLength() == type.getMainClass().getLength())
				// System.out.println("");
				// if(type.getMainClass().equals(class_))
				ret.add(type);
		}

		return ret;
	}

	/**
	 * Returns all method smells in the project
	 */
	public Set<CodeSmell> getMethodAnomalies() {
		HashSet<CodeSmell> ret = new HashSet<CodeSmell>();
		for (Iterator<CodeSmell> iterator = smells.iterator(); iterator
				.hasNext();) {
			CodeSmell type = (CodeSmell) iterator.next();
			if (type.isMethodSmell())
				ret.add(type);
		}
		return ret;
	}

	public boolean isVisitDependencies() {
		return visitDependencies;
	}

	public void setVisitDependencies(boolean visitDependencies) {
		this.visitDependencies = visitDependencies;
	}

	public boolean isVisitOnlyModified() {
		return visitOnlyModified;
	}

	public void setVisitOnlyModified(boolean visitOnlyModified) {
		this.visitOnlyModified = visitOnlyModified;
	}

	public void setModifiedClasses(Set<String> classes) {
		modifiedClasses.addAll(classes);
	}

	public Set<String> getModifiedClasses() {
		return modifiedClasses;
	}

	public void setSmells(Vector<CodeSmell> smells) {
		this.smells = smells;
	}

	public HashMap<String, Integer> getClassesByPackage() {
		return classesByPackage;
	}

	public void setClassesByPackage(HashMap<String, Integer> classesByPackage) {
		this.classesByPackage = classesByPackage;
	}
	
	public Vector<String> getClassesList() {
		return classesList;
	}

}
