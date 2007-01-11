package net.sf.regadb.db;

// Generated Jan 11, 2007 2:11:17 PM by Hibernate Tools 3.2.0.beta8

/**
 * AaInsertion generated by hbm2java
 */
public class AaInsertion implements java.io.Serializable {

    // Fields    

    private AaInsertionId id;

    private Integer version;

    private AaSequence aaSequence;

    private String aaInsertion;

    private String ntInsertionCodon;

    // Constructors

    /** default constructor */
    public AaInsertion() {
    }

    /** full constructor */
    public AaInsertion(AaInsertionId id, AaSequence aaSequence,
            String aaInsertion, String ntInsertionCodon) {
        this.id = id;
        this.aaSequence = aaSequence;
        this.aaInsertion = aaInsertion;
        this.ntInsertionCodon = ntInsertionCodon;
    }

    // Property accessors
    public AaInsertionId getId() {
        return this.id;
    }

    public void setId(AaInsertionId id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public AaSequence getAaSequence() {
        return this.aaSequence;
    }

    public void setAaSequence(AaSequence aaSequence) {
        this.aaSequence = aaSequence;
    }

    public String getAaInsertion() {
        return this.aaInsertion;
    }

    public void setAaInsertion(String aaInsertion) {
        this.aaInsertion = aaInsertion;
    }

    public String getNtInsertionCodon() {
        return this.ntInsertionCodon;
    }

    public void setNtInsertionCodon(String ntInsertionCodon) {
        this.ntInsertionCodon = ntInsertionCodon;
    }

}
