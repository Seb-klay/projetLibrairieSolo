package ch.hegarc.ig.excel;

import ch.hegarc.ig.business.Competition;
import ch.hegarc.ig.util.Utils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainExcel {
    private static final Logger logger = Logger.getLogger(MainExcel.class.getName());
    public static final String SOMME = "SUM(";
    public static final char VIRGULE = ',';
    public static final char CLOSE = ')';
    public static void excelFileOneCompetition(Competition competition){
        // Nouveau document, nouvel onglet
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(competition.getLibelle());

        //Créer les polices standard et gras
        XSSFFont boldFont = wb.createFont();
        boldFont.setBold(true);
        //Setter le style
        CellStyle boldStyle = wb.createCellStyle();
        boldStyle.setFont(boldFont);

        int rowNb = 0;
        int colNb = 0;
        XSSFRow row = sheet.createRow(rowNb);
        XSSFCell cell;

        // Nom de la compétition
        cell = row.createCell(++colNb);
        cell.setCellValue(competition.getLibelle());

        // Somme des dons
        colNb = 0;
        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb++);
        cell.setCellStyle(boldStyle); //Mettre en gras
        cell.setCellValue("Somme des dons");
        cell = row.createCell(colNb);
        cell.setCellStyle(boldStyle); //Mettre en gras
        cell.setCellValue(Utils.sums(competition));

        // Dons déjà payés
        colNb = 0;
        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb++);
        cell.setCellValue("Dons payés");
        cell = row.createCell(colNb);
        cell.setCellValue(Utils.sommePayee(competition));

        // Dons restants à payer
        colNb = 0;
        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb++);
        cell.setCellValue("Dons restants");
        cell = row.createCell(colNb);
        cell.setCellValue(Utils.sommeRestante(competition));

        // Nombre d'annulations
        colNb = 0;
        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb++);
        cell.setCellValue("Nb d'annulation");
        cell = row.createCell(colNb);
        cell.setCellValue(Utils.nbAnnulations(competition));

        rowNb++;
        colNb = 0;

        // Infos inscriptions
        StringBuilder somme = new StringBuilder(SOMME);
        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb++);
        cell.setCellStyle(boldStyle); //Mettre en gras
        cell.setCellValue("Inscriptions");
        cell = row.createCell(colNb);
        cell.setCellStyle(boldStyle); //Mettre en gras
        somme.append(cell.getReference().charAt(0)).append(rowNb+2)
                .append(VIRGULE)
                .append(cell.getReference().charAt(0)).append(rowNb+3)
                .append(VIRGULE)
                .append(cell.getReference().charAt(0)).append(rowNb+4)
                .append(VIRGULE)
                .append(cell.getReference().charAt(0)).append(rowNb+5)
                .append(CLOSE);
        cell.setCellFormula(somme.toString());

        // Nombre d'inscriptions par categorie
        List<String> categories = new ArrayList<>();
        categories.add("Junior");
        categories.add("Elite");
        categories.add("Vétéran 1");
        categories.add("Vétéran 2");

        for (String category : categories) {
            colNb = 0;
            row = sheet.createRow(++rowNb);
            cell = row.createCell(colNb++);
            cell.setCellValue(category);
            cell = row.createCell(colNb);
            Utils.getCategorie(competition);
            cell.setCellValue(Utils.nbInscriptionByCategorie(competition, category));
        }

        try (OutputStream fileOut = new FileOutputStream("competition" + competition.getLibelle() + ".xlsx")) {
            wb.write(fileOut);
            logger.info("\u001B[37m" + "Création du fichier Excel <competition " + competition.getLibelle() + ".xlsx> de la compétition " + competition.getLibelle() + "\u001B[0m");
        } catch (FileNotFoundException e) {
            logger.warning("\u001B[33m" + "Un problème est survenu, supprimer le fichier avec le même nom ou vérifiez la commande..." + "\u001B[0m");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void excelFileAllCompetitions(List<Competition> competitions){
        int counter;

        // Nouveau document, nouvel onglet
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("allCompetitions");

        //Créer les polices standard et gras
        XSSFFont boldFont = wb.createFont();
        boldFont.setBold(true);
        //Setter le style
        CellStyle boldStyle = wb.createCellStyle();
        boldStyle.setFont(boldFont);

        int rowNb = 0;
        int colNb = 0;
        XSSFRow row = sheet.createRow(rowNb);
        XSSFCell cell;

        for (Competition competition : competitions){
            // Nom de la compétition
            cell = row.createCell(++colNb);
            cell.setCellValue(competition.getLibelle());
        }
        cell = row.createCell(++colNb);
        cell.setCellStyle(boldStyle); //Mettre en gras
        cell.setCellValue("Total");

        colNb = 0;
        StringBuilder sommesDons = new StringBuilder();
        sommesDons.append(SOMME);
        // Somme des dons
        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb++);
        cell.setCellStyle(boldStyle); //Mettre en gras
        cell.setCellValue("Somme des dons");
        counter = 0;
        for (Competition competition : competitions){
            cell = row.createCell(colNb++);
            cell.setCellStyle(boldStyle); //Mettre en gras
            cell.setCellValue(Utils.sums(competition));
            sommesDons.append(cell.getReference());
            counter++;
            if (counter != competitions.size()){
                sommesDons.append(VIRGULE);
            }
        }

        cell = row.createCell(colNb);
        sommesDons.append(CLOSE);
        cell.setCellStyle(boldStyle); //Mettre en gras
        cell.setCellFormula(sommesDons.toString());

        // Dons déjà payés
        colNb = 0;
        StringBuilder donsPayes = new StringBuilder();
        donsPayes.append(SOMME);
        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb++);
        cell.setCellValue("Dons payés");
        counter = 0;
        for (Competition competition : competitions){
            cell = row.createCell(colNb++);
            cell.setCellValue(Utils.sommePayee(competition));
            donsPayes.append(cell.getReference());
            counter++;
            if (counter != competitions.size()){
                donsPayes.append(VIRGULE);
            }
        }
        cell = row.createCell(colNb);
        donsPayes.append(CLOSE);
        cell.setCellFormula(donsPayes.toString());

        // Dons restants à payer
        colNb = 0;
        StringBuilder donsRestants = new StringBuilder();
        donsRestants.append(SOMME);
        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb++);
        cell.setCellValue("Dons restants");
        counter = 0;
        for (Competition competition : competitions){
            cell = row.createCell(colNb++);
            cell.setCellValue(Utils.sommeRestante(competition));
            donsRestants.append(cell.getReference());
            counter++;
            if (counter != competitions.size()){
                donsRestants.append(VIRGULE);
            }
        }
        cell = row.createCell(colNb);
        donsRestants.append(CLOSE);
        cell.setCellFormula(donsRestants.toString());

        // Nombre d'annulations
        colNb = 0;
        StringBuilder nbAnnulations = new StringBuilder();
        nbAnnulations.append(SOMME);
        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb++);
        cell.setCellValue("Nb d'annulation");
        counter = 0;
        for (Competition competition : competitions){
            cell = row.createCell(colNb++);
            cell.setCellValue(Utils.nbAnnulations(competition));
            nbAnnulations.append(cell.getReference());
            counter++;
            if (counter != competitions.size()){
                nbAnnulations.append(VIRGULE);
            }
        }
        cell = row.createCell(colNb);
        nbAnnulations.append(CLOSE);
        cell.setCellFormula(nbAnnulations.toString());

        rowNb++;

        // Infos inscriptions
        colNb = 0;
        StringBuilder infosInscriptions = new StringBuilder();
        infosInscriptions.append(SOMME);
        row = sheet.createRow(++rowNb);
        cell = row.createCell(colNb++);
        cell.setCellStyle(boldStyle); //Mettre en gras
        cell.setCellValue("Inscriptions");
        for (Competition competition : competitions){
            StringBuilder somme = new StringBuilder(SOMME);
            cell = row.createCell(colNb++);
            cell.setCellStyle(boldStyle); //Mettre en gras
            somme.append(cell.getReference().charAt(0)).append(rowNb+2)
                    .append(VIRGULE)
                    .append(cell.getReference().charAt(0)).append(rowNb+3)
                    .append(VIRGULE)
                    .append(cell.getReference().charAt(0)).append(rowNb+4)
                    .append(VIRGULE)
                    .append(cell.getReference().charAt(0)).append(rowNb+5)
                    .append(CLOSE);
            cell.setCellFormula(somme.toString());
            infosInscriptions.append(cell.getReference());
            counter++;
            if (counter != competitions.size()){
                infosInscriptions.append(VIRGULE);
            }
        }
        cell = row.createCell(colNb);
        infosInscriptions.append(CLOSE);
        cell.setCellStyle(boldStyle); //Mettre en gras
        cell.setCellFormula(infosInscriptions.toString());

        // Nombre d'inscriptions par categorie
        List<String> categories = new ArrayList<>();
        categories.add("Junior");
        categories.add("Elite");
        categories.add("Vétéran 1");
        categories.add("Vétéran 2");

        for (String category : categories) {
            colNb = 0;
            StringBuilder cat = new StringBuilder();
            cat.append(SOMME);
            row = sheet.createRow(++rowNb);
            cell = row.createCell(colNb++);
            cell.setCellValue(category);
            counter = 0;
            for (Competition competition : competitions){
                cell = row.createCell(colNb++);
                Utils.getCategorie(competition);
                cell.setCellValue(Utils.nbInscriptionByCategorie(competition, category));
                cat.append(cell.getReference());
                counter++;
                if (counter != competitions.size()){
                    cat.append(VIRGULE);
                }
            }
            cell = row.createCell(colNb);
            cat.append(CLOSE);
            cell.setCellFormula(cat.toString());
        }

        try (OutputStream fileOut = new FileOutputStream("allCompetitions.xlsx")) {
            wb.write(fileOut);
            logger.info("\u001B[37m" + "Création du fichier Excel <allCompetition.xlsx> avec toutes les compétitions " + "\u001B[0m");
        } catch (FileNotFoundException e) {
            logger.warning("\u001B[33m" + "Un fichier existe déjà sous ce nom..." + "\u001B[0m");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
