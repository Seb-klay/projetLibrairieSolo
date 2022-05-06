package ch.hegarc.ig.util;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;
import ch.hegarc.ig.json.DeserialisationJson;
import ch.hegarc.ig.json.SerialisationJson;
import ch.hegarc.ig.xml.MainUnmarshalling;
import org.apache.commons.cli.*;

import java.util.*;
import java.util.stream.Collectors;

public class Console {

    final private String CMD_IMPORT = "import";
    final private String CMD_EXPORT = "export";
    final private String CMD_STATS = "stats";
    final private String CMD_EXIT = "exit";
    final private String CMD_ADD = "add";
    final private String CMD_DELETE = "delete";
    final private String CMD_5_DONATEURS = "donateurs";
    final private String CMD_LISTE_SANS_DON = "dons";
    final private String CMD_LISTE_SOMME = "somme";
    final private String CMD_LISTE_EMAIL = "mail";
    final private String CMD_PAYS = "pays";
    final private String CMD_CATEGORIE = "categorie";

    final private Option OPT_FICHIER = new Option("f", "fichier", true, "nom du fichier");
    final private Option OPT_COMP = new Option("c", "competiton", true, "nom de la competition");
    final private Option OPT_NOM = new Option("n", "nom", true, "nom");
    final private Option OPT_PRENOM = new Option("p", "prenom", true, "prénom");
    final private Option OPT_ANNEE = new Option("a", "annee", true, "année athlète");
    final private Option OPT_PRIX = new Option("$", "prix", true, "prix");

