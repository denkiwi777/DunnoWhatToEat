package databaseExtractor;

import java.io.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class IngredientsExtractor {
	public static String enterPath = "C:/Users/denkiwi/Desktop/Development/dunnoWhatToEatv2/src/main/resources/database/ing.txt";
	public static String exitPath = "C:/Users/denkiwi/Desktop/Development/dunnoWhatToEatv2/src/main/resources/database/ingredientiNormalizzati.txt";
	public static String finalPath = "C:/Users/denkiwi/Desktop/Development/dunnoWhatToEatv2/src/main/resources/database/ingredientiNormalizzatiNODUPLI.txt";

	public static void main(String[] args) throws IOException {

		FileWriter writer = null;
		File inputDatabaseFile = new File(enterPath);
		FileReader reader = new FileReader(inputDatabaseFile);
		try(BufferedReader br = new BufferedReader(reader)) {
			File fileOutput = new File(exitPath);
			if (!fileOutput.exists()) {
				fileOutput.createNewFile();
			}
			 writer = new FileWriter(fileOutput, false);
			int i =0;
			System.out.println(new Date());
			writer.write(new Date().toString());
		    for(String line; (line = br.readLine()) != null; ) {

		    	if(line.length()>0) {
		    		String finalVal ="";
					i++;
					if(line.indexOf("=", 1)>0){
						finalVal= line.substring(0, line.indexOf("=", 1));

					}

					if(line.indexOf("_", 1)>0){
						finalVal = line.substring(0,line.indexOf("_", 1) );
					}

					writer.write( finalVal);
					writer.write((System.lineSeparator()));
					System.out.println(finalVal);

		    	}
		        }
			System.out.println("FINITO");
			System.out.println(new Date());
			writer.write(new Date().toString());
			writer.close();
			deleteDuplicates(exitPath, finalPath);


		}
		catch (Exception e){
			writer.write(e.getMessage());
			System.out.println(e.getMessage());
		}

		
	
	}

	public static void deleteDuplicates(String input ,String output) throws IOException {
		String filePath = input;
		String exitVal = null;
		//Instantiating the Scanner class
		Scanner sc = new Scanner(new File(filePath));
		//Instantiating the FileWriter class
		FileWriter writer = new FileWriter(output);
		//Instantiating the Set class
		Set set = new HashSet();
		int i = 0;
		while (sc.hasNextLine()) {

			input = sc.nextLine();
			if(set.add(input)) {
				i++;
				writer.append(i + " " + input+"\n");
			}
		}
		writer.flush();
		System.out.println("Contents added............");
	}
}
