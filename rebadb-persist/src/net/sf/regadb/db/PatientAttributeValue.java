package net.sf.regadb.db;

// Generated Jan 3, 2007 3:29:22 PM by Hibernate Tools 3.2.0.beta8

/**
 * PatientAttributeValue generated by hbm2java
 */
public class PatientAttributeValue implements java.io.Serializable {

    // Fields    

    private PatientAttributeValueId id;

    private Integer version;

    private Attribute attribute;

    private PatientImpl patient;

    private String value;

    private Integer nominalValueIi;

    // Constructors

    /** default constructor */
    public PatientAttributeValue() {
    }

    /** minimal constructor */
    public PatientAttributeValue(PatientAttributeValueId id,
            Attribute attribute, PatientImpl patient) {
        this.id = id;
        this.attribute = attribute;
        this.patient = patient;
    }

    /** full constructor */
    public PatientAttributeValue(PatientAttributeValueId id,
            Attribute attribute, PatientImpl patient, String value,
            Integer nominalValueIi) {
        this.id = id;
        this.attribute = attribute;
        this.patient = patient;
        this.value = value;
        this.nominalValueIi = nominalValueIi;
    }

    // Property accessors
    public PatientAttributeValueId getId() {
        return this.id;
    }

    public void setId(PatientAttributeValueId id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Attribute getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public PatientImpl getPatient() {
        return this.patient;
    }

    public void setPatient(PatientImpl patient) {
        this.patient = patient;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getNominalValueIi() {
        return this.nominalValueIi;
    }

    public void setNominalValueIi(Integer nominalValueIi) {
        this.nominalValueIi = nominalValueIi;
    }

}
