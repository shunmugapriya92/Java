package com.cts.csvassignment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;



public class ValidateCsv {
public static void main(String[] args) {	
	File file =new File(".//src//files//records.csv");
	byte[] fileContent = null;
	CsvReader<Record> csvRecords = null;
	Set<ErrorRecord> errorRecords = null;
	try {
		fileContent = Files.readAllBytes(file.toPath());
		csvRecords = new CsvReader<Record>(Record.class, fileContent, true).read();
		errorRecords = CsvValidator.validateRecords(csvRecords.getData());
		ReportGenerator.generateCsvReport(errorRecords);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println(csvRecords.getData());
	System.out.println(errorRecords);
}
}

