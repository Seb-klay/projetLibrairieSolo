package ch.hegarc.ig.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainUnmarshalling {

    private static final Logger logger = Logger.getLogger(MainUnmarshalling.class.getName());

    private MainUnmarshalling() {
    }
/*
    private void run() {

        try {
            JAXBContext jc = JAXBContext.newInstance("ch.hegarc.ig.cpo");
            
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            XMLStreamReader in = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream("Students.xml"));

            JAXBElement<Students> o = (JAXBElement<Students>) unmarshaller.unmarshal(in, Students.class);

            Students students = o.getValue();

            for (Students.Student s : students.getStudent()) {
                logger.log(Level.INFO,
                        "{0}, {1}", new Object[]{s.getFirstname(), s.getLastname()});
            }

        } catch (Exception ex) {
            Logger.getLogger(MainUnmarshalling.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new MainUnmarshalling().run();
    }*/

}
