package net.sf.regadb.db;

// Generated Jan 3, 2007 3:29:22 PM by Hibernate Tools 3.2.0.beta8

/**
 * TherapyGenericId generated by hbm2java
 */
public class TherapyGenericId implements java.io.Serializable {

    // Fields    

    private int therapyIi;

    private int genericIi;

    // Constructors

    /** default constructor */
    public TherapyGenericId() {
    }

    /** full constructor */
    public TherapyGenericId(int therapyIi, int genericIi) {
        this.therapyIi = therapyIi;
        this.genericIi = genericIi;
    }

    // Property accessors
    public int getTherapyIi() {
        return this.therapyIi;
    }

    public void setTherapyIi(int therapyIi) {
        this.therapyIi = therapyIi;
    }

    public int getGenericIi() {
        return this.genericIi;
    }

    public void setGenericIi(int genericIi) {
        this.genericIi = genericIi;
    }

}
