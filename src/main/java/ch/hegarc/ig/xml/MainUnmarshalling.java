package ch.hegarc.ig.xml;

import ch.hegarc.ig.business.Competition;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainUnmarshalling {

    private MainUnmarshalling() {
    }

    public static List<Competition> XMLReader(String filename) {
        List<Competition> competitionList = new ArrayList<>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);

        RecordHandler handler = new RecordHandler();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new File(filename), handler);

            competitionList = handler.getCompetitionsList();

        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException ex) {
            System.err.println(ex);
        }
        return competitionList;
    }
}
