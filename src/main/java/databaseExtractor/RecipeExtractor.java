package databaseExtractor;


import com.dunnoWhatToEat.snapshot.Entity.Ingrediente;
import com.dunnoWhatToEat.snapshot.Entity.IngredienteRicetta;
import com.dunnoWhatToEat.snapshot.Entity.Ricetta;
import com.dunnoWhatToEat.snapshot.repository.IngredienteRepository;
import com.dunnoWhatToEat.snapshot.repository.IngredienteRicettaRepository;
import com.dunnoWhatToEat.snapshot.repository.RicettaRepository;
import org.hibernate.Session;
import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
@ComponentScan({"package com.DunnoWhatToEat.snapshot"})
public class RecipeExtractor implements CommandLineRunner {


    private Session session;

    @Autowired
    private RicettaRepository ricettaRepository;
    @Autowired
    private IngredienteRepository ingredienteRepository;
    @Autowired
    private IngredienteRicettaRepository ingredienteRicettaRepo;
    public static String enterPath = "C:/Users/denkiwi/Desktop/Development/dunnoWhatToEat/src/main/resources/database/ricette.txt";
    public static String exitPath = "C:/Users/denkiwi/Desktop/Development/dunnoWhatToEat/src/main/resources/database/log.txt";
    public static String tipiPiattitxt = "C:/Users/denkiwi/Desktop/Development/dunnoWhatToEat/src/main/resources/database/tipipiatti.txt";

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RecipeExtractor.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        int i = 0;
        File inputDatabaseFile = new File(enterPath);
        FileReader reader = new FileReader(inputDatabaseFile);


        FileInputStream inputStream = new FileInputStream(enterPath);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        FileWriter logWriter = null;
        File fileLog = new File(exitPath);
        File tipiPiattiFile = new File(tipiPiattitxt);
        FileWriter tipiPiattiWriter = new FileWriter(tipiPiattitxt);
        if (fileLog.exists()) {
            fileLog.delete();
        }
        if (tipiPiattiFile.exists()) {
            tipiPiattiFile.delete();
        }


