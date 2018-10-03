package testOCR;

import java.io.*;
import java.util.Iterator;
import testOCR.SplitPDF.*;

import org.apache.commons.io.monitor.FileEntry;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import net.sourceforge.tess4j.*;


public class elementExtract {
	
	static BufferedWriter finalWriter;
	
	public static void main(String[] args) throws IOException{
		
		finalWriter = new BufferedWriter(new FileWriter("C:\\Users\\Clark\\eclipse-workspace\\ISYS358\\ISYS358\\OCR\\images\\testfolder\\finalResult.txt"));
		
		final File folder = new File("C:\\Users\\Clark\\eclipse-workspace\\ISYS358\\ISYS358\\OCR\\images\\testfolder");
		listFilesForFolder(folder);
		
		finalWriter.close();
	}
	
	public static void listFilesForFolder(final File folder) throws IOException{
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	String pdfName = folder.getPath() + "\\" + fileEntry.getName();
	        	String txtName = pdfName.replace(".pdf", ".txt");
	        	String csvName = pdfName.replace(".pdf", ".csv");
	        	//System.out.println("Processing " + fileEntry.getName() + " file");
	        	parsePDFtoCSV(pdfName, txtName, csvName);
	        	
//	            System.out.println(pdfName);
//	            System.out.println(txtName);
	        }
	    }
	}
	
	public static void parsePDFtoCSV(String pdfName, String txtName, String csvName)  throws IOException{
		
		//creating file instance and referencing the file in its location
				//SplitPDF split = new SplitPDF();
				
File imageFile = new File(pdfName);
		
//		public void getFiles(File f) {
//			File files[];
//			if(f.isFile())
//				System.out.println(f.getAbsolutePath());
//			else {
//				f.listFiles();
//				for (int i = 0; i<files.length;i++)
//				{
//					getFiles(files[i]);
//				
//				}
//			}
//		}
		//creating a new tesseract instance and setting the data path that references trained data and the English Language Library
		ITesseract instance = new Tesseract();
		instance.setDatapath("C:\\Users\\Clark\\eclipse-workspace\\ISYS358\\ISYS358\\OCR\\tessdata");
		
		
		
		//create a try catch to run the OCR on the document referenced above
		try {
			String result = instance.doOCR(imageFile);
			//result = result.replaceAll(",", "");
			//result = result.replaceAll("[ ]", ",");

			String fileName = txtName;
			
//			File fold = new File(txtName);
//			fold.delete();

			System.out.println("Extraction Completed");
			//this is to write the text file for which the later codes will use for extraction
			PrintWriter outputStream = new PrintWriter(fileName);
			outputStream.println(result);		
			outputStream.flush();
			outputStream.close();
			
			
		
			//catch that delivers an error message if OCR fails
		} catch (TesseractException e1) {
			System.err.println(e1.getMessage());
		}
		String contractNumber = "";
		String name = "";
		String contractType = "";
		String amount = ""; 
		Boolean findAmount = false;
		String previousLine = "";
		String address = "";
			
			try {
				int page = 1;
				String line = "";
				BufferedReader bReader = new BufferedReader(new FileReader(txtName));
				BufferedWriter bWriter = new BufferedWriter(new FileWriter(csvName));
				while ((line = bReader.readLine()) != null) {
					if(line.startsWith("ABN")) {
						bWriter.write(name+","+ contractType + "," + contractNumber + "," + amount + "," + address + "\n"); //csv format
						finalWriter.write("name: " + name + "\nContract Type: " + contractType + "\nContract Number: " + contractNumber + "\namount: " + amount + "\nAddress: " + address + "\n**********\n\n");
						contractNumber = ""; //reset back to blank
						name = "";
						contractType = "";
						amount = ""; 
						page++;
					}
					else if(line.contains("Contract Number") || line.contains("Loan Number") || line.contains("Loan no.")) {
						contractNumber = line.substring(line.indexOf("Number")+7);
						//System.out.println("Contract Number: " + contractNumber + "page: " + page);
					}
					else if(line.contains("Dear")) {
						name = line.substring(line.indexOf("Dear")+5);
						name = name.replace("Mr", "");
						name = name.replace("Mrs", "");
						name = name.replace("Ms", "");
						System.out.println("Name: " + name + "page: " + page);
					}
					
					else if(line.contains("Arrears Amount") || line.contains("Dishonour Amount")) {
						contractType = "Arrears";
						if(line.contains("$")) {
							amount = line.substring(line.indexOf("$"));
							//amount = amount.replace(".", "");
							amount = amount.replace(",", "");
							System.out.println("Amount: " + amount + "page: " + page + "Contract Type: " + contractType);
						}
						else {
							findAmount = true;
						}
					}
					else if(line.contains("Arrears")){
						contractType = "Arrears Letter";
						findAmount = true;
					}
					else if(line.contains("password") || line.contains("pin") || line.contains("passcode") ||line.contains("access") || line.contains("pass code")){
						contractType = "Password Letter";
					}
					else if(line.contains("congratulations") || line.contains("welcome")){
						contractType = "Welcome Letter";
					}
					else if(line.contains("Letter of demand")){
						contractType = "Demand Letter";
					}
					else if(line.contains("dishonour") || line.contains("dishonoured")){
						contractType = "Dishonour Letter";
					}
					else if(line.contains("realize") || line.contains("outstanding")){
						contractType = "Arrears Letter";
					}
					else if(findAmount && line.contains("$")) {
						findAmount = false;
						amount = line.substring(line.indexOf("$"));
						//amount = amount.replace(".", "");
						amount = amount.replace(",", "");
						System.out.println("Amount: " + amount + "page: " + page);
					}
					else if(line.contains("NSW") || line.contains("QLD")|| line.contains("SA") || line.contains("VIC") || line.contains("WA") || line.contains("TAS") || line.contains("ACT") || line.contains("NT")){
						address = previousLine + "\n         " + line;
					}
					previousLine = line;
				}
				bReader.close();
				bWriter.close();
			}
			catch (IOException e) {
				// We encountered an error with the file, print it to the user.
				System.out.println("Error: " + e.toString());
			}
	}
}