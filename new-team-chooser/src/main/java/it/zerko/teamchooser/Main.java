package it.zerko.teamchooser;

import org.paukov.combinatorics.CombinatoricsFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) throws IOException {
    int teamSize = 5;

    List<Player> manualPlayers = Stream.of(new Player[]{
//     new Player("kolega Johnego", 1.0),
    }).collect(Collectors.toList());

    List<Player> automaticPlayers = Files.readAllLines(Paths.get("results.tsv"))
      .stream()
      .map(Player::new)
      .collect(Collectors.toList());

    automaticPlayers.forEach(player -> System.out.println(Double.toString(player.getRating()).replace(".", ",")));

    List<Player> players = Stream.concat(manualPlayers.stream(), automaticPlayers.stream())
      .limit(teamSize * 2)
      .collect(Collectors.toList());

    Combination bestCombination = CombinatoricsFactory.createSimpleCombinationGenerator(
      CombinatoricsFactory.createVector(players), teamSize)
      .generateAllObjects()
      .stream()
      .map(combination -> new Combination(players, combination))
      .filter(combination -> filterTeam(combination.getFirstTeam(), "ignac8", "Koala"))
      .filter(combination -> filterTeam(combination.getSecondTeam()))
      .filter(combination -> combination.getFirstTeam().getPlayers().size() == teamSize)
      .sorted()
      .findFirst()
      .orElseThrow(NullPointerException::new);

    System.out.println(bestCombination);

    boolean debug = true;
  }

  private static boolean filterTeam(Team team, String... nicknames) {
    return team.getPlayers()
      .stream()
      .map(Player::getNickname)
      .collect(Collectors.toList())
      .containsAll(Arrays.asList(nicknames));
  }
}
