package spirit.priotization.criteria.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public class MappedInformation {
	private String name;
	private List<String> classes;
	private List<String> packages;
	private String projectName;
	private Long id;
	private Collection<String> allAffectedClassesCache;
	public MappedInformation(String name,Vector<String> classes, Vector<String> packages,String projectName) {
		this.classes = classes;
		this.packages = packages;
		this.projectName=projectName;
		allAffectedClassesCache=null;
		this.name = name;
	}
	public List<String> getClasses() {
		return classes;
	}
	public void setClasses(List<String> classes) {
		this.classes = classes;
		allAffectedClassesCache=null;
	}
	public List<String> getPackages() {
		return packages;
	}
	public void setPackages(List<String> packages) {
		this.packages = packages;
		allAffectedClassesCache=null;
	}
	public Collection<String> getAllAffectedClasses() {
		if(allAffectedClassesCache==null){
			Set<String> ret = new TreeSet<String>();
			ret.addAll(classes);
			for (Iterator<String> iterator = packages.iterator(); iterator.hasNext();) {
				String aPackage = (String) iterator.next();
				ret.addAll(getAllClassesInsideAPackage(aPackage));
			}
			allAffectedClassesCache=ret;
		}
		
		
		return allAffectedClassesCache;
	}

	private Collection<String> getAllClassesInsideAPackage(
			String aPackage) {
		Vector<String> ret=new Vector<String>();
		IProject currentProject=ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		IJavaProject javaProject = JavaCore.create(currentProject);
		IPath filePath = Path.fromPortableString(aPackage.replace(".", "/"));
		
		try {
			IJavaElement element=javaProject.findElement(filePath);
			if(element==null)
 				System.out.println(filePath);
			else{
				IPath absolutePath=element.getPath();
				IPackageFragment packageFragment=javaProject.findPackageFragment(absolutePath);
				for (ICompilationUnit unit : packageFragment.getCompilationUnits()) {
			 		// System.out.println("Source file " + unit.getElementName());
			 		
			 		for(IType type:unit.getAllTypes()){
			 			ret.add(type.getFullyQualifiedName().toString().replace("$", "."));
			 		}
			 			
			 		
			 	 }
			}
			
			
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public boolean affectsAClass(String aClassPath) {
		return getAllAffectedClasses().contains(aClassPath);
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}
