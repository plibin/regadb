package net.sf.regadb.db;

// Generated 17/07/2007 16:15:00 by Hibernate Tools 3.2.0.beta8

/**
 * AaMutation generated by hbm2java
 */
public class AaMutation implements java.io.Serializable {

    // Fields    

    private AaMutationId id;

    private int version;

    private String aaReference;

    private String aaMutation;

    private String ntReferenceCodon;

    private String ntMutationCodon;

    // Constructors

    /** default constructor */
    public AaMutation() {
    }

    /** minimal constructor */
    public AaMutation(AaMutationId id, String aaReference,
            String ntReferenceCodon) {
        this.id = id;
        this.aaReference = aaReference;
        this.ntReferenceCodon = ntReferenceCodon;
    }

    /** full constructor */
    public AaMutation(AaMutationId id, String aaReference, String aaMutation,
            String ntReferenceCodon, String ntMutationCodon) {
        this.id = id;
        this.aaReference = aaReference;
        this.aaMutation = aaMutation;
        this.ntReferenceCodon = ntReferenceCodon;
        this.ntMutationCodon = ntMutationCodon;
    }

    // Property accessors
    public AaMutationId getId() {
        return this.id;
    }

    public void setId(AaMutationId id) {
        this.id = id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getAaReference() {
        return this.aaReference;
    }

    public void setAaReference(String aaReference) {
        this.aaReference = aaReference;
    }

    public String getAaMutation() {
        return this.aaMutation;
    }

    public void setAaMutation(String aaMutation) {
        this.aaMutation = aaMutation;
    }

    public String getNtReferenceCodon() {
        return this.ntReferenceCodon;
    }

    public void setNtReferenceCodon(String ntReferenceCodon) {
        this.ntReferenceCodon = ntReferenceCodon;
    }

    public String getNtMutationCodon() {
        return this.ntMutationCodon;
    }

    public void setNtMutationCodon(String ntMutationCodon) {
        this.ntMutationCodon = ntMutationCodon;
    }

}
