package com.kmware.dao;

public enum DAOMessage {
	
	OK("ok"),OptimisticLock("optimistic.lock"),UniqueConstraintViolation("unique.constraint.violation"),
	NoResult("no.result");
	
	final String message;
	
	private DAOMessage(String msg) {
		message = msg;
	}
	
	public String getMessage() {
		return message;
	}
}
