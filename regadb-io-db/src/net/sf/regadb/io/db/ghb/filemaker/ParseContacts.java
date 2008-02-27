package net.sf.regadb.io.db.ghb.filemaker;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import net.sf.regadb.csv.Table;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.TestNominalValue;
import net.sf.regadb.db.TestResult;
import net.sf.regadb.io.db.util.Utils;
import net.sf.regadb.io.util.StandardObjects;

public class ParseContacts {
    //public Map<String, List<TestResult>> fileMakerTests = new HashMap<String, List<TestResult>>();
    private static DateFormat filemakerDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    private TestNominalValue posSeroStatus_ = Utils.getNominalValue(StandardObjects.getHivSeroStatusTestType(), "Positive");
    
    Date firstCd4_;
    Date firstCd8_;
    Date firstViralLoad_;
    
    public ParseContacts(Date firstCd4, Date firstCd8, Date firstViralLoad) {
        firstCd4_ = firstCd4;
        firstCd8_ = firstCd8;
        firstViralLoad_ = firstViralLoad;
    }
        
    public void run(Map<String,Patient> patients) {
        Table contacts = null;
        try {
             contacts = new Table(new InputStreamReader(new BufferedInputStream(new FileInputStream("/home/plibin0/import/ghb/filemaker/contacten.csv"))), false, ';');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        int CHIVPos = Utils.findColumn(contacts, "HIV_Status_Staal");
        int CCD4 = Utils.findColumn(contacts, "CD4_Absoluut");
        int CCD8 = Utils.findColumn(contacts, "CD8_Absoluut");
        int CViralLoad = Utils.findColumn(contacts, "Viral_Load_Absoluut");
        int CPatientId = Utils.findColumn(contacts, "Patient_ID");
        int CDate = Utils.findColumn(contacts, "Datum");
        
        for(int i = 1; i<contacts.numRows(); i++) {
            String patientId = contacts.valueAt(CPatientId, i);
            Date date = null;
            try {
                date = filemakerDateFormat.parse(contacts.valueAt(CDate, i).replace('/', '-'));
            } catch (ParseException e) {
                
            }
            if(!"".equals(patientId) && date!=null) {
                Patient p = patients.get(patientId);
                if(p!=null) {
                    if(!contacts.valueAt(CCD4, i).equals("")) {
                        try{
                        double cd4 = Double.parseDouble(contacts.valueAt(CCD4, i).replace(',', '.'));
                        storeCD4(date, cd4, null, p);
                        } catch(NumberFormatException nfe) {
                            System.err.println("Cannot parse cd4 value " + contacts.valueAt(CCD4, i));
                        }
                    }
                    
                    if(!contacts.valueAt(CCD8, i).equals("")) {
                        try{
                            double cd8 = Double.parseDouble(contacts.valueAt(CCD8, i).replace(',', '.'));
                            storeCD8(date, cd8, null, p);
                            } catch(NumberFormatException nfe) {
                                System.err.println("Cannot parse cd8 value " + contacts.valueAt(CCD8, i));
                            }
                    }
                    
                    if(!contacts.valueAt(CViralLoad, i).equals(""))
                        storeViralLoad(date, contacts.valueAt(CViralLoad, i), null, p);
                    
                    String sero = contacts.valueAt(CHIVPos, i);
                    if(!sero.equals("")) {
                        if(sero.toLowerCase().contains("hiv") && sero.toLowerCase().contains("positief")) {
                            storePosSero(date, null, p);
                        }
                    }
                } else {
                    System.err.println("invalid patientId: " + patientId);
                }
            } else {
                System.err.println("Cannot parse contact, no date or wrong patientId");
            }
        }
    }
    
    private void storeCD4(Date date, double value, String sampleId, Patient p) {
        if(date.before(firstCd4_)) {
            TestResult t = p.createTestResult(StandardObjects.getGenericCD4Test());
            t.setValue(value+"");
            t.setTestDate(date);
            t.setSampleId(sampleId);
        }
    }
    
    private void storeCD8(Date date, double value, String sampleId, Patient p) {
        if(date.before(firstCd8_)) {
            TestResult t = p.createTestResult(StandardObjects.getGenericCD8Test());
            t.setValue(value+"");
            t.setTestDate(date);
            t.setSampleId(sampleId);
        }
    }
    
    private void storePosSero(Date date, String sampleId, Patient p) {
        TestResult t = p.createTestResult(StandardObjects.getGenericHivSeroStatusTest());
        t.setTestNominalValue(posSeroStatus_);
        t.setTestDate(date);
        t.setSampleId(sampleId);
    }
    
    private String removeCharsFromString(String src, char toRemove) {
        String toReturn = "";
        for(int i = 0; i<src.length(); i++) {
            if(src.charAt(i)!=toRemove) 
                toReturn += src.charAt(i);
        }
        return toReturn;
    }
    
    private void storeViralLoad(Date date, String value, String sampleId, Patient p) {
        String parsedValue = null;
        char sensChar = ' ';
        if(!Character.isDigit(value.charAt(0))) {
            sensChar = value.charAt(0);
        }

        try {
            if(sensChar==' ') {
                parsedValue = Double.parseDouble(removeCharsFromString(value,' ')) + "";
            }
            else {
                parsedValue = Character.toString(sensChar) + Double.parseDouble(removeCharsFromString(value.substring(1, value.length()), ' ')) + "";
                if(sensChar == '>' || sensChar == '<' || sensChar=='=') {
                    
                } else {
                    parsedValue = null;
                }
            }
        } catch(NumberFormatException nfe) {

        }
        
        if(date.before(firstViralLoad_)) {
            if(parsedValue!=null) {
                TestResult t = p.createTestResult(StandardObjects.getGenericViralLoadTest());
                t.setValue(parsedValue+"");
                t.setTestDate(date);
                t.setSampleId(sampleId);
            } else {
                System.err.println("Cannot parse viral load value: " + value);
            }    
        }
    }
    
    public static void main(String [] args) {
        //ParseContacts pc = new ParseContacts();
        //pc.run();
    }
}