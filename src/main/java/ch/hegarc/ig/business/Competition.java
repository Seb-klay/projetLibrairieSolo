package ch.hegarc.ig.business;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Competition implements Comparable<Competition>{
    private int id;
    @JsonProperty("competition")
    private String libelle;
    private List<Athlete> athletes = new ArrayList<>();

    public Competition(){

    }

    public Competition(int id, String libelle, List<Athlete> athletes) {
        this.id = id;
        this.libelle = libelle;
        this.athletes = athletes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<Athlete> athletes) {
        this.athletes = athletes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Competition)) return false;
        Competition that = (Competition) o;
        return libelle.equals(that.libelle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(libelle);
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", athletes=" + athletes +
                '}';
    }

    @Override
    public int compareTo(Competition competition) {
        if (this.libelle.compareTo(competition.libelle) != 0){
            return this.libelle.compareTo(competition.libelle);
        }
        return 0;
    }
}
