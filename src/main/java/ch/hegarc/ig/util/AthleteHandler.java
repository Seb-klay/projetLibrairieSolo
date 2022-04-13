package ch.hegarc.ig.util;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

public class AthleteHandler {
    public static List<Competition> add(List<Competition> Competitions, String projectName, String nom, String prenom, String annee, String prix) {
        if (Competitions == null) {
            System.out.println("Aucune compétitions enregistrées");
            return null;
        }
        Integer index = IntStream.range(0, Competitions.size())
                .filter(competition -> projectName.equals(Competitions.get(competition).getName()))
                .findFirst()
                .orElse(-1);
        if (index == -1){
            System.out.println("Aucune compétitions trouvé à au nom de " + projectName);
            return null;
        }
        else {
            Competition c = Competitions.get(index);
            long id = c.getAthletes().stream().map(u -> u.getId()).max(Comparator.naturalOrder()).get() + 1;
            ArrayList<Athlete> athletes = (ArrayList<Athlete>) Competitions.get(index).getAthletes();
            athletes.add(new Athlete(id, prenom, nom, null, null, null, null, null, Long.valueOf(prix), false, false, Integer.valueOf(annee), LocalDate.now(), null));
            c.setAthletes(athletes);
            Competitions.set(index, c);
            System.out.println("élément " + prenom + " " + nom + " " + annee + " de prix" + prix +" de la compétition " + projectName + " à été ajouté");
            return Competitions;
        }
    }

    public static List<Competition> delete(List<Competition> Competitions, String projectName, String nom, String prenom, String annee) {
        Integer index = IntStream.range(0, Competitions.size())
                .filter(competition -> projectName.equals(Competitions.get(competition).getName()))
                .findFirst()
                .orElse(-1);
        if (index == -1)
            return null;
        Competition c = Competitions.get(index);
        List<Athlete> athletes = c.getAthletes();

        if (athletes.removeIf(a -> a.getNom().equals(nom) && a.getPrNom().equals(prenom) && a.getAnnee() == Integer.parseInt(annee))) {
            Competitions.set(index, c);
            System.out.println("élément " + prenom + " " + nom + " " + annee + " de la compétition " + projectName + " à été supprimé");
            return Competitions;
        }
        else
            return null;
    }
}