    /**
     * Démarre la commande
     */
    public void runCommand() {

        Scanner command = new Scanner(System.in);
        System.out.println("Entrer votre commande: ");
        List<Competition> dataJsonAndXML = new ArrayList<>();
        List<Competition> dataXML = new ArrayList<>();
        List<Competition> dataJson = new ArrayList<>();

        boolean running = true;
        while (running) {
            String com = command.nextLine();
            String[] arguments = com.split(" ");
            CommandLine cmdLine = parseArguments(arguments);

            switch (cmdLine.getArgs()[0]) {

                case CMD_IMPORT:
                    if (cmdLine.hasOption(OPT_FICHIER.getOpt())) {
                        String fileName = cmdLine.getOptionValue(OPT_FICHIER.getOpt());
                        System.out.println("Import du fichier " + fileName);

                        // TODO Import du fichier XML ou JSON
                        if (fileName.substring(fileName.length() - 3).equals("xml")) {
                            if (dataJsonAndXML.isEmpty())
                                dataJsonAndXML = MainUnmarshalling.XMLReader(fileName);
                            else
                                dataXML = MainUnmarshalling.XMLReader(fileName);
                        } else {
                            if (dataJsonAndXML.isEmpty())
                                dataJsonAndXML = DeserialisationJson.JsonReader(fileName);
                            else
                                dataJson = DeserialisationJson.JsonReader(fileName);
                        }

                        if (!dataJsonAndXML.isEmpty()) {
                            Set<Competition> fusionnedCompetitions = null;
                            if (!dataJson.isEmpty()) {
                                fusionnedCompetitions = AthleteHandler.fusionListsCompetitions(dataJsonAndXML, dataJson);
                                dataJson = null;
                            } else if (!dataXML.isEmpty()){
                                fusionnedCompetitions = AthleteHandler.fusionListsCompetitions(dataJsonAndXML, dataXML);
                                dataXML = null;
                            }
                            if (!fusionnedCompetitions.isEmpty())
                                dataJsonAndXML = AthleteHandler.sortList(fusionnedCompetitions.stream().collect(Collectors.toList()));
                        }
                    } else {
                        printAppHelp();
                    }
                    break;

                case CMD_EXPORT:
                    if (cmdLine.hasOption(OPT_FICHIER.getOpt()) && cmdLine.hasOption(OPT_COMP.getOpt())) {

                        String fileName = cmdLine.getOptionValue(OPT_FICHIER.getOpt());
                        String projectName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                        System.out.println("Export du " + projectName + " dans le fichier " + fileName);

                        // TODO Export du fichier JSON
                        SerialisationJson.JsonWriter(fileName, projectName, dataJsonAndXML);


                    } else if (cmdLine.hasOption(OPT_FICHIER.getOpt())) {
                        String fileName = cmdLine.getOptionValue(OPT_FICHIER.getOpt());
                        System.out.println("Export dans le fichier " + fileName);
                        String projectName = null;
                        // TODO Export du fichier JSON
                        SerialisationJson.JsonWriter(fileName, projectName, dataJsonAndXML);
                    } else {
                        printAppHelp();
                    }
                    break;

                case CMD_STATS:

                    // TODO Calcule des stats des competitions

                    break;

                case CMD_EXIT:
                    System.out.println("Fermeture!");
                    running = false;
                    break;

                case CMD_ADD:
                    if (cmdLine.hasOption(OPT_COMP.getOpt()) && cmdLine.hasOption(OPT_NOM.getOpt())
                            && cmdLine.hasOption(OPT_PRENOM.getOpt()) && cmdLine.hasOption(OPT_ANNEE.getOpt())
                            && cmdLine.hasOption(OPT_PRIX.getOpt())) {

                        // TODO Insertion d'un athlète
                        //import -f data.json
                        //add -c Paris -n test -p test -a 2002 -$ 32
                        //add -c Effium -n Hardman -p Julietta -a 2000 -$ 32

                        if (!dataJsonAndXML.isEmpty()) {
                            String projectName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                            String nom = cmdLine.getOptionValue(OPT_NOM.getOpt());
                            String prenom = cmdLine.getOptionValue(OPT_PRENOM.getOpt());
                            String annee = cmdLine.getOptionValue(OPT_ANNEE.getOpt());
                            String prix = cmdLine.getOptionValue(OPT_PRIX.getOpt());
                            System.out.println("Insertion de " + nom + " " + prenom + " annnée " + annee + " dans la compétition" + projectName + " avec le prix de " + prix + " en cours");
                            List<Competition> dataWithAddedAthlete = AthleteHandler.add(dataJsonAndXML, projectName, nom, prenom, annee, prix);
                            dataJsonAndXML = Objects.isNull(dataWithAddedAthlete) ? dataJsonAndXML : dataWithAddedAthlete;
                        } else {
                            System.out.println("La liste est vide...");
                        }

                    } else {
                        printAppHelp();
                    }
                    break;

                case CMD_DELETE:
                    if (cmdLine.hasOption(OPT_COMP.getOpt()) && cmdLine.hasOption(OPT_NOM.getOpt())
                            && cmdLine.hasOption(OPT_PRENOM.getOpt()) && cmdLine.hasOption(OPT_ANNEE.getOpt())) {

                        // TODO Suppression d'un athlète
                        //delete -c Paris -n test -p test -a 2002

                        if (!dataJsonAndXML.isEmpty()) {
                            String projectName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                            String nom = cmdLine.getOptionValue(OPT_NOM.getOpt());
                            String prenom = cmdLine.getOptionValue(OPT_PRENOM.getOpt());
                            String annee = cmdLine.getOptionValue(OPT_ANNEE.getOpt());
                            StringBuilder sb = new StringBuilder();
                            sb.append("Insertion de ").append(nom).append(" ").append(prenom)
                                    .append(" annnée ").append(annee).append(" dans la compétition")
                                    .append(projectName).append(" en cours");
                            System.out.println(sb);
                            List<Competition> dataWithDeletedAthlete = AthleteHandler.delete(dataJsonAndXML, projectName, nom, prenom, annee);
                            dataJsonAndXML = Objects.isNull(dataWithDeletedAthlete) ? dataJsonAndXML : dataWithDeletedAthlete;
                        } else {
                            System.out.println("La liste est vide...");
                        }
                    } else {
                        printAppHelp();
                    }
                    break;

                case CMD_5_DONATEURS:
                    if (cmdLine.hasOption(OPT_COMP.getOpt())) {

                        // TODO Affichage des 5 plus gros donateurs
                        //donateurs -c Effium

                        if (!dataJsonAndXML.isEmpty()) {
                            String projectName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                            StringBuilder sb = new StringBuilder();
                            sb.append("Affichage des 5 plus gros donateurs : ")
                                    .append(projectName);
                            System.out.println(sb);
                            AthleteHandler.biggestDonator(dataJsonAndXML, projectName);
                        } else
                            System.out.println("La liste est vide...");

                    } else {
                        printAppHelp();
                    }
                    break;
                case CMD_LISTE_SANS_DON:
                    if (cmdLine.hasOption(OPT_COMP.getOpt())) {

                        // TODO Affichage des athlètes sans don
                        //dons -c Effium

                        if (!dataJsonAndXML.isEmpty()) {
                            String projectName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                            StringBuilder sb = new StringBuilder();
                            sb.append("Affichage de la liste des athlètes sans dons et sans annulation d'inscription : ")
                                    .append(projectName);
                            System.out.println(sb);
                            AthleteHandler.showPayAndInsFalse(dataJsonAndXML, projectName);
                        } else
                            System.out.println("La liste est vide...");
                    }
                    break;
                case CMD_LISTE_SOMME:
                    if (cmdLine.hasOption(OPT_COMP.getOpt())) {

                        // TODO Affichage de la somme des dons
                        //somme -c Effium

                        if (!dataJsonAndXML.isEmpty()) {
                            String projectName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                            StringBuilder sb = new StringBuilder();
                            sb.append("Affichage de la somme déjà payée, somme restante et la somme totale des inscriptions : ")
                                    .append(projectName);
                            System.out.println(sb);
                            AthleteHandler.showSum(dataJsonAndXML, projectName);
                        } else
                            System.out.println("La liste est vide...");
                    }
                    break;
                case CMD_LISTE_EMAIL:
                    if (cmdLine.hasOption(OPT_COMP.getOpt())) {

                        // TODO Affichage de la liste des emails
                        //mail -c Effium

                        if (!dataJsonAndXML.isEmpty()) {
                            String projectName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                            StringBuilder sb = new StringBuilder();
                            sb.append("Affichage des e-mails des athlètes de la compétition : ")
                                    .append(projectName);
                            System.out.println(sb);
                            AthleteHandler.showMail(dataJsonAndXML, projectName);
                        } else
                            System.out.println("La liste est vide...");
                    }
                    break;

                case CMD_PAYS:
                    if (cmdLine.hasOption(OPT_COMP.getOpt())) {

                        // TODO Affichage des pays
                        //pays -c Effium

                        if (!dataJsonAndXML.isEmpty()) {
                            String projectName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                            StringBuilder sb = new StringBuilder();
                            sb.append("Affichage des pays ou se déroule de la compétition : ")
                                    .append(projectName);
                            System.out.println(sb);
                            AthleteHandler.showPays(dataJsonAndXML, projectName);
                        } else
                            System.out.println("La liste est vide...");
                    }
                    break;
                case CMD_CATEGORIE:
                    if (cmdLine.hasOption(OPT_COMP.getOpt())) {

                        // TODO Affichage des catégories
                        //categorie -c Effium

                        if (!dataJsonAndXML.isEmpty()) {
                            String projectName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                            StringBuilder sb = new StringBuilder();
                            sb.append("Définition des catégories selon une compétition : ")
                                    .append(projectName);
                            System.out.println(sb);
                            AthleteHandler.defineCategorieAthlete(dataJsonAndXML, projectName);
                        } else {
                            System.out.println("La liste est vide...");
                        }
                    }
                    break;

                default:
                    System.out.println("Commande non reconnue!");
                    break;
            }
        }
        command.close();
    }

