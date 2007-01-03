package net.sf.regadb.db;

// Generated Jan 3, 2007 3:29:22 PM by Hibernate Tools 3.2.0.beta8

import java.util.HashSet;
import java.util.Set;

/**
 * Attribute generated by hbm2java
 */
public class Attribute implements java.io.Serializable {

    // Fields    

    private Integer attributeIi;

    private Integer version;

    private ValueType valueType;

    private String name;

    private Boolean patientAttribute;

    private Boolean sequenceAttribute;

    private String attributeGroup;

    private Set<AttributeNominalValue> attributeNominalValues = new HashSet<AttributeNominalValue>(
            0);

    // Constructors

    /** default constructor */
    public Attribute() {
    }

    /** minimal constructor */
    public Attribute(String name) {
        this.name = name;
    }

    /** full constructor */
    public Attribute(ValueType valueType, String name,
            Boolean patientAttribute, Boolean sequenceAttribute,
            String attributeGroup,
            Set<AttributeNominalValue> attributeNominalValues) {
        this.valueType = valueType;
        this.name = name;
        this.patientAttribute = patientAttribute;
        this.sequenceAttribute = sequenceAttribute;
        this.attributeGroup = attributeGroup;
        this.attributeNominalValues = attributeNominalValues;
    }

    // Property accessors
    public Integer getAttributeIi() {
        return this.attributeIi;
    }

    public void setAttributeIi(Integer attributeIi) {
        this.attributeIi = attributeIi;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public ValueType getValueType() {
        return this.valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPatientAttribute() {
        return this.patientAttribute;
    }

    public void setPatientAttribute(Boolean patientAttribute) {
        this.patientAttribute = patientAttribute;
    }

    public Boolean getSequenceAttribute() {
        return this.sequenceAttribute;
    }

    public void setSequenceAttribute(Boolean sequenceAttribute) {
        this.sequenceAttribute = sequenceAttribute;
    }

    public String getAttributeGroup() {
        return this.attributeGroup;
    }

    public void setAttributeGroup(String attributeGroup) {
        this.attributeGroup = attributeGroup;
    }

    public Set<AttributeNominalValue> getAttributeNominalValues() {
        return this.attributeNominalValues;
    }

    public void setAttributeNominalValues(
            Set<AttributeNominalValue> attributeNominalValues) {
        this.attributeNominalValues = attributeNominalValues;
    }

}
