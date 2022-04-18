package ch.hegarc.ig.util;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AthleteHandler {
    public static HashSet<Competition> fusionLists(List<Competition> competition1, List<Competition> competition2){
        HashSet<Competition> competitions = new HashSet<>(competition1);
        System.out.println("Début de la fusion : ");
        competitions.addAll(competition2);
        return competitions;
    }

    public static List<Competition> sortList(List<Competition> competitions){
        System.out.println("Début du tri : ");
        //Tri uniquement la liste d'athlètes
        /*for (Competition compets : competitions) {
            Collections.sort(compets.getAthletes());
        }*/

        //Tri uniquement la liste de compétitions
        Collections.sort(competitions);
        return competitions;
    }

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

            int readAnnee = -1;
            long readPrix = -1;

            try {
                readAnnee = Integer.valueOf(annee);
                readPrix = Long.valueOf(prix);
            } catch (Exception e) {
                System.out.println("L'année ou le prix n'est pas un nombre");
                return null;
            }
            athletes.add(new Athlete(id, prenom, nom, null, null, null, null, null, readPrix, false, false, readAnnee, LocalDate.now(), null));
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

        try {
            int readAnnee = Integer.parseInt(annee);
            if (athletes.removeIf(a -> a.getNom().equals(nom) && a.getPrNom().equals(prenom) && a.getAnnee() == readAnnee)) {
                Competitions.set(index, c);
                System.out.println("élément " + prenom + " " + nom + " " + annee + " de la compétition " + projectName + " à été supprimé");
                return Competitions;
            }
            else
                return null;
        } catch (Exception e) {
            System.out.println("L'année n'est pas un nombre");
            return null;
        }


    }
}