        try (BufferedReader br = new BufferedReader(inputStreamReader)) {
            logWriter = new FileWriter(exitPath);


            logWriter.write("INIZIO ESTRAZIONE " + "\n");
            logWriter.write(new Date() + "\n");
            Ricetta ricetta = new Ricetta();
            String tempIngredientePrinc = "";
            for (String line; (line = br.readLine()) != null; ) {

                if (line.equals(":Ricette")) {
                    line = br.readLine();
                    i++;
                }

                if (line.equals("-Nome")) {
                    line = br.readLine();
                    i++;
                    ricetta = new Ricetta();
                    ricetta.setTitolo(line);
                } else {
                    logWriter.write("FORMATO SBAGLIATO -Nome" + "\n");
                    logWriter.write(line + " " + i);
                    logWriter.close();
                    throw new Exception("FORMATO SBAGLIATO -Nome");


                }
                line = br.readLine();
                i++;
                if (!line.equals("-Tipo_Piatto")) {
                    logWriter.write(line + " " + i);
                    logWriter.write("FORMATO SBAGLIATO -Tipo_Piatto" + "\n");
                    logWriter.close();
                    throw new Exception("FORMATO SBAGLIATO -Tipo_Piatto");
                } else {

                    line = br.readLine();
                    i++;
                    tipiPiattiWriter.write(line + "\n"); // da inserire un metodo che inserire un id
                    // e non la stringa
                }
                line = br.readLine();
                i++;

                if (!line.equals("-Ing_Principale")) {

                    logWriter.write("FORMATO SBAGLIATO -Ing_Principale" + "\n");
                    logWriter.write(line + " " + i);
                    logWriter.close();
                    throw new Exception("FORMATO SBAGLIATO -Ing_Principale");
                } else {
                    line = br.readLine();
                    i++;
                    tempIngredientePrinc = line;
                    // l'assocciazione con la foreign key
                }
                line = br.readLine();
                i++;
                if (!line.equals("-Persone")) {
                    logWriter.write("FORMATO SBAGLIATO -Persone" + "\n");
                    logWriter.write(line + " " + i);
                    logWriter.close();
                    throw new Exception("FORMATO SBAGLIATO -Persone");

                } else {
                    line = br.readLine();
                    i++;
                    ricetta.setNr_persone(Integer.parseInt(line));
                }
                line = br.readLine();
                i++;
                if (!line.equals("-Note")) {
                    logWriter.write("FORMATO SBAGLIATO -Note" + "\n");
                    logWriter.write(line + " " + i);
                    logWriter.close();
                    throw new Exception("FORMATO SBAGLIATO -Note");

                } else {
                    line = br.readLine();
                    i++;
                    //if(!line.equals("-"));
                    //{
                    ricetta.setNote(line); // capire se inserire un valore tipo ""
                    //}                                           // o se fare 2 insert con query diverse visto che alcune avranno
                    //il campo note  e altre no

                }
                line = br.readLine();
                i++;
                if (!line.equals("-Ingredienti")) {

                    logWriter.write("FORMATO SBAGLIATO -Ingredienti" + "\n");
                    logWriter.write(line + " " + i);
                    logWriter.close();
                    throw new Exception("FORMATO SBAGLIATO -Ingredienti");
                } else {
                    boolean keepGoing = true;
                    HashSet<IngredienteRicetta> set = new HashSet<>();
                    while (keepGoing) {
                        line = br.readLine();
                        i++;
                        if (line.equals("-Preparazione")) {
                            keepGoing = false;
                            // ricetta.setIngredienti(set);
                            Iterator<IngredienteRicetta> ingreIter = set.iterator();
                            for (Iterator<IngredienteRicetta> it = ingreIter; it.hasNext(); ) {
                                IngredienteRicetta ingr = it.next();
                                if (ingr.getIngrediente().getNome().replace(" ", "").equalsIgnoreCase(tempIngredientePrinc.replace(" ", ""))) {
                                    ricetta.setIngrediente_princ(ingr.getIngrediente());

                                }


                            }
                            set = new HashSet<>();
                        } else {
                            IngredienteRicetta ingr = new IngredienteRicetta();
                            String proporzione = line.substring(0, line.indexOf("=", 1));
                            String nomeIngrediente = line.substring(line.indexOf("==", 1)).replace("=", "");
                            Pattern patternParentesi = Pattern.compile("\\(([^\\)]+)\\)");
                            Matcher matcher = patternParentesi.matcher(nomeIngrediente);
                            Pattern patternPossibilePeso = Pattern.compile("(k?Da|k?Di) (\\d+) (k?G)|(\\d+) (G)");
                            Matcher matcherPossibilePeso = patternPossibilePeso.matcher(nomeIngrediente);
                            String valoreParentesi = "";
                            boolean someThingFound = false;
                            if (matcher.find()) {
                                valoreParentesi = matcher.group();
                                someThingFound = true;

                            } else if (matcherPossibilePeso.find()) {
                                valoreParentesi = matcherPossibilePeso.group();
                                someThingFound = true;
                            }

                            if (someThingFound) {


                                Pattern patternPesoGrammi = Pattern.compile("(k?Da|k?Di) (\\d+) (k?G)|(\\d+) (G)");
                                Matcher matcherPeso = patternPesoGrammi.matcher(valoreParentesi);
                                if (matcherPeso.find() && !nomeIngrediente.contains("Gradi") ) {
                                    proporzione = matcherPeso.group();
                                    proporzione = proporzione.replaceAll("(k?Da|k?Di) ", "");

                                        nomeIngrediente = nomeIngrediente.replaceAll("(k?Da|k?Di) (\\d+) (k?G)|(\\d+) (G)", "");

                                    if (nomeIngrediente.contains("()")) {
                                        nomeIngrediente = nomeIngrediente.replace(" ()", "");
                                    }
                                }

                            }


                            if (!line.equals(" ==== Per Decorare:")) {
                                Ingrediente ingredientePresente = checkExistingIngredientOrAdd(nomeIngrediente);

                                if (proporzione.contains("/")) {
                                    Expression e = new Expression(proporzione);
                                    double res = e.calculate();
                                    if (Double.isNaN(res)) {
                                        StringBuilder propor = new StringBuilder();
                                        StringBuilder tipo = new StringBuilder();
                                        for (int k = 0; k < proporzione.length(); k++) {
                                            if (Character.isDigit(proporzione.charAt(k)) || proporzione.toCharArray()[k] == "/".charAt(0)) {
                                                propor.append(proporzione.charAt(k));
                                            } else tipo.append(proporzione.charAt(k));
                                        }
                                        Expression e2 = new Expression(propor.toString());
                                        double res2 = e2.calculate();
                                        ingr.setQuantita(res2);
                                        ingr.setUnitaMisura(tipo.toString());
                                    } else {
                                        ingr.setQuantita((res));
                                        ingr.setUnitaMisura("parti");
                                    }

                                } else if (onlyDigits(proporzione) && !proporzione.equals(" ")) {
                                    ingr.setQuantita(Integer.parseInt(proporzione.replace(" ", "")));

                                } else if (proporzione.contains("0 G") && onlyDigits(proporzione.substring(0, proporzione.indexOf(" G")))) {
                                    String typ = proporzione.substring(proporzione.indexOf(" G"));
                                    String quant = proporzione.substring(0, proporzione.indexOf(" G"));
                                    quant = quant.replace(" ", "");
                                    ingr.setQuantita(Integer.parseInt(quant));
                                    ingr.setUnitaMisura(typ);

                                } else if (onlyLetters(proporzione) && !proporzione.equals(" ")) {
                                    ingr.setUnitaMisura(proporzione);

                                } else {
                                    if (proporzione.equals(" ")) {
                                        ingr.setQuantita(0);
                                    } else {
                                        StringBuilder tipo = new StringBuilder();
                                        StringBuilder qfinale = new StringBuilder();
                                        for (int k = 0; k < proporzione.length(); k++) {
                                            if (Character.isDigit(proporzione.charAt(k))) {
                                                qfinale.append(proporzione.charAt(k));
                                            } else tipo.append(proporzione.charAt(k));
                                        }
                                        ingr.setUnitaMisura(tipo.toString());
                                        ingr.setQuantita(Integer.parseInt(qfinale.toString()));
                                    }
                                }
                                String ingredienti = line.substring(line.indexOf("==", 1));
                                ingr.setIngrediente(ingredientePresente);
                                ingr.setRicetta(ricetta);
                                ricettaRepository.save(ricetta);
                                ingredienteRicettaRepo.save(ingr);
                                set.add(ingr);
                            }
                        }

                    }

                }

                if (!line.equals("-Preparazione")) {

                    logWriter.write("FORMATO SBAGLIATO -Preparazione" + "\n");
                    logWriter.write(line + " " + i);
                    logWriter.close();
                    throw new Exception("FORMATO SBAGLIATO -Preparazione");
                } else {
                    boolean keepGoing = true;
                    StringBuilder procediment = new StringBuilder();
                    while (keepGoing) {
                        line = br.readLine();
                        i++;
                        if (!line.equals(":Ricette")) {
                            procediment.append(line + " ");
                        } else {
                            ricetta.setPreparazione(procediment.toString());
                            keepGoing = false;
                        }

                    }


                }


                ricettaRepository.save(ricetta);
                i++;
                logWriter.write("Ricetta salvata " + ricetta.toString() + "\n");
                logWriter.write("--------------------------------");
            }

            System.out.println("FINITO");
            System.out.println(new Date());
            logWriter.write(new Date().toString());
            logWriter.close();
            tipiPiattiWriter.close();

            //deleteDuplicates(exitPath, finalPath);


        } catch (Exception e) {
            Iterator stackiter = Arrays.stream(e.getStackTrace()).iterator();
            while (stackiter.hasNext()) {
                StackTraceElement el = (StackTraceElement) stackiter.next();
                logWriter.write(el.toString());
            }

            logWriter.write("ERRORE LINEA " + i);
            logWriter.close();
            tipiPiattiWriter.close();
            System.out.println(e.getStackTrace());
        }


    }

    private Ingrediente checkExistingIngredientOrAdd(String nomeIngrediente) {
        String nome = nomeIngrediente;
        if (nomeIngrediente.startsWith(" ")) {
            nome = nomeIngrediente.substring(1);
        }
        Ingrediente ingredienteEssistente = ingredienteRepository.getByName(nome);
        if (ingredienteEssistente == null) {
            Ingrediente newIngrediente = new Ingrediente(nome);
            ingredienteRepository.save(newIngrediente);
            return newIngrediente;
        }
        return ingredienteEssistente;
    }

    public void deleteDuplicates(String input, String output) throws IOException {
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
            if (set.add(input)) {
                i++;
                writer.append(i + " " + input + "\n");
            }
        }
        writer.flush();
        System.out.println("Contents added............");
    }


    public boolean onlyLetters(String name) {
        return name.matches("[a-zA-Z\\s]*$");
    }

    // Function to check if a string
    // contains only digits
    public static boolean onlyDigits(String str) {
        return str.matches("^[0-9\\s]*$");
    }


}
