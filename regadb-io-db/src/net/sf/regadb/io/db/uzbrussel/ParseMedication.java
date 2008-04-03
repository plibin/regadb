package net.sf.regadb.io.db.uzbrussel;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import net.sf.regadb.csv.Table;
import net.sf.regadb.db.DrugCommercial;
import net.sf.regadb.db.DrugGeneric;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.Therapy;
import net.sf.regadb.db.TherapyCommercial;
import net.sf.regadb.db.TherapyCommercialId;
import net.sf.regadb.db.TherapyGeneric;
import net.sf.regadb.db.TherapyGenericId;
import net.sf.regadb.io.db.util.ConsoleLogger;
import net.sf.regadb.io.db.util.Utils;

import org.jdom.Element;

public class ParseMedication {
    private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    
    private static List<DrugCommercial> drugCommercials;
    private static List<DrugCommercial> commercialDrugsNoDosages = new ArrayList<DrugCommercial>();
    private static List<DrugGeneric> drugGenerics;
    
    private static List<String> medicationToIgnore_ = new ArrayList<String>();
    
    public static String mappingPath;
    
    static Set<String> notParsableMeds = new HashSet<String>();
    
    public static Map<String, String> commercialDrugMappings = new HashMap<String, String>();
    
    public static void init() {
        drugCommercials = Utils.prepareRegaDrugCommercials();
        
        for(DrugCommercial dc : drugCommercials) {
            if(!dc.getName().contains("mg") && !dc.getName().contains("ml"))
                commercialDrugsNoDosages.add(dc);
        }
        
        drugGenerics = Utils.prepareRegaDrugGenerics();
        
        Table medicationToIgnore = Utils.readTable(mappingPath + File.separatorChar + "medicationToIgnore");
        for(int i = 0; i<medicationToIgnore.numRows(); i++) {
            medicationToIgnore_.add(medicationToIgnore.valueAt(0, i).trim());
        }
        
        Table commercialMedication = Utils.readTable(mappingPath + File.separatorChar + "commercialMedication.mapping");
        for(int i = 1; i<commercialMedication.numRows(); i++) {
            commercialDrugMappings.put(commercialMedication.valueAt(0, i).trim(),commercialMedication.valueAt(1, i).trim());
        }
    }

    public static void parseTherapy(Element therapyEl, Patient p ) {
        List<Medication> medications = new ArrayList<Medication>();
        for(Object medication : therapyEl.getChildren("Medication")) {
            Element medicationEl = (Element)medication;
            String quantity = text(medicationEl, "Quantity");
            String startDate = text(medicationEl, "StartDate");
            String stopDate = text(medicationEl, "StopDate");
            String name = medicationEl.getAttributeValue("Name");
            String unit = medicationEl.getAttributeValue("Unit");
            Double quantityD = null;
            try {
            if(quantity!=null)
                quantityD = Double.parseDouble(quantity);
            } catch (NumberFormatException nfe) {
                quantityD = null;
            }
            if(name!=null && !canIgnoreMedication(name)) {
                DrugCommercial dc = getCommercialDrug(name);
                DrugGeneric dg = getGenericDrug(name);
                if(dc!=null || dg!=null) {
                    medications.add(createMedication(startDate, stopDate, quantityD, dc, dg));
                } else {
                    boolean found = false;
                    for(DrugCommercial dc2 : getCommercialMapping(name)) {
                        medications.add(createMedication(startDate, stopDate, quantityD, dc2, null));
                        found = true;
                    }
                    if(!found)
                        notParsableMeds.add(name);
                }
            }
        }
        
        List<Therapy> therapies = createTherapies(p, medications);
        printTherapies(therapies);
    }
    
    private static List<Therapy> createTherapies(Patient p, List<Medication> meds) {
        List<Therapy> therapies = new ArrayList<Therapy>();
        
        for(Medication m : meds) {
            Therapy t = getTherapy(m, therapies);
            if(t==null) {
                t = p.createTherapy(m.start);
                t.setStopDate(m.stop);
                therapies.add(t);
            }
            if(m.dc!=null) {
                boolean duplicate = false;
                for(TherapyCommercial tc : t.getTherapyCommercials()) {
                    if(tc.getId().getDrugCommercial().getName().equals(m.dc.getName())) {
                        duplicate = true;
                    }
                }
                if(!duplicate) {
                    TherapyCommercial tc = new TherapyCommercial(new TherapyCommercialId(t, m.dc),false,false);
                    t.getTherapyCommercials().add(tc);
                }
            } else if(m.dg!=null) {
                boolean duplicate = false;
                for(TherapyGeneric tg : t.getTherapyGenerics()) {
                    if(tg.getId().getDrugGeneric().getGenericId().equals(m.dg.getGenericId())) {
                        duplicate = true;
                    }
                }
                if(!duplicate) {
                TherapyGeneric tg = new TherapyGeneric(new TherapyGenericId(t, m.dg),false,false);
                t.getTherapyGenerics().add(tg);
                }
            }
        }
        
        return therapies;
    }
    
