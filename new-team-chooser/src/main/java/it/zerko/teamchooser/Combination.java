package it.zerko.teamchooser;

import lombok.Getter;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
public class Combination implements Comparable<Combination> {

  private Team firstTeam;
  private Team secondTeam;
  private double difference;

  public Combination(Collection<Player> players, ICombinatoricsVector<Player> combination) {
    Set<Player> firstPlayers = new HashSet<>(players);
    firstPlayers.retainAll(combination.getVector());
    firstTeam = new Team(firstPlayers);

    Set<Player> secondPlayers = new HashSet<>(players);
    secondPlayers.removeAll(combination.getVector());
    secondTeam = new Team(secondPlayers);

    difference = Math.abs(firstTeam.getRating() - secondTeam.getRating());
  }

  @Override
  public int compareTo(Combination o) {
    return Double.compare(difference, o.difference);
  }

  @Override
  public String toString() {
    return String.format("%s\n%s\nDiff: %s", firstTeam.toString(), secondTeam.toString(), difference);
  }
}
