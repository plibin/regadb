package net.sf.regadb.db;

// Generated Jan 11, 2007 2:11:17 PM by Hibernate Tools 3.2.0.beta8

import java.util.HashSet;
import java.util.Set;

/**
 * DrugGeneric generated by hbm2java
 */
public class DrugGeneric implements java.io.Serializable {

    // Fields    

    private Integer genericIi;

    private Integer version;

    private DrugClass drugClass;

    private String genericId;

    private String genericName;

    private Set<DrugCommercial> drugCommercials = new HashSet<DrugCommercial>(0);

    // Constructors

    /** default constructor */
    public DrugGeneric() {
    }

    /** minimal constructor */
    public DrugGeneric(DrugClass drugClass, String genericId, String genericName) {
        this.drugClass = drugClass;
        this.genericId = genericId;
        this.genericName = genericName;
    }

    /** full constructor */
    public DrugGeneric(DrugClass drugClass, String genericId,
            String genericName, Set<DrugCommercial> drugCommercials) {
        this.drugClass = drugClass;
        this.genericId = genericId;
        this.genericName = genericName;
        this.drugCommercials = drugCommercials;
    }

    // Property accessors
    public Integer getGenericIi() {
        return this.genericIi;
    }

    public void setGenericIi(Integer genericIi) {
        this.genericIi = genericIi;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public DrugClass getDrugClass() {
        return this.drugClass;
    }

    public void setDrugClass(DrugClass drugClass) {
        this.drugClass = drugClass;
    }

    public String getGenericId() {
        return this.genericId;
    }

    public void setGenericId(String genericId) {
        this.genericId = genericId;
    }

    public String getGenericName() {
        return this.genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public Set<DrugCommercial> getDrugCommercials() {
        return this.drugCommercials;
    }

    public void setDrugCommercials(Set<DrugCommercial> drugCommercials) {
        this.drugCommercials = drugCommercials;
    }

}
