package advent.day07;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import advent.common.util.InputReader;

public class CamelCards {
  
  private static final Map<Character, Integer> CARD_RANKS;
  static {
    Map<Character, Integer> ranks = new HashMap<>();
    ranks.put('A', 14);
    ranks.put('K', 13);
    ranks.put('Q', 12);
    ranks.put('J', 11);
    ranks.put('T', 10);
    ranks.put('9', 9);
    ranks.put('8', 8);
    ranks.put('7', 7);
    ranks.put('6', 6);
    ranks.put('5', 5);
    ranks.put('4', 4);
    ranks.put('3', 3);
    ranks.put('2', 2);
    CARD_RANKS = Collections.unmodifiableMap(ranks);
  }

  private static class CamelCardHand implements Comparable {
    
    public static enum Hands {
      HIGH_CARD,
      ONE_PAIR,
      TWO_PAIR,
      THREE_OF_A_KIND,
      FULL_HOUSE,
      FOUR_OF_A_KIND,
      FIVE_OF_A_KIND
    };

    private String cards;

    private Hands hand;

    private long bid;
    
    public CamelCardHand(String input, String bid) {
      cards = input;
      hand = evaluate(input);
      this.bid = Long.parseLong(bid);
    }

    public static Hands evaluate(String input) {
      Map<Character, Integer> cardCountMap = new HashMap<>();
      for (char c : input.toCharArray()) {
        if(cardCountMap.get(c) == null) {
          cardCountMap.put(c, 1);
        } else {
          cardCountMap.put(c, cardCountMap.get(c) + 1);
        }
      }

      List<Integer> cardCounts = new LinkedList<>();
      cardCounts.addAll(cardCountMap.values());
      cardCounts.sort(Integer::compare);
      cardCounts = cardCounts.reversed();

      if (cardCounts.get(0) == 5) {
        return Hands.FIVE_OF_A_KIND;
      } else if (cardCounts.get(0) == 4) {
        return Hands.FOUR_OF_A_KIND;
      } else if (cardCounts.get(0) == 3 && cardCounts.get(1) == 2) {
        return Hands.FULL_HOUSE;
      } else if (cardCounts.get(0) == 3) {
        return Hands.THREE_OF_A_KIND;
      } else if (cardCounts.get(0) == 2 && cardCounts.get(1) == 2) {
        return Hands.TWO_PAIR;
      } else if (cardCounts.get(0) == 2) {
        return Hands.ONE_PAIR;
      }
      return Hands.HIGH_CARD;
    }

    public long getWinnings(long rank) {
      return bid * rank;
    } 

    @Override
    public int compareTo(Object o) {
      CamelCardHand other = (CamelCardHand)o;
      if (this.hand == other.hand) {
        for (int i=0; i<this.cards.length(); i++) {
          int myValue = CARD_RANKS.get(this.cards.charAt(i));
          int otherValue = CARD_RANKS.get(other.cards.charAt(i));
          if (myValue != otherValue) {
            return myValue - otherValue;
          }
        }
        return 0;
      } else {
        return this.hand.compareTo(other.hand);
      }
    } 
  }

  public static void main(String[] args) {
    List<String> lines = InputReader.readLinesFromInput("advent/day07/input.txt");
    List<CamelCardHand> hands = new LinkedList<>();
    for (String line : lines) {
      String[] tokens = line.split("\s+");
      hands.add(new CamelCardHand(tokens[0], tokens[1]));
    }

    hands.sort(CamelCardHand::compareTo);
    
    long totalWinnings = 0l;
    for (int i=0; i<hands.size(); i++) {
      totalWinnings += hands.get(i).getWinnings(i + 1);
    }
    System.out.println(totalWinnings);
  }
}
