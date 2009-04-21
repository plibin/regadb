package net.sf.regadb.io.db.ghb.lis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.sf.regadb.csv.Table;
import net.sf.regadb.db.Attribute;
import net.sf.regadb.db.Dataset;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.PatientAttributeValue;
import net.sf.regadb.db.TestNominalValue;
import net.sf.regadb.db.TestResult;
import net.sf.regadb.db.TestType;
import net.sf.regadb.db.session.Login;
import net.sf.regadb.io.db.ghb.GhbUtils;
import net.sf.regadb.io.db.util.ConsoleLogger;
import net.sf.regadb.io.db.util.Utils;
import net.sf.regadb.io.db.util.mapping.DbObjectStore;
import net.sf.regadb.io.db.util.mapping.ObjectMapper;
import net.sf.regadb.io.db.util.mapping.ObjectStore;
import net.sf.regadb.io.db.util.mapping.ObjectMapper.InvalidValueException;
import net.sf.regadb.io.db.util.mapping.ObjectMapper.MappingDoesNotExistException;
import net.sf.regadb.io.db.util.mapping.ObjectMapper.MappingException;
import net.sf.regadb.io.db.util.mapping.ObjectMapper.ObjectDoesNotExistException;
import net.sf.regadb.io.util.StandardObjects;
import net.sf.regadb.util.mapper.XmlMapper;
import net.sf.regadb.util.settings.RegaDBSettings;

public class AutoImport {
    private class FileLogger{
        private PrintStream out;
        
        public FileLogger(File file) throws FileNotFoundException{
            out = new PrintStream(new FileOutputStream(file));
        }
        
        public void print(String msg){
            out.print(msg);
        }
        public void println(String msg){
            out.println(msg);
        }
        
        public void close(){
            out.close();
        }
    }
    private class ErrorTypes{
        public boolean mapping = false;
        public boolean object = false;
        public boolean value = false;
        
        public String toString(){
            String s = "";
            if(mapping)
                s = "not mapped, ";
            if(object)
                s += "wrong mapping, ";
            if(value)
                s += "invalid value";
            
            return s.length() == 0 ? "ok":s;
        }
    }
    
    private FileLogger errLog, importLog;
    private Date earliestDate = new Date(System.currentTimeMillis());
    
    public Date firstCd4 = new Date();
    public Date firstCd8 = new Date();
    public Date firstViralLoad = new Date();
    public Date firstSeroStatus = new Date();
    
    private Table nationMapping;
    
    //for checking nation codes
    Set<String> temp;
    Map<String, ErrorTypes> lisTests = new TreeMap<String, ErrorTypes>();
    
    private ObjectMapper objMapper;
    private XmlMapper xmlMapper;
    private ObjectStore objectStore;
    
    public static void main(String [] args) {
        AutoImport ai = new AutoImport(
                new File("/home/simbre1/workspaces/regadb.import/regadb-io-db/src/net/sf/regadb/io/db/ghb/mapping/mapping.xml"),
                new File("/home/simbre1/workspaces/regadb.import/regadb-io-db/src/net/sf/regadb/io/db/ghb/mapping/LIS-nation.mapping"));
                
        ai.run(new File("/home/simbre1/import/ghb/2009-04-07"));
    }
    
