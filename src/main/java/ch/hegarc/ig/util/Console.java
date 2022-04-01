package ch.hegarc.ig.util;

import ch.hegarc.ig.json.DeserialisationJson;
import ch.hegarc.ig.json.SerialisationJson;
import ch.hegarc.ig.xml.MainUnmarshalling;
import org.apache.commons.cli.*;

import java.util.Scanner;

public class Console {

    final private String CMD_IMPORT = "import";
    final private String CMD_EXPORT = "export";
    final private String CMD_STATS = "stats";
    final private String CMD_EXIT = "exit";

    final private Option OPT_FICHIER = new Option("f", "fichier", true, "nom du fichier");
    final private Option OPT_COMP = new Option("c", "competiton", true, "nom de la competition");

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

                        String fileName = cmdLine.getOptionValue(OPT_FICHIER.getOpt());
                        System.out.println("Import du fichier " + fileName);

                        // TODO Import du fichier XML ou JSON
                        if (fileName.substring(fileName.length() - 3).equals("xml"))
                            MainUnmarshalling.run(fileName);
                        else
                            DeserialisationJson.JsonReader(fileName);

                    } else {
                        printAppHelp();
                    }
                    break;

                case CMD_EXPORT:
                    if (cmdLine.hasOption(OPT_FICHIER.getOpt()) && cmdLine.hasOption(OPT_COMP.getOpt())) {

                        String fileName = cmdLine.getOptionValue(OPT_FICHIER.getOpt());
                        String projectName = cmdLine.getOptionValue(OPT_COMP.getOpt());
                        System.out.println("Export du " + projectName + "dans le fichier " + fileName);

                        // TODO Export du fichier JSON
                        SerialisationJson.JsonWriter(fileName, projectName);


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
        options.addOption(OPT_FICHIER).addOption(OPT_COMP);
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
    }
}
