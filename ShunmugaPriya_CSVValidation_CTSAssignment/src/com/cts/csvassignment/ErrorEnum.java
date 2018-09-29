package com.cts.csvassignment;

public enum ErrorEnum {

DUPLICATEREFERENCE("Transaction reference found twice"),
INVALIDENDAMOUNT("The end amount is not valid");	
	
	private String description;
	
	ErrorEnum(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
}
