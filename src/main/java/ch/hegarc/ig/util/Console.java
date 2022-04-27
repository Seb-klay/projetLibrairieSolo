package ch.hegarc.ig.util;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;
import ch.hegarc.ig.json.DeserialisationJson;
import ch.hegarc.ig.json.SerialisationJson;
import ch.hegarc.ig.xml.MainUnmarshalling;
import org.apache.commons.cli.*;

import java.util.*;

public class Console {

    final private String CMD_IMPORT = "import";
    final private String CMD_EXPORT = "export";
    final private String CMD_STATS = "stats";
    final private String CMD_EXIT = "exit";
    final private String CMD_ADD = "add";
    final private String CMD_DELETE = "delete";

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
        List<Competition> dataJson = new ArrayList<>();
        List<Competition> dataXML = new ArrayList<>();
        List<Competition> sortedCompetitions = new ArrayList<>();
        Set<Competition> fusionnedCompetitions = new HashSet<>();

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
                            dataXML = MainUnmarshalling.XMLReader(fileName);
                        } else {
                            dataJson = DeserialisationJson.JsonReader(fileName);
                        }

                        if (dataJson != null && dataXML != null) {
                            sortedCompetitions = AthleteHandler.sortList(dataXML);
                            fusionnedCompetitions = AthleteHandler.fusionLists(dataJson, dataXML);
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
                        SerialisationJson.JsonWriter(fileName, projectName, dataJson);


                    } else if (cmdLine.hasOption(OPT_FICHIER.getOpt())) {
                        String fileName = cmdLine.getOptionValue(OPT_FICHIER.getOpt());
                        System.out.println("Export dans le fichier " + fileName);
                        String projectName = null;
                        // TODO Export du fichier JSON
                        SerialisationJson.JsonWriter(fileName, projectName, dataJson);
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

                        String projectName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                        String nom = cmdLine.getOptionValue(OPT_NOM.getOpt());
                        String prenom = cmdLine.getOptionValue(OPT_PRENOM.getOpt());
                        String annee = cmdLine.getOptionValue(OPT_ANNEE.getOpt());
                        String prix = cmdLine.getOptionValue(OPT_PRIX.getOpt());

                        System.out.println("Insertion de " + nom + " " + prenom + " annnée "+ annee + " dans la compétition" + projectName + " avec le prix de " + prix + " en cours");

                        // TODO Insertion d'un athlète
                        //import -f data.json
                        //add -c Paris -n test -p test -a 2002 -$ 32

                        List<Competition> dataWithAddedAthlete = AthleteHandler.add(dataJson, projectName, nom, prenom, annee, prix);
                        dataJson = Objects.isNull(dataWithAddedAthlete) ? dataJson : dataWithAddedAthlete ;

                    } else {
                        printAppHelp();
                    }
                    break;

                case CMD_DELETE:
                    if (cmdLine.hasOption(OPT_COMP.getOpt()) && cmdLine.hasOption(OPT_NOM.getOpt())
                            && cmdLine.hasOption(OPT_PRENOM.getOpt()) && cmdLine.hasOption(OPT_ANNEE.getOpt())) {

                        String projectName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                        String nom = cmdLine.getOptionValue(OPT_NOM.getOpt());
                        String prenom = cmdLine.getOptionValue(OPT_PRENOM.getOpt());
                        String annee = cmdLine.getOptionValue(OPT_ANNEE.getOpt());

                        System.out.println("Insertion de " + nom + " " + prenom + " annnée "+ annee + " dans la compétition" + projectName + " en cours");

                        // TODO Suppression d'un athlète
                        //delete -c Paris -n test -p test -a 2002
                        List<Competition> dataWithDeletedAthlete = AthleteHandler.delete(dataJson, projectName, nom, prenom, annee);
                        dataJson = Objects.isNull(dataWithDeletedAthlete) ? dataJson : dataWithDeletedAthlete ;
                    } else {
                        printAppHelp();
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
    }
}
