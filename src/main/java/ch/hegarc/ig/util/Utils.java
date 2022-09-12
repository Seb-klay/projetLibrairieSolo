package ch.hegarc.ig.util;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;

import java.util.Set;

public class Utils {

    public static Competition findCompetition(String competitionName, Set<Competition> dataset){
        return dataset.stream()
                .filter(compet -> compet.getLibelle().equals(competitionName))
                .findAny()
                .orElse(null);
    }

    public static void ajoutAthlete(){

    }

    public static Competition suppressionAthlete(Competition competition, String athleteNom, String athletePrenom, String athleteAnnee){
        Athlete athlete = new Athlete();
        athlete.setNom(athleteNom);
        athlete.setPrenom(athletePrenom);
        athlete.setAnnee(Integer.parseInt(athleteAnnee));
        Athlete foundAthlete = competition.getAthletes().stream()
                .filter(a -> a.getNom().equals(athleteNom) && a.getPrenom().equals(athletePrenom) && a.getAnnee() == Integer.parseInt(athleteAnnee))
                .findAny()
                .orElse(null);
        competition.getAthletes().remove(foundAthlete);
        //TODO renvoyer nouvelle competition et l'ajouter à la
        return competition;
    }
}
