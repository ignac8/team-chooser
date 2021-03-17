package it.zerko.teamchooser;

import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Team {

  private Set<Player> players;
  private double rating;

  public Team(Set<Player> players) {
    this.players = players;
    rating = players.stream()
      .mapToDouble(Player::getRating)
      .sum();

  }

  @Override
  public String toString() {
    return String.format("%s, %s",
      players.stream()
        .map(Object::toString)
        .collect(Collectors.joining(", ")),
      rating);
  }
}