    private static void printTherapies(List<Therapy> therapies) {
        List<String> lines = new ArrayList<String>();
        
        for(Therapy t : therapies) {
            String l = dateFormatter.format(t.getStartDate()) + "-" + (t.getStopDate()==null?"":dateFormatter.format(t.getStopDate())) + " ";
            for(TherapyCommercial tc : t.getTherapyCommercials()) {
                for(DrugGeneric dg : tc.getId().getDrugCommercial().getDrugGenerics()) {
                    l += dg.getGenericId() + " ";
                }
            }
            for(TherapyGeneric tg : t.getTherapyGenerics()) {
                l += tg.getId().getDrugGeneric().getGenericId() + " ";
            }
            lines.add(l);
        }
        
        Collections.sort(lines);
//        System.err.println("=======================");
//        for(String l : lines) {
//            System.err.println(l);
//        }
//        System.err.println("=======================");
    }
    
    private static Therapy getTherapy(Medication m, List<Therapy> therapies) {
        for(Therapy t : therapies) {
            if(t.getStartDate().equals(m.start)) {
                if(t.getStopDate()==null) {
                    if(t.getStopDate()==m.stop) {
                        return t;
                    }
                } else {
                    if(t.getStopDate().equals(m.stop)) {
                        return t;
                    }
                }
            }
        }
        return null;
    }

    private static Medication createMedication(String startDate, String stopDate, Double quantitiy, DrugCommercial dc, DrugGeneric dg) {
        Medication med = new Medication();
        try {
            med.start = dateFormatter.parse(startDate);
        } catch (ParseException e) {
            ConsoleLogger.getInstance().logError("Cannot parse therapy startdate: " + startDate);
        }
        if(stopDate!=null) {
            try {
                med.stop = dateFormatter.parse(stopDate);
            } catch (ParseException e) {
                ConsoleLogger.getInstance().logError("Cannot parse therapy stopdate: " + stopDate);
            }
        }
        med.dc = dc;
        med.dg = dg;
        med.quantity = quantitiy;
        return med;
    }
    
    private static DrugCommercial getCommercialDrug(String name) {
        for(DrugCommercial dc : commercialDrugsNoDosages) {
            String dcName = dc.getName().split(" ")[0].trim().toLowerCase();
            for(String s : name.split(" ")){
                String nameTmp = s.trim().toLowerCase();
                if(nameTmp.equals(dcName)) {
                    return dc;
                }
            }
        }
        return null;
    }
    
    private static DrugGeneric getGenericDrug(String name) {
        for(DrugGeneric dg : drugGenerics) {
            String nameTmp = name.split(" ")[0].trim().toLowerCase();
            String dgName = dg.getGenericName().split(" ")[0].trim().toLowerCase();
            if(nameTmp.equals(dgName)) {
                return dg;
            }
        }
        return null;
    }
    
    private static List<DrugCommercial> getCommercialMapping(String name) {
        List<DrugCommercial> drugsToReturn = new ArrayList<DrugCommercial>();
        
        String mapping = commercialDrugMappings.get(name.trim());
        if(mapping==null)
            return drugsToReturn;
        
        StringTokenizer st = new StringTokenizer(mapping, "+");
        
        while(st.hasMoreTokens()) {
            String d = st.nextToken();
            DrugCommercial dc = getCommercialDrug(d);
            if(dc!=null)
                drugsToReturn.add(dc);
        }
        
        return drugsToReturn;
    }
    
    private static String text(Element el, String name) {
        if(el.getChild(name)==null)
            return null;
        String toReturn = el.getChild(name).getText();
        el.getChild(name).detach();
        return toReturn;
    }
    
    private static boolean canIgnoreMedication(String name) {
        return medicationToIgnore_.contains(name.trim());
    }
}
