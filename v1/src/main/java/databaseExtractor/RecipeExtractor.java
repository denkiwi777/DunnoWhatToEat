package databaseExtractor;

import com.DunnoWhatToEat.v1.Entity.Ingrediente;
import com.DunnoWhatToEat.v1.Entity.Ricetta;
import com.DunnoWhatToEat.v1.repository.IngredienteRepository;
import com.DunnoWhatToEat.v1.repository.RicettaRepository;
import com.DunnoWhatToEat.v1.utility.SesionCreator;
import org.hibernate.Session;
import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


import java.io.*;
import java.util.*;

@SpringBootApplication
@ComponentScan({"package com.DunnoWhatToEat.v1"})
public class RecipeExtractor implements CommandLineRunner {


    private Session session;

    @Autowired
    private  RicettaRepository ricettaRepository;
    @Autowired
    private  IngredienteRepository ingredienteRepository;

    public static String enterPath = "/Users/danutandres/Desktop/Programmazione/DunnoWhatToEat/v1/src/main/resources/database/ricette.txt";
    public static String exitPath = "/Users/danutandres/Desktop/Programmazione/DunnoWhatToEat/v1/src/main/resources/database/log.txt";
    public static String tipiPiattitxt = "/Users/danutandres/Desktop/Programmazione/DunnoWhatToEat/v1/src/main/resources/database/tipipiatti.txt";
    public static void main(String[] args) throws Exception {
        SpringApplication.run(RecipeExtractor.class, args);
    }
    @Override
    public void run(String... args) throws IOException {

        File inputDatabaseFile = new File(enterPath);
        FileReader reader = new FileReader(inputDatabaseFile);
        FileWriter myWriter = null;
        try (BufferedReader br = new BufferedReader(reader)) {
            myWriter = new FileWriter(exitPath);
            FileWriter tipiPiatti = new FileWriter(tipiPiattitxt);


            int i = 0;
            System.out.println(new Date());
            Ricetta ricetta = new Ricetta();
            String tempIngredientePrinc = "";
            for (String line; (line = br.readLine()) != null; ) {

                if (line.equals(":Ricette")) {
                    line = br.readLine();
                }

                    if (line.equals("-Nome")) {
                        line = br.readLine();
                        ricetta = new Ricetta();
                        ricetta.setTitolo(line);
                    } else {
                        myWriter.write("FORMATO SBAGLIATO -Nome" + "\n");
                        myWriter.write(line + " " + i);
                        throw new Exception("FORMATO SBAGLIATO -Nome");


                    }
                    line = br.readLine();
                    if (!line.equals("-Tipo_Piatto")) {
                        myWriter.write(line + " " + i);
                        myWriter.write("FORMATO SBAGLIATO -Tipo_Piatto" + "\n");
                        throw new Exception("FORMATO SBAGLIATO -Tipo_Piatto");
                    } else {

                        line = br.readLine();
                        tipiPiatti.write(line + "\n"); // da inserire un metodo che inserire un id
                        // e non la stringa
                    }
                    line = br.readLine();
                    if (!line.equals("-Ing_Principale")) {

                        myWriter.write("FORMATO SBAGLIATO -Ing_Principale" + "\n");
                        myWriter.write(line + " " + i);
                        throw new Exception("FORMATO SBAGLIATO -Ing_Principale");
                    } else {
                        line = br.readLine();
                        tempIngredientePrinc = line;
                        // l'assocciazione con la foreign key
                    }
                    line = br.readLine();
                    if (!line.equals("-Persone")) {
                        myWriter.write("FORMATO SBAGLIATO -Persone" + "\n");
                        myWriter.write(line + " " + i);
                        throw new Exception("FORMATO SBAGLIATO -Persone");

                    } else {
                        line = br.readLine();
                        ricetta.setNr_persone(Integer.parseInt(line));
                    }
                    line = br.readLine();
                    if (!line.equals("-Note")) {
                        myWriter.write("FORMATO SBAGLIATO -Note" + "\n");
                        myWriter.write(line + " " + i);
                        throw new Exception("FORMATO SBAGLIATO -Note");

                    } else {
                        line = br.readLine();
                        //if(!line.equals("-"));
                        //{
                        ricetta.setNote(line); // capire se inserire un valore tipo ""
                        //}                                           // o se fare 2 insert con query diverse visto che alcune avranno
                        //il campo note  e altre no

                    }
                    line = br.readLine();
                    if (!line.equals("-Ingredienti")) {

                        myWriter.write("FORMATO SBAGLIATO -Ingredienti" + "\n");
                        myWriter.write(line + " " + i);
                        throw new Exception("FORMATO SBAGLIATO -Ingredienti");
                    } else {
                        boolean keepGoing = true;
                        HashSet<Ingrediente> set = new HashSet<>();
                        while (keepGoing) {
                            line = br.readLine();
                            if (line.equals("-Preparazione")) {
                                keepGoing = false;
                                ricetta.setIngredienti(set);
                                Iterator<Ingrediente> ingreIter = set.iterator();
                                for (Iterator<Ingrediente> it = ingreIter; it.hasNext(); ) {
                                    Ingrediente ingr = it.next();
                                    if(ingr.getNome().replace(" ", "").equalsIgnoreCase(tempIngredientePrinc.replace(" " ,""))){
                                        ricetta.setIngrediente_princ(ingr.getId());

                                    }


                                }
                                set = new HashSet<>();
                            } else {
                                Ingrediente ingr = new Ingrediente();
                                String proporzione = line.substring(0, line.indexOf("=", 1));
                                if (proporzione.contains("/")) {
                                    Expression e = new Expression(proporzione);
                                    double res = e.calculate();
                                    ingr.setQuantita(( res));
                                    ingr.setUnitaMisura("parti");
                                } else if (onlyDigits(proporzione, proporzione.length())) {
                                    ingr.setQuantita(Integer.parseInt(proporzione.replace(" ", "")));

                                }
                                else if (onlyLetters(proporzione)){
                                    ingr.setUnitaMisura(proporzione);

                                }else {
                                    StringBuilder tipo = new StringBuilder();
                                    StringBuilder qfinale = new StringBuilder();
                                    for (int k = 0; k < proporzione.length(); i++) {
                                        if (Character.isDigit(proporzione.charAt(i))) {
                                            qfinale.append(proporzione.charAt(i));
                                        } else tipo.append(proporzione.charAt(i));
                                    }
                                    ingr.setUnitaMisura(tipo.toString());
                                    ingr.setQuantita(Integer.parseInt(qfinale.toString()));
                                }
                                String ingredienti = line.substring(line.indexOf("==", 1), line.length());
                                ingr.setNome(ingredienti.replace("=" ,""));
                                ingredienteRepository.save(ingr);
                                set.add(ingr);
                            }

                        }

                    }

                    if (!line.equals("-Preparazione")) {

                        myWriter.write("FORMATO SBAGLIATO -Preparazione" + "\n");
                        myWriter.write(line + " " + i);
                        throw new Exception("FORMATO SBAGLIATO -Preparazione");
                    } else {
                        boolean keepGoing = true;
                        StringBuilder procediment = new StringBuilder();
                        while (keepGoing) {
                            line = br.readLine();
                            if (!line.equals(":Ricette")) {
                                procediment.append(line + " ");
                            } else {
                                ricetta.setPreparazione(procediment.toString());
                                keepGoing = false;
                            }

                        }


                    }



                ricettaRepository.save(ricetta);
            }

            System.out.println("FINITO");
            System.out.println(new Date());
            myWriter.write(new Date().toString());
            myWriter.close();
            tipiPiatti.close();

            //deleteDuplicates(exitPath, finalPath);


        } catch (Exception e) {
            myWriter.write(e.getMessage());
            System.out.println(e.getMessage());
        }


    }

    public  void deleteDuplicates(String input ,String output) throws IOException {
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


    public boolean onlyLetters(String name) {
        return name.matches("^[a-zA-Z0-9_ ]*$");

    }
    // Function to check if a string
    // contains only digits
    public static boolean
    onlyDigits(String str, int n)
    {
        // Traverse the string from
        // start to end
        boolean returnValue = true;

        for (int i = 0; i < n; i++) {

            // Check if character is
            // digit from 0-9
            // then return true
            // else false
            if (str.charAt(i) >= '0'
                    && str.charAt(i) <= '9') {
                returnValue = true;
            }
            else {
                returnValue =  false;
            }
        }
        return returnValue;
    }


}
