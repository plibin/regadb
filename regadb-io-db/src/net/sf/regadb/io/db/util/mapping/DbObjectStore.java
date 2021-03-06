package net.sf.regadb.io.db.util.mapping;

import java.util.Collection;

import net.sf.regadb.db.Attribute;
import net.sf.regadb.db.AttributeGroup;
import net.sf.regadb.db.AttributeNominalValue;
import net.sf.regadb.db.Dataset;
import net.sf.regadb.db.DrugCommercial;
import net.sf.regadb.db.DrugGeneric;
import net.sf.regadb.db.Event;
import net.sf.regadb.db.EventNominalValue;
import net.sf.regadb.db.Genome;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.Test;
import net.sf.regadb.db.TestNominalValue;
import net.sf.regadb.db.TestObject;
import net.sf.regadb.db.TestType;
import net.sf.regadb.db.Transaction;
import net.sf.regadb.db.ValueType;
import net.sf.regadb.db.session.Login;

public class DbObjectStore extends ObjectStore{
    private Login login;
    private Transaction transaction;
    
    public DbObjectStore(Login login){
        setLogin(login);
        createTransaction();
    }

    private Login getLogin(){
        return login;
    }
    private void setLogin(Login login){
        this.login = login;
    }
    
    private void createTransaction(){
        setTransaction(getLogin().createTransaction());
    }

    public Transaction getTransaction(){
        return transaction;
    }
    private void setTransaction(Transaction transaction){
        this.transaction = transaction;
    }

    public Attribute getAttribute(String name, String group) {
        return getTransaction().getAttribute(name, group);
    }

    public AttributeNominalValue getAttributeNominalValue(Attribute attribute,
            String value) {
        
        return getTransaction().getAttributeNominalValue(attribute, value);
    }

    public Test getTest(String description, String testTypeDescription,
            String organismName) {
        return getTransaction().getTest(description, testTypeDescription, organismName);
    }

    public TestNominalValue getTestNominalValue(TestType testType, String value) {
        return getTransaction().getTestNominalValue(testType, value);
    }

    public TestType getTestType(String description, String organismName) {
        return getTransaction().getTestType(description, organismName);
    }

    public Patient getPatient(Dataset dataset, String id){
        return getTransaction().getPatient(dataset, id);
    }
    
    public Patient createPatient(Dataset dataset, String id){
        Patient p = new Patient();
        p.setPatientId(id);
        p.setSourceDataset(dataset, getTransaction());
        getTransaction().save(p);
        return p;
    }
    
    public Dataset getDataset(String description){
        return getTransaction().getDataset(description);
    }

    public void commit() {
        getTransaction().commit();
        getTransaction().clearCache();
        createTransaction();
    }

	@Override
	public Attribute createAttribute(AttributeGroup attributeGroup,
			ValueType valueType, String name) {
    	Attribute a = new Attribute();
    	a.setName(name);
    	a.setAttributeGroup(attributeGroup);
    	a.setValueType(valueType);
    	getTransaction().save(a);
    	return a;
    }

	@Override
	public AttributeGroup createAttributeGroup(String name) {
		AttributeGroup ag = new AttributeGroup();
		getTransaction().save(ag);
		return ag;
	}

	@Override
	public AttributeGroup getAttributeGroup(String name) {
		return getTransaction().getAttributeGroup(name);
	}

	@Override
	public ValueType getValueType(String description) {
		return getTransaction().getValueType(description);
	}

	@Override
	public Collection<Patient> getPatients() {
		return getTransaction().getPatients();
	}

	@Override
	public Genome getGenome(String organismName) {
		return getTransaction().getGenome(organismName);
	}

	@Override
	public TestObject getTestObject(String description) {
		return getTransaction().getTestObject(description);
	}

	@Override
	public void close() {
		login.closeSession();
	}

	@Override
	public DrugGeneric getDrugGeneric(String genericId) {
		return getTransaction().getDrugGeneric(genericId);
	}

	@Override
	public DrugCommercial getDrugCommercial(String name) {
		return getTransaction().getDrugCommercial(name);
	}

	@Override
	public Event getEvent(String name) {
		return getTransaction().getEvent(name);
	}

	@Override
	public Event createEvent(ValueType valueType, String name) {
		Event e = new Event();
		e.setName(name);
		e.setValueType(valueType);
		getTransaction().save(e);
		return e;
	}

	@Override
	public EventNominalValue getEventNominalValue(Event event, String value) {
		return getTransaction().getEventNominalValue(event, value);
	}
}
