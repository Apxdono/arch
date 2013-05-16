package com.kmware.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.kmware.jsf.annotation.UIEntity;

@Entity
@UIEntity(messageBundle="subtopic_messages")
@Table(name="subtopics")
public class Subtopic extends DBObject {
	private static final long serialVersionUID = 6476562817011018750L;

}
