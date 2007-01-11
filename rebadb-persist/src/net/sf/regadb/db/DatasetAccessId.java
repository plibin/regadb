package net.sf.regadb.db;

// Generated Jan 11, 2007 2:11:17 PM by Hibernate Tools 3.2.0.beta8

/**
 * DatasetAccessId generated by hbm2java
 */
public class DatasetAccessId implements java.io.Serializable {

    // Fields    

    private SettingsUser settingsUser;

    private Dataset dataset;

    // Constructors

    /** default constructor */
    public DatasetAccessId() {
    }

    /** full constructor */
    public DatasetAccessId(SettingsUser settingsUser, Dataset dataset) {
        this.settingsUser = settingsUser;
        this.dataset = dataset;
    }

    // Property accessors
    public SettingsUser getSettingsUser() {
        return this.settingsUser;
    }

    public void setSettingsUser(SettingsUser settingsUser) {
        this.settingsUser = settingsUser;
    }

    public Dataset getDataset() {
        return this.dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

}
