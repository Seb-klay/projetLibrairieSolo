package ch.hegarc.ig.json;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Marshalling {

    private static final Logger logger = Logger.getLogger(Marshalling.class.getName());

    public static void JsonWriter(String filename, String projectName) {
        try {

            ObjectMapper om = new ObjectMapper();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            om.setDateFormat(df);

            // Ignorer les champs vide
            //om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            List<Athlete> athletes = new ArrayList<>();
            athletes.add(new Athlete(1,"diano","test","diano@diano.com","canadian","diano","Kiev","diano",3,true,false,"diano","test",2002));
            Competition competition = new Competition(2L, projectName, LocalDate.of(2020, 1, 8), athletes);
            List<Competition> competitions = new ArrayList<>();
            competitions.add(competition);

            // Ecriture avec pretty print -> retour à la ligne etc.
            om.writerWithDefaultPrettyPrinter().writeValue(new File(filename), competitions);
            
            logger.log(Level.INFO, "Fichier <"+ filename +"> créé");
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}