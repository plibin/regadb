package net.sf.regadb.db;

// Generated 22/01/2007 12:43:36 by Hibernate Tools 3.2.0.beta8

/**
 * TestObject generated by hbm2java
 */
public class TestObject implements java.io.Serializable {

    // Fields    

    private Integer testObjectIi;

    private Integer version;

    private String description;

    // Constructors

    /** default constructor */
    public TestObject() {
    }

    /** full constructor */
    public TestObject(String description) {
        this.description = description;
    }

    // Property accessors
    public Integer getTestObjectIi() {
        return this.testObjectIi;
    }

    public void setTestObjectIi(Integer testObjectIi) {
        this.testObjectIi = testObjectIi;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
