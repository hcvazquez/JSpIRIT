package spirit.metrics.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class InvokingCache {
    private static InvokingCache INSTANCE = null;
    private HashMap<String, List<String>> cacheMethodsInvokingMethod;
    private HashMap<String, List<String>> cacheClassesInvokingMethod;
    private HashMap<String, List<String>> cacheClassesInvokingClass;
    
    private InvokingCache(){
    	initialize();
    }
 
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new InvokingCache();
        }
    }
 
    public static InvokingCache getInstance() {
        createInstance();
        return INSTANCE;
    }
    
    public void initialize(){
    	cacheMethodsInvokingMethod = new HashMap<String, List<String>>();
    	cacheClassesInvokingMethod = new HashMap<String, List<String>>();
    	cacheClassesInvokingClass = new HashMap<String, List<String>>();
    }   
    
    public void saveInvocation(MethodInvocation invocation, String methodInvocator, String nameOfParentClass){
    	String key = invocation.resolveMethodBinding().getKey();
    	if(cacheMethodsInvokingMethod.get(key)!=null){
    		if(!cacheMethodsInvokingMethod.get(key).contains(methodInvocator)){
    	   		cacheMethodsInvokingMethod.get(key).add(methodInvocator);
    		}
    	}else{
    		List<String> invocations = new ArrayList<String>();
    		invocations.add(methodInvocator);
    		cacheMethodsInvokingMethod.put(key, invocations);
    	}
    	
    	if(cacheClassesInvokingMethod.get(key)!=null){
    		if(!cacheClassesInvokingMethod.get(key).contains(nameOfParentClass)){
    			cacheClassesInvokingMethod.get(key).add(nameOfParentClass);
    		}
    	}else{
    		List<String> invocations = new ArrayList<String>();
    		invocations.add(nameOfParentClass);
    		cacheClassesInvokingMethod.put(key, invocations);
    	}
    	
    	String nameOfClass = invocation.resolveMethodBinding().getDeclaringClass().getBinaryName();
    	if(cacheClassesInvokingClass.get(nameOfClass)!=null){
    		if(!cacheClassesInvokingClass.get(nameOfClass).contains(nameOfParentClass)){
    			cacheClassesInvokingClass.get(nameOfClass).add(nameOfParentClass);
    		}
    	}else{
    		List<String> invocations = new ArrayList<String>();
    		invocations.add(nameOfParentClass);
    		cacheClassesInvokingClass.put(nameOfClass, invocations);
    	}
    	
    }
    
    public List<String> loadMethodsInvokingMethod(MethodDeclaration method){
    	String key = method.resolveBinding().getKey();
    	return cacheMethodsInvokingMethod.get(key);
    }
    
    public List<String> loadClassesInvokingMethod(MethodDeclaration method){
    	String key = method.resolveBinding().getKey();
    	return cacheClassesInvokingMethod.get(key);
    }
    
    public List<String> loadClassesInvokingClass(TypeDeclaration clazz){
    	String key = clazz.resolveBinding().getBinaryName();
    	return cacheClassesInvokingClass.get(key);
    }
}
