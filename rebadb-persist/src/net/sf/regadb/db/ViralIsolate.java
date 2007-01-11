package net.sf.regadb.db;

// Generated Jan 11, 2007 2:11:17 PM by Hibernate Tools 3.2.0.beta8

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ViralIsolate generated by hbm2java
 */
public class ViralIsolate implements java.io.Serializable {

    // Fields    

    private Integer viralIsolateIi;

    private Integer version;

    private PatientImpl patient;

    private String sampleId;

    private Date sampleDate;

    private Set<NtSequence> ntSequences = new HashSet<NtSequence>(0);

    private Set<TestResult> testResults = new HashSet<TestResult>(0);

    // Constructors

    /** default constructor */
    public ViralIsolate() {
    }

    /** minimal constructor */
    public ViralIsolate(PatientImpl patient) {
        this.patient = patient;
    }

    /** full constructor */
    public ViralIsolate(PatientImpl patient, String sampleId, Date sampleDate,
            Set<NtSequence> ntSequences, Set<TestResult> testResults) {
        this.patient = patient;
        this.sampleId = sampleId;
        this.sampleDate = sampleDate;
        this.ntSequences = ntSequences;
        this.testResults = testResults;
    }

    // Property accessors
    public Integer getViralIsolateIi() {
        return this.viralIsolateIi;
    }

    public void setViralIsolateIi(Integer viralIsolateIi) {
        this.viralIsolateIi = viralIsolateIi;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public PatientImpl getPatient() {
        return this.patient;
    }

    public void setPatient(PatientImpl patient) {
        this.patient = patient;
    }

    public String getSampleId() {
        return this.sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public Date getSampleDate() {
        return this.sampleDate;
    }

    public void setSampleDate(Date sampleDate) {
        this.sampleDate = sampleDate;
    }

    public Set<NtSequence> getNtSequences() {
        return this.ntSequences;
    }

    public void setNtSequences(Set<NtSequence> ntSequences) {
        this.ntSequences = ntSequences;
    }

    public Set<TestResult> getTestResults() {
        return this.testResults;
    }

    public void setTestResults(Set<TestResult> testResults) {
        this.testResults = testResults;
    }

}
