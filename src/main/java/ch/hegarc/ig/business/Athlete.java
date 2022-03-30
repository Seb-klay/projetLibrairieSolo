package ch.hegarc.ig.business;

public class Athlete {

    private long id;
    private String prNom;
    private String nom;
    private String email;
    private String ville;
    private int annee;
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
}
