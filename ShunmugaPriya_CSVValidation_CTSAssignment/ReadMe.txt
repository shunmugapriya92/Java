1)By default , the project accepts a .csv file of transaction records, validate it and creates a report of all the records with duplicate transaction id and invalid end amount
2)By default, the .csv file is placed in <root>/src/files folder for easy access. Any other .csv file can be used by just changing the path "com.cts.csvassignment.ValidateCsv.java"
3)The main methos is in the class "com.cts.csvassignment.ValidateCsv.java"
4)Once the main class is run, reports are created in the folder "files" with name "InvalidCSVRecords.pdf" and "InvalidCSVRecords.html"
5)Need not add external jar files, as I have already kept them in the folder "jars" and also added them in .classpath