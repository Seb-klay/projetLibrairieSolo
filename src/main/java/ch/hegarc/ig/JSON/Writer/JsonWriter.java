package ch.hegarc.ig.JSON.Writer;

import ch.hegarc.ig.business.Competition;
import ch.hegarc.ig.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class JsonWriter {

    private static final Logger logger = Logger.getLogger(JsonWriter.class.getName());
    public static void generateFileJackson(String filename, List<Competition> dataset) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), dataset);
            logger.info("\u001B[32m" + "Fichier créé sous le nom de <" + filename +  "> avec l'ensemble des compétitions" + "\u001B[0m");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateFileJacksonByCompetition(String filename, String competitionName, List<Competition> dataset) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), dataset);
            Competition competition = Utils.findCompetition(competitionName, dataset);
            if (competition.equals(null)){
                logger.warning("\u001B[37m" + "Aucune compétition trouvée sous ce nom..." + "\u001B[0m");
            }else {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), competition);
                logger.info("\u001B[32m" + "Fichier créé sous le nom de <" + filename + "> avec la compétition " + competitionName + "\u001B[0m");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException n){
            logger.warning("\u001B[0m" + "Aucune compétition trouvée sous ce nom..." + "\u001B[0m");
        }
    }
}
