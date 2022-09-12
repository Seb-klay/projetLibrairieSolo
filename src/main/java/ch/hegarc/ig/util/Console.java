package ch.hegarc.ig.util;

import ch.hegarc.ig.JSON.Writer.JsonWriter;
import ch.hegarc.ig.XML.MainDOM;
import ch.hegarc.ig.business.Competition;
import ch.hegarc.ig.JSON.Reader.JsonReader;
import org.apache.commons.cli.*;

import java.util.*;
import java.util.logging.Logger;

public class Console {
    private Set<Competition> competitionsXmlAndJson = new HashSet<>();
    private static final Logger logger = Logger.getLogger(Console.class.getName());

    final private String CMD_IMPORT = "import";
    final private String CMD_EXPORT = "export";
    final private String CMD_ADD = "ajouter";
    final private String CMD_DELETE = "supprimer";
    final private String CMD_EXIT = "exit";

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

        boolean running = true;
        while (running) {
            String com = command.nextLine();
            String[] arguments = com.split(" ");
            CommandLine cmdLine = parseArguments(arguments);

            switch (cmdLine.getArgs()[0]) {

                case CMD_IMPORT:
                    if (cmdLine.hasOption(OPT_FICHIER.getOpt())) {
                        String filename = cmdLine.getOptionValue(OPT_FICHIER.getOpt());
                        StringBuilder sb = new StringBuilder();
                        sb.append("Import du fichier ").append(filename);
                        System.out.println(sb);

                        // TODO Import du fichier XML ou JSON
                        List<Competition> competitionsList;
                        if (filename.substring(filename.lastIndexOf(".") + 1).equals("xml")){
                            competitionsList = MainDOM.getDataXML(filename);
                        }else {
                            competitionsList = JsonReader.readSourceJackson(filename);
                        }
                        if (competitionsXmlAndJson.isEmpty()){
                            competitionsXmlAndJson = CompetitionHandler.sortList(competitionsList);
                        }else {
                            competitionsXmlAndJson = CompetitionHandler.sortList(competitionsList);
                            competitionsXmlAndJson = CompetitionHandler.fusionLists(competitionsXmlAndJson, competitionsList);
                        }
                        System.out.println(competitionsXmlAndJson);

                    } else {
                        printAppHelp();
                    }
                    break;

                case CMD_EXPORT:
                    String filename = cmdLine.getOptionValue(OPT_FICHIER.getOpt());
                    String competitionName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                    if ((cmdLine.hasOption(OPT_FICHIER.getOpt()) && cmdLine.hasOption(OPT_COMP.getOpt()))){
                        logger.info("\u001B[37m" + "Export de la compétition " + competitionName + " dans le fichier " + filename + "\u001B[0m");
                        // TODO Export du fichier JSON
                        JsonWriter.generateFileJacksonByCompetition(filename, competitionName, competitionsXmlAndJson);
                    } else if (cmdLine.hasOption(OPT_FICHIER.getOpt())) {
                        logger.info("\u001B[37m" + "Export de 'ensemble des compétitions dans le fichier " + filename + "\u001B[0m");
                        // TODO Export du fichier JSON
                        JsonWriter.generateFileJackson(filename, competitionsXmlAndJson);
                    } else {
                        printAppHelp();
                    }
                    break;

                case CMD_ADD:
                    String compName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                    String athNom = cmdLine.getOptionValue(OPT_NOM.getOpt());
                    String athPrenom = cmdLine.getOptionValue(OPT_PRENOM.getOpt());
                    String athAnnee = cmdLine.getOptionValue(OPT_ANNEE.getOpt());
                    String compPrice = cmdLine.getOptionValue(OPT_PRIX.getOpt());
                    if ((cmdLine.hasOption(OPT_COMP.getOpt()) && cmdLine.hasOption(OPT_NOM.getOpt()) && cmdLine.hasOption(OPT_PRENOM.getOpt()) && cmdLine.hasOption(OPT_ANNEE.getOpt()) && cmdLine.hasOption(OPT_PRIX.getOpt()))){
                        logger.info("\u001B[37m" + "Ajout de l'athlète " + athNom + " dans la compétition " + compName + "\u001B[0m");
                        // TODO Ajout de l'athlète
                    } else {
                        printAppHelp();
                    }
                    break;

                case CMD_DELETE:
                    String competName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                    String athleteNom = cmdLine.getOptionValue(OPT_NOM.getOpt());
                    String athletePrenom = cmdLine.getOptionValue(OPT_PRENOM.getOpt());
                    String athleteAnnee = cmdLine.getOptionValue(OPT_ANNEE.getOpt());
                    if ((cmdLine.hasOption(OPT_COMP.getOpt()) && cmdLine.hasOption(OPT_NOM.getOpt()) && cmdLine.hasOption(OPT_PRENOM.getOpt()) && cmdLine.hasOption(OPT_ANNEE.getOpt()))){
                        logger.info("\u001B[37m" + "Suppression de l'athlète " + athleteNom + " dans la compétition " + competName + "\u001B[0m");
                        // TODO Suppression de l'athlète
                        Competition compet = Utils.findCompetition(competName, competitionsXmlAndJson);
                        if (!compet.equals(null)){
                            competitionsXmlAndJson.add(Utils.suppressionAthlete(compet, athleteNom, athletePrenom, athleteAnnee));
                            System.out.println(competitionsXmlAndJson);
                        }else {
                            logger.warning("\u001B[33m" + "Aucune compétition trouvée sous ce nom..." + "\u001B[0m");
                        }
                    } else {
                        printAppHelp();
                    }
                    break;

                case CMD_EXIT:
                    System.out.println("Fermeture!");
                    running = false;
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
        formatter.printHelp(CMD_ADD, new Options().addOption(OPT_COMP).addOption(OPT_NOM).addOption(OPT_PRENOM).addOption(OPT_ANNEE).addOption(OPT_PRIX), true);
        formatter.printHelp(CMD_DELETE, new Options().addOption(OPT_COMP).addOption(OPT_NOM).addOption(OPT_PRENOM).addOption(OPT_ANNEE), true);
        formatter.printHelp(CMD_EXIT, new Options());
    }
}
