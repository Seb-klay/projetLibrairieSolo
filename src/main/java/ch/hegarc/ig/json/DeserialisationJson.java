package ch.hegarc.ig.json;

import ch.hegarc.ig.business.Competition;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeserialisationJson {

    private static final Logger logger = Logger.getLogger(DeserialisationJson.class.getName());
    
    public static ArrayList JsonReader(String filename) {
        try {

            // ObjectMapper - Ignorer les propriétés inconnues
            ObjectMapper om = new ObjectMapper();//.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            om.setDateFormat(df);
            ArrayList listCompetition = om.readValue(new File(filename), new TypeReference<List<Competition>>() {});

            for (Competition c: (ArrayList<Competition>) listCompetition) {
                logger.log(Level.INFO, c.toString());
            }
            return listCompetition;

        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }
    }
}