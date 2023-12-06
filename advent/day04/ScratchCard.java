package advent.day04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import advent.common.util.InputReader;

public class ScratchCard {
  
  private Set<Integer> targetNumbers = new HashSet<>();
  private List<Integer> playerNumbers = new ArrayList<>();

  public ScratchCard(String inputLine) {
    String[] tokens = inputLine.split(":|\\|");
    targetNumbers.addAll(Arrays.asList(tokens[1].split("\s")).stream().filter(num -> !num.isBlank()).map(Integer::parseInt).toList());
    playerNumbers.addAll(Arrays.asList(tokens[2].split("\s")).stream().filter(num -> !num.isBlank()).map(Integer::parseInt).toList());
  }

  public List<Integer> getMatches() {
    return playerNumbers.stream().filter(num -> targetNumbers.contains(num)).toList();
  }

  public long getNumMatches() {
    return getMatches().size();
  }

  public long getScore() {
    return (long)Math.pow(2, getNumMatches() - 1);
  }

  public static void main(String[] args) {
    List<String> lines = InputReader.readLinesFromInput("advent/day04/input.txt");
    List<ScratchCard> cards = lines.stream().map(ScratchCard::new).toList();
    
    //System.out.println(cards.stream().map(ScratchCard::getScore).reduce(0l, Long::sum));

    List<Long> numMatches = cards.stream().map(ScratchCard::getNumMatches).toList();
    List<Long> numCards = cards.stream().map(card -> 1l).collect(Collectors.toCollection(ArrayList::new));

    for (int i=0; i<cards.size(); i++) {
      for (int j=i+1; j<i+1+numMatches.get(i); j++) {
        numCards.set(j, numCards.get(j) + numCards.get(i));
      }
    }

    System.out.println(numCards.stream().reduce(0l, Long::sum));
  }
}
