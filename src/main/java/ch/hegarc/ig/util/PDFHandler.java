package ch.hegarc.ig.util;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFHandler {
    public static void generatePDF(List<Competition> competitions) {
        StringBuilder sbFileName = new StringBuilder();
        int nbCompetitions = competitions.size();
        String competitionsName = "";
        if (competitions.size() > 1) {
            sbFileName.append("competitions").append(".pdf");
        } else
            sbFileName.append(competitions.get(0).getName()).append(".pdf");
        try {

            // Créer un nouveau document et ajoute la page au document
            PDDocument document = new PDDocument();
            List<PDPage> pages = new ArrayList();
            PDRectangle rect = null;

            // Créer un un objet Font et en sélectionner un font de base
            PDFont fontPlain = PDType1Font.HELVETICA;
            PDFont fontBold = PDType1Font.HELVETICA_BOLD;

            for (int i = 0; i < nbCompetitions * 2; i++) {
                pages.add(new PDPage(PDRectangle.A4));

                if (i == 0)
                    rect = pages.get(i).getMediaBox();

                document.addPage(pages.get(i));

                //Débute un nouveau flux qui contient tout le contenu de la page qui va être créé
                PDPageContentStream cos = new PDPageContentStream(document, pages.get(i));


                if (i % 2 == 0) {
                    // Définit un contenu de texte à partir de la police de caractère, bouge le curseur et définit le texte
                    cos.beginText();
                    cos.setFont(fontBold, 14);
                    // Titre plus ou moins centré
                    cos.newLineAtOffset(rect.getWidth() / 2 - 20, rect.getHeight() - 100);
                    cos.showText("Bilan - " + competitions.get((int) Math.floor(i / 2)).getName());
                    cos.endText();
                    competitionsName = competitions.get((int) Math.floor(i / 2)).getName();

                    cos.setLeading(20f);
                    cos.setFont(fontPlain, 14);
                    cos.beginText();
                    cos.newLineAtOffset(100, 600);
                    StringBuilder sb = new StringBuilder();

                    sb.append("5 plus grosses donateurs : ");
                    cos.showText(sb.toString());
                    cos.newLine();


                    List<Athlete> top5Donators = AthleteHandler.biggestDonator(competitions, competitionsName);

                    for (Athlete athlete : top5Donators) {
                        sb.setLength(0);
                        sb.append(athlete.getNom()).append(" ").append(athlete.getPrNom()).append(" : ").append(athlete.getSomme()).append(".-");
                        cos.showText(sb.toString());
                        cos.newLine();
                    }

                    sb.setLength(0);
                    sb.append("Athlètes n'ayant pas payés : ");
                    cos.showText(sb.toString());
                    cos.newLine();

                    List<Athlete> notPaidAthletes = AthleteHandler.showPayAndInsFalse(competitions, competitionsName);

                    for (Athlete athlete : notPaidAthletes) {
                        sb.setLength(0);
                        sb.append(athlete.getNom()).append(" ").append(athlete.getPrNom()).append(" : ").append(athlete.getSomme()).append(".-");
                        cos.showText(sb.toString());

                    }

                    cos.newLine();

                    sb.setLength(0);
                    sb.append("Somme totale des dons : ").append(AthleteHandler.showSum(competitions, competitionsName));
                    cos.showText(sb.toString());
                    cos.newLine();

                    sb.setLength(0);
                    sb.append("Athlètes qui sont inscrits : ").append(AthleteHandler.nbInscr(competitions, competitionsName));
                    cos.showText(sb.toString());
                    cos.newLine();

                    sb.setLength(0);
                    sb.append("Pays participants : ").append(AthleteHandler.showPays(competitions, competitionsName));
                    cos.showText(sb.toString());
                    cos.newLine();

                }else {

                    cos.setLeading(20f);
                    cos.setFont(fontPlain, 14);
                    cos.beginText();
                    cos.newLineAtOffset(100, 600);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Mails des athletes inscrits : ");
                    sb.append(AthleteHandler.showMail(competitions, competitionsName));
                    
                    cos.showText(sb.toString());
                }

                cos.endText();
                cos.close();
            }

            document.save(sbFileName.toString());
            document.close();

            System.out.println("PDF created");


        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}