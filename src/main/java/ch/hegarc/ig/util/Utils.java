package ch.hegarc.ig.util;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    public static Competition findCompetition(String competitionName, List<Competition> dataset){
        return dataset.stream()
                .filter(compet -> compet.getLibelle().equals(competitionName))
                .findAny()
                .orElse(null);
    }

    public static int findBiggestId(Competition competition){
        Athlete athlete = competition.getAthletes().stream()
                .max(Comparator.comparingInt(Athlete::getId))
                .get();
        return athlete.getId();
    }

    public static Competition ajoutAthlete(Competition competition, String athleteNom, String athletePrenom, String athleteAnnee, int competitionPrice){
        Athlete athlete = new Athlete();

        athlete.setId(findBiggestId(competition)+1);
        athlete.setNom(athleteNom);
        athlete.setPrenom(athletePrenom);
        athlete.setAnnee(Integer.parseInt(athleteAnnee));
        athlete.setPrixInscription(competitionPrice);
        athlete.setDateInscription(LocalDate.now());

        competition.getAthletes().add(athlete);
        logger.info("\u001B[37m" + "Ajout de l'athlète " + athleteNom + " dans la compétition " + competition.getLibelle() + "\u001B[0m");
        Collections.sort(competition.getAthletes());
        return competition;
    }

    public static Competition suppressionAthlete(Competition competition, String athleteNom, String athletePrenom, String athleteAnnee){
        Athlete foundAthlete = competition.getAthletes().stream()
                .filter(a -> a.getNom().equals(athleteNom) &&
                        a.getPrenom().equals(athletePrenom) &&
                        a.getAnnee() == Integer.parseInt(athleteAnnee))
                .findAny()
                .orElse(null);
        if (foundAthlete != null){
            competition.getAthletes().remove(foundAthlete);
            logger.info("\u001B[37m" + "Suppression de l'athlète " + athleteNom + " dans la compétition " + competition.getLibelle() + "\u001B[0m");
            return competition;
        }else {
            logger.warning("\u001B[33m" + "Aucun athlète trouvé sous ce nom..." + "\u001B[0m");
            return null;
        }
    }

    public static List<Athlete> find5BiggestDonators(Competition competition){
        StringBuilder sb = new StringBuilder();
        List<Athlete> athletes = competition.getAthletes().stream()
                .sorted(Comparator.comparing(Athlete::getPrixInscription).reversed())
                .limit(5)
                .collect(Collectors.toList());
        sb.append("----- 5 plus gros donateurs -----\n");
        for (Athlete a : athletes){
            sb.append(a.getNom()).append(" ")
                    .append(a.getPrenom()).append(" ")
                    .append(a.getPrixInscription()).append("\n");
        }
        //System.out.println(sb);
        return athletes;
    }

    public static List<Athlete> hasNotPaid(Competition competition){
        StringBuilder sb = new StringBuilder();
        List<Athlete> athletes = competition.getAthletes().stream()
                .filter(a -> !a.isPaye() && !a.isAnnule())
                .collect(Collectors.toList());
        sb.append("----- Athlètes n'ayant pas payé -----\n");
        for (Athlete a : athletes){
            sb.append(a.getNom()).append(" ")
                    .append(a.getPrenom()).append(" ")
                    .append(a.getPrixInscription()).append("\n");
        }
        //System.out.println(sb);
        return athletes;
    }

    public static long sums(Competition competition){
        StringBuilder sb = new StringBuilder();
        long sommePayee = sommePayee(competition);
        long sommeRestante = sommeRestante(competition);
        long sommeTotale = sommePayee + sommeRestante;
        sb.append("----- Sommes de la compétition ").append(competition.getLibelle())
                        .append(" -----\n")
                        .append("Somme déjà payée : ").append(sommePayee).append("\n")
                        .append("Somme restante : ").append(sommeRestante).append("\n")
                        .append("Somme totale : ").append(sommeTotale);

        //System.out.println(sb);
        return sommeTotale;
    }

    public static long sommePayee(Competition competition){
        return competition.getAthletes().stream()
                .filter(a -> a.isPaye() && !a.isAnnule())
                .mapToInt(Athlete::getPrixInscription)
                .sum();
    }

    public static long sommeRestante(Competition competition){
        return competition.getAthletes().stream()
                .filter(a -> !a.isPaye() && !a.isAnnule())
                .mapToInt(Athlete::getPrixInscription)
                .sum();
    }

    public static List<String> getMails(Competition competition){
        StringBuilder sb = new StringBuilder();
        String emails = competition.getAthletes().stream()
                .filter(a -> a.getEmail() != null && !a.getEmail().isEmpty())
                .map(Athlete::getEmail)
                .collect(Collectors.joining(" ; ", " ", " "));
        sb.append("----- Emails de la compétition ").append(competition.getLibelle())
                .append(" -----\n")
                .append(emails).append("\n");

        //System.out.println(sb);
        return Arrays.asList(emails.split(";"));
    }

    public static List<String> getPays(Competition competition){
        StringBuilder sb = new StringBuilder();
        List<String> pays = competition.getAthletes().stream()
                .map(Athlete::getPays)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        sb.append("----- Pays des participants de la compétition ").append(competition.getLibelle())
                .append(" -----\n")
                .append(pays).append("\n");

        //System.out.println(sb);
        return pays;
    }

    public static void getCategorie(Competition competition){
        StringBuilder sb = new StringBuilder();
        sb.append("----- Categorie des participants de la compétition ").append(competition.getLibelle())
                .append(" -----\n");
        //System.out.println(sb);
        competition.getAthletes().stream()
                .forEach(Utils::accept);
    }

    private static void accept(Athlete a) {
        StringBuilder sb = new StringBuilder();
        int age = (LocalDate.now().getYear() - a.getAnnee());

        if ((age >= 18) && (age <= 25)) {
            a.setCategorie("Junior");
        } else if ((age >= 26) && (age <= 45)) {
            a.setCategorie("Elite");
        } else if ((age >= 46) && (age <= 65)) {
            a.setCategorie("Vétéran 1");
        } else if ((age >= 66)) {
            a.setCategorie("Vétéran 2");
        }
        sb.append("Nom : ").append(a.getNom()).append(" ")
                .append("Prénom : ").append(a.getPrenom()).append(" ")
                .append("Année : ").append(a.getAnnee()).append(" ")
                .append("Catégorie : ").append(a.getCategorie());
        //System.out.println(sb);
    }

    public static long nbAnnulations(Competition competition){
        return competition.getAthletes().stream()
                .filter(Athlete::isAnnule)
                .count();
    }

    public static long nbInscriptionByCategorie(Competition competition, String categorie){
        return competition.getAthletes().stream()
                .filter(a -> a.getCategorie().equals(categorie) && !a.isAnnule())
                .count();
    }

    public static long nbInscriptions(Competition competition){
        return competition.getAthletes().stream()
                .filter(a -> !a.isAnnule())
                .count();
    }
}
