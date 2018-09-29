package com.cts.csvassignment;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CsvValidator {

	public static Set<ErrorRecord> validateRecords(List<Record> records) {
		Set<ErrorRecord> errorRecords = new LinkedHashSet<>();
		List<String> seenValues = new ArrayList<>();
		for(Record record :records) {
			if(seenValues.contains(record.getTransactionReference())) {
				ErrorRecord errorRecord = new ErrorRecord();
				errorRecord.setTransactionReference(record.getTransactionReference());
				errorRecord.setErrorDescription(ErrorEnum.DUPLICATEREFERENCE.getDescription());
				if(validateEndAmount(new BigDecimal(record.getStartBalance()),new BigDecimal(record.getMutation()),new BigDecimal(record.getEndBalance()))) {
					errorRecord.setErrorDescription(errorRecord.getErrorDescription().concat(" && ").concat(ErrorEnum.INVALIDENDAMOUNT.getDescription()));
				}
				if(errorRecords.contains(errorRecord)) {
					errorRecords.remove(errorRecord);					
					errorRecord.setErrorDescription(ErrorEnum.DUPLICATEREFERENCE.getDescription().concat(" && ").concat(ErrorEnum.INVALIDENDAMOUNT.getDescription()));
				}
				errorRecords.add(errorRecord);
			}
			else if(!seenValues.contains(record.getTransactionReference())) {
				seenValues.add(record.getTransactionReference());
			}		
			if(validateEndAmount(new BigDecimal(record.getStartBalance()),new BigDecimal(record.getMutation()),new BigDecimal(record.getEndBalance()))) {
				ErrorRecord errorRecord = new ErrorRecord();
				errorRecord.setTransactionReference(record.getTransactionReference());
				errorRecord.setErrorDescription(ErrorEnum.INVALIDENDAMOUNT.getDescription());
				if(errorRecords.contains(errorRecord)) {
					errorRecords.remove(errorRecord);
					errorRecord.setErrorDescription(ErrorEnum.DUPLICATEREFERENCE.getDescription().concat(" && ").concat(ErrorEnum.INVALIDENDAMOUNT.getDescription()));
				}
				errorRecords.add(errorRecord);
			}
		}
		
		
		return errorRecords;
	}
	private static boolean validateEndAmount(BigDecimal startAmount, BigDecimal mutation, BigDecimal endAmount) {
		boolean isValid = false;
		if(!endAmount.equals(mutation.add(startAmount))) {
			isValid = true;
		}
		return isValid;
	}
	
}
