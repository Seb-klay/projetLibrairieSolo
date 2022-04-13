package ch.hegarc.ig.business;

import java.util.ArrayList;
import java.util.List;

public class AthleteList {

    private final List<Athlete> athletes = new ArrayList<>();

    public AthleteList() {
    }

    public List<Athlete> getAthletes() {
        return athletes;
    }

    public static AthleteList newPopulatedAthletes(){
        AthleteList athleteList = new AthleteList();
        athleteList.getAthletes().add(new Athlete(1, "Jean", "Delasalle", "Jean@Delasalle.com", "Français", "rue de la Cote 32", "PetiteVille", "CHF", (long) 60.0, true, true, "20.02.2022", "22.02.2022", 2000));
        athleteList.getAthletes().add(new Athlete(2, "Stamm", "Marielle", "Stamm@marielle.com", "Anglais", "rue de la Farge 22", "GrandeVile", "USD", (long) 60.0, false, false, null, null, 1980));
        athleteList.getAthletes().add(new Athlete(3, "Wozniak", "Steve", "Steve@wozniak.com", "Russe", "rue de la berge 11", "MoyenneVille", "CHF", (long) 60.0, true, true, "23.03.2022", "24.03.2022", 1089));

        return athleteList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("-- My athletes' list --\n");

        for (Athlete a: getAthletes()) {
            sb.append(a);
            sb.append("\n");
        }

        return sb.toString();
    }

}