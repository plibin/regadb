package net.sf.regadb.db;


/**
 * TherapyMotivation generated by hbm2java
 */
public class TherapyMotivation implements java.io.Serializable {

    private Integer therapyMotivationIi;

    private int version;

    private String value;

    public TherapyMotivation() {
    }

    public TherapyMotivation(String value) {
        this.value = value;
    }

    public Integer getTherapyMotivationIi() {
        return this.therapyMotivationIi;
    }

    public void setTherapyMotivationIi(Integer therapyMotivationIi) {
        this.therapyMotivationIi = therapyMotivationIi;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
