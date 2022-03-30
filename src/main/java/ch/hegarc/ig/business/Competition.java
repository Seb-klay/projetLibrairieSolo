package ch.hegarc.ig.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class Competition {

    private long id;
    @JsonProperty("competition")
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate date;
    private List<Athlete> athletes = new ArrayList<Athlete>();

    public Competition() {
    }

    /**
     * @param id
     * @param name
     * @param date
     * @param athletes
     */
    public Competition(long id, String name, LocalDate date, List<Athlete> athletes) {
        super();
        this.id = id;
        this.name = name;
        this.date = date;
        this.athletes = athletes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<Athlete> athletes) {
        this.athletes = athletes;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Competition : \n");
        strBuilder.append("id=" + id + "\n");
        strBuilder.append("name=" + name + "\n");
        strBuilder.append("date=" + date+ "\n");
        strBuilder.append("athletes=' {" + athletes+ "}\n");
        return strBuilder.toString();
    }
}