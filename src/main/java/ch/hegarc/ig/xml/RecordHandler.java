package ch.hegarc.ig.xml;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecordHandler extends DefaultHandler {
    private Competition competition;
    private final List<Competition> competitionList = new ArrayList<>();
    private Athlete athlete;
    private StringBuffer texte;
    private boolean isAthlete = false ;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (localName.equals("record")) {
            competition = new Competition();
            competitionList.add(competition);
        }
        if (localName.equals("athletes")) {
            athlete = new Athlete();
            isAthlete = true;
        }
        if (localName.equals("id") || localName.equals("competition") || localName.equals("genre") || localName.equals("prenom") || localName.equals("nom") || localName.equals("annee") || localName.equals("email") || localName.equals("pays") || localName.equals("prixInscription") || localName.equals("paye") || localName.equals("annule") || localName.equals("dateInscription") || localName.equals("dateVersement")) {
            texte = new StringBuffer();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (localName.equals("id")) {
            if (isAthlete){
                athlete.setId((Long.parseLong(texte.toString())));
            } else {
                competition.setId(Long.parseLong(texte.toString()));
            }
        }
        if (localName.equals("competition")) {
            competition.setName(texte.toString());
        }
        if (localName.equals("athletes")) {
            competition.getAthletes().add(athlete);
            isAthlete = false;
        }
        if (localName.equals("genre")) {
            //rien
        }
        if (localName.equals("prenom")) {
            athlete.setPrNom(texte.toString());
        }
        if (localName.equals("nom")) {
            athlete.setNom(texte.toString());
        }
        if (localName.equals("annee")) {
            athlete.setAnnee(Integer.parseInt(texte.toString()));
        }
        if (localName.equals("email")) {
            athlete.setEmail(texte.toString());
        }
        if (localName.equals("pays")) {

        }
        if (localName.equals("ville")) {
            athlete.setVille(texte.toString());
        }
        if (localName.equals("prixInscription")) {
            athlete.setSomme(Long.parseLong(texte.toString()));
        }
        if (localName.equals("paye")) {
            athlete.setPay(Boolean.parseBoolean(texte.toString()));
        }
        if (localName.equals("annule")) {
            athlete.setAnnul(Boolean.parseBoolean(texte.toString()));
        }
        if (localName.equals("dateInscription")) {
            //athlete.setDateInscription(LocalDate.parse(texte.toString()));
        }
        if (localName.equals("dateVersement")) {
            //athlete.setDateVersement(LocalDate.parse(texte.toString()));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length){
        if (texte != null) {
            texte.append(new String(ch, start, length));
        }
    }

    public List<Competition> getAthleteList() {
        return competitionList;
    }

}
