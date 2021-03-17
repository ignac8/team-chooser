package it.zerko.teamchooser;

import lombok.Getter;
import lombok.ToString;

import static java.lang.Double.parseDouble;

@ToString(of = "name")
@Getter
public class Player {

    private String name;
    private double rating;

    public Player(String line) {
        String[] split = line.split("\t");
        name = split[0];
        rating = parseDouble(split[1]);
    }
}
