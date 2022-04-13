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

@JsonIgnoreProperties(ignoreUnknown = true)
public class Athlete {

    private long id;
    @JsonProperty("prenom")
    private String prNom;
    private String nom;
    private String email;
    private String ville;
    private int annee;
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

    public Athlete(long id, String prNom, String nom, String email, String langue, String adresse, String ville, String monnaie, long somme, boolean pay, boolean annul,  int annee, LocalDate dateInscription, LocalDate dateVersement) {
        super();
        this.id = id;
        this.prNom = prNom;
        this.nom = nom;
        this.email = email;
        this.ville = ville;
        this.somme = somme;
        this.annee = annee;
        this.pay = pay;
        this.annul = annul;
        this.dateInscription = dateInscription;
        this.dateVersement = dateVersement;
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

    public Athlete(long id, String prNom, String nom, String email, String langue, String adresse, String ville, String monnaie, long somme, boolean pay, boolean annul, String dateDon, String dateVersement, int annee) {
        super();
        this.id = id;
        this.prNom = prNom;
        this.nom = nom;
        this.email = email;
        this.ville = ville;
        this.somme = somme;
        this.annee = annee;
    }

    public Athlete(long id, String prenom, String nom, int annee, String email, String ville, Long prixInscription) {
        this.id = id;
        this.prNom = prenom;
        this.nom = nom;
        this.email = email;
        this.ville = ville;
        this.somme = prixInscription;
        this.annee = annee;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Athlete : \n");
        strBuilder.append("id= " + id + "\n");
        strBuilder.append("prénom= " + prNom + "\n");
        strBuilder.append("nom= " + nom + "\n");
        strBuilder.append("email= " + email + "\n");
        strBuilder.append("ville= " + ville + "\n");
        strBuilder.append("annee= " + annee + "\n");
        strBuilder.append("somme= " + somme + "\n");
        strBuilder.append("pay= " + pay + "\n");
        strBuilder.append("annul= " + annul + "\n");
        if (dateInscription != null)
            strBuilder.append("dateInscription= " + dateInscription + "\n");
        if (dateVersement != null)
            strBuilder.append("dateVersement= " + dateVersement + "\n");
        return strBuilder.toString();
    }
}
