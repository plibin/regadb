package net.sf.regadb.io.db.util.mapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
import net.sf.regadb.db.ValueType;
import net.sf.regadb.io.db.util.Utils;
import net.sf.regadb.io.util.StandardObjects;

public class OfflineObjectStore extends ObjectStore{
	Map<String, Dataset> datasets = new HashMap<String, Dataset>();
	Map<String, Patient> patients = new HashMap<String, Patient>();
	Map<String, AttributeGroup> attributeGroups = new HashMap<String, AttributeGroup>();
	Map<String, ValueType> valueTypes = new HashMap<String, ValueType>();
	Map<String, Attribute> attributes = new HashMap<String, Attribute>();
	Map<String, AttributeNominalValue> attributeNominalValues = new HashMap<String, AttributeNominalValue>();
	Map<String, Genome> genomes = new HashMap<String, Genome>();
	Map<String, TestType> testTypes = new HashMap<String, TestType>();
	Map<String, Test> tests = new HashMap<String, Test>();
	Map<String, TestNominalValue> testNominalValues = new HashMap<String, TestNominalValue>();
	Map<String, TestObject> testObjects = new HashMap<String, TestObject>();
	Map<String, Event> events = new HashMap<String, Event>();
	Map<String, EventNominalValue> eventNominalValues = new HashMap<String, EventNominalValue>();

	Map<String, DrugGeneric> drugGenerics = null;
	Map<String, DrugCommercial> drugCommercials = null;
	
	public OfflineObjectStore(){
		loadStandardObjects();
	}
	
	private void loadStandardObjects(){
		System.out.println("load standard objects");
		for(Attribute a : StandardObjects.getAttributes()){
			attributes.put(key(a), a);
			for(AttributeNominalValue anv : a.getAttributeNominalValues())
				attributeNominalValues.put(key(anv), anv);
		}
		
		for(Genome g : StandardObjects.getGenomes())
			genomes.put(key(g), g);
		
		for(TestType tt : StandardObjects.getTestTypes()){
			testTypes.put(key(tt), tt);
			for(TestNominalValue tnv : tt.getTestNominalValues())
				testNominalValues.put(key(tnv), tnv);
		}
		
		for(Test t : StandardObjects.getTests())
			tests.put(key(t), t);
		
		for(ValueType vt : StandardObjects.getValueTypes())
			valueTypes.put(key(vt), vt);
		
		for(AttributeGroup ag : StandardObjects.getAttributeGroups())
			attributeGroups.put(key(ag), ag);
		
		for(TestObject to : StandardObjects.getTestObjects())
			testObjects.put(key(to), to);
		
		for(Event e : StandardObjects.getEvents()){
			events.put(key(e), e);
			for(EventNominalValue env : e.getEventNominalValues())
				eventNominalValues.put(key(env), env);
		}

	}

	@Override
	public Patient createPatient(Dataset dataset, String id) {
		Patient p = new Patient();
		if(dataset != null)
			p.addDataset(dataset);
		p.setPatientId(id);
		patients.put(key(p),p);
		return p;
	}

	@Override
	public Attribute getAttribute(String name, String group) {
		return attributes.get(implode(group,name));
	}

	@Override
	public AttributeNominalValue getAttributeNominalValue(Attribute attribute,
			String value) {
		return attributeNominalValues.get(implode(key(attribute),value));
	}

	@Override
	public Dataset getDataset(String description) {
		return datasets.get(description);
	}

	@Override
	public Patient getPatient(Dataset dataset, String id) {
		return patients.get(id);
	}

	@Override
	public Test getTest(String description, String testTypeDescription,
			String organismName) {
		return tests.get(implode(organismName,testTypeDescription,description));
	}

	@Override
	public TestNominalValue getTestNominalValue(TestType testType, String value) {
		return testNominalValues.get(implode(key(testType),value));
	}

	@Override
	public TestType getTestType(String description, String organismName) {
		return testTypes.get(implode(organismName,description));
	}
	
	@Override
	public Test createTest(TestType testType, String description){
		Test t = super.createTest(testType, description);
		tests.put(key(t), t);
		return t;
	}
	
	@Override
	public TestType createTestType(String description, TestObject testObject, Genome genome, ValueType valueType){
		TestType tt = super.createTestType(description, testObject, genome, valueType);
		testTypes.put(key(tt),tt);
		return tt;
	}
	
