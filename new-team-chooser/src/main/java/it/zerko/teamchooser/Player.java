package it.zerko.teamchooser;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@AllArgsConstructor
@Getter
public class Player {

  private String nickname;
  private double rating;

  @SuppressWarnings({"ComparatorMethodParameterNotUsed", "OptionalGetWithoutIsPresent"})
  public Player(String line) {
    List<String> splits = Arrays.asList(line.split("\\t+", -1));
    nickname = splits.get(1);
    rating = IntStream.range(10, splits.size())
      .mapToObj(splits::get)
      .map(split -> split.replace(",", "."))
      .filter(split -> !split.isEmpty())
      .sorted((split1, split2) -> -1)
      .limit(5)
      .mapToDouble(Double::valueOf)
      .average()
      .getAsDouble();
  }

  @Override
  public String toString() {
    return nickname;
  }
}
