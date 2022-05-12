package ch.hegarc.ig.util;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AthleteHandler {
    public static Integer getIndexOfListOfCompetitionByAttributeProjectName(List<Competition> competitions, String projectName) {
        return IntStream.range(0, competitions.size())
                .filter(competition -> projectName.equals(competitions.get(competition).getName()))
                .findFirst()
                .orElse(-1);
    }

    public static HashSet<Competition> fusionListsCompetitions(List<Competition> competition1, List<Competition> competition2) {
        HashSet<Competition> competitions = new HashSet<>(competition1);
        competitions.addAll(competition2);
        System.out.println("Listes fusionnées\n");
        return competitions;
    }

    public static List<Athlete> fusionListsAthletes(List<Athlete> athletes, Athlete a) {
        HashSet<Athlete> listeAthletes = new HashSet<>(athletes);
        listeAthletes.add(a);
        return listeAthletes.stream().collect(Collectors.toList());
    }

    public static List<Competition> sortList(List<Competition> competitions) {
        List<Competition> compets = competitions;

        for (Competition competition : compets) {
            Collections.sort(competition.getAthletes());
        }
        Collections.sort(compets);
        System.out.println("Liste triée \n");
        return compets;
    }

    public static List<Competition> add(List<Competition> competitions, String projectName, String nom, String prenom, String annee, String prix) {
        if (competitions == null) {
            System.out.println("Aucune compétitions enregistrées");
            return null;
        }
        Integer index = getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
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
        Integer index = getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
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
                        .append(" à été supprimé");
                System.out.println(sbAthlete);
                return competitions;
            } else
                return null;
        } catch (Exception e) {
            System.out.println("L'année n'est pas un nombre");
            return null;
        }
    }

    public static void biggestDonator(List<Competition> competitions, String projectName) {
        Integer index = getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
        ;
        if (index == -1)
            System.out.println("Pas de compétition trouvée à ce nom");
        else {
            StringBuilder sb = new StringBuilder();
            Competition c = competitions.get(index);
            List<Athlete> athletes = sortBiggestDonator(c.getAthletes());
            for (Athlete athlete : athletes) {
                sb.append(athlete.getPrNom()).append(" ").append(athlete.getNom()).append(" ").append(athlete.getSomme()).append("\n");
            }
            System.out.println(sb);
        }
    }

    public static List<Athlete> sortBiggestDonator(List<Athlete> athletes) {
        Stream<Athlete> stream = athletes.stream();
        return stream.sorted(Comparator.comparing(Athlete::getSomme).reversed())
                .limit(5).collect(Collectors.toList());
    }

    public static void showPayAndInsFalse(List<Competition> competitions, String projectName) {
        Integer index = getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
        if (index == -1)
            System.out.println("Pas de compétition trouvée à ce nom");
        else {
            List<Athlete> athletes = competitions.get(index).getAthletes();
            StringBuilder sb = new StringBuilder();
            for (Athlete a : athletes) {
                if ((!a.isPay()) && (!a.isAnnul())) {
                    sb.append(a.getPrNom())
                            .append(" ")
                            .append(a.getNom())
                            .append(" ")
                            .append(a.getSomme())
                            .append("\n");
                }
            }
            System.out.println(sb);
        }
    }

    public static void showSum(List<Competition> competitions, String projectName) {
        Integer index = getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
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
            System.out.println(sb);
        }
    }

    public static void showMail(List<Competition> competitions, String projectName) {
        Integer index = getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
        if (index == -1)
            System.out.println("Pas de compétition trouvée à ce nom");
        else {
            StringBuilder sb = new StringBuilder();
            boolean premierPassage = false;
            for (Athlete a : competitions.get(index).getAthletes()) {
                if (premierPassage) {
                    sb.append(";");
                }
                premierPassage = true;
                sb.append(a.getEmail());
            }
            System.out.println(sb);
        }
    }

    public static void showPays(List<Competition> competitions, String projectName) {
        Integer index = getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
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
            System.out.println(sb);
        }
    }

    public static void defineCategorieAthlete(List<Competition> competitions, String projectName) {
        Integer index = getIndexOfListOfCompetitionByAttributeProjectName(competitions, projectName);
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

    public static void generationStatsExcel(List<Competition> competitions) {
        List<String> pays;
        // Nouveau document, nouvel onglet
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Stats compétitions");

        int rowNb = 0;
        int colNb = 0;
        XSSFRow row = sheet.createRow(rowNb);
        XSSFCell cell;

        pays = getCompetitionPlaceInList(competitions);

        int colPays = 1;

        for (String p : pays) {
            cell = row.createCell(colPays);
            cell.setCellValue(p);
            colPays += 1;
        }

        Font font = wb.createFont();
        CellStyle style = wb.createCellStyle();
        font.setBold(true);
        style.setFont(font);

        if (competitions.size() > 1) {
            cell = row.createCell(colPays);
            cell.setCellValue("Total");
            row.getCell(colPays).setCellStyle(style);
        }
        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb);
        cell.setCellValue("Somme des dons");
        row.getCell(colNb).setCellStyle(style);

        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb);
        cell.setCellValue("Dons payés");

        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb);
        cell.setCellValue("Dons restants");

        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb);
        cell.setCellValue("Nb d'annulations");

        row = sheet.createRow(rowNb += 2);
        cell = row.createCell(colNb);
        cell.setCellValue("Inscription");
        row.getCell(colNb).setCellStyle(style);

        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb);
        cell.setCellValue("Junior");

        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb);
        cell.setCellValue("Elite");

        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb);
        cell.setCellValue("Vétéran 1");

        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb);
        cell.setCellValue("Vétéran 2");

        defineCategorieAthlete(competitions);
        List<Integer> sommeDonsInscr = constructExcelData(competitions);

        colNb = 1;
        int sommeCpt = 0;

        int oneCompet = competitions.size() > 1 ? 0 : 1;

        for (int i = 0; i <= pays.size() - oneCompet; i++) {;
            for (int rowCpt = 1; rowCpt < 11; rowCpt++) {
                if (rowCpt == 5) {
                    rowCpt++;
                }
                row = sheet.getRow(rowCpt);
                cell = row.createCell(colNb);
                cell.setCellValue(sommeDonsInscr.get(sommeCpt));
                if (rowCpt == 1|| rowCpt == 6){
                    row.getCell(colNb).setCellStyle(style);
                }
                sommeCpt++;
            }
            colNb += 1;
        }

        try (OutputStream fileOut = new FileOutputStream("stats.xlsx")) {
            wb.write(fileOut);
            System.out.println("Excel créer avec réussite");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getCompetitionPlaceInList(List<Competition> competitions) {
        List<String> pays = new ArrayList<>();
        for (Competition c : competitions) {
            pays.add(c.getName());
        }
        return pays;
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

    public static List<Integer> constructExcelData(List<Competition> competitions) {
        List<Integer> sommeDonsInscr = new ArrayList<>();
        int cpt = 0;

        for (Competition c : competitions) {

            for (int i = 0; i < 9; i++) {
                sommeDonsInscr.add(0);
            }

            for (Athlete a : c.getAthletes()) {
                if (!a.isAnnul()) {
                    sommeDonsInscr.set(9 * cpt, Math.toIntExact(sommeDonsInscr.get(9 * cpt) + a.getSomme()));
                    if (a.isPay()) {
                        sommeDonsInscr.set(9 * cpt + 1, Math.toIntExact(sommeDonsInscr.get(9 * cpt + 1) + a.getSomme()));
                    } else {
                        sommeDonsInscr.set(9 * cpt + 2, Math.toIntExact(sommeDonsInscr.get(9 * cpt + 2) + a.getSomme()));
                    }

                    sommeDonsInscr.set(9 * cpt + 4, sommeDonsInscr.get(9 * cpt + 4) + 1);

                    switch (a.getCategorie()) {
                        case "Junior":
                            sommeDonsInscr.set(9 * cpt + 5, sommeDonsInscr.get(9 * cpt + 5) + 1);
                            break;
                        case "Elite":
                            sommeDonsInscr.set(9 * cpt + 6, sommeDonsInscr.get(9 * cpt + 6) + 1);
                            break;
                        case "Vétéran 1":
                            sommeDonsInscr.set(9 * cpt + 7, sommeDonsInscr.get(9 * cpt + 7) + 1);
                            break;
                        case "Vétéran 2":
                            sommeDonsInscr.set(9 * cpt + 8, sommeDonsInscr.get(9 * cpt + 8) + 1);
                            break;
                        default:
                            break;
                    }
                } else {
                    sommeDonsInscr.set(9 * cpt + 3, sommeDonsInscr.get(9 * cpt + 3) + 1);
                }
            }
            cpt++;
        }

        if (competitions.size() > 1) {
            int total;

            for (int i = 0; i < 9; i++) {
                total = 0;
                sommeDonsInscr.add(0);
                for (int j = 0; j < cpt; j++) {
                    total += sommeDonsInscr.get(9 * j + i);
                }
                sommeDonsInscr.set(9 * cpt + i, total);
            }
        }
        return sommeDonsInscr;
    }
}
