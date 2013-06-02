package com.kmware.model;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.kmware.jpa.annotations.TrackChanges;


@MappedSuperclass
public abstract class DBObject implements Serializable, Cloneable {
	private static final long serialVersionUID = -6548247131111801538L;
	
	private String id;
	private String displayName;
	private Long version;
	private Boolean deleted;
	private GregorianCalendar dateCreated;
	private GregorianCalendar dateModified;
	
	public DBObject() {
		id = UUID.randomUUID().toString();
		displayName = "";
		version = 0L;
		deleted = false;
		dateCreated = new GregorianCalendar();
		dateModified = new GregorianCalendar();
		dateCreated.setTimeZone(TimeZone.getTimeZone("GMT"));
		dateModified.setTimeZone(TimeZone.getTimeZone("GMT"));
		
	}

	@Id
	@Column(name="id",length=36)
	public String getId() {
		return id;
	}
	
	@TrackChanges
	@Column(name="display_name",length=255,nullable=false)
	public String getDisplayName() {
		return displayName;
	}

	@Version
	public Long getVersion() {
		return version;
	}

	@TrackChanges
	@Column(name="deleted",nullable=false)
	public Boolean getDeleted() {
		return deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	public Date getDateCreated() {
		return dateCreated.getTime();
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_modified")
	public Date getDateModified() {
		return dateModified.getTime();
	}
	

	public void setId(String id) {
		this.id = id;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated.setTimeInMillis(dateCreated.getTime());
	}

	public void setDateModified(Date dateModified) {
		this.dateModified.setTimeInMillis(dateModified.getTime());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBObject other = (DBObject) obj;
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}	
	
	@PrePersist
	public void prePersist(){
		dateCreated.setTimeInMillis((new Date()).getTime());
		dateModified.setTimeInMillis(dateCreated.getTimeInMillis());
	}
	
	@PreUpdate
	public void preUpdate(){
		dateModified.setTimeInMillis((new Date()).getTime());
	}
	
}
