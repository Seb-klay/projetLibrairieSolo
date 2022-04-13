package ch.hegarc.ig.xml;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.AthleteList;
import ch.hegarc.ig.business.Competition;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class RecordHandler extends DefaultHandler {
    private Competition competition;
    private AthleteList athleteList;
    private Athlete athlete;
    private StringBuffer texte;
    private boolean isAthlete = false ;

    //Le doc fonctionne UNIQUEMENT si le "id" et "competition" dans le document dataset.xml sont retirés...

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (localName.equals("dataset")) {
            athleteList = new AthleteList();
        }
        if (localName.equals("record")) {
            competition = new Competition();
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
            athleteList.getAthletes().add(athlete);
            isAthlete = false;
        }
        if (localName.equals("genre")) {

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
        /*if (localName.equals("ville")) {
            athlete.setVille(texte.toString());
        }*/
        if (localName.equals("prixInscription")) {
            athlete.setSomme(Long.parseLong(texte.toString()));
        }
        if (localName.equals("paye")) {

        }
        if (localName.equals("annule")) {

        }
        if (localName.equals("dateInscription")) {

        }
        if (localName.equals("dateVersement")) {

        }
    }

    @Override
    public void characters(char[] ch, int start, int length){
        if (texte != null) {
            texte.append(new String(ch, start, length));
        }
    }

    public AthleteList getAthleteList() {
        return athleteList;
    }

}
