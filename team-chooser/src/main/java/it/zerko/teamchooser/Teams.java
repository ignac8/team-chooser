package it.zerko.teamchooser;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.Double.compare;
import static java.lang.Math.abs;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

@Getter
public class Teams implements Comparable<Teams> {

    private List<Player> firstTeam = new ArrayList<>();
    private List<Player> secondTeam = new ArrayList<>();

    public static List<Teams> generateTeams(List<Player> players) {
        List<Teams> teamsList = new ArrayList<>();
        teamsList.add(new Teams());
        for (Player player : players) {
            teamsList = teamsList.stream()
                    .map(teams -> teams.addPlayerToTeams(player))
                    .flatMap(Collection::stream)
                    .collect(toList());
        }
        return teamsList;
    }

    public static double getTeamStrength(List<Player> team) {
        return team.stream()
                .mapToDouble(Player::getRating)
                .sum();
    }

    public static String getTeamPlayerNames(List<Player> team) {
        return team.stream()
                .map(Player::getName)
                .collect(joining(", "));
    }

    public double getStrengthDifference() {
        return abs(getTeamStrength(firstTeam) - getTeamStrength(secondTeam));
    }

    public boolean isFairTeam() {
        return firstTeam.size() == secondTeam.size();
    }

    public Teams copy() {
        Teams teams = new Teams();
        teams.firstTeam = new ArrayList<>(firstTeam);
        teams.secondTeam = new ArrayList<>(secondTeam);
        return teams;
    }

    public List<Teams> addPlayerToTeams(Player player) {
        List<Teams> teamsList = range(0, 2).mapToObj(i -> copy()).collect(toList());
        teamsList.get(0).getFirstTeam().add(player);
        teamsList.get(1).getSecondTeam().add(player);
        return teamsList;
    }

    @Override
    public int compareTo(Teams o) {
        return compare(getStrengthDifference(), o.getStrengthDifference());
    }
}