    public AutoImport(File mappingFile, File nationMappingFile) {
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File logDir = RegaDBSettings.getInstance().getInstituteConfig().getLogDir();
            importLog = new FileLogger(new File(logDir.getAbsolutePath() + File.separatorChar +"ghb-not-imported-"+ date +".txt"));
            errLog = new FileLogger(new File(logDir.getAbsolutePath() + File.separatorChar +"ghb-error-log-"+ date +".txt"));
            
            xmlMapper = new XmlMapper(mappingFile);
            
            Login login = Login.authenticate("admin", "admin");
            objectStore = new DbObjectStore(login);
            
            objMapper = new ObjectMapper(objectStore, xmlMapper);
            
            nationMapping = Utils.readTable(nationMappingFile.getAbsolutePath());
            
            temp = new HashSet<String>();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public TestNominalValue getNominalValue(TestType tt, String str){
        for(TestNominalValue tnv : tt.getTestNominalValues()){
            if(tnv.getTestType().equals(tt) && tnv.getValue().equals(str)){
                return tnv;
            }
        }
        return null;
    }
    
    public void run(File dir) {
                
        File[] files = dir.listFiles();
        for(final File f : files) {
            if(f.getAbsolutePath().endsWith(".txt") && f.getName().startsWith("GHB")){
                parse(f);
            }
        }
        
        for(String s: temp) {
            System.err.println(s);
        }
        
        logInfo("Tests summary ------");
        for(Map.Entry<String, ErrorTypes> me : lisTests.entrySet())
            logInfo(me.getKey() +": \t"+ me.getValue());
        logInfo("------");
        
        importLog.close();
        errLog.close();
    }
    
    private void parse(File file){
        logInfo("\nProcessing file: "+ file.getAbsolutePath());
        try {
            InputStream is = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = br.readLine();
            if(line != null){
                Map<String, Integer> headers = new HashMap<String, Integer>(); 
                int i=0;
                for(String s : tokenizeTab(line))
                    headers.put(s, i++);
            
                int n = 1;
                while((line = br.readLine()) != null) {
                    String[] fields = tokenizeTab(line);
                    
                    if(fields.length > 0 && headers.get(fields[0]) == null){
                        Patient p = handlePatient(headers, fields, n);
                        if(p != null)
                            handleTest(p, headers, fields, n);
                        objectStore.commit();
                    }
                    ++n;
                }
            }
            
            br.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Patient handlePatient(Map<String,Integer> headers, String[] line, int lineNumber) {
        String ead = line[headers.get("EADnr")];
        if(ead == null || ead.length() == 0)
            return null;
        String emd = line[headers.get("EMDnr")];
        Date birthDate = null;
        try {
            birthDate = GhbUtils.LISDateFormat.parse(line[headers.get("geboortedatum")]);
        } catch (ParseException e) {
           e.printStackTrace();
        }
        char sex = line[headers.get("geslacht")].toUpperCase().charAt(0);
        String nation = line[headers.get("nation")].toUpperCase();
        
        
        Patient p = getPatient(ead);
        if(p==null){
            p = createPatient(ead);
        }
        
        Attribute emdAttribute = objectStore.getAttribute("EMD Number", "UZ Leuven");
        Attribute genderAttribute = objectStore.getAttribute(
                StandardObjects.getGenderAttribute().getName(),
                StandardObjects.getGenderAttribute().getAttributeGroup().getGroupName());
        Attribute birthDateAttribute = objectStore.getAttribute("Birth date","Personal");

        if(!containsAttribute(emdAttribute, p))
            p.createPatientAttributeValue(emdAttribute).setValue(emd);
        if(!containsAttribute(genderAttribute, p)){
            try {
                handleNominalAttributeValue(p, genderAttribute.getName(), sex + "");
            } catch (MappingException e) {
                e.printStackTrace();
            }
        }
        if(!containsAttribute(birthDateAttribute, p))
            p.createPatientAttributeValue(birthDateAttribute).setValue(birthDate.getTime()+"");
        
        if(mapCountry(nation)==null) {
            temp.add(nation);
        }
        
        return p;
    }
    
    public boolean containsAttribute(Attribute a, Patient p) {
        String name = a.getName();
        for(PatientAttributeValue pav : p.getPatientAttributeValues()) {
            if(pav.getAttribute().getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public String mapCountry(String code) {
        for(int i = 1; i<nationMapping.numRows(); i++) {
            if(nationMapping.valueAt(0, i).equals(code)) {
                return nationMapping.valueAt(1, i);
            }
        }
        return null;
    }
    
    private void handleTest(Patient p, Map<String,Integer> headers, String[] line, int lineNumber) {
        String correctId = getCorrectSampleId(headers, line);
        Date sampleDate = null;
        String aanvraagTestNaam = line[headers.get("aanvraagTestNaam")];
        
        try {
            sampleDate = GhbUtils.LISDateFormat.parse(line[headers.get("afname")]);
            if(sampleDate.before(earliestDate)) {
                earliestDate = sampleDate;
            }
            if(!lisTests.containsKey(aanvraagTestNaam))
                lisTests.put(aanvraagTestNaam,new ErrorTypes());
            
            String reeel = line[headers.get("reeel")];
            String resultaat = line[headers.get("resultaat")];
            
            //work with a mapping files
            if((reeel != null && reeel.length() > 0)
                    || (resultaat != null && resultaat.length() > 0)) {
                
                Map<String,String> variables = new HashMap<String,String>();
                
                variables.put("reeel", reeel);
                variables.put("resultaat",resultaat);
                variables.put("elementNaam", line[headers.get("elementNaam")]);
                variables.put("aanvraagTestNaam", aanvraagTestNaam);
                variables.put("relatie", line[headers.get("relatie")]);
                variables.put("eenheden", line[headers.get("eenheden")]);
                variables.put("relatie+reeel", line[headers.get("relatie")]+reeel);
                
                long ul = 0;
                long geheel = 0;
                try{
                    double dreeel = Double.parseDouble(reeel);
                    ul = Math.round(dreeel * 1000);
                    geheel = Math.round(dreeel);
                }
                catch(Exception e){
                }
                variables.put("reeel*1000", ""+ul);
                variables.put("geheel", ""+ geheel);
                variables.put("relatie+geheel", line[headers.get("relatie")]+geheel);

                
                TestResult tr = null;
                try {
                    tr = objMapper.getTestResult(variables);
                    tr.setSampleId(correctId);
                    tr.setTestDate(sampleDate);
                    
                    if(duplicateTestResult(p, tr)){
                        logError(lineNumber, "Duplicate test result ignored");
                        return;
                    }
                    
                    p.addTestResult(tr);
                }
                catch(MappingDoesNotExistException e){
                    logError(lineNumber, e.getMessage());
                    lisTests.get(aanvraagTestNaam).mapping = true;
                    logNotImported(toString(line));
                }
                catch(ObjectDoesNotExistException e){
                    logError(lineNumber, e.getMessage());
                    lisTests.get(aanvraagTestNaam).object = true;
                    logNotImported(toString(line));
                }
                catch(InvalidValueException e){
                    logError(lineNumber, e.getMessage());
                    lisTests.get(aanvraagTestNaam).value = true;
                    logNotImported(toString(line));
                }
                catch (MappingException e) {
                    logError(lineNumber, "MappingException: "+ e.getMessage());
                    logNotImported(toString(line));
                }
                catch (Exception e){
                    logError(lineNumber, "Exception: "+ e.getMessage());
                    logNotImported(toString(line));
                }
            }
            else{
                logError(lineNumber, "No result");
            }
        } catch (ParseException e) {
            logNotImported(toString(line));
            logError("ParseException at line "+ lineNumber +": "+ e.getMessage());
        }
    }
       
    private String getCorrectSampleId(Map<String,Integer> headers, String[] line) {
        String id = line[headers.get("otheeknr")];
        if(id.equals("")) {
            id = line[headers.get("staalId")];
        }
        if(id.equals("")) {
            id = line[headers.get("metingId")];
        }
        if(id.equals("")) {
           id = line[headers.get("berekeningId")];
        }

        return id;
    }
    
    private void handleNominalAttributeValue(Patient p, String name, String nominalValue) throws MappingException {
        try {
            Map<String,String> variables = new HashMap<String,String>();
            variables.put("name", name);
            variables.put("value", nominalValue);
            PatientAttributeValue pav = objMapper.getAttributeValue(variables);
            p.addPatientAttributeValue(pav);

        } catch (ObjectDoesNotExistException e) {
            ConsoleLogger.getInstance().logWarning("Unsupported attribute value" + name + ": "+nominalValue);
        }
    }
    
    private String[] tokenizeTab(String line) {
        return line.split("\t");
    }
    
    private boolean duplicateTestResult(Patient p, TestResult result) {
        for(TestResult tr : p.getTestResults()) {
            if(tr.getTest().getDescription().equals(result.getTest().getDescription()) &&
                    tr.getTestDate().equals(result.getTestDate()) &&
                    tr.getSampleId().equals(result.getSampleId())) {
                if(tr.getTestNominalValue() == null)
                    return tr.getValue().equals(result.getValue());
                else if(result.getTestNominalValue() != null)
                    return tr.getTestNominalValue().getValue().equals(result.getTestNominalValue().getValue());
                return false;
            }
        }
        return false;
    }
    
    private Patient getPatient(String ead){
        Dataset dataset = objectStore.getDataset("GHB");
        return objectStore.getPatient(dataset, ead);
    }
    private Patient createPatient(String ead){
        Dataset dataset = objectStore.getDataset("GHB");
        return objectStore.createPatient(dataset, ead);
    }
    
    private void logNotImported(String msg){
        importLog.println(msg);
    }
    private void logError(String msg){
        errLog.println(msg);
    }
    private void logError(int lineNumber, String msg){
        logError(lineNumber +": "+ msg);
    }
    private void logInfo(String msg){
        System.out.println(msg);
        logError(msg);
    }
    
    private String toString(String[] line){
        StringBuilder sb = new StringBuilder();
        for(String s : line){
            sb.append(s);
            sb.append('\t');
        }
        return sb.toString();
    }
}