	public AttributeNominalValue createAttributeNominalValue(Attribute attribute, String value){
		AttributeNominalValue anv = super.createAttributeNominalValue(attribute, value);
		attributeNominalValues.put(key(anv), anv);
		return anv;
	}
	
	public TestNominalValue createTestNominalValue(TestType testType, String value){
		TestNominalValue tnv = super.createTestNominalValue(testType, value);
		testNominalValues.put(key(tnv), tnv);
		return tnv;
	}
	
	private String implode(String... strings){
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(String s : strings){
			sb.append(s == null ? "" : s);
			if(!first)
				sb.append('\t');
			else
				first = false;
		}
		return sb.toString();
	}
	
	private String key(Dataset dataset){
		return dataset.getDescription();
	}
	private String key(Patient p){
		return p.getPatientId();
	}
	private String key(Attribute a){
		return implode(a.getAttributeGroup().getGroupName(),a.getName());
	}
	private String key(AttributeNominalValue anv){
		return implode(key(anv.getAttribute()),anv.getValue());
	}
	private String key(TestType tt){
		return implode(key(tt.getGenome()),tt.getDescription());
	}
	private String key(Test t){
		return implode(key(t.getTestType()),t.getDescription());
	}
	private String key(TestNominalValue tnv){
		return implode(key(tnv.getTestType()),tnv.getValue());
	}
	private String key(AttributeGroup ag){
		return ag.getGroupName();
	}
	private String key(ValueType vt){
		return vt.getDescription();
	}
	private String key(Genome g){
		return g == null ? "":g.getOrganismName();
	}
	private String key(TestObject to){
		return to.getDescription();
	}
	private String key(Event e){
		return e.getName();
	}
	private String key(EventNominalValue env){
		return implode(key(env.getEvent()),env.getValue());
	}
	
	public void setPatients(Map<String, Patient> patients){
		this.patients = patients;
	}
	public Collection<Patient> getPatients(){
		return patients.values();
	}


	@Override
	public Attribute createAttribute(AttributeGroup attributeGroup,
			ValueType valueType, String name) {
		Attribute a = new Attribute(name);
		a.setAttributeGroup(attributeGroup);
		a.setValueType(valueType);
		attributes.put(key(a),a);
		return a;
	}

	@Override
	public AttributeGroup createAttributeGroup(String name) {
		AttributeGroup ag = new AttributeGroup(name);
		attributeGroups.put(key(ag),ag);
		return ag;
	}

	@Override
	public AttributeGroup getAttributeGroup(String name) {
		return attributeGroups.get(name);
	}

	@Override
	public ValueType getValueType(String description) {
		return valueTypes.get(description);
	}
	
	public void addDataset(Dataset dataset){
		datasets.put(key(dataset), dataset);
	}

	@Override
	public Genome getGenome(String organismName) {
		return genomes.get(organismName);
	}

	@Override
	public TestObject getTestObject(String description) {
		return testObjects.get(description);
	}

	@Override
	public void close() {
	}

	@Override
	public DrugGeneric getDrugGeneric(String genericId) {
		if(drugGenerics == null){
			drugGenerics = new HashMap<String, DrugGeneric>();
			for(DrugGeneric dg : Utils.prepareRegaDrugGenerics())
				drugGenerics.put(dg.getGenericId(), dg);
		}
		
		return drugGenerics.get(genericId);
	}

	@Override
	public DrugCommercial getDrugCommercial(String name) {
		if(drugCommercials == null){
			drugCommercials = new HashMap<String, DrugCommercial>();
			for(DrugCommercial dc : Utils.prepareRegaDrugCommercials())
				drugCommercials.put(dc.getName(), dc);
		}
		
		return drugCommercials.get(name);
	}

	@Override
	public Event getEvent(String name) {
		return events.get(name);
	}

	@Override
	public Event createEvent(ValueType valueType, String name) {
		Event e = new Event(name);
		e.setValueType(valueType);
		events.put(key(e),e);
		return e;
	}

	@Override
	public EventNominalValue getEventNominalValue(Event event, String value) {
		return eventNominalValues.get(implode(key(event),value));
	}
}
