package net.sf.regadb.db;

// Generated 22/01/2007 12:43:36 by Hibernate Tools 3.2.0.beta8

import java.util.HashSet;
import java.util.Set;

/**
 * SettingsUser generated by hbm2java
 */
public class SettingsUser implements java.io.Serializable {

    // Fields    

    private String uid;

    private Integer version;

    private Test test;

    private Dataset dataset;

    private int chartWidth;

    private int chartHeight;

    private String password;

    private Set<DatasetAccess> datasetAccesses = new HashSet<DatasetAccess>(0);

    // Constructors

    /** default constructor */
    public SettingsUser() {
    }

    /** minimal constructor */
    public SettingsUser(int chartWidth, int chartHeight) {
        this.chartWidth = chartWidth;
        this.chartHeight = chartHeight;
    }

    /** full constructor */
    public SettingsUser(Test test, Dataset dataset, int chartWidth,
            int chartHeight, String password, Set<DatasetAccess> datasetAccesses) {
        this.test = test;
        this.dataset = dataset;
        this.chartWidth = chartWidth;
        this.chartHeight = chartHeight;
        this.password = password;
        this.datasetAccesses = datasetAccesses;
    }

    // Property accessors
    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Test getTest() {
        return this.test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Dataset getDataset() {
        return this.dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public int getChartWidth() {
        return this.chartWidth;
    }

    public void setChartWidth(int chartWidth) {
        this.chartWidth = chartWidth;
    }

    public int getChartHeight() {
        return this.chartHeight;
    }

    public void setChartHeight(int chartHeight) {
        this.chartHeight = chartHeight;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<DatasetAccess> getDatasetAccesses() {
        return this.datasetAccesses;
    }

    public void setDatasetAccesses(Set<DatasetAccess> datasetAccesses) {
        this.datasetAccesses = datasetAccesses;
    }

}
