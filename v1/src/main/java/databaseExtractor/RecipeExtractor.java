package databaseExtractor;

import java.io.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class RecipeExtractor {
    public static String enterPath = "/Users/danutandres/Desktop/Programmazione/DunnoWhatToEat/v1/src/main/resources/database/ricette.txt";
    public static String exitPath = "/Users/danutandres/Desktop/Programmazione/DunnoWhatToEat/v1/src/main/resources/database/RicetteNormalizzate.txt";
    public static String finalPath = "/Users/danutandres/Desktop/Programmazione/DunnoWhatToEat/v1/src/main/resources/database/ingredientiNormalizzatiNODUPLI.txt";

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
            StringBuilder queryOutput = new StringBuilder();
            for(String line; (line = br.readLine()) != null; ) {
                queryOutput.append("Insert to ----ancora da vedere-(ricetta_id INT, Ingredients_lists...) VALUES((");

                if(line.length()>0 || line.equals(":Ricette")) {
                    line = br.readLine();
               if(line.equals("-Nome")){
                  line=  br.readLine();
                   queryOutput.append("'" + line +"' , " );

               }
               line = br.readLine();
               if(!line.equals("-Tipo_Piatto")){
                   throw new Exception("FORMATO SBAGLIATO");
               }
               else{
                   line=br.readLine();
                   queryOutput.append("'" + line +"' , " ); // da inserire un metodo che inserire un id
                                                            // e non la stringa
               }
                    line = br.readLine();
                    if(!line.equals("-Ing_Principale")){
                        throw new Exception("FORMATO SBAGLIATO");
                    }
                    else{
                        line=br.readLine();
                        queryOutput.append("'" + line +"' , " ); // da inserire un metodo che fa
                                                                // l'assocciazione con la foreign key
                    }
                    line = br.readLine();
                    if(!line.equals("-Persone")){
                        throw new Exception("FORMATO SBAGLIATO");
                    }
                    else{
                        line=br.readLine();
                        queryOutput.append("'" + line +"' , " ); // qui bisogna inserire un int

                    }
                    line = br.readLine();
                    if(!line.equals("-Note")){
                        throw new Exception("FORMATO SBAGLIATO");
                    }
                    else{
                        line=br.readLine();
                        //if(!line.equals("-"));
                        //{
                            queryOutput.append("'" + line + "' , "); // capire se inserire un valore tipo ""
                        //}                                           // o se fare 2 insert con query diverse visto che alcune avranno
                                                                    //il campo note  e altre no

                    }
                    line = br.readLine();
                    if(!line.equals("-Ingredienti")){
                        throw new Exception("FORMATO SBAGLIATO");

                    }
                    else{
                        boolean keepGoing = true;
                        StringBuilder ingrBuilder = new StringBuilder();

                        while(keepGoing){
                            if(line.equals("-Preparazione")){
                                keepGoing = false;
                                queryOutput.append("(" + ingrBuilder.toString() + ")" );
                            }
                            else {
                                line = br.readLine();
                                String proporzione = line.substring(0, line.indexOf("=", 1));
                                String ingredienti = line.substring(line.indexOf("==", 1), line.length() );
                                ingrBuilder.append("{proporzione: '" + proporzione + "'\n ingredient: '" + ingredienti + "'},");
                            }
                        }

                    }
                    line = br.readLine();
                    if(!line.equals("-Preparazione")){
                        throw new Exception("FORMATO SBAGLIATO");
                    }
                    else{
                        boolean keepGoing = true;
                        StringBuilder procediment = new StringBuilder();
                        while(keepGoing){
                            line=br.readLine();
                            if(!line.equals(":Ricette")){
                                procediment.append(line + " ");
                            }
                            else {
                                queryOutput.append("'" + procediment.toString() + "'),(");
                            }

                        }


                    }





                    }
            }
            writer.write(queryOutput.toString());

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
