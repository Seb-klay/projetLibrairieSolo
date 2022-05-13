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
import java.util.ArrayList;
import java.util.List;

public class ExcelHandler {
    public static void generationStatsExcel(List<Competition> competitions) {

        final String SOMME = "SUM(";
        final char CLOSE = ')';

        List<String> pays;
        // Nouveau document, nouvel onglet
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Stats compétitions");

        int rowNb = 0;
        int colNb = 0;
        XSSFRow row = sheet.createRow(rowNb);
        XSSFCell cell;

        pays = CompetitionHandler.getCompetitionPlaceInList(competitions);

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

        AthleteHandler.defineCategorieAthlete(competitions);
        List<Integer> sommeDonsInscr = constructExcelData(competitions);

        colNb = 1;
        int sommeDonsInscrCpt = 0;
        int oneCompet = competitions.size() > 1 ? 0 : 1;
        int sizeListPays = pays.size();

        for (int i = 0; i <= sizeListPays - oneCompet; i++) {
            for (int rowCpt = 10; rowCpt > 0; rowCpt--) {
                if (rowCpt == 5) {
                    rowCpt--;
                }
                row = sheet.getRow(rowCpt);
                cell = row.createCell(colNb);
                if (rowCpt == 1 || rowCpt == 6) {
                    StringBuilder ref = new StringBuilder(cell.getReference());
                    char columnName = ref.charAt(0);
                    row.getCell(colNb).setCellStyle(style);
                    if (rowCpt == 1) {
                        cell.setCellFormula(SOMME + columnName + "5:" + columnName + "3" + CLOSE);
                    } else{
                        cell.setCellFormula(SOMME + columnName + "11:" + columnName + "8" + CLOSE);
                    }
                } else if (i < sizeListPays) {
                    cell.setCellValue(sommeDonsInscr.get(sommeDonsInscrCpt));
                    sommeDonsInscrCpt++;
                } else{
                    StringBuilder ref = new StringBuilder(row.getCell(colNb - 1).getReference());
                    char columnName = ref.charAt(0);
                    cell.setCellFormula(SOMME + "B"+ (rowCpt + 1) + ":" + columnName + (rowCpt + 1) + CLOSE);
                }
            }
            colNb += 1;
        }

        try (OutputStream fileOut = new FileOutputStream("stats.xlsx")) {
            wb.write(fileOut);
            System.out.println("Excel créé avec succès !");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> constructExcelData(List<Competition> competitions) {
        List<Integer> sommeDonsInscr = new ArrayList<>();
        int cpt = 0;

        for (Competition c : competitions) {

            for (int i = 0; i < 7; i++) {
                sommeDonsInscr.add(0);
            }

            for (Athlete a : c.getAthletes()) {
                if (!a.isAnnul()) {
                    if (a.isPay()) {
                        sommeDonsInscr.set(7 * cpt + 6, Math.toIntExact(sommeDonsInscr.get(7 * cpt + 6) + a.getSomme()));
                    } else {
                        sommeDonsInscr.set(7 * cpt + 5, Math.toIntExact(sommeDonsInscr.get(7 * cpt + 5) + a.getSomme()));
                    }
                    switch (a.getCategorie()) {
                        case "Junior":
                            sommeDonsInscr.set(7 * cpt + 3, sommeDonsInscr.get(7 * cpt + 3) + 1);
                            break;
                        case "Elite":
                            sommeDonsInscr.set(7 * cpt + 2, sommeDonsInscr.get(7 * cpt + 2) + 1);
                            break;
                        case "Vétéran 1":
                            sommeDonsInscr.set(7 * cpt + 1, sommeDonsInscr.get(7 * cpt + 1) + 1);
                            break;
                        case "Vétéran 2":
                            sommeDonsInscr.set(7 * cpt, sommeDonsInscr.get(7 * cpt) + 1);
                            break;
                    }
                } else {
                    sommeDonsInscr.set(7 * cpt + 4, sommeDonsInscr.get(7 * cpt + 4) + 1);
                }
            }
            cpt++;
        }
/*
        if (competitions.size() > 1) {
            int total;

            for (int i = 0; i < 7; i++) {
                total = 0;
                sommeDonsInscr.add(0);
                for (int j = 0; j < cpt; j++) {
                    total += sommeDonsInscr.get(7 * j + i);
                }
                sommeDonsInscr.set(7 * cpt + i, total);
            }
        }*/
        return sommeDonsInscr;
    }
}
