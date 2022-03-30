package ch.hegarc.ig.json;

import ch.hegarc.ig.business.Competition;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWriter {

    private static final Logger logger = Logger.getLogger(MainWriter.class.getName());
    /*
    public static void main(String[] args) {
        try {

            ObjectMapper om = new ObjectMapper();

            // Ignorer les champs vide
            //om.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            //Competition competitions = Competition.newPopulatedStudents();

            // Ecriture avec pretty print -> retour à la ligne etc.
            om.writerWithDefaultPrettyPrinter().writeValue(new File("EtudiantsDataBinding.json"), students);
            
            logger.log(Level.INFO, "Fichier <EtudiantsDataBinding.jackson> créé");
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }*/
}