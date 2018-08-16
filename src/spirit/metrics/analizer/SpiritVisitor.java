package spirit.metrics.analizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.metrics.calculate.AccessToData;
import spirit.metrics.calculate.AverageMethodWeight;
import spirit.metrics.calculate.BaseClassUsageRatio;
import spirit.metrics.calculate.ChangingClasses;
import spirit.metrics.calculate.ChangingMethods;
import spirit.metrics.calculate.CouplingDispersion;
import spirit.metrics.calculate.CouplingIntensity;
import spirit.metrics.calculate.ForeignDataProviders;
import spirit.metrics.calculate.IAttribute;
import spirit.metrics.calculate.LocalityOfAttributesAccesses;
import spirit.metrics.calculate.MaximumNestingLevel;
import spirit.metrics.calculate.McCabe;
import spirit.metrics.calculate.NameOfFields;
import spirit.metrics.calculate.NamesOfInvokedClasses;
import spirit.metrics.calculate.NamesOfInvokingClasses;
import spirit.metrics.calculate.NumOverrideMethods;
import spirit.metrics.calculate.NumProtMembersInParent;
import spirit.metrics.calculate.NumberOfAccessorMethods;
import spirit.metrics.calculate.NumberOfAddedServices;
import spirit.metrics.calculate.NumberOfFields;
import spirit.metrics.calculate.NumberOfLinesOfCode;
import spirit.metrics.calculate.NumberOfMethods;
import spirit.metrics.calculate.NumberOfPublicFields;
import spirit.metrics.calculate.NumberOfPublicMethods;
import spirit.metrics.calculate.PercentageOfAddedServices;
import spirit.metrics.calculate.SuperClassHierarchy;
import spirit.metrics.calculate.TightClassCohesion;
import spirit.metrics.calculate.WeightOfClass;
import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class SpiritVisitor extends ASTVisitor {

	HashMap<TypeDeclaration, ClassMetrics> classesMetrics = new HashMap<TypeDeclaration, ClassMetrics>();
	List<IAttribute> metricForClasses = new ArrayList<IAttribute>();
	List<IAttribute> metricForMethods = new ArrayList<IAttribute>();
	List<IAttribute> couplingMetrics = new ArrayList<IAttribute>();
	
	List<IAttribute> changingMetrics = new ArrayList<IAttribute>();
	
	List<IAttribute> invokingAttributes = new ArrayList<IAttribute>();
	
	List<String> nameOfClasses = new ArrayList<String>();
	
	public SpiritVisitor() {
		super();
				
		this.metricForClasses.add(new NameOfFields());
		this.metricForClasses.add(new McCabe()); 
		this.metricForClasses.add(new NumberOfMethods());
		this.metricForClasses.add(new NumberOfFields());
		this.metricForClasses.add(new AccessToData());
		this.metricForClasses.add(new TightClassCohesion());
		this.metricForClasses.add(new NumberOfLinesOfCode());
		this.metricForClasses.add(new NumberOfPublicFields());
		this.metricForClasses.add(new NumberOfPublicMethods());
		this.metricForClasses.add(new WeightOfClass());
		this.metricForClasses.add(new NumberOfAccessorMethods());
		this.metricForClasses.add(new AverageMethodWeight());
		this.metricForClasses.add(new NumProtMembersInParent(nameOfClasses));
		this.metricForClasses.add(new NumOverrideMethods(nameOfClasses));
		this.metricForClasses.add(new BaseClassUsageRatio());
		this.metricForClasses.add(new NumberOfAddedServices());
		this.metricForClasses.add(new PercentageOfAddedServices());
		
		
		this.metricForMethods.add(new NameOfFields());
		this.metricForMethods.add(new McCabe());
		this.metricForMethods.add(new AccessToData());
		this.metricForMethods.add(new NumberOfFields());
		this.metricForMethods.add(new TightClassCohesion());
		this.metricForMethods.add(new NumberOfLinesOfCode());
		this.metricForMethods.add(new MaximumNestingLevel());
		this.metricForMethods.add(new LocalityOfAttributesAccesses());
		this.metricForMethods.add(new ForeignDataProviders());
		
		this.couplingMetrics.add(new CouplingIntensity());
		this.couplingMetrics.add(new CouplingDispersion());
		
		this.invokingAttributes.add(new NamesOfInvokedClasses(nameOfClasses));
		this.invokingAttributes.add(new NamesOfInvokingClasses());
		
		this.changingMetrics.add(new ChangingClasses());
		this.changingMetrics.add(new ChangingMethods());
		
	}

	@Override
	public boolean visit(TypeDeclaration clazz) {
		nameOfClasses.add(clazz.resolveBinding().getBinaryName());
		ClassMetrics classMetrics = new ClassMetrics(clazz);
		
		classesMetrics.put(clazz, classMetrics);
		return super.visit(clazz);
	}
	
	public void executeMetrics(){
		for(TypeDeclaration clazz:classesMetrics.keySet()){
			ClassMetrics classMetrics = classesMetrics.get(clazz);
			classMetrics.setAttribute(MetricNames.nameOfClasses, nameOfClasses);
			IAttribute superClasses = new SuperClassHierarchy(nameOfClasses);
			superClasses.calculate(classMetrics);
			
			MethodDeclaration[] methods = clazz.getMethods();
			for (MethodDeclaration method : methods) {
				MethodMetrics methodMetrics = new MethodMetrics(method,classMetrics);
				for (IAttribute metric : metricForMethods) {
					metric.calculate(methodMetrics);
				}
				classMetrics.getMethodsMetrics().add(methodMetrics);
			}
			
			for (IAttribute metric : metricForClasses) {
				metric.calculate(classMetrics);
			}
		}
	}
	
	public List<ClassMetrics> getLClassesMetrics() {
		List<ClassMetrics> lclassMetrics = new ArrayList<ClassMetrics>();
		for(TypeDeclaration clazz:classesMetrics.keySet()){
			lclassMetrics.add(classesMetrics.get(clazz));
		}
		return lclassMetrics;
	}
	
	private void executeInvokingAttributes(){
		for(IAttribute invokingAttribute:invokingAttributes){
			for(ClassMetrics classMetrics:getLClassesMetrics()){
				for(MethodMetrics methodMetric:classMetrics.getMethodsMetrics()){
					invokingAttribute.calculate(methodMetric);
				}
				invokingAttribute.calculate(classMetrics);
			}
		}
	}
	
	public void calculateAditionalMetrics(){

		for(IAttribute couplingMetric:couplingMetrics){
			for(ClassMetrics classMetric:getLClassesMetrics()){
				for(MethodMetrics methodMetric:classMetric.getMethodsMetrics()){
					couplingMetric.calculate(methodMetric);
				}
				couplingMetric.calculate(classMetric);
			}
		}
		
		executeInvokingAttributes();
		
		for(IAttribute changingMetric:changingMetrics){
			for(ClassMetrics classMetric:getLClassesMetrics()){
				for(MethodMetrics methodMetric:classMetric.getMethodsMetrics()){
					changingMetric.calculate(methodMetric);
				}
				changingMetric.calculate(classMetric);
			}
		}
	}

}
