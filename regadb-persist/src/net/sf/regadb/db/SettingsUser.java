package net.sf.regadb.db;


import java.util.HashSet;
import java.util.Set;

/**
 * SettingsUser generated by hbm2java
 */
public class SettingsUser implements java.io.Serializable {

    private String uid;

    private int version;

    private Test test;

    private Dataset dataset;

    private int chartWidth;

    private int chartHeight;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String role;

    private Set<DatasetAccess> datasetAccesses = new HashSet<DatasetAccess>(0);

    private Set<UserAttribute> userAttributes = new HashSet<UserAttribute>(0);

    public SettingsUser() {
    }

    public SettingsUser(String uid, int chartWidth, int chartHeight) {
        this.uid = uid;
        this.chartWidth = chartWidth;
        this.chartHeight = chartHeight;
    }

    public SettingsUser(String uid, Test test, Dataset dataset, int chartWidth,
            int chartHeight, String password, String email, String firstName,
            String lastName, String role, Set<DatasetAccess> datasetAccesses,
            Set<UserAttribute> userAttributes) {
        this.uid = uid;
        this.test = test;
        this.dataset = dataset;
        this.chartWidth = chartWidth;
        this.chartHeight = chartHeight;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.datasetAccesses = datasetAccesses;
        this.userAttributes = userAttributes;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<DatasetAccess> getDatasetAccesses() {
        return this.datasetAccesses;
    }

    public void setDatasetAccesses(Set<DatasetAccess> datasetAccesses) {
        this.datasetAccesses = datasetAccesses;
    }

    public Set<UserAttribute> getUserAttributes() {
        return this.userAttributes;
    }

    public void setUserAttributes(Set<UserAttribute> userAttributes) {
        this.userAttributes = userAttributes;
    }

}
