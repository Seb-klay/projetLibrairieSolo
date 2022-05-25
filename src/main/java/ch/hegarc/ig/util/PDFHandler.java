package ch.hegarc.ig.util;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFHandler extends PDFTextStripper{
    public PDFHandler() throws IOException {
    }

    public static void generatePDF(List<Competition> competitions)  {
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
            int nbPages = nbCompetitions * 2;
            int iCompetition = 0;
            boolean isPageMail = false;

            for (int i = 0; i < nbPages; i++) {
                pages.add(new PDPage(PDRectangle.A4));
                if (i == 0)
                    rect = pages.get(i).getMediaBox();
                document.addPage(pages.get(i));
                //Débute un nouveau flux qui contient tout le contenu de la page qui va être créé
                PDPageContentStream cos = new PDPageContentStream(document, pages.get(i));
                int limitPage = 30;
                int cptLignePage = 16;
                int comptageHeightPage = 0;

                if (!isPageMail) {
                    // Définit un contenu de texte à partir de la police de caractère, bouge le curseur et définit le texte
                    cos.beginText();
                    cos.setFont(fontBold, 14);
                    // Titre plus ou moins centré
                    cos.newLineAtOffset(rect.getWidth() / 2 - 20, rect.getHeight() - 100);
                    cos.showText("Bilan - " + competitions.get(iCompetition).getName());
                    cos.endText();
                    competitionsName = competitions.get(iCompetition).getName();
                    PDRectangle mediabox = pages.get(i).getMediaBox();

                    cos.setLeading(20f);
                    cos.setFont(fontPlain, 14);
                    cos.beginText();
                    cos.newLineAtOffset(100, 700);
                    StringBuilder sb = new StringBuilder();

                    sb.append("5 plus gros donateurs : ");
                    cos.showText(sb.toString());
                    cos.newLine();


                    List<Athlete> top5Donators = AthleteHandler.biggestDonator(competitions, competitionsName, false);

                    for (Athlete athlete : top5Donators) {
                        sb.setLength(0);
                        sb.append(athlete.getNom()).append(" ").append(athlete.getPrNom()).append(" : ").append(athlete.getSomme()).append(".-");
                        cos.showText(sb.toString());
                        cos.newLine();
                    }

                    sb.setLength(0);
                    cos.newLine();
                    sb.append("Athlètes n'ayant pas payés : ");
                    cos.showText(sb.toString());
                    cos.newLine();

                    List<Athlete> notPaidAthletes = AthleteHandler.showPayAndInsFalse(competitions, competitionsName, false);


                    int iAthlete = 0;
                    for (Athlete athlete : notPaidAthletes) {
                        if (comptageHeightPage == limitPage) {
                            cos.endText();
                            cos.close();
                            pages.add(new PDPage(PDRectangle.A4));
                            i++;
                            nbPages++;
                            document.addPage(pages.get(i));
                            //Débute un nouveau flux qui contient tout le contenu de la page qui va être créé
                            cos = new PDPageContentStream(document, pages.get(i));
                            cos.setLeading(20f);
                            cos.setFont(fontPlain, 14);
                            cos.beginText();
                            cos.newLineAtOffset(100, 700);
                            limitPage = 35;
                            cptLignePage = 1;
                            comptageHeightPage= 0;
                        }
                        iAthlete++;
                        sb.setLength(0);
                        sb.append(athlete.getNom()).append(" ").append(athlete.getPrNom()).append(" : ").append(athlete.getSomme()).append(".-");
                        cos.showText(sb.toString());
                        if (iAthlete % 2 == 0){
                            cptLignePage++;
                            cos.newLine();
                            comptageHeightPage++;
                        }
                    }

                    cos.newLine();

                    sb.setLength(0);
                    cos.newLine();
                    sb.append("Somme totale des dons : ").append(AthleteHandler.showSum(competitions, competitionsName, false));
                    cos.showText(sb.toString());
                    cos.newLine();

                    sb.setLength(0);
                    cos.newLine();
                    sb.append("Athlètes qui sont inscrits : ").append(AthleteHandler.nbInscr(competitions, competitionsName));
                    cos.showText(sb.toString());
                    cos.newLine();

                    sb.setLength(0);
                    cos.newLine();
                    sb.append("Pays participants : ");
                    cos.showText(sb.toString());
                    int iPays = 0;
                    cos.newLine();
                    limitPage = 35 - cptLignePage ;
                    cptLignePage = 0;
                    comptageHeightPage = 0;

                    for (String pays : AthleteHandler.showPays(competitions, competitionsName, false)) {
                        if (comptageHeightPage == limitPage) {
                            cos.endText();
                            cos.close();
                            pages.add(new PDPage(PDRectangle.A4));
                            i++;
                            nbPages++;
                            document.addPage(pages.get(i));
                            //Débute un nouveau flux qui contient tout le contenu de la page qui va être créé
                            cos = new PDPageContentStream(document, pages.get(i));
                            cos.setLeading(20f);
                            cos.setFont(fontPlain, 14);
                            cos.beginText();
                            cos.newLineAtOffset(100, 700);
                            limitPage = 35;
                            cptLignePage = 1;
                            comptageHeightPage= 0;
                        }
                        iPays++;
                        sb.setLength(0);
                        sb.append(pays).append(" ");
                        cos.showText(sb.toString());
                        if (iPays % 5 == 0){
                            cptLignePage++;
                            cos.newLine();
                            comptageHeightPage++;
                        }
                    }

                    cos.newLine();
                    isPageMail = true;

                } else {
                    competitionsName = competitions.get(iCompetition).getName();
                    cos.setLeading(20f);
                    cos.setFont(fontPlain, 14);
                    cos.beginText();
                    //cos.newLineAtOffset(100, 600);
                    cos.newLineAtOffset(100, 800);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Mails des athletes inscrits : ");
                    cos.showText(sb.toString());
                    int iMail = 0;
                    cos.newLine();
                    limitPage = 35;
                    comptageHeightPage = 0;
                    boolean premierPassage = false;
                    for (String mail : AthleteHandler.showMail(competitions, competitionsName, false)) {
                        iMail++;
                        if (comptageHeightPage == limitPage) {
                            cos.endText();
                            cos.close();
                            pages.add(new PDPage(PDRectangle.A4));
                            i++;
                            nbPages++;
                            document.addPage(pages.get(i));
                            //Débute un nouveau flux qui contient tout le contenu de la page qui va être créé
                            cos = new PDPageContentStream(document, pages.get(i));
                            cos.setLeading(20f);
                            cos.setFont(fontPlain, 14);
                            cos.beginText();
                            cos.newLineAtOffset(100, 700);
                            limitPage = 35;
                            comptageHeightPage= 0;
                        }
                        sb.setLength(0);
                        if (premierPassage) {
                            sb.append(";");
                        }
                        sb.append(mail);
                        cos.showText(sb.toString());
                        if (iMail % 2 == 0){
                            cos.newLine();
                            comptageHeightPage++;
                        }
                        premierPassage = true;
                    }

                    cos.showText(sb.toString());
                    iCompetition++;
                    isPageMail = false;
                }
                cos.endText();
                cos.close();
            }

            document.save(sbFileName.toString());
            document.close();

            System.out.println("PDF créé");


        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}