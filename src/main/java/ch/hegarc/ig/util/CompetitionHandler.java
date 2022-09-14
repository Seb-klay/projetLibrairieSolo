package ch.hegarc.ig.util;

import ch.hegarc.ig.business.Competition;

import java.util.*;

public class CompetitionHandler {
    public static List<Competition> fusionLists(List<Competition> firstList, List<Competition> secondList){
        Set<Competition> compets = new HashSet<>(firstList);
        compets.addAll(secondList);
        return new ArrayList<>(compets);
    }
    public static List<Competition> sortList(List<Competition> competitionList){
        for (Competition competition : competitionList){
            Collections.sort(competition.getAthletes());
        }
        Collections.sort(competitionList);
        return competitionList;
    }
}
