package testOCR;

import java.io.File;
import java.io.*;
import net.sourceforge.tess4j.*;
import java.io.PrintWriter;

public class test {
	public static void main(String[] args) throws IOException{
		//creating file instance and referencing the file in its location
		File imageFile = new File("H:\\eclipse-workspace\\OCR\\images\\arrears.pdf");
		
		//creating a new tesseract instance and setting the data path that references trained data and the English Language Library
		ITesseract instance = new Tesseract();
		instance.setDatapath("H:\\eclipse-workspace\\OCR\\tessdata");
		
		//create a try catch to run the OCR on the document referenced above
		try {
			String result = instance.doOCR(imageFile);
			String fileName = "out.txt";
			System.out.println("Extraction Completed");
			
			PrintWriter outputStream = new PrintWriter(fileName);
			outputStream.println(result);
			outputStream.flush();
			outputStream.close();
			
			
		
			//catch that delivers an error message if OCR fails
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
	}
}
