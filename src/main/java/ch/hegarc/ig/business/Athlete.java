package ch.hegarc.ig.business;

import ch.hegarc.ig.formatter.LocalDateDeserializer;
import ch.hegarc.ig.formatter.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.util.Objects;

public class Athlete implements Comparable<Athlete> {
    private int id;
    private char genre;
    private String prenom;
    private String nom;
    private int annee;
    private String email;
    private String pays;
    private String categorie;
    private int prixInscription;
    private boolean paye;
    private boolean annule;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateInscription;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateVersement;

    public Athlete(){

    }

    public Athlete(int id, char genre, String prenom, String nom, int annee, String email, String pays, int prixInscription, boolean paye, boolean annule, LocalDate dateInscription, LocalDate dateVersement) {
        this.id = id;
        this.genre = genre;
        this.prenom = prenom;
        this.nom = nom;
        this.annee = annee;
        this.email = email;
        this.pays = pays;
        this.prixInscription = prixInscription;
        this.paye = paye;
        this.annule = annule;
        this.dateInscription = dateInscription;
        this.dateVersement = dateVersement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getGenre() {
        return genre;
    }

    public void setGenre(char genre) {
        this.genre = genre;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
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

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie){
        this.categorie = categorie;
    }

    public int getPrixInscription() {
        return prixInscription;
    }

    public void setPrixInscription(int prixInscription) {
        this.prixInscription = prixInscription;
    }

    public boolean isPaye() {
        return paye;
    }

    public void setPaye(boolean paye) {
        this.paye = paye;
    }

    public boolean isAnnule() {
        return annule;
    }

    public void setAnnule(boolean annule) {
        this.annule = annule;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public LocalDate getDateVersement() {
        return dateVersement;
    }

    public void setDateVersement(LocalDate dateVersement) {
        this.dateVersement = dateVersement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Athlete)) return false;
        Athlete athlete = (Athlete) o;
        return annee == athlete.annee && prenom.equals(athlete.prenom) && nom.equals(athlete.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prenom, nom, annee);
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "id=" + id +
                ", genre=" + genre +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", annee=" + annee +
                ", email='" + email + '\'' +
                ", pays='" + pays + '\'' +
                ", prixInscription=" + prixInscription +
                ", paye=" + paye +
                ", annule=" + annule +
                ", dateInscription=" + dateInscription +
                ", dateVersement=" + dateVersement +
                '}'+ '\n';
    }

    @Override
    public int compareTo(Athlete athlete) {
        int parametrePrenom, parametreNom;
        parametrePrenom = this.prenom.compareTo(athlete.prenom);
        parametreNom = this.nom.compareTo(athlete.nom);
        if ((parametrePrenom) == 0){
            return parametreNom;
        }
        return parametrePrenom;
    }
}
