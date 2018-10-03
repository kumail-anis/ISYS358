package testOCR;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//this is the RunRTS that lewis showed us. This is from line 16 down. I am again unsure of what was above these lines.
//There may be some small errors with curly braces but this is more or less exactly what he had.

public static void RunRTS(String fileDir) throws IOException {

	DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	System.out.println("Start time: "+df.format(new Date()));
	
	String renderOpt = "1";
	
	String outputDir = csvWriter.folderCreate();
	File outputCSV = csvWriter.csvCreate(outputDir);
	
	File[] files = null;
	
	if (renderOpt == "1") {
		files = imageRender.pdfUtilities(fileDir, files);
		
		for (File file :files) {
			runOCR(file, outputDir, outputCSV, renderOpt);
		}
	
	
System.out.println("End time: "+df.format(new Date()));
	}
}


public static void runOCR(File file, String outputDir, File outputCSV, String renderOpt) throws IOException{
	String result = null;
	int counter = 0;
	
	ITesseract instance = new Tesseract();
	instance.setDatapath("C:\\Users\\KUMAIL\\ISYS358\\OCR\\tessdata");
	//we need to set this ourselves depending on the computer
	System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
	}
}	

	
	
