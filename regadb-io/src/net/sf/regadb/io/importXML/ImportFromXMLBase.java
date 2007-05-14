/*
 * Created on May 11, 2007
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.sf.regadb.io.importXML;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.regadb.db.DrugCommercial;
import net.sf.regadb.db.DrugGeneric;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.Protein;
import net.sf.regadb.db.Transaction;
import net.sf.regadb.io.exportXML.ExportToXML;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ImportFromXMLBase extends DefaultHandler{
    protected Patient patient = null;
    protected String value = null;
    private DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private Map<String, DrugGeneric> genericDrugs;
    private Map<String, DrugCommercial> commercialDrugs;
    private Map<String, Protein> proteins;
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (value == null)
            value = new String(ch, start, length);
        else
            value += new String(ch, start, length);
    }

    protected Date parseDate(String value) throws SAXException {
        try {
            return dateFormatter.parse(value);
        } catch (ParseException e) {
            throw new SAXException(new ImportException("Cannot parse date: " + value));
        }
    }

    protected String parseString(String value) {
        return value != null ? value.trim() : null;
    }

    protected Boolean parseBoolean(String value) {
        return value != null ? Boolean.parseBoolean(value) : null;
    }

    protected Double parseDouble(String value) {
        return value != null ? Double.parseDouble(value) : null;
    }

    protected short parseshort(String value) {
        return value != null ? Short.parseShort(value) : 0;
    }

    protected Integer parseInteger(String value) {
        return value != null ? Integer.parseInt(value) : null;
    }

    protected Integer nullValueInteger() {
        return null;
    }

    protected Boolean nullValueBoolean() {
        return null;
    }

    protected Double nullValueDouble() {
        return null;
    }

    protected short nullValueshort() {
        return 0;
    }

    protected Date nullValueDate() {
        return null;
    }

    protected String nullValueString() {
        return null;
    }

    protected DrugGeneric resolveDrugGeneric(String value) throws SAXException {
        DrugGeneric result = genericDrugs.get(value);
        if (result == null)
            throw new SAXException(new ImportException("Could not resolve generic drug: '" + value + "'"));
        else
            return result;
    }

    protected Protein resolveProtein(String value) throws SAXException {
        Protein result = proteins.get(value);
        if (result == null)
            throw new SAXException(new ImportException("Could not resolve protein: '" + value + "'"));
        else
            return result;
    }

    protected DrugCommercial resolveDrugCommercial(String value) throws SAXException {
        DrugCommercial result = commercialDrugs.get(value);
        if (result == null)
            throw new SAXException(new ImportException("Could not resolve commercial drug: '" + value + "'"));
        else
            return result;
    }

    public void loadDatabaseObjects(Transaction t) {
        genericDrugs = new TreeMap<String, DrugGeneric>();
        for (DrugGeneric d : t.getGenericDrugs()) {
            genericDrugs.put(d.getGenericId(), d);
        }

        commercialDrugs = new TreeMap<String, DrugCommercial>();
        for (DrugCommercial d : t.getCommercialDrugs()) {
            commercialDrugs.put(d.getName(), d);
        }

        proteins = new TreeMap<String, Protein>();
        for (Protein p : t.getProteins()) {
            proteins.put(p.getAbbreviation(), p);
        }
    }
    
    protected void importPatient(Patient patient) {
        ExportToXML l = new ExportToXML();
        Element root = new Element("Patients");
        l.writePatient(patient, root);
        
        Document n = new Document(root);
        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());
        try {
            outputter.output(n, System.out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            System.err.println("New patient");
            outputter.output(n, System.out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
