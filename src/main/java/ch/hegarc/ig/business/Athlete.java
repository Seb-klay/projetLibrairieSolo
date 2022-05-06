package ch.hegarc.ig.business;

import ch.hegarc.ig.json.CustomLocalDateDeserializer;
import ch.hegarc.ig.json.CustomLocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Athlete implements Comparable<Athlete> {

    private long id;
    private String genre;
    @JsonProperty("prenom")
    private String prNom;
    private String nom;
    private String email;
    private String pays;
    private String ville;
    private int annee;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String categorie;
    @JsonProperty("paye")
    private boolean pay;
    @JsonProperty("annule")
    private boolean annul;
    @JsonProperty("prixInscription")
    private long somme;
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate dateInscription;
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate dateVersement;

    public Athlete() {
    }

    public Athlete(long id, String genre, String prNom, String nom, String email, String pays, String langue, String adresse, String ville, String monnaie, long somme, boolean pay, boolean annul, int annee, LocalDate dateInscription, LocalDate dateVersement) {
        this.id = id;
        this.genre = genre;
        this.prNom = prNom;
        this.nom = nom;
        this.email = email;
        this.pays = pays;
        this.ville = ville;
        this.somme = somme;
        this.annee = annee;
        this.pay = pay;
        this.annul = annul;
        this.dateInscription = dateInscription;
        this.dateVersement = dateVersement;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPrNom() {
        return prNom;
    }

    public void setPrNom(String prNom) {
        this.prNom = prNom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays){
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public long getSomme() {
        return somme;
    }

    public void setSomme(long somme) {
        this.somme = somme;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public boolean isPay() {
        return pay;
    }

    public void setPay(boolean pay) {
        this.pay = pay;
    }

    public boolean isAnnul() {
        return annul;
    }

    public void setAnnul(boolean annul) {
        this.annul = annul;
    }

    public LocalDate getDateVersement() {
        return dateVersement;
    }

    public void setDateVersement(LocalDate dateVersement) {
        this.dateVersement = dateVersement;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Athlete : \n");
        strBuilder.append("id= ");
        strBuilder.append(id);
        strBuilder.append("\n");
        strBuilder.append("genre= ");
        strBuilder.append(genre);
        strBuilder.append("\n");
        strBuilder.append("prénom= ");
        strBuilder.append(prNom);
        strBuilder.append("\n");
        strBuilder.append("nom= ");
        strBuilder.append(nom);
        strBuilder.append("\n");
        strBuilder.append("email= ");
        strBuilder.append(email);
        strBuilder.append("\n");
        strBuilder.append("pays= ");
        strBuilder.append(pays);
        strBuilder.append("\n");
        strBuilder.append("ville= ");
        strBuilder.append(ville);
        strBuilder.append("\n");
        strBuilder.append("annee= ");
        strBuilder.append(annee);
        strBuilder.append("\n");
        strBuilder.append("categorie= ");
        strBuilder.append(categorie);
        strBuilder.append("\n");
        strBuilder.append("somme= ");
        strBuilder.append(somme);
        strBuilder.append("\n");
        strBuilder.append("pay= ");
        strBuilder.append(pay);
        strBuilder.append("\n");
        strBuilder.append("annul= ");
        strBuilder.append(annul);
        strBuilder.append("\n");
        if (dateInscription != null) {
            strBuilder.append("dateInscription= ");
            strBuilder.append(dateInscription);
            strBuilder.append("\n");
        }
        if (dateVersement != null) {
            strBuilder.append("dateVersement= ");
            strBuilder.append(dateVersement);
            strBuilder.append("\n");
        }
        return strBuilder.toString();
    }

    @Override
    public int compareTo(Athlete o) {
        int compare = this.prNom.compareTo(o.getPrNom());

        if (compare == 0) {
            return this.nom.compareTo(o.getNom());
        }
        return compare;
    }

    @Override
    public boolean equals(Object o) {
        Athlete athlete = (Athlete) o;
        return this.prNom.equals(athlete.getPrNom()) && this.nom.equals(athlete.getNom()) && (this.annee == athlete.getAnnee());
    }

    @Override
    public int hashCode() {
        return Objects.hash(prNom, nom, annee);
    }
}
