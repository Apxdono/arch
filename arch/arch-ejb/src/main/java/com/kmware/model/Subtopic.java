package com.kmware.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.kmware.ui.annotations.UIEntity;

@Entity
@Table(name = "subtopics")
@UIEntity(resourceBundle="subtopic_messages")
public class Subtopic extends DBObject {
	private static final long serialVersionUID = 6476562817011018750L;

}
