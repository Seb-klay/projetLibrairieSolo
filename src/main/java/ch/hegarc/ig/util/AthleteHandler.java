package ch.hegarc.ig.util;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
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
        List<Competition> compets = competitions;

        System.out.println("Début du tri : ");
        for (Competition competition : compets) {
            Collections.sort(competition.getAthletes());
        }
        Collections.sort(compets);
        return compets;
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
            StringBuilder sb = new StringBuilder();
            sb.append("Aucune compétitions trouvé à au nom de ");
            sb.append(projectName);
            System.out.println(sb);
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
            StringBuilder sbAthlete = new StringBuilder();
            sbAthlete.append("L'athlète ");
            sbAthlete.append(prenom);
            sbAthlete.append(" ");
            sbAthlete.append(nom);
            sbAthlete.append(" ");
            sbAthlete.append(annee);
            sbAthlete.append( " de prix " );
            sbAthlete.append(prix);
            sbAthlete.append(" de la compétition ");
            sbAthlete.append(projectName);
            sbAthlete.append(" à été ajouté");
            System.out.println(sbAthlete);
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
                StringBuilder sbAthlete = new StringBuilder();
                sbAthlete.append("L'athlète ");
                sbAthlete.append(prenom);
                sbAthlete.append(" ");
                sbAthlete.append(nom);
                sbAthlete.append(" ");
                sbAthlete.append(annee);
                sbAthlete.append(" de la compétition ");
                sbAthlete.append(projectName);
                sbAthlete.append(" à été supprimé");
                System.out.println(sbAthlete);
                return Competitions;
            }
            else
                return null;
        } catch (Exception e) {
            System.out.println("L'année n'est pas un nombre");
            return null;
        }
    }
    public static void biggestDonator(List<Competition> competitions, String nameCompetition) {
        Integer index = IntStream.range(0, competitions.size())
                .filter(competition -> nameCompetition.equals(competitions.get(competition).getName()))
                .findFirst()
                .orElse(-1);
        if (index == -1)
            System.out.println("Pas de compétition trouvée à ce nom");
        else {
            StringBuilder sb = new StringBuilder();
            Competition c = competitions.get(index);
            List<Athlete> athletes = sortBiggestDonator(c.getAthletes());
            for (Athlete athlete : athletes) {
                sb.append(athlete.getPrNom()).append(" ").append(athlete.getNom()).append(" ").append(athlete.getSomme());
                System.out.println(sb);
            }
        }
    }

    public static List<Athlete> sortBiggestDonator(List<Athlete> athletes) {
        Stream<Athlete> stream = athletes.stream();
        return stream.sorted(Comparator.comparing(Athlete::getSomme).reversed())
                .limit(5).collect(Collectors.toList());
    }
}
