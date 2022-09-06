package ch.hegarc.ig.Business;

import java.time.LocalDateTime;
import java.util.Objects;

public class Athlete {
    private int id;
    private char genre;
    private String prenom;
    private String nom;
    private int annee;
    private String email;
    private String pays;
    private int prixInscription;
    private boolean paye;
    private boolean annule;
    private LocalDateTime dateInscription;
    private LocalDateTime dateVersement;

    public Athlete(){

    }

    public Athlete(int id, char genre, String prenom, String nom, int annee, String email, String pays, int prixInscription, boolean paye, boolean annule, LocalDateTime dateInscription, LocalDateTime dateVersement) {
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

    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    public LocalDateTime getDateVersement() {
        return dateVersement;
    }

    public void setDateVersement(LocalDateTime dateVersement) {
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
                '}';
    }
}
