package com.cts.csvassignment;

public class ErrorRecord {

	private String transactionReference;
	private String errorDescription;
	
	
	public String getTransactionReference() {
		return transactionReference;
	}
	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public boolean equals(Object o) {
		boolean isEqual = false;
		ErrorRecord errorRecord = (ErrorRecord) o;
		if(this.transactionReference.equals(errorRecord.getTransactionReference())) {
			isEqual = true;
		}
		return isEqual;
	}
	@Override
	public String toString() {
		return "ErrorRecord [transactionReference=" + transactionReference + ", errorDescription=" + errorDescription
				+ "]";
	}
	@Override
    public int hashCode() {
        int result = 17;        
        result = 31 * result + this.transactionReference.hashCode();        
        return result;
    }
	
	
}
