package cpp2java.scripts.pretty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CheckPrettyBatch {
	public static void main(String [] args ) {
		String workDirectory = args[0];
		String reportDir = args[1];
		try {
			run(workDirectory, reportDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void run(String workDirectory, String reportDir) throws IOException {
		FileWriter prettyCheckOutput = new FileWriter(reportDir);
		File dirF = new File(workDirectory);
		prettyCheckOutput.write("Name,Preproc,Pretty\n");
		for(File f : dirF.listFiles()) {
		    if(f.isFile() && f.getAbsolutePath().endsWith(".C")) {
		    	prettyCheckOutput.write(f.getName() + ",");
		    	File preprocErrors = new File(f.getAbsolutePath()+".preproc");
		    	StringBuffer preprocErrorsSB = readFileAsString(preprocErrors.getAbsolutePath());
		    	if(preprocErrorsSB.indexOf("error:")==-1) {
		    		prettyCheckOutput.write("OK,");
		    	} else {
		    		prettyCheckOutput.write("ERROR,");
		    	}
		    	
		    	File prettyErrors = new File(f.getAbsolutePath()+".ii.pretty");
		    	//prettyCheckOutput.writeln(prettyErrors.getAbsolutePath());
		    	StringBuffer prettyErrorsSB = readFileAsString(prettyErrors.getAbsolutePath());
		    	int typechecking = prettyErrorsSB.indexOf("typechecking results:");
		    	if(typechecking!=-1) {
		    		int errorsIndex = prettyErrorsSB.indexOf("errors:", typechecking);
		    		int errorsEnd = errorsIndex+"errors:".length();
		    		String errorNumbers = prettyErrorsSB.substring(errorsEnd, prettyErrorsSB.indexOf("\n",errorsEnd));
		    		try {
		    			int i = Integer.parseInt(errorNumbers.trim());
		    			if(i==0) {
		    				prettyCheckOutput.write("OK\n");
		    			} else {
		    				prettyCheckOutput.write("ERROR\n");
		    			}
		    		} catch(NumberFormatException nfe) {
		    			prettyCheckOutput.write("ERROR\n");
		    		}
		    	} else {
		    		prettyCheckOutput.write("ERROR\n");
		    	}
		    }
		}
		prettyCheckOutput.close();
	}
	
    private static StringBuffer readFileAsString(String filePath) {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new FileReader(filePath));
            char[] buf = new char[1024];
            int numRead=0;
            while((numRead=reader.read(buf)) != -1){
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            reader.close();
            return fileData;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}