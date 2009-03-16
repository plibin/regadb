package net.sf.regadb.db;


import java.util.HashSet;
import java.util.Set;

/**
 * Attribute generated by hbm2java
 */
public class Attribute implements java.io.Serializable {

    private Integer attributeIi;

    private int version;

    private ValueType valueType;

    private AttributeGroup attributeGroup;

    private String name;

    private Set<AttributeNominalValue> attributeNominalValues = new HashSet<AttributeNominalValue>(
            0);

    public Attribute() {
    }

    public Attribute(String name) {
        this.name = name;
    }

    public Attribute(ValueType valueType, AttributeGroup attributeGroup,
            String name, Set<AttributeNominalValue> attributeNominalValues) {
        this.valueType = valueType;
        this.attributeGroup = attributeGroup;
        this.name = name;
        this.attributeNominalValues = attributeNominalValues;
    }

    public Integer getAttributeIi() {
        return this.attributeIi;
    }

    public void setAttributeIi(Integer attributeIi) {
        this.attributeIi = attributeIi;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public ValueType getValueType() {
        return this.valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public AttributeGroup getAttributeGroup() {
        return this.attributeGroup;
    }

    public void setAttributeGroup(AttributeGroup attributeGroup) {
        this.attributeGroup = attributeGroup;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AttributeNominalValue> getAttributeNominalValues() {
        return this.attributeNominalValues;
    }

    public void setAttributeNominalValues(
            Set<AttributeNominalValue> attributeNominalValues) {
        this.attributeNominalValues = attributeNominalValues;
    }

}