    /**
     * Parses des arguments
     *
     * @param args application arguments
     * @return <code>CommandLine</code> which represents a list of application
     * arguments.
     */
    private CommandLine parseArguments(String[] args) {

        Options options = getAllOptions();
        CommandLine line = null;
        CommandLineParser parser = new DefaultParser();

        try {
            line = parser.parse(options, args);

        } catch (ParseException ex) {

            System.err.println("Erreur dans la lecture des arguments!");
            System.err.println(ex.toString());
            printAppHelp();
        }

        return line;
    }

    /**
     * Generates application command line options
     *
     * @return application <code>Options</code>
     */
    private Options getAllOptions() {

        Options options = new Options();
        options.addOption(OPT_FICHIER).addOption(OPT_COMP).addOption(OPT_NOM).addOption(OPT_PRENOM).addOption(OPT_ANNEE).addOption(OPT_PRIX);
        return options;
    }

    /**
     * Prints application help
     */
    private void printAppHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(CMD_IMPORT, new Options().addOption(OPT_FICHIER), true);
        formatter.printHelp(CMD_EXPORT, new Options().addOption(OPT_FICHIER).addOption(OPT_COMP), true);
        formatter.printHelp(CMD_STATS, new Options().addOption(OPT_COMP), true);
        formatter.printHelp(CMD_EXIT, new Options());
        formatter.printHelp(CMD_ADD, new Options().addOption(OPT_COMP).addOption(OPT_NOM).addOption(OPT_PRENOM).addOption(OPT_ANNEE).addOption(OPT_PRIX), true);
        formatter.printHelp(CMD_DELETE, new Options().addOption(OPT_COMP).addOption(OPT_NOM).addOption(OPT_PRENOM).addOption(OPT_ANNEE), true);
        formatter.printHelp(CMD_5_DONATEURS, new Options().addOption(OPT_COMP), true);
        formatter.printHelp(CMD_LISTE_SANS_DON, new Options().addOption(OPT_COMP), true);
        formatter.printHelp(CMD_LISTE_SOMME, new Options().addOption(OPT_COMP), true);
        formatter.printHelp(CMD_LISTE_EMAIL, new Options().addOption(OPT_COMP), true);
        formatter.printHelp(CMD_PAYS, new Options().addOption(OPT_COMP), true);
        formatter.printHelp(CMD_CATEGORIE, new Options().addOption(OPT_COMP), true);
    }
}
