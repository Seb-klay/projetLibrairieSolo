package ch.hegarc.ig.json;

import ch.hegarc.ig.business.Competition;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainReader {

    private static final Logger logger = Logger.getLogger(MainReader.class.getName());
    
    public static void JsonReader() {
        try {

            // ObjectMapper - Ignorer les propriétés inconnues
            ObjectMapper om = new ObjectMapper();//.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            //ObjectMapper om = new ObjectMapper();

            //Competition competitions = om.readValue(new File("data.json"), Competition.class);
            ArrayList listCompetition = om.readValue(new File("data.json"), new TypeReference<List<Competition>>() {});
            Competition competitions = (Competition) listCompetition.get(0);

            System.out.println(competitions.toString());
            logger.log(Level.INFO, competitions.toString());
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}
