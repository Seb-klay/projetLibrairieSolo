package ch.hegarc.ig.PDF;

import ch.hegarc.ig.business.Athlete;
import ch.hegarc.ig.business.Competition;
import ch.hegarc.ig.util.Utils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainPDF {
    private static final Logger logger = Logger.getLogger(MainPDF.class.getName());
    public static int margin = 70 ;
    public static int marginHeight = 40;
    public static float positionY;
    public static float positionX;
    public static int fontSize = 14;
    public static int pageCounter = 0;
    public static int heightPiedPage = 0;

    public static void writePdfAllCompetitions(List<Competition> competitions){
        for (Competition competition : competitions){
            writePdfOneCompetition(competition);
        }
    }
    public static void writePdfOneCompetition(Competition competition){
        String outputFileName = competition.getLibelle() + ".pdf";
        try {
            // Création d'un document
            PDDocument doc = new PDDocument();
            List<PDPage> pages = new ArrayList<>();

            pages.add(new PDPage(PDRectangle.A4));
            PDRectangle rec = pages.get(pageCounter).getMediaBox();
            positionY = rec.getHeight() - 70;
            positionX = rec.getWidth();
            doc.addPage(pages.get(pageCounter));

            // Création d'une police bold
            PDFont fontPlain = PDType1Font.HELVETICA;
            PDFont fontBold = PDType1Font.HELVETICA_BOLD;

            // Démarrer un nouveau flux avec le contenu qui va être créé
            PDPageContentStream cos = new PDPageContentStream(doc, pages.get(pageCounter));

            // Définir un text de flux, bouger le curseur et écrire du texte
            cos.beginText();
            cos.setFont(fontBold, fontSize);

            // Titre plus ou moins centré
            cos.newLineAtOffset(positionX / 2-20, positionY);
            cos.showText("Bilan - " + competition.getLibelle());
            cos.endText();

            cos.setLeading(20f);
            cos.setFont(fontPlain, fontSize);
            cos.beginText();
            cos.setFont(fontBold, fontSize);
            positionY = positionY - marginHeight;
            cos.newLineAtOffset(margin, positionY);

            // 5 plus gros donateurs
            cos.showText("5 plus gros donateurs de la compétition : ");
            cos.newLine();
            cos.setFont(fontPlain, fontSize);
            for (Athlete athlete : Utils.find5BiggestDonators(competition)){
                cos.showText(athlete.getNom() + " " + athlete.getPrenom() + " : " + athlete.getPrixInscription());
                cos.newLine();
                positionY -= fontSize + 10;
            }
            cos.endText();

            // Athlètes qui n'ont pas payés
            cos.beginText();
            cos.setFont(fontBold, fontSize);
            positionY = positionY - marginHeight;
            cos.newLineAtOffset(margin, positionY);
            cos.showText("Athlètes n'ayant pas encore payés : ");
            cos.newLine();
            cos.setFont(fontPlain, fontSize);
            for (Athlete athlete : Utils.hasNotPaid(competition)){
                if (positionY < heightPiedPage){
                    cos = createNewPage(doc, pages, rec, fontPlain, cos);
                }
                cos.showText(athlete.getNom() + " " + athlete.getPrenom() + " : " + athlete.getPrixInscription());
                cos.newLine();
                positionY -= fontSize + 10;
            }
            cos.endText();

            // Somme totale des dons
            cos.beginText();
            cos.newLineAtOffset(margin, positionY);
            cos.setFont(fontBold, fontSize);
            positionY = positionY - marginHeight;
            if (positionY < heightPiedPage){
                cos = createNewPage(doc, pages, rec, fontPlain, cos);
            }
            cos.showText("Somme totale des dons de la compétition : " + Utils.sums(competition));
            cos.newLine();
            positionY -= fontSize + 10;
            cos.endText();

            // Nb total d'inscrits
            cos.beginText();
            cos.newLineAtOffset(margin, positionY);
            cos.setFont(fontBold, fontSize);
            positionY = positionY - marginHeight;
            if (positionY < heightPiedPage){
                cos = createNewPage(doc, pages, rec, fontPlain, cos);
            }
            cos.showText("Nombre total d'inscrits : " + Utils.nbInscriptions(competition));
            cos.newLine();
            positionY -= fontSize + 10;
            cos.endText();

            // Pays représentés
            cos.beginText();
            cos.newLineAtOffset(margin, positionY);
            positionY = positionY - marginHeight;
            if (positionY < heightPiedPage){
                cos = createNewPage(doc, pages, rec, fontPlain, cos);
            }
            cos.setFont(fontBold, fontSize);
            cos.showText("Pays représentés : ");
            int indPays = 0;
            for (String pays : Utils.getPays(competition)){
                if ((indPays % 6) == 0){
                    cos.newLine();
                    positionY -= fontSize + 10;
                    if (positionY < heightPiedPage){
                        cos = createNewPage(doc, pages, rec, fontPlain, cos);
                    }
                }
                cos.setFont(fontPlain, fontSize);
                cos.showText(pays + ", ");
                indPays++;
            }

            cos = createNewPage(doc, pages, rec, fontPlain, cos);
            cos.endText();

            cos.beginText();
            cos.setFont(fontBold, fontSize);
            positionY = positionY - marginHeight;
            cos.newLineAtOffset(margin, positionY);
            cos.showText("Emails des participants : ");
            cos.newLine();
            cos.setFont(fontPlain, fontSize);
            int indMails = 0;
            for (String mail : Utils.getMails(competition)){
                if ((indMails % 2) == 0){
                    cos.newLine();
                    positionY -= fontSize + 10;
                    if (positionY < heightPiedPage){
                        cos = createNewPage(doc, pages, rec, fontPlain, cos);
                    }
                }
                cos.setFont(fontPlain, fontSize);
                cos.showText(mail + "; ");
                indMails++;
            }
            cos.endText();
            cos.close();

            doc.save(outputFileName);
            pageCounter = 0;
            doc.close();

            logger.info("\u001B[32m" + "Création du fichier PDF <" + outputFileName + ">" + "\u001B[0m");

        } catch (IOException e){
            logger.warning("\u001B[33m" + "Un problème est survenu lors de la création du fichier PDF " + outputFileName + "..." + "\u001B[0m");
        }
    }

    private static PDPageContentStream createNewPage(PDDocument doc, List<PDPage> pages, PDRectangle rec, PDFont fontPlain, PDPageContentStream cos) throws IOException {
        // Fermer le flux en cours
        cos.endText();
        cos.close();
        // Créer une nouvelle page et l'ajouter au document
        pageCounter++;
        pages.add(new PDPage(PDRectangle.A4));
        doc.addPage(pages.get(pageCounter));
        //Réinitialiser la hauteur du doc
        positionY = rec.getHeight() - 70;
        // Créer un nouveau flux
        cos = new PDPageContentStream(doc, pages.get(pageCounter));
        cos.setLeading(20f);
        cos.beginText();
        cos.setFont(fontPlain, fontSize);
        cos.newLineAtOffset(margin, positionY);

        return cos;
    }
}
