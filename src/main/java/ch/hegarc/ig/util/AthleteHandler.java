package ch.hegarc.ig.util;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AthleteHandler {

    public static List<Athlete> fusionListsAthletes(List<Athlete> athletes, Athlete a) {
        HashSet<Athlete> listeAthletes = new HashSet<>(athletes);
        listeAthletes.add(a);
        return listeAthletes.stream().collect(Collectors.toList());
    }

    public static List<Competition> add(List<Competition> competitions, String projectName, String nom, String prenom, String annee, String prix) {
        if (competitions == null) {
            System.out.println("Aucune compétitions enregistrées");
            return null;
        }
        Integer index = CompetitionHandler.getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
        if (index == -1) {
            StringBuilder sb = new StringBuilder();
            sb.append("Aucune compétitions trouvé au nom de ");
            sb.append(projectName);
            System.out.println(sb);
            return null;
        } else {
            Competition c = competitions.get(index);
            long id = c.getAthletes().stream().map(u -> u.getId()).max(Comparator.naturalOrder()).get() + 1;
            List<Athlete> athletes = competitions.get(index).getAthletes();

            int readAnnee = -1;
            long readPrix = -1;

            try {
                readAnnee = Integer.valueOf(annee);
                readPrix = Long.valueOf(prix);
            } catch (Exception e) {
                System.out.println("L'année ou le prix n'est pas un nombre");
                return null;
            }
            List<Athlete> athleteFusioned;
            athleteFusioned = fusionListsAthletes(athletes, new Athlete(id, null, prenom, nom, null, null, null, null, null, null, readPrix, false, false, readAnnee, LocalDate.now(), null));
            Collections.sort(athleteFusioned);
            c.setAthletes(athleteFusioned);
            competitions.set(index, c);
            StringBuilder sbAthlete = new StringBuilder();
            sbAthlete.append("L'athlète ")
                    .append(prenom)
                    .append(" ")
                    .append(nom)
                    .append(" ")
                    .append(annee)
                    .append(" de prix ")
                    .append(prix)
                    .append(" de la compétition ")
                    .append(projectName)
                    .append(" à été ajouté");
            System.out.println(sbAthlete);
            return competitions;
        }
    }

    public static List<Competition> delete(List<Competition> competitions, String projectName, String nom, String prenom, String annee) {
        Integer index = CompetitionHandler.getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
        ;
        if (index == -1)
            return null;
        Competition c = competitions.get(index);
        List<Athlete> athletes = c.getAthletes();

        try {
            int readAnnee = Integer.parseInt(annee);
            if (athletes.removeIf(a -> a.getNom().equals(nom) && a.getPrNom().equals(prenom) && a.getAnnee() == readAnnee)) {
                competitions.set(index, c);
                StringBuilder sbAthlete = new StringBuilder();
                sbAthlete.append("L'athlète ")
                        .append(prenom)
                        .append(" ")
                        .append(nom)
                        .append(" ")
                        .append(annee)
                        .append(" de la compétition ")
                        .append(projectName)
                        .append(" à été supprimé-e");
                System.out.println(sbAthlete);
                return competitions;
            } else
                System.out.println("Aucune personne trouvée avec ces paramètres");
                return null;
        } catch (Exception e) {
            System.out.println("L'année n'est pas un nombre");
            return null;
        }
    }

    public static List<Athlete> biggestDonator(List<Competition> competitions, String projectName, boolean show) {
        Integer index = CompetitionHandler.getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
        if (index == -1)
            System.out.println("Pas de compétition trouvée à ce nom");
        else {
            StringBuilder sb = new StringBuilder();
            Competition c = competitions.get(index);
            List<Athlete> athletes = sortBiggestDonator(c.getAthletes());
            List<Athlete> athltesBiggest = new ArrayList<>();
            for (Athlete athlete : athletes) {
                athltesBiggest.add(athlete);
                sb.append(athlete.getPrNom()).append(" ").append(athlete.getNom()).append(" ").append(athlete.getSomme()).append("\n");
            }
            if (show)
                System.out.println(sb);
            return athltesBiggest;
        }
        return null;
    }

    public static List<Athlete> sortBiggestDonator(List<Athlete> athletes) {
        Stream<Athlete> stream = athletes.stream();
        return stream.sorted(Comparator.comparing(Athlete::getSomme).reversed())
                .limit(5).collect(Collectors.toList());
    }

    public static List<Athlete> showPayAndInsFalse(List<Competition> competitions, String projectName, boolean show) {
        Integer index = CompetitionHandler.getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
        if (index == -1)
            System.out.println("Pas de compétition trouvée à ce nom");
        else {
            List<Athlete> athletes = competitions.get(index).getAthletes();
            List<Athlete> athletesNotPaid = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for (Athlete a : athletes) {
                if ((!a.isPay()) && !(a.isAnnul())) {
                    athletesNotPaid.add(a);
                    sb.append(a.getPrNom())
                            .append(" ")
                            .append(a.getNom())
                            .append(" ")
                            .append(a.getSomme())
                            .append("\n");
                }
            }
            if (show)
                 System.out.println(sb);
            return athletesNotPaid;
        }
        return null;
    }

    public static int nbInscr(List<Competition> competitions, String projectName) {
        Integer index = CompetitionHandler.getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
        if (index == -1)
            System.out.println("Pas de compétition trouvée à ce nom");
        else {
            List<Athlete> athletes = competitions.get(index).getAthletes();
            int count = 0;
            for (Athlete a : athletes) {
                if (!a.isAnnul()) {
                    count++;
                }
            }
            return count;
        }
        return -1;
    }

    public static int showSum(List<Competition> competitions, String projectName, boolean show) {
        Integer index = CompetitionHandler.getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
        if (index == -1)
            System.out.println("Pas de compétition trouvée à ce nom");
        else {
            List<Athlete> athletes = competitions.get(index).getAthletes();
            int argentPaye = 0;
            int sommeRestant = 0;
            int sommeTotale;
            StringBuilder sb = new StringBuilder();
            for (Athlete a : athletes) {
                if (!a.isAnnul()) {
                    if (a.isPay()) {
                        argentPaye += a.getSomme();
                    } else {
                        sommeRestant += a.getSomme();
                    }
                }
            }
            sommeTotale = argentPaye + sommeRestant;
            sb.append("La somme déjà payée s'élève à ")
                    .append(argentPaye)
                    .append(" CHF, la somme restante à payer est de ")
                    .append(sommeRestant)
                    .append(" CHF et la somme totale est de ")
                    .append(sommeTotale)
                    .append(" CHF.");
            if (show)
                System.out.println(sb);
            return sommeTotale;
        }
        return -1;
    }

    public static List<String> showMail(List<Competition> competitions, String projectName, boolean show) {
        Integer index = CompetitionHandler.getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
        if (index == -1)
            System.out.println("Pas de compétition trouvée à ce nom");
        else {
            StringBuilder sb = new StringBuilder();
            boolean premierPassage = false;
            for (Athlete a : competitions.get(index).getAthletes()) {
                if (a.getEmail() != null && !a.getEmail().isEmpty()) {
                    if (premierPassage) {
                        sb.append(";");
                    }
                    premierPassage = true;
                    sb.append(a.getEmail());
                }
            }
            if(show)
                System.out.println(sb);
            return Arrays.asList(sb.toString().split(";"));
        }
        return null;
    }

    public static List<String> showPays(List<Competition> competitions, String projectName, boolean show) {
        Integer index = CompetitionHandler.getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
        if (index == -1) {
            System.out.println("Pas de compétition trouvée à ce nom");
        } else {
            Set<String> pays = new HashSet<>();
            StringBuilder sb = new StringBuilder();
            for (Athlete a : competitions.get(index).getAthletes()) {
                pays.add(a.getPays());
            }
            sb.append("Pays : \n");
            for (String p : pays) {
                sb.append(p)
                        .append("\n");
            }
            if( show)
                System.out.println(sb);
            return pays.stream().collect(Collectors.toList());
        }
        return null;
    }

    public static void defineCategorieAthlete(List<Competition> competitions, String projectName) {
        Integer index = CompetitionHandler.getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
        if (index == -1)
            System.out.println("Pas de compétition trouvée à ce nom");
        else {
            int age = 0;
            for (Athlete a : competitions.get(index).getAthletes()) {
                age = (LocalDate.now().getYear() - a.getAnnee());
                if ((age >= 18) && (age <= 25)) {
                    a.setCategorie("Junior");
                } else if ((age >= 26) && (age <= 45)) {
                    a.setCategorie("Elite");
                } else if ((age >= 46) && (age <= 65)) {
                    a.setCategorie("Vétéran 1");
                } else if (age >= 66) {
                    a.setCategorie("Vétéran 2");
                }
            }
            System.out.println("Catégorie des athlètes définies pour la compétition donnée");
        }
    }

    public static void defineCategorieAthlete(List<Competition> competitions) {
        for (Competition c : competitions) {
            int age = 0;
            for (Athlete a : c.getAthletes()) {
                age = (LocalDate.now().getYear() - a.getAnnee());
                if ((age >= 18) && (age <= 25)) {
                    a.setCategorie("Junior");
                } else if ((age >= 26) && (age <= 45)) {
                    a.setCategorie("Elite");
                } else if ((age >= 46) && (age <= 65)) {
                    a.setCategorie("Vétéran 1");
                } else if (age >= 66) {
                    a.setCategorie("Vétéran 2");
                }
            }
        }
    }

}
