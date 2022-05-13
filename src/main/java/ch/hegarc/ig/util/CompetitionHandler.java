package ch.hegarc.ig.util;

import ch.hegarc.ig.business.Competition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class CompetitionHandler {
    public static Integer getIndexOfListOfCompetitionByAttributeProjectName(List<Competition> competitions, String projectName) {
        return IntStream.range(0, competitions.size())
                .filter(competition -> projectName.equals(competitions.get(competition).getName()))
                .findFirst()
                .orElse(-1);
    }

    public static HashSet<Competition> fusionListsCompetitions(List<Competition> competition1, List<Competition> competition2) {
        HashSet<Competition> competitions = new HashSet<>(competition1);
        competitions.addAll(competition2);
        System.out.println("Listes fusionnées");
        return competitions;
    }

    public static List<Competition> sortList(List<Competition> competitions) {
        List<Competition> compets = competitions;

        for (Competition competition : compets) {
            Collections.sort(competition.getAthletes());
        }
        Collections.sort(compets);
        System.out.println("Liste triée");
        return compets;
    }

    public static List<String> getCompetitionPlaceInList(List<Competition> competitions) {
        List<String> pays = new ArrayList<>();
        for (Competition c : competitions) {
            pays.add(c.getName());
        }
        return pays;
    }
}
