package testOCR;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class searchfile {
	public static void main(String args[]) {
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
							try(FileWriter fw = new FileWriter("test13.csv", true);
									BufferedWriter bw = new BufferedWriter(fw);
									PrintWriter out = new PrintWriter(bw))
							{
								out.println(",," +line.substring(posAmount,posAmount + posEnd)+ ",");}
							/*String csvfile = "test12.txt";
							PrintWriter pwrite = new PrintWriter(csvfile);
							pwrite.println(line.substring(posAmount,posAmount + posEnd));
							pwrite.flush();
							pwrite.close();*/
							}
							else{
								System.out.println(line.substring(posAmount));
								try(FileWriter fw = new FileWriter("test13.csv", true);
										BufferedWriter bw = new BufferedWriter(fw);
										PrintWriter out = new PrintWriter(bw))
								{
									out.println(",,"+ line.substring(posAmount)+ ",");}
								/*String csvfile = "test12.txt";
								PrintWriter pwrite = new PrintWriter(csvfile);
								pwrite.println(line.substring(posAmount));
								pwrite.flush();
								pwrite.close();*/
								}
						
						}
						System.out.println("Search word '" + searchword + "' found at position " + posFound + " on line " + LineCount + "page number: " + page);
						System.out.println(line.substring(posFound)); 
						//String csvfile = "test12.txt";
						//PrintWriter pwrite = new PrintWriter(csvfile);
						//pwrite.println(line.substring(posAmount));
						//pwrite.close();

					}	
				}
				//bReader.close();
				LineCount = 0;
			}
			catch (IOException e) {
				// We encountered an error with the file, print it to the user.
				System.out.println("Error: " + e.toString());
			}
		}
//		else {
//			// They obviously didn't provide a search term when starting the program.
//			System.out.println("Please provide a word to search the file for.");
//		}
	}

}