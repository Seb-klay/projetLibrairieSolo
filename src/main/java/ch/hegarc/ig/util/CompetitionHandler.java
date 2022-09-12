package ch.hegarc.ig.util;

import ch.hegarc.ig.business.Competition;

import java.util.*;

public class CompetitionHandler {
    public static Set<Competition> fusionLists(Set<Competition> firstList, List<Competition> secondList){
        firstList.addAll(secondList);
        return firstList;
    }
    public static Set<Competition> sortList(List<Competition> competitionList){
        Set<Competition> competitionsSorted = new HashSet<>();
        for (Competition competition : competitionList){
            Collections.sort(competition.getAthletes());
        }
        competitionsSorted.addAll(competitionList);
        Collections.sort(competitionList);
        return competitionsSorted;
    }
}
