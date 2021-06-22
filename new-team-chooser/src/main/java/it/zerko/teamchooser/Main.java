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
      .filter(combination -> keepTogether(combination, "ignac8", "Koala", "Baleron"))
      .filter(combination -> keepTogether(combination, "Mr. Sandman", "yayebie"))
      .filter(combination -> !keepTogether(combination, "dzast.da.breslau", "Mr. Sandman"))
      .filter(combination -> combination.getFirstTeam().getPlayers().size() == teamSize)
      .sorted()
      .findFirst()
      .orElseThrow(NullPointerException::new);

    System.out.println(bestCombination);

    boolean debug = true;
  }

  private static boolean keepTogether(Combination combination, String... nicknames) {
    return isTogether(combination.getFirstTeam(), nicknames) || isTogether(combination.getSecondTeam(), nicknames);
  }

  private static boolean isTogether(Team team, String[] nicknames) {
    return team
      .getPlayers()
      .stream()
      .map(Player::getNickname)
      .collect(Collectors.toList())
      .containsAll(Arrays.asList(nicknames));
  }
}
