package cpp2java.targets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class GenerateTargets {
	public void run(File makeErr) {
		Map<String, Map<String, List<String>>> elements = new HashMap<String, Map<String, List<String>>>();
		
		try {
	        BufferedReader in = new BufferedReader(new FileReader(makeErr));
	        String str;
	        String enitityName;
	        String abstractFileName;
	        String fileName;
	        while ((str = in.readLine()) != null) {
	            StringTokenizer st = new StringTokenizer(str, ";");
	            st.nextToken();
	            enitityName = st.nextToken();
	            abstractFileName = st.nextToken();
	            fileName = st.nextToken();
	            if(!enitityName.toUpperCase().equals(abstractFileName)) {
	            	Map<String, List<String>> filesInPackage = elements.get(getPackage(fileName));
	            	if(filesInPackage==null) {
	            		elements.put(getPackage(fileName), new HashMap<String, List<String>>());
	            		filesInPackage = elements.get(getPackage(fileName));
	            	}
	            	
	            	List<String> javaFiles = filesInPackage.get(getCFile(fileName));
	            	if(javaFiles==null) {
	            		filesInPackage.put(getCFile(fileName), new ArrayList<String>());
	            		javaFiles = filesInPackage.get(getCFile(fileName));
	            	}
	            	
	            	javaFiles.add(enitityName+".java");
	            }
	        }
	        in.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    
	    printPackageRule("jwt", elements.get("jwt"));
	    printPackageRule("chart", elements.get("chart"));
	    printPackageRule("ext", elements.get("ext"));
	}
	
	private void printPackageRule(String packageName, Map<String, List<String>> packageMap) {
    	System.out.println("Package " +  packageName + ":\n");
    	for(Map.Entry<String, List<String>> e2 : packageMap.entrySet()) {
    		System.out.print("From " + e2.getKey() + ": ");
    		for(String l : e2.getValue()) {
    			System.out.print(l+" ");
    		}
    		System.out.println();
    	}
    	System.out.println();
	}
	
	private String getPackage(String fileName) {
		String basePath = fileName.substring(0,fileName.lastIndexOf("/"));
		basePath = basePath.substring(basePath.lastIndexOf("/")+1);
		
    	if(basePath.equals("Wt")) {
    		basePath = "jwt";
    	} else if(basePath.equals("web")) {
    		basePath = "jwt";
    	} else if(basePath.equals("Chart")) {
    		basePath = "chart";
    	} else if(basePath.equals("Ext")) {
    		basePath = "ext";
    	}
    	
    	return basePath;
	}
	
	private String getCFile(String fileName) {
		String srcFile = fileName.substring(fileName.lastIndexOf('/')+1, fileName.indexOf(':'));
		
		if(srcFile.endsWith(".C")) {
			
		} else if(srcFile.endsWith(".h")) {
			srcFile = srcFile.substring(0, srcFile.lastIndexOf(".h")) + ".C";
		} else {
			srcFile += ".C";
		}
		
		return srcFile;
	}
	
	public static void main(String [] args) {
		GenerateTargets gt = new GenerateTargets();
		gt.run(new File("/home/plibin0/projects/cnor/wt-port/java/SourceLoc.txt"));
	}
}
