package testOCR;

import java.io.*;
import net.sourceforge.tess4j.*;

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
			result = result.replaceAll(",", "");
			result = result.replaceAll("[ ]", ",");
			String fileName = "test.txt";
			
			System.out.println("Extraction Completed");
			//this is to write the text file for which the later codes will use for extraction
			PrintWriter outputStream = new PrintWriter(fileName);
			outputStream.println(result);
			outputStream.flush();
			outputStream.close();
			
			
		
			//catch that delivers an error message if OCR fails
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
		String[] keywords = {"Mr" , "Arrear", "Dishonour"};
		
		for (int j = 0; j < keywords.length; j++) {
			
			String searchword = keywords[j];
			try {
				int LineCount = 0;
				int page = 1;
				String line = "";
				BufferedReader breader = new BufferedReader(new FileReader("H:\\eclipse-workspace\\OCR\\test.txt"));
				
				while ((line = breader.readLine()) != null) {
					if(line.contains("sincerely")) {
						page++;
									}
					int posFound = line.indexOf(searchword);
					LineCount++;
					
					if (posFound > - 1) {
						if (line.contains("$")) {
							int posAmount = line.indexOf("$");
							int posEnd = line.substring(posAmount).indexOf(",");
							if(posEnd > 0) {
							System.out.println(line.substring(posAmount,posAmount + posEnd));
							//this is to print out the csv file, will improve this later
							try(FileWriter fw = new FileWriter("test12.csv", true);
									BufferedWriter bw = new BufferedWriter(fw);
									PrintWriter out = new PrintWriter(bw))
							{
								out.println(",," +line.substring(posAmount,posAmount + posEnd)+ ","+page);}
		
							}
							else{
								System.out.println(line.substring(posAmount));
								//this is to print csv file, will improve later
								try(FileWriter fw = new FileWriter("test12.csv", true);
										BufferedWriter bw = new BufferedWriter(fw);
										PrintWriter out = new PrintWriter(bw))
								{
									out.println(",,"+ line.substring(posAmount)+ ","+page);}

								}
						
						}
						System.out.println("Search word '" + searchword + "' found at position " + posFound + " on line " + LineCount + "page number: " + page);
						System.out.println(line.substring(posFound)); 

						}	
					}
				breader.close();
				LineCount = 0;
			
				}
		catch (IOException e) {
			// We encountered an error with the file, print it to the user.
			System.out.println("Error: " + e.toString());
			}
		}
	}
}
		
	
