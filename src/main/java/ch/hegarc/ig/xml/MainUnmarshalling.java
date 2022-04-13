package ch.hegarc.ig.xml;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class MainUnmarshalling {

    private MainUnmarshalling() {
    }

    public static void run(String filename) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);

        RecordHandler handler = new RecordHandler();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new File(filename), handler);

            System.out.println(handler.getAthleteList().toString());
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException ex) {
            System.err.println(ex);
        }
    }
}
