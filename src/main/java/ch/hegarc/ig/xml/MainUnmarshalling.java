package ch.hegarc.ig.xml;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.awt.color.ICC_ColorSpace;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainUnmarshalling {

    private static final Logger logger = Logger.getLogger(MainUnmarshalling.class.getName());

    private MainUnmarshalling() {
    }

    public static void run(String filename) {

        try {
            JAXBContext jc = JAXBContext.newInstance("ch.hegarc.ig");
            
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            XMLStreamReader in = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(filename));

            JAXBElement<Competition> o = (JAXBElement<Competition>) unmarshaller.unmarshal(in, Competition.class);

            Competition competition = o.getValue();

            for (Athlete a : competition.getAthletes()) {
                logger.log(Level.INFO,
                        "{0}", new Object[]{a.getPrNom()});
            }

        } catch (Exception ex) {
            Logger.getLogger(MainUnmarshalling.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //public static void main(String[] args) {
      //  new MainUnmarshalling().run();
    //}

}
