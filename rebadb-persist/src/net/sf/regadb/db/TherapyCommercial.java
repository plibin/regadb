package net.sf.regadb.db;

// Generated Jan 11, 2007 2:11:17 PM by Hibernate Tools 3.2.0.beta8

/**
 * TherapyCommercial generated by hbm2java
 */
public class TherapyCommercial implements java.io.Serializable {

    // Fields    

    private TherapyCommercialId id;

    private Integer version;

    private Therapy therapy;

    private DrugCommercial drugCommercial;

    private Double dayDosageUnits;

    // Constructors

    /** default constructor */
    public TherapyCommercial() {
    }

    /** minimal constructor */
    public TherapyCommercial(TherapyCommercialId id, Therapy therapy,
            DrugCommercial drugCommercial) {
        this.id = id;
        this.therapy = therapy;
        this.drugCommercial = drugCommercial;
    }

    /** full constructor */
    public TherapyCommercial(TherapyCommercialId id, Therapy therapy,
            DrugCommercial drugCommercial, Double dayDosageUnits) {
        this.id = id;
        this.therapy = therapy;
        this.drugCommercial = drugCommercial;
        this.dayDosageUnits = dayDosageUnits;
    }

    // Property accessors
    public TherapyCommercialId getId() {
        return this.id;
    }

    public void setId(TherapyCommercialId id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Therapy getTherapy() {
        return this.therapy;
    }

    public void setTherapy(Therapy therapy) {
        this.therapy = therapy;
    }

    public DrugCommercial getDrugCommercial() {
        return this.drugCommercial;
    }

    public void setDrugCommercial(DrugCommercial drugCommercial) {
        this.drugCommercial = drugCommercial;
    }

    public Double getDayDosageUnits() {
        return this.dayDosageUnits;
    }

    public void setDayDosageUnits(Double dayDosageUnits) {
        this.dayDosageUnits = dayDosageUnits;
    }

}
