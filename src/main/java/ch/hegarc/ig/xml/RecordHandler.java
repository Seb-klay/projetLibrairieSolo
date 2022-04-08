package ch.hegarc.ig.xml;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.AthleteList;
import ch.hegarc.ig.business.Competition;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RecordBuilder extends DefaultHandler {
    private Competition competition;
    private AthleteList athleteList;
    private Athlete athlete;
    private StringBuffer texte;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equals("record")) {
            competition = new Competition();
            competition.setId(Long.parseLong("id"));
        }
        if (localName.equals("student")) {
            athlete = new Athlete();
        }

        if (localName.equals("id") || localName.equals("genre") || localName.equals("prenom") || localName.equals("nom") || localName.equals("annee") || localName.equals("email") || localName.equals("pays") || localName.equals("prixInscription") || localName.equals("paye") || localName.equals("annule") || localName.equals("dateInscription") || localName.equals("dateVersement")) {
            texte = new StringBuffer();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals("athlete")) {
            athleteList.getAthletes().add(athlete);
        }
        if (localName.equals("id")) {
            athlete.setId((Long.parseLong(texte.toString())));
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
        if (localName.equals("ville")) {
            athlete.setVille(texte.toString());
        }
        if (localName.equals("prixInscription")) {
            athlete.setSomme(Long.parseLong(texte.toString()));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (texte != null) {
            texte.append(new String(ch, start, length));
        }
    }

    public AthleteList getAthleteList() {
        return athleteList;
    }

}
