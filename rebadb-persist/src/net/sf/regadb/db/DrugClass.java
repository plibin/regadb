package net.sf.regadb.db;

// Generated Jan 3, 2007 4:02:11 PM by Hibernate Tools 3.2.0.beta8

import java.util.HashSet;
import java.util.Set;

/**
 * DrugClass generated by hbm2java
 */
public class DrugClass implements java.io.Serializable {

    // Fields    

    private Integer classIi;

    private Integer version;

    private String classId;

    private String className;

    private Set<DrugGeneric> drugGenerics = new HashSet<DrugGeneric>(0);

    // Constructors

    /** default constructor */
    public DrugClass() {
    }

    /** minimal constructor */
    public DrugClass(String classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    /** full constructor */
    public DrugClass(String classId, String className,
            Set<DrugGeneric> drugGenerics) {
        this.classId = classId;
        this.className = className;
        this.drugGenerics = drugGenerics;
    }

    // Property accessors
    public Integer getClassIi() {
        return this.classIi;
    }

    public void setClassIi(Integer classIi) {
        this.classIi = classIi;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getClassId() {
        return this.classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Set<DrugGeneric> getDrugGenerics() {
        return this.drugGenerics;
    }

    public void setDrugGenerics(Set<DrugGeneric> drugGenerics) {
        this.drugGenerics = drugGenerics;
    }

}
