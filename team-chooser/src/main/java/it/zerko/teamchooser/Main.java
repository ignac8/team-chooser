package it.zerko.teamchooser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        generateTeams();
        generateMaps();
    }

    private static void generateTeams() throws Exception {
        List<Player> players = Files.lines(Paths.get("input.txt"))
                .map(line -> line.replace(",", "."))
                .map(Player::new)
                .collect(Collectors.toList());
        Teams bestTeams = Teams.generateTeams(players)
                .stream()
                .filter(Teams::isFairTeam)
                .sorted()
                .findFirst()
                .get();
        List<Player> firstTeam = bestTeams.getFirstTeam();
        List<Player> secondTeam = bestTeams.getSecondTeam();
        List<String> outputFileContent = Arrays.asList(
                String.join(", ",
                        Teams.getTeamPlayerNames(firstTeam),
                        Double.toString(Teams.getTeamStrength(firstTeam))),
                String.join(", ",
                        Teams.getTeamPlayerNames(secondTeam),
                        Double.toString(Teams.getTeamStrength(secondTeam))),
                String.join(" ",
                        "Diff:",
                        Double.toString(bestTeams.getStrengthDifference())));
        Files.write(Paths.get("output.txt"), outputFileContent,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static void generateMaps() throws Exception {
        List<String> maps = Files.lines(Paths.get("maps.txt"))
                .collect(Collectors.toList());
        Collections.shuffle(maps);
        Files.write(Paths.get("output.txt"), Collections.singletonList(String.join(" -> ", maps)),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
    }


}
