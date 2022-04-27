package ch.hegarc.ig.business;

import ch.hegarc.ig.json.CustomLocalDateDeserializer;
import ch.hegarc.ig.json.CustomLocalDateSerializer;
import ch.hegarc.ig.json.CustomLocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Competition implements Comparable<Competition>{
    private long id;
    @JsonProperty("competition")
    private String name;
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate date;
    private List<Athlete> athletes = new ArrayList<>();

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
        strBuilder.append("id= ");
        strBuilder.append(id);
        strBuilder.append("\n");
        strBuilder.append("name= ");
        strBuilder.append(name);
        strBuilder.append("\n");
        strBuilder.append("date= ");
        strBuilder.append(date);
        strBuilder.append("\n");
        strBuilder.append("athletes=' { ");
        strBuilder.append(athletes);
        strBuilder.append("}\n");
        return strBuilder.toString();
    }

    @Override
    public int compareTo(Competition o) {
        int compare = this.name.compareTo(o.getName());

        if(compare == 0){
            Collections.sort(athletes); //Ne fonctionne pas
            return this.name.compareTo(o.getName());
        }
        return compare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competition that = (Competition) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}