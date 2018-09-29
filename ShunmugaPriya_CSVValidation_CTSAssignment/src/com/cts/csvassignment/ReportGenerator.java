package com.cts.csvassignment;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;


public class ReportGenerator {
public static void generateCsvReport (Set<ErrorRecord> errorRecords) {
	
	try {
		   System.out.println("Start ....");
System.out.println("given error records"+errorRecords);
		   String jrxmlFileName = ".//src//files//InvalidCSVRecords.jrxml";

		   String jasperFileName = ".//src//files//InvalidCSVRecords.jasper";
		   String pdfFileName = ".//src//files//InvalidCSVRecords.pdf";
		   String htmlFileName = ".//src//files//InvalidCSVRecords.html";

		   JasperCompileManager.compileReportToFile(jrxmlFileName, jasperFileName);
		   System.out.println("Done compiling JRXML to Jasper");

		   Map<String, Object> parameters = new HashMap<>();
		   parameters.put("ReportTitle", "List of Contacts");
		   parameters.put("Author", "Report Prepared By ShunmugaPriya");

		   
		   JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(errorRecords);
           System.out.println("beanColDataSource"+beanColDataSource.getRecordCount());
		   JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperFileName, parameters, beanColDataSource);
		   System.out.println("Done filling data to report as JasperPrint");

		   JasperExportManager.exportReportToPdfFile(jprint, pdfFileName);
		   System.out.println("Done exporting the filledIn report to pdf");

		   JasperExportManager.exportReportToHtmlFile(jprint, htmlFileName);
		   System.out.println("Done exporting the filledIn report to html");

		   JasperViewer.viewReport(jprint);
		   System.out.println("Done viewing the report using the JasperViewer");

		  } catch (Exception e) {
		   System.out.print("Exceptiion" + e);
		  }
}

}
