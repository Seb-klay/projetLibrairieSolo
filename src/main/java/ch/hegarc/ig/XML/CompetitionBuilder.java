package ch.hegarc.ig.XML;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CompetitionBuilder {
    private Competition competition;
    private List<Competition> competitions = new ArrayList<>();
    private boolean isFirst = false;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public CompetitionBuilder() {
    }

    public void build(Document doc) {
        NodeList competitionNL = doc.getElementsByTagName("record");
        int counter = 0;
        for (int j = 0; j < competitionNL.getLength(); j++){
            //Utilisé pour terminer la boucle while lorsqu'une liste d'athlètes arrive à sa fin
            boolean isAthlete = true;
            //Utilisé pour empecher le premier athlète d'être ajouté à la liste précédente
            boolean toDelete = false;

            competition = new Competition();
            Element competElement = (Element) competitionNL.item(j);

            Element idCompet = (Element) competElement.getElementsByTagName("id").item(0);
            competition.setId(Integer.parseInt(idCompet.getTextContent()));

            Element libelleCompet = (Element) competElement.getElementsByTagName("competition").item(0);
            competition.setLibelle(libelleCompet.getTextContent());

            NodeList athleteNL = doc.getElementsByTagName("athletes");

            while(isAthlete){
                Element studE = (Element) athleteNL.item(counter);
                if (counter >= athleteNL.getLength()-1){
                    isAthlete = false;
                }

                int idAthlete = Integer.parseInt(studE.getElementsByTagName("id").item(0).getTextContent());
                Athlete athlete = new Athlete();

                if (idAthlete == 1 && isFirst){
                    isAthlete = false;
                    toDelete = true;
                    counter--;
                }

                if (idAthlete == 1){
                    isFirst = !isFirst;
                }

                Element idE = (Element) studE.getElementsByTagName("id").item(0);
                athlete.setId(Integer.parseInt(idE.getTextContent()));

                Element genreE = (Element) studE.getElementsByTagName("genre").item(0);
                athlete.setGenre(genreE.getTextContent().charAt(0));

                Element firstNameE = (Element) studE.getElementsByTagName("prenom").item(0);
                athlete.setPrenom(firstNameE.getTextContent());

                Element lastNameE = (Element) studE.getElementsByTagName("nom").item(0);
                athlete.setNom(lastNameE.getTextContent());

                Element anneeE = (Element) studE.getElementsByTagName("annee").item(0);
                athlete.setAnnee(Integer.parseInt(anneeE.getTextContent()));

                Element emailE = (Element) studE.getElementsByTagName("email").item(0);
                athlete.setEmail(emailE.getTextContent());

                Element paysE = (Element) studE.getElementsByTagName("pays").item(0);
                athlete.setPays(paysE.getTextContent());

                Element prixInscriptionE = (Element) studE.getElementsByTagName("prixInscription").item(0);
                athlete.setPrixInscription(Integer.parseInt(prixInscriptionE.getTextContent()));

                Element payeE = (Element) studE.getElementsByTagName("paye").item(0);
                athlete.setPaye(Boolean.parseBoolean(payeE.getTextContent()));

                Element annuleE = (Element) studE.getElementsByTagName("annule").item(0);
                athlete.setAnnule(Boolean.parseBoolean(annuleE.getTextContent()));

                Element dateInscriptionE = (Element) studE.getElementsByTagName("dateInscription").item(0);
                LocalDate dateInsc = LocalDate.parse(dateInscriptionE.getTextContent(), formatter);
                athlete.setDateInscription(dateInsc);

                if (!studE.getElementsByTagName("dateVersement").item(0).getTextContent().isEmpty()){
                    Element dateVersementE = (Element) studE.getElementsByTagName("dateVersement").item(0);
                    LocalDate dateVers = LocalDate.parse(dateVersementE.getTextContent(), formatter);
                    athlete.setDateVersement(dateVers);
                }

                if (!toDelete){
                    competition.getAthletes().add(athlete);
                }
                counter++;
            }
            competitions.add(competition);
        }
    }

    public List<Competition> getCompetition() {
        return competitions;
    }
}
