package ch.hegarc.ig.XML.Reader;
import ch.hegarc.ig.Business.Athlete;
import ch.hegarc.ig.Business.Competition;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class MainDOM {
    public static List<Competition> getDataXML(String filename) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            DocumentBuilder loader = factory.newDocumentBuilder();
            Document document = loader.parse(filename);

            CompetitionBuilder competition = new CompetitionBuilder();

            competition.build(document);

            return competition.getCompetition();
        } catch (ParserConfigurationException pe){
            pe.printStackTrace();
        } catch (IOException io){
            io.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
}
