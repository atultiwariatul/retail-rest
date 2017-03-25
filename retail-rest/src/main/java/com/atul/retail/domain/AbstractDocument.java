package com.atul.retail.domain;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Abstract Document which will be extended by every Document in Retail system.
 * We will enforce any common functionality through this super class.
 * Created by atiwa00 on 6/4/16.
 */
@Document
public abstract class AbstractDocument implements Serializable {

    /** creation date of this document */
    @CreatedDate
    private DateTime createdAt;

    /** Modification date of This document. When This is created first time
     * it will be equal to created date */
    @LastModifiedDate
    private DateTime lastModified;

    /** version of this document in the database to identify the version of this Document */
    @Version
    private Long version;

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(DateTime lastModified) {
        this.lastModified = lastModified;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}