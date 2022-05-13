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

public class SerialisationJson {

    private static final Logger logger = Logger.getLogger(SerialisationJson.class.getName());

    public static void JsonWriter(String filename, String projectName, List dataJson) {
        try {

            ObjectMapper om = new ObjectMapper();
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            om.setDateFormat(df);

            Competition c = null;
            if (projectName != null) {
                c = ((ArrayList<Competition>) dataJson).stream()
                        .filter(competition -> projectName.equals(competition.getName()))
                        .findAny()
                        .orElse(null);
                dataJson = new ArrayList();
                dataJson.add(c);
            }
            // Ecriture avec pretty print -> retour à la ligne etc.
            if (projectName == null){
                om.writerWithDefaultPrettyPrinter().writeValue(new File(filename), dataJson);
                logger.log(Level.INFO, "Fichier <" + filename + "> créé");
            }
            else if (c != null) {
                om.writerWithDefaultPrettyPrinter().writeValue(new File(filename), dataJson);
                logger.log(Level.INFO, "Fichier <" + filename + "> créé");
            } else {
                System.out.println("Aucune compétition trouvé à ce nom");
            }

        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}