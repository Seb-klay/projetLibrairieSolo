package ch.hegarc.ig.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Athlete {

    private long id;
    @JsonProperty("prenom")
    private String prNom;
    private String nom;
    private String email;
    private String ville;
    private int annee;
    @JsonProperty("prixInscription")
    private long somme;

    public Athlete() {
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
        strBuilder.append("nom= " + nom+ "\n");
        strBuilder.append("email= " + email+ "\n");
        strBuilder.append("ville= " + ville+ "\n");
        strBuilder.append("annee= " + annee+ "\n");
        strBuilder.append("somme= " + somme+ "\n");
        return strBuilder.toString();
    }
}
