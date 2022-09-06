package ch.hegarc.ig.XML.Reader;

import ch.hegarc.ig.Business.Athlete;
import ch.hegarc.ig.Business.Competition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class CompetitionBuilder {
    private Competition competition;
    private List<Competition> competitions = new ArrayList<>();
    public CompetitionBuilder() {
    }

    public void build(Document doc) {
        NodeList competitionNL = doc.getElementsByTagName("record");
        competition = new Competition();
        Element competElement = (Element) competitionNL.item(0);

        Element idCompet = (Element) competElement.getElementsByTagName("id").item(0);
        competition.setId(Integer.parseInt(idCompet.getTextContent()));

        Element libelleCompet = (Element) competElement.getElementsByTagName("competition").item(0);
        competition.setLibelle(libelleCompet.getTextContent());

        NodeList athleteNL = doc.getElementsByTagName("athletes");

        for (int i = 0; i < athleteNL.getLength(); i++) {
            Element studE = (Element) athleteNL.item(i);
            Athlete athlete = new Athlete();

            Element idE = (Element) studE.getElementsByTagName("id").item(0);
            athlete.setId(idE.getTextContent().charAt(0));

            Element genreE = (Element) studE.getElementsByTagName("genre").item(0);
            athlete.setGenre(genreE.getTextContent().charAt(0));

            Element firstNameE = (Element) studE.getElementsByTagName("prenom").item(0);
            athlete.setPrenom(firstNameE.getTextContent());

            Element lastNameE = (Element) studE.getElementsByTagName("nom").item(0);
            athlete.setNom(lastNameE.getTextContent());

            Element anneeE = (Element) studE.getElementsByTagName("annee").item(0);
            athlete.setAnnee(Integer.parseInt(anneeE.getTextContent()));

            competition.getAthletes().add(athlete);
        }
        competitions.add(competition);
    }

    public List<Competition> getCompetition() {
        return competitions;
    }
}
