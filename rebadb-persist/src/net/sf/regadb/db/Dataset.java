package net.sf.regadb.db;

// Generated Jan 3, 2007 3:29:22 PM by Hibernate Tools 3.2.0.beta8

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Dataset generated by hbm2java
 */
public class Dataset implements java.io.Serializable {

    // Fields    

    private Integer datasetIi;

    private Integer version;

    private String description;

    private Date creationDate;

    private Date closedDate;

    private Integer revision;

    private Set<DatasetAccess> datasetAccesses = new HashSet<DatasetAccess>(0);

    // Constructors

    /** default constructor */
    public Dataset() {
    }

    /** minimal constructor */
    public Dataset(String description, Date creationDate) {
        this.description = description;
        this.creationDate = creationDate;
    }

    /** full constructor */
    public Dataset(String description, Date creationDate, Date closedDate,
            Integer revision, Set<DatasetAccess> datasetAccesses) {
        this.description = description;
        this.creationDate = creationDate;
        this.closedDate = closedDate;
        this.revision = revision;
        this.datasetAccesses = datasetAccesses;
    }

    // Property accessors
    public Integer getDatasetIi() {
        return this.datasetIi;
    }

    public void setDatasetIi(Integer datasetIi) {
        this.datasetIi = datasetIi;
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

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getClosedDate() {
        return this.closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public Integer getRevision() {
        return this.revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public Set<DatasetAccess> getDatasetAccesses() {
        return this.datasetAccesses;
    }

    public void setDatasetAccesses(Set<DatasetAccess> datasetAccesses) {
        this.datasetAccesses = datasetAccesses;
    }

}